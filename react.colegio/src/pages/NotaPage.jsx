import { useState } from 'react'; // Correção: Removido useEffect pois não estava sendo usado
import api from '../services/api';

const NotaPage = () => {
    const [notas, setNotas] = useState([]);
    const [form, setForm] = useState({ idAluno: '', idDisciplina: '', valor: '', semestre: 1 });

    const fetchNotas = async () => {
        try {
            const res = await api.get('/api/notas'); 
            setNotas(res.data);
        } catch(e) { 
            console.error(e); // Correção: Usando a variável 'e'
            console.log("Endpoint de listagem pode não existir ainda"); 
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post('/api/notas', form); 
            alert('Nota lançada!');
            fetchNotas();
        } catch (error) {
            console.error(error);
            alert(error.response?.data?.message || "Erro ao lançar nota");
        }
    };

    return (
        <div>
            <h2>Lançamento de Notas</h2>
            <form onSubmit={handleSubmit}>
                <input type="number" placeholder="ID Aluno" value={form.idAluno} onChange={e => setForm({...form, idAluno: e.target.value})} required />
                <input type="number" placeholder="ID Disciplina" value={form.idDisciplina} onChange={e => setForm({...form, idDisciplina: e.target.value})} required />
                <input type="number" step="0.1" placeholder="Valor (0-10)" value={form.valor} onChange={e => setForm({...form, valor: e.target.value})} required />
                <input type="number" placeholder="Semestre" value={form.semestre} onChange={e => setForm({...form, semestre: e.target.value})} required />
                <button type="submit">Lançar</button>
            </form>

            {/* Correção: Adicionada listagem para usar a variável 'notas' */}
            <hr />
            <h3>Notas Lançadas</h3>
            <ul>
                {notas.map((nota, index) => (
                    <li key={index}>
                        Aluno ID: {nota.idAluno} - Disciplina ID: {nota.idDisciplina} - Nota: {nota.valor}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default NotaPage;