import { useState, useEffect } from 'react';
import api from '../services/api';

const AlunoPage = () => {
    const [alunos, setAlunos] = useState([]);
    
    const [form, setForm] = useState({
        sala: '',
        turno: 'MANHA',
        nomeResponsavel: '', 
        dadosPessoais: {
            nome: '',
            cpf: '',
            email: '',
            matricula: '',
            dataNascimento: '',
            telefone: '',
            tipoUsuario: 'ALUNO',
            endereco: { 
                logradouro: '', 
                numero: '', 
                cep: '', 
                bairro: '', 
                cidade: '', 
                estado: '', 
                complemento: '' 
            }
        }
    });
    const [editingId, setEditingId] = useState(null);
    const [errorMsg, setErrorMsg] = useState('');

    const initialFormState = {
        sala: '',
        turno: 'MANHA',
        nomeResponsavel: '',
        dadosPessoais: {
            nome: '',
            cpf: '',
            email: '',
            matricula: '',
            dataNascimento: '',
            telefone: '',
            tipoUsuario: 'ALUNO',
            endereco: { 
                logradouro: '', 
                numero: '', 
                cep: '', 
                bairro: '', 
                cidade: '', 
                estado: '', 
                complemento: '' 
            }
        }
    };

    const getAlunos = async () => {
        try {
            const res = await api.get('/api/alunos');
            return res.data;
        } catch (error) {
            console.error(error);
            return [];
        }
    };

    useEffect(() => { 
        getAlunos().then(data => setAlunos(data)); 
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        
        if (name.includes('.')) {
            const parts = name.split('.');
            if (parts.length === 2) {
                const [parent, child] = parts;
                setForm(prev => ({
                    ...prev,
                    [parent]: { ...prev[parent], [child]: value }
                }));
            } else if (parts.length === 3) {
                const [parent, mid, child] = parts;
                setForm(prev => ({
                    ...prev,
                    [parent]: {
                        ...prev[parent],
                        [mid]: { ...prev[parent][mid], [child]: value }
                    }
                }));
            }
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
            
            const dados = await getAlunos();
            setAlunos(dados);
            
        } catch (error) {
            console.error(error);
            // Captura a mensagem de erro do backend (ex: "Bairro é obrigatório")
            const backendError = error.response?.data?.message || 'Erro ao salvar.';
            setErrorMsg(backendError);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm("Confirmar exclusão?")) return;
        try {
            await api.delete(`/api/alunos/${id}`);
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
            nomeResponsavel: aluno.nomeResponsavel || '',
            dadosPessoais: {
                ...aluno.usuario, 
                tipoUsuario: 'ALUNO',
                // Garante que o endereço não seja null ao editar
                endereco: aluno.usuario.endereco || initialFormState.dadosPessoais.endereco 
            }
        });
    };

    return (
        <div>
            <h2>Gestão de Alunos</h2>
            {errorMsg && <div style={{ color: 'red', marginBottom: '10px', padding: '10px', border: '1px solid red', borderRadius: '4px', backgroundColor: '#ffe6e6' }}>{errorMsg}</div>}
            
            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '10px', maxWidth: '600px' }}>
                <h4>Dados Pessoais</h4>
                <input name="dadosPessoais.nome" placeholder="Nome Completo" value={form.dadosPessoais.nome} onChange={handleChange} required />
                <input name="dadosPessoais.cpf" placeholder="CPF (apenas números)" value={form.dadosPessoais.cpf} onChange={handleChange} required />
                <input name="dadosPessoais.matricula" placeholder="Matrícula" value={form.dadosPessoais.matricula} onChange={handleChange} required />
                <input name="dadosPessoais.email" type="email" placeholder="Email" value={form.dadosPessoais.email} onChange={handleChange} required />
                <input type="date" name="dadosPessoais.dataNascimento" value={form.dadosPessoais.dataNascimento} onChange={handleChange} required />
                <input name="dadosPessoais.telefone" placeholder="Telefone" value={form.dadosPessoais.telefone} onChange={handleChange} />
                
                <h4>Endereço</h4>
                <div style={{display: 'flex', gap: '5px'}}>
                    <input name="dadosPessoais.endereco.cep" placeholder="CEP (00000-000)" value={form.dadosPessoais.endereco.cep} onChange={handleChange} required pattern="\d{5}-\d{3}" title="Formato: 12345-678"/>
                    <input name="dadosPessoais.endereco.logradouro" placeholder="Logradouro" value={form.dadosPessoais.endereco.logradouro} onChange={handleChange} required style={{flex: 2}}/>
                    <input name="dadosPessoais.endereco.numero" placeholder="Nº" value={form.dadosPessoais.endereco.numero} onChange={handleChange} required style={{width: '60px'}}/>
                </div>
                
                {/* CAMPOS QUE FALTAVAM */}
                <div style={{display: 'flex', gap: '5px'}}>
                    <input name="dadosPessoais.endereco.bairro" placeholder="Bairro" value={form.dadosPessoais.endereco.bairro} onChange={handleChange} required />
                    <input name="dadosPessoais.endereco.cidade" placeholder="Cidade" value={form.dadosPessoais.endereco.cidade} onChange={handleChange} required />
                    <input name="dadosPessoais.endereco.estado" placeholder="UF" value={form.dadosPessoais.endereco.estado} onChange={handleChange} required maxLength="2" style={{width: '50px'}}/>
                </div>
                <input name="dadosPessoais.endereco.complemento" placeholder="Complemento (opcional)" value={form.dadosPessoais.endereco.complemento} onChange={handleChange} />

                <h4>Dados Escolares</h4>
                <input name="nomeResponsavel" placeholder="Nome do Responsável" value={form.nomeResponsavel} onChange={handleChange} required />
                
                <div style={{display: 'flex', gap: '10px'}}>
                    <input name="sala" placeholder="Sala (ex: 1A)" value={form.sala} onChange={handleChange} required />
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
                        <strong>{aluno.usuario?.nome}</strong> (Sala: {aluno.sala} - {aluno.turno})
                        <br />
                        <small>Matrícula: {aluno.usuario?.matricula} | CPF: {aluno.usuario?.cpf}</small>
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