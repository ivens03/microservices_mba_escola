import { useState, useEffect } from 'react';
import api from '../services/api';

const MATERIAS = [
    { value: 'JI_EU_OUTRO_NOS', label: 'EI: O eu, o outro e o nós' },
    { value: 'JI_CORPO_GESTOS_MOVIMENTOS', label: 'EI: Corpo, gestos e movimentos' },
    { value: 'EF_PORTUGUES', label: 'Fund: Língua Portuguesa' },
    { value: 'EF_MATEMATICA', label: 'Fund: Matemática' },
    { value: 'EF_HISTORIA', label: 'Fund: História' },
    { value: 'EF_GEOGRAFIA', label: 'Fund: Geografia' },
    { value: 'EF_CIENCIAS', label: 'Fund: Ciências' },
    { value: 'EM_MATEMATICA', label: 'Médio: Matemática' },
    { value: 'EM_FISICA', label: 'Médio: Física' },
    { value: 'EM_QUIMICA', label: 'Médio: Química' },
    { value: 'EM_BIOLOGIA', label: 'Médio: Biologia' },
    { value: 'EM_LINGUA_PORTUGUESA', label: 'Médio: Português' }
];

// Sub-componente para gerenciar o estado de seleção de aluno em cada card
const DisciplinaCard = ({ disciplina, alunosDisponiveis, onDelete, onMatricular, onDesmatricular }) => {
    const [selectedAluno, setSelectedAluno] = useState('');

    const handleAdd = () => {
        if (!selectedAluno) return;
        onMatricular(disciplina.idDisciplina, selectedAluno);
        setSelectedAluno(''); // Limpa a seleção após adicionar
    };

    return (
        <div style={{ border: '1px solid #ccc', padding: '15px', borderRadius: '8px', background: '#fff' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h3 style={{ margin: 0 }}>{disciplina.materia} ({disciplina.cargaHoraria}h)</h3>
                <button onClick={() => onDelete(disciplina.idDisciplina)} style={{ background: '#ff4444', padding: '5px 10px', fontSize: '0.8rem', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>Excluir</button>
            </div>
            <p style={{ color: '#666', margin: '5px 0' }}>Prof. {disciplina.professor?.nome || 'Sem professor'}</p>
            
            <div style={{ marginTop: '10px', background: '#f9f9f9', padding: '10px', borderRadius: '5px' }}>
                <strong>Alunos Matriculados:</strong>
                <ul style={{ margin: '5px 0', paddingLeft: '20px' }}>
                    {disciplina.alunos && disciplina.alunos.length > 0 ? (
                        disciplina.alunos.map(a => (
                            <li key={a.idAluno}>
                                {a.usuario?.nome} 
                                <button 
                                    onClick={() => onDesmatricular(disciplina.idDisciplina, a.idAluno)} 
                                    style={{ marginLeft: '10px', color: 'red', border: 'none', background: 'none', cursor: 'pointer', fontWeight: 'bold' }}
                                >
                                    (x)
                                </button>
                            </li>
                        ))
                    ) : <li style={{ color: '#999' }}>Nenhum aluno matriculado</li>}
                </ul>

                <div style={{ display: 'flex', gap: '5px', marginTop: '10px' }}>
                    <select 
                        value={selectedAluno} 
                        onChange={(e) => setSelectedAluno(e.target.value)} 
                        style={{ flex: 1, padding: '5px' }}
                    >
                        <option value="">Selecione para matricular...</option>
                        {alunosDisponiveis.map(a => (
                            <option key={a.idAluno} value={a.idAluno}>{a.usuario?.nome}</option>
                        ))}
                    </select>
                    <button type="button" onClick={handleAdd} style={{ background: '#28a745', color: 'white', border: 'none', padding: '5px 10px', borderRadius: '4px', cursor: 'pointer' }}>Add</button>
                </div>
            </div>
        </div>
    );
};

const DisciplinaPage = () => {
    const [disciplinas, setDisciplinas] = useState([]);
    const [professores, setProfessores] = useState([]);
    const [alunos, setAlunos] = useState([]);
    
    const [form, setForm] = useState({ materia: 'EF_MATEMATICA', cargaHoraria: '', idProfessor: '' });
    const [errorMsg, setErrorMsg] = useState('');

    const loadData = async () => {
        try {
            const [discRes, profRes, alunRes] = await Promise.all([
                api.get('/api/disciplinas'),
                api.get('/api/professores'),
                api.get('/api/alunos')
            ]);
            setDisciplinas(discRes.data);
            setProfessores(profRes.data);
            setAlunos(alunRes.data);
        } catch (error) {
            console.error(error);
            setErrorMsg("Erro ao carregar dados.");
        }
    };

    // CORREÇÃO: Removemos a dependência [loadData] e usamos [] para rodar apenas na montagem.
    // Isso evita o erro de "cascading renders" reportado.
    useEffect(() => {
        loadData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMsg('');
        try {
            await api.post('/api/disciplinas', form);
            setForm({ materia: 'EF_MATEMATICA', cargaHoraria: '', idProfessor: '' });
            loadData();
        } catch (error) {
            console.error(error);
            setErrorMsg(error.response?.data?.message || 'Erro ao criar disciplina');
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm("Excluir disciplina?")) return;
        try {
            await api.delete(`/api/disciplinas/${id}`);
            loadData();
        } catch (error) {
            console.error(error);
        }
    };

    const handleMatricular = async (idDisciplina, idAluno) => {
        try {
            await api.post(`/api/disciplinas/${idDisciplina}/matricula/${idAluno}`);
            loadData();
        } catch (error) {
            alert("Erro ao matricular: " + (error.response?.data?.message || error.message));
        }
    };

    const handleDesmatricular = async (idDisciplina, idAluno) => {
        if(!window.confirm("Remover aluno da disciplina?")) return;
        try {
            await api.delete(`/api/disciplinas/${idDisciplina}/matricula/${idAluno}`);
            loadData();
        } catch (error) {
            alert("Erro ao remover: " + error.message);
        }
    };

    return (
        <div>
            <h2>Disciplinas e Turmas</h2>
            {errorMsg && <p style={{ color: 'red', border: '1px solid red', padding: '10px', borderRadius: '4px' }}>{errorMsg}</p>}
            
            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '10px', maxWidth: '600px', marginBottom: '20px' }}>
                <label>Matéria:</label>
                <select name="materia" value={form.materia} onChange={handleChange} required style={{ padding: '8px' }}>
                    {MATERIAS.map(m => <option key={m.value} value={m.value}>{m.label}</option>)}
                </select>

                <div style={{ display: 'flex', gap: '10px' }}>
                    <input type="number" name="cargaHoraria" placeholder="Carga Horária (h)" value={form.cargaHoraria} onChange={handleChange} required style={{ padding: '8px' }} />
                    
                    <select name="idProfessor" value={form.idProfessor} onChange={handleChange} required style={{ flex: 1, padding: '8px' }}>
                        <option value="">Selecione o Professor...</option>
                        {professores.map(p => (
                            <option key={p.idProfessor} value={p.idProfessor}>{p.nome} ({p.especialidade})</option>
                        ))}
                    </select>
                </div>
                
                <button type="submit" style={{ padding: '10px', background: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>Criar Turma/Disciplina</button>
            </form>

            <hr />

            <div style={{ display: 'grid', gap: '15px' }}>
                {disciplinas.map(d => (
                    <DisciplinaCard 
                        key={d.idDisciplina} 
                        disciplina={d} 
                        alunosDisponiveis={alunos}
                        onDelete={handleDelete}
                        onMatricular={handleMatricular}
                        onDesmatricular={handleDesmatricular}
                    />
                ))}
            </div>
        </div>
    );
};

export default DisciplinaPage;