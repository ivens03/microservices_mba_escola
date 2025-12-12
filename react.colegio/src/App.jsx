import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import AlunoPage from './pages/AlunoPage';
import ResponsavelPage from './pages/ResponsavelPage';
import ProfessorPage from './pages/ProfessorPage';
import DisciplinaPage from './pages/DisciplinaPage';
import NotaPage from './pages/NotaPage';
import './App.css';

function App() {
  return (
    <Router>
      <nav style={{ padding: '1rem', borderBottom: '1px solid #ccc' }}>
        <Link to="/alunos" style={{ marginRight: '10px' }}>Alunos</Link>
        <Link to="/responsaveis" style={{ marginRight: '10px' }}>Responsáveis</Link>
        <Link to="/professores" style={{ marginRight: '10px' }}>Professores</Link>
        <Link to="/disciplinas" style={{ marginRight: '10px' }}>Disciplinas</Link>
        <Link to="/notas">Notas</Link>
      </nav>

      <div style={{ padding: '2rem' }}>
        <Routes>
          <Route path="/alunos" element={<AlunoPage />} />
          <Route path="/responsaveis" element={<ResponsavelPage />} />
          <Route path="/professores" element={<ProfessorPage />} />
          <Route path="/disciplinas" element={<DisciplinaPage />} />
          <Route path="/notas" element={<NotaPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;