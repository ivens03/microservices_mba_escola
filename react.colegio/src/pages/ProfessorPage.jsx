import { useState, useEffect } from 'react';
import api from '../services/api';

const ProfessorPage = () => {
    const [professores, setProfessores] = useState([]);
    const [form, setForm] = useState({ nome: '', cpf: '', especialidade: '', formacao: '' });

    // Função auxiliar apenas para BUSCAR os dados (não altera estado diretamente)
    const getProfessores = async () => {
        try {
            const res = await api.get('/api/professores');
            return res.data;
        } catch (error) {
            console.error(error);
            return [];
        }
    };

    // useEffect chama a busca e atualiza o estado
    useEffect(() => {
        getProfessores().then(data => setProfessores(data));
    }, []); 

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post('/api/professores', form);
            setForm({ nome: '', cpf: '', especialidade: '', formacao: '' });
            
            // Recarrega a lista chamando a função auxiliar e atualizando o estado
            const dadosAtualizados = await getProfessores();
            setProfessores(dadosAtualizados);
            
        } catch (error) {
            console.error(error);
            alert(error.response?.data?.message || 'Erro ao salvar');
        }
    };

    return (
        <div>
            <h2>Professores</h2>
            <form onSubmit={handleSubmit}>
                <input name="nome" placeholder="Nome" value={form.nome} onChange={handleChange} required />
                <input name="cpf" placeholder="CPF" value={form.cpf} onChange={handleChange} required />
                <input name="especialidade" placeholder="Especialidade" value={form.especialidade} onChange={handleChange} required />
                <input name="formacao" placeholder="Formação" value={form.formacao} onChange={handleChange} />
                <button type="submit">Salvar</button>
            </form>
            <ul>
                {professores.map(p => (
                    <li key={p.idProfessor}>{p.nome} - {p.especialidade}</li>
                ))}
            </ul>
        </div>
    );
};

export default ProfessorPage;