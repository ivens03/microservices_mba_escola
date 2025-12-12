package br.com.mba.spring.colegio.funcionarios.service;

import br.com.mba.spring.colegio.funcionarios.dto.ProfessorDTO;
import br.com.mba.spring.colegio.funcionarios.mapper.ProfessorMapper;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.funcionarios.repository.ProfessorRepository;
import br.com.mba.spring.colegio.funcionarios.service.impl.ProfessorServiceImpl;
import br.com.mba.spring.colegio.globalHandler.exeption.DuplicateResourceException;
import br.com.mba.spring.colegio.globalHandler.exeption.ProfessorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService implements ProfessorServiceImpl {

    private final ProfessorRepository repository;
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
}
