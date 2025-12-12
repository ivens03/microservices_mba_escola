import { useState, useEffect } from 'react';
import api from '../services/api';

const ResponsavelPage = () => {
    const [responsaveis, setResponsaveis] = useState([]);
    const [form, setForm] = useState({
        dadosPessoais: {
            nome: '', cpf: '', email: '', matricula: '', dataNascimento: '', telefone: '',
            tipoUsuario: 'RESPONSAVEIS_DE_ALUNO',
            endereco: { logradouro: '', numero: '', cep: '' }
        },
        historicoAluno: { observacoes: '' },
        pagamento: { metodo: 'PIX' }
    });
    const [editingId, setEditingId] = useState(null);
    const [errorMsg, setErrorMsg] = useState('');

    const initialFormState = {
        dadosPessoais: {
            nome: '', cpf: '', email: '', matricula: '', dataNascimento: '', telefone: '',
            tipoUsuario: 'RESPONSAVEIS_DE_ALUNO',
            endereco: { logradouro: '', numero: '', cep: '' }
        },
        historicoAluno: { observacoes: '' },
        pagamento: { metodo: 'PIX' }
    };

    // Função auxiliar pura para buscar dados
    const getResponsaveis = async () => {
        try {
            const res = await api.get('/responsaveis');
            return res.data;
        } catch (error) {
            console.error(error);
            return [];
        }
    };

    // useEffect gerencia a chamada e atualização inicial
    useEffect(() => {
        getResponsaveis().then(data => setResponsaveis(data));
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        const keys = name.split('.');
        
        if (keys.length === 2) {
            setForm(prev => ({
                ...prev,
                [keys[0]]: { ...prev[keys[0]], [keys[1]]: value }
            }));
        } else if (keys.length === 3) {
            setForm(prev => ({
                ...prev,
                [keys[0]]: {
                    ...prev[keys[0]],
                    [keys[1]]: { ...prev[keys[0]][keys[1]], [keys[2]]: value }
                }
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
                await api.patch(`/responsaveis/${editingId}`, form);
            } else {
                await api.post('/responsaveis', form);
            }
            setForm(initialFormState);
            setEditingId(null);
            
            const dados = await getResponsaveis();
            setResponsaveis(dados);
            
        } catch (error) {
            console.error(error);
            setErrorMsg(error.response?.data?.message || 'Erro ao salvar.');
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm("Confirmar inativação?")) return;
        try {
            await api.delete(`/responsaveis/${id}`);
            const dados = await getResponsaveis();
            setResponsaveis(dados);
        } catch (error) {
            console.error(error);
            setErrorMsg('Erro ao deletar.');
        }
    };

    const handleEdit = (responsavel) => {
        setEditingId(responsavel.idResponsavel);
        setForm({
            dadosPessoais: {
                ...responsavel.usuario,
                tipoUsuario: 'RESPONSAVEIS_DE_ALUNO'
            },
            historicoAluno: responsavel.historicoAluno || { observacoes: '' },
            pagamento: responsavel.pagamento || { metodo: 'PIX' }
        });
    };

    return (
        <div>
            <h2>Gestão de Responsáveis</h2>
            {errorMsg && <p style={{ color: 'red' }}>{errorMsg}</p>}
            
            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '10px', maxWidth: '600px' }}>
                <h4>Dados Pessoais</h4>
                <input name="dadosPessoais.nome" placeholder="Nome" value={form.dadosPessoais.nome} onChange={handleChange} required />
                <input name="dadosPessoais.cpf" placeholder="CPF" value={form.dadosPessoais.cpf} onChange={handleChange} required />
                <input name="dadosPessoais.matricula" placeholder="Matrícula" value={form.dadosPessoais.matricula} onChange={handleChange} required />
                <input name="dadosPessoais.email" placeholder="Email" value={form.dadosPessoais.email} onChange={handleChange} required />
                <input type="date" name="dadosPessoais.dataNascimento" value={form.dadosPessoais.dataNascimento} onChange={handleChange} required />
                
                <h4>Endereço</h4>
                <div style={{ display: 'flex', gap: '5px' }}>
                    <input name="dadosPessoais.endereco.logradouro" placeholder="Rua" value={form.dadosPessoais.endereco.logradouro} onChange={handleChange} required />
                    <input name="dadosPessoais.endereco.numero" placeholder="Nº" value={form.dadosPessoais.endereco.numero} onChange={handleChange} required />
                    <input name="dadosPessoais.endereco.cep" placeholder="CEP" value={form.dadosPessoais.endereco.cep} onChange={handleChange} required />
                </div>

                <h4>Outros</h4>
                <input name="historicoAluno.observacoes" placeholder="Observações" value={form.historicoAluno.observacoes} onChange={handleChange} />
                <select name="pagamento.metodo" value={form.pagamento.metodo} onChange={handleChange}>
                    <option value="PIX">Pix</option>
                    <option value="BOLETO">Boleto</option>
                    <option value="CARTAO">Cartão</option>
                </select>

                <button type="submit">{editingId ? 'Atualizar' : 'Cadastrar'}</button>
            </form>

            <hr />
            <ul>
                {responsaveis.map(resp => (
                    <li key={resp.idResponsavel} style={{ marginBottom: '10px', border: '1px solid #ccc', padding: '10px' }}>
                        <strong>{resp.usuario?.nome || resp.nome}</strong> - CPF: {resp.usuario?.cpf || resp.cpf}
                        <div style={{ marginTop: '5px' }}>
                            <button onClick={() => handleEdit(resp)} style={{ marginRight: '5px' }}>Editar</button>
                            <button onClick={() => handleDelete(resp.idResponsavel)} style={{ background: '#ffcccc' }}>Excluir</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ResponsavelPage;