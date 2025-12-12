package br.com.mba.spring.colegio.disciplinas.service;

import br.com.mba.spring.colegio.disciplinas.dto.NotaDTO;
import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import br.com.mba.spring.colegio.disciplinas.model.Nota;
import br.com.mba.spring.colegio.disciplinas.repository.NotaRepository;
import br.com.mba.spring.colegio.disciplinas.service.impl.DisciplinaServiceImpl;
import br.com.mba.spring.colegio.disciplinas.service.impl.NotaServiceImpl;
import br.com.mba.spring.colegio.globalHandler.exeption.DuplicateResourceException;
import br.com.mba.spring.colegio.globalHandler.exeption.NotaNotFoundException;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.service.impl.AlunoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotaService implements NotaServiceImpl {

    private final NotaRepository notaRepository;
    private final AlunoServiceImpl alunoService;         // Serviço para buscar/validar Aluno
    private final DisciplinaServiceImpl disciplinaService; // Serviço para buscar/validar Disciplina

    @Override
    @Transactional
    public Nota lancharNota(NotaDTO dto) {
        // 1. Busca e valida se Aluno e Disciplina existem (lançam 404 se não existirem)
        Aluno aluno = alunoService.findAlunoById(dto.getIdAluno());
        Disciplina disciplina = disciplinaService.findDisciplinaById(dto.getIdDisciplina());

        // 2. Valida duplicidade: Um aluno não pode ter duas notas para a mesma matéria no mesmo semestre
        if (notaRepository.existsByAlunoAndDisciplinaAndSemestre(aluno, disciplina, dto.getSemestre())) {
            throw new DuplicateResourceException(
                    String.format("Já existe uma nota lançada para o aluno %s na disciplina %s no semestre %d.",
                            aluno.getUsuario().getNome(), disciplina.getNome(), dto.getSemestre())
            );
        }

        // 3. Monta a entidade
        Nota novaNota = Nota.builder()
                .aluno(aluno)
                .disciplina(disciplina)
                .valor(dto.getValor())
                .semestre(dto.getSemestre())
                .build();

        return notaRepository.save(novaNota);
    }

    @Override
    @Transactional
    public Nota atualizarNota(Long id, NotaDTO dto) {
        Nota nota = buscarNotaPorId(id);

        // Atualiza apenas o valor, pois mudar aluno/disciplina seria um novo lançamento
        if (dto.getValor() != null) {
            nota.setValor(dto.getValor());
        }

        // Se permitir alterar semestre, validar duplicidade novamente aqui

        return notaRepository.save(nota);
    }

    @Override
    public List<Nota> listarTodasNotas() {
        return notaRepository.findAll();
    }

    @Override
    public Nota buscarNotaPorId(Long id) {
        return notaRepository.findById(id)
                .orElseThrow(() -> new NotaNotFoundException("Nota não encontrada com ID: " + id));
    }

    @Override
    public List<Nota> buscarNotasPorAluno(Long idAluno) {
        // Garante que o aluno existe antes de buscar
        alunoService.findAlunoById(idAluno);
        return notaRepository.findByAluno_IdAluno(idAluno);
    }

    @Override
    @Transactional
    public void excluirNota(Long id) {
        if (!notaRepository.existsById(id)) {
            throw new NotaNotFoundException("Nota não encontrada para exclusão.");
        }
        notaRepository.deleteById(id);
    }
}
