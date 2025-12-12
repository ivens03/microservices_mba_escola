import { useState, useEffect } from 'react';
import api from '../services/api';

const DisciplinaPage = () => {
    const [disciplinas, setDisciplinas] = useState([]);
    
    // Helper puro
    const getDisciplinas = async () => {
        try {
            const res = await api.get('/api/disciplinas');
            return res.data;
        } catch (error) {
            console.error(error);
            return [];
        }
    };

    useEffect(() => {
        getDisciplinas().then(data => setDisciplinas(data));
    }, []);

    return (
        <div>
            <h2>Disciplinas</h2>
            <ul>
                {disciplinas.map(d => <li key={d.idDisciplina}>{d.nome}</li>)}
            </ul>
        </div>
    );
};

export default DisciplinaPage;