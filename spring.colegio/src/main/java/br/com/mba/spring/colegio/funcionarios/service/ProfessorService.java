package br.com.mba.spring.colegio.funcionarios.service;

import br.com.mba.spring.colegio.funcionarios.dto.ProfessorDTO;
import br.com.mba.spring.colegio.funcionarios.mapper.ProfessorMapper;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.funcionarios.repository.ProfessorRepository;
import br.com.mba.spring.colegio.funcionarios.service.impl.ProfessorServiceImpl;
import br.com.mba.spring.colegio.globalHandler.exeption.AlunoNotFoundException;
import br.com.mba.spring.colegio.globalHandler.exeption.DuplicateResourceException;
import br.com.mba.spring.colegio.globalHandler.exeption.ProfessorNotFoundException;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfessorService implements ProfessorServiceImpl {

    private final ProfessorRepository repository;
    private final AlunoRepository alunoRepository;
    private final ProfessorMapper mapper;

    @Override
    @Transactional
    public Professor createProfessor(ProfessorDTO dto) {
        if (repository.existsByCpf(dto.getCpf())) {
            throw new DuplicateResourceException("Professor já cadastrado com este CPF.");
        }
        return repository.save(mapper.toEntity(dto));
    }

    @Override
    @Transactional
    public Professor updateProfessor(Long id, ProfessorDTO dto) {
        Professor entity = findProfessorById(id);
        mapper.updateEntityFromDto(dto, entity);
        return repository.save(entity);
    }

    @Override
    public List<Professor> findAllProfessores() {
        return repository.findAll();
    }

    @Override
    public Professor findProfessorById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProfessorNotFoundException("Professor não encontrado com ID: " + id));
    }

    @Override
    @Transactional
    public void deleteProfessor(Long id) {
        if (!repository.existsById(id)) {
            throw new ProfessorNotFoundException("Professor não encontrado para exclusão.");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void addAlunoToProfessor(Long idProfessor, Long idAluno) {
        Professor professor = findProfessorById(idProfessor);
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com ID: " + idAluno));
        professor.getAlunos().add(aluno);
        repository.save(professor);
    }

    @Override
    @Transactional
    public void removeAlunoFromProfessor(Long idProfessor, Long idAluno) {
        Professor professor = findProfessorById(idProfessor);
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com ID: " + idAluno));

        professor.getAlunos().remove(aluno);
        repository.save(professor);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Aluno> findAlunosByProfessor(Long idProfessor) {
        Professor professor = findProfessorById(idProfessor);
        professor.getAlunos().size();
        return professor.getAlunos();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Professor> findProfessoresByAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com ID: " + idAluno));

        aluno.getProfessores().size();
        return aluno.getProfessores();
    }
}
