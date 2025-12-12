package br.com.mba.spring.colegio.disciplinas.service;

import br.com.mba.spring.colegio.disciplinas.dto.DisciplinaDTO;
import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import br.com.mba.spring.colegio.disciplinas.repository.DisciplinaRepository;
import br.com.mba.spring.colegio.disciplinas.service.impl.DisciplinaServiceImpl;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.funcionarios.service.impl.ProfessorServiceImpl;
import br.com.mba.spring.colegio.globalHandler.exeption.DisciplinaNotFoundException;
import br.com.mba.spring.colegio.globalHandler.exeption.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisciplinaService implements DisciplinaServiceImpl {

    private final DisciplinaRepository repository;
    private final ProfessorServiceImpl professorService;

    @Override
    @Transactional
    public Disciplina createDisciplina(DisciplinaDTO dto) {
        if (repository.existsByNome(dto.getNome())) {
            throw new DuplicateResourceException("Disciplina já cadastrada com este nome.");
        }
        Professor professor = professorService.findProfessorById(dto.getIdProfessor());

        Disciplina disciplina = Disciplina.builder()
                .nome(dto.getNome())
                .cargaHoraria(dto.getCargaHoraria())
                .professor(professor)
                .build();

        return repository.save(disciplina);
    }

    @Override
    @Transactional
    public Disciplina updateDisciplina(Long id, DisciplinaDTO dto) {
        Disciplina entity = findDisciplinaById(id);

        if (StringUtils.hasText(dto.getNome())) entity.setNome(dto.getNome());
        if (dto.getCargaHoraria() != null) entity.setCargaHoraria(dto.getCargaHoraria());
        if (dto.getIdProfessor() != null) {
            Professor novoProf = professorService.findProfessorById(dto.getIdProfessor());
            entity.setProfessor(novoProf);
        }
        return repository.save(entity);
    }

    @Override
    public List<Disciplina> findAllDisciplinas() {
        return repository.findAll();
    }

    @Override
    public Disciplina findDisciplinaById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DisciplinaNotFoundException("Disciplina não encontrada com ID: " + id));
    }

    @Override
    @Transactional
    public void deleteDisciplina(Long id) {
        if (!repository.existsById(id)) throw new DisciplinaNotFoundException("Disciplina não encontrada.");
        repository.deleteById(id);
    }
}
