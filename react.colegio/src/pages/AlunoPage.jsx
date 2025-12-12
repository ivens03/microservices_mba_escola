import { useState, useEffect } from 'react';
import api from '../services/api';

const AlunoPage = () => {
    const [alunos, setAlunos] = useState([]);
    const [form, setForm] = useState({
        sala: '',
        turno: 'MANHA',
        usuario: {
            nome: '',
            cpf: '',
            email: '',
            matricula: '',
            dataNascimento: '',
            telefone: '',
            tipoUsuario: 'ALUNO',
            endereco: { logradouro: '', numero: '', cep: '' }
        }
    });
    const [editingId, setEditingId] = useState(null);
    const [errorMsg, setErrorMsg] = useState('');

    const initialFormState = {
        sala: '', turno: 'MANHA',
        usuario: { nome: '', cpf: '', email: '', matricula: '', dataNascimento: '', telefone: '', tipoUsuario: 'ALUNO', endereco: { logradouro: '', numero: '', cep: '' } }
    };

    // CORREÇÃO: Função auxiliar PURA (não chama setAlunos nem setErrorMsg internamente).
    // Apenas retorna os dados ou lança o erro para quem chamou tratar.
    const getAlunos = async () => {
        const res = await api.get('/api/alunos');
        return res.data;
    };

    // CORREÇÃO: O useEffect gerencia a chamada e o estado explicitamente.
    useEffect(() => { 
        getAlunos()
            .then(data => setAlunos(data))
            .catch(error => {
                console.error(error);
                setErrorMsg('Erro ao buscar alunos.');
            });
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name.includes('.')) {
            const [parent, child] = name.split('.');
            setForm(prev => ({
                ...prev,
                [parent]: { ...prev[parent], [child]: value }
            }));
        } else {
            setForm(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMsg('');
        try {
            if (editingId) {
                await api.patch(`/api/alunos/${editingId}`, form);
            } else {
                await api.post('/api/alunos', form);
            }
            setForm(initialFormState);
            setEditingId(null);
            
            // Reutiliza o helper e atualiza o estado aqui
            const dados = await getAlunos();
            setAlunos(dados);
            
        } catch (error) {
            console.error(error);
            const backendError = error.response?.data?.message || 'Erro ao salvar.';
            setErrorMsg(backendError);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm("Confirmar exclusão?")) return;
        try {
            await api.delete(`/api/alunos/${id}`);
            
            // Reutiliza o helper e atualiza o estado aqui
            const dados = await getAlunos();
            setAlunos(dados);
            
        } catch (error) {
            console.error(error);
            setErrorMsg('Erro ao excluir.');
        }
    };

    const handleEdit = (aluno) => {
        setEditingId(aluno.idAluno);
        setForm({
            sala: aluno.sala,
            turno: aluno.turno,
            usuario: aluno.usuario
        });
    };

    return (
        <div>
            <h2>Gestão de Alunos</h2>
            {errorMsg && <div style={{ color: 'red', marginBottom: '10px' }}>{errorMsg}</div>}
            
            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '10px', maxWidth: '500px' }}>
                <input name="usuario.nome" placeholder="Nome" value={form.usuario.nome} onChange={handleChange} required />
                <input name="usuario.cpf" placeholder="CPF" value={form.usuario.cpf} onChange={handleChange} required />
                <input name="usuario.matricula" placeholder="Matrícula" value={form.usuario.matricula} onChange={handleChange} required />
                <input name="usuario.email" placeholder="Email" value={form.usuario.email} onChange={handleChange} required />
                <input type="date" name="usuario.dataNascimento" value={form.usuario.dataNascimento} onChange={handleChange} required />
                <input name="usuario.telefone" placeholder="Telefone" value={form.usuario.telefone} onChange={handleChange} />
                
                <div style={{display: 'flex', gap: '10px'}}>
                    <input name="sala" placeholder="Sala" value={form.sala} onChange={handleChange} required />
                    <select name="turno" value={form.turno} onChange={handleChange}>
                        <option value="MANHA">Manhã</option>
                        <option value="TARDE">Tarde</option>
                        <option value="NOITE">Noite</option>
                    </select>
                </div>

                <button type="submit">{editingId ? 'Atualizar' : 'Cadastrar'}</button>
                {editingId && <button type="button" onClick={() => { setEditingId(null); setForm(initialFormState); }}>Cancelar</button>}
            </form>

            <hr />

            <ul>
                {alunos.map(aluno => (
                    <li key={aluno.idAluno} style={{ marginBottom: '10px', border: '1px solid #ddd', padding: '10px' }}>
                        <strong>{aluno.usuario.nome}</strong> (Sala: {aluno.sala})
                        <br />
                        <small>Matrícula: {aluno.usuario.matricula} | CPF: {aluno.usuario.cpf}</small>
                        <div style={{ marginTop: '5px' }}>
                            <button onClick={() => handleEdit(aluno)} style={{ marginRight: '5px' }}>Editar</button>
                            <button onClick={() => handleDelete(aluno.idAluno)} style={{ backgroundColor: '#ffcccc' }}>Excluir</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AlunoPage;