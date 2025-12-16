package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.globalHandler.exeption.AlunoNotFoundException;
import br.com.mba.spring.colegio.globalHandler.exeption.DuplicateCpfException;
import br.com.mba.spring.colegio.globalHandler.exeption.ResponsavelAlunoNotFoundException;
import br.com.mba.spring.colegio.usuarios.dto.ResponsavelAlunoDTO;
import br.com.mba.spring.colegio.usuarios.enums.TipoUsuario;
import br.com.mba.spring.colegio.usuarios.mapper.ResponsavelAlunoMapper;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.model.ResponsavelAluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.AlunoRepository;
import br.com.mba.spring.colegio.usuarios.repository.ResponsavelAlunoRepository;
import br.com.mba.spring.colegio.usuarios.service.impl.ResponsavelAlunoServiceImpl;
import br.com.mba.spring.colegio.usuarios.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ResponsavelAlunoService implements ResponsavelAlunoServiceImpl {

    private final ResponsavelAlunoRepository repository;
    private final UsuarioServiceImpl usuarioService;
    private final ResponsavelAlunoMapper mapper;
    private final AlunoRepository alunoRepository;

    @Override
    @Transactional
    public ResponsavelAluno createResponsavel(ResponsavelAlunoDTO dto) {
        if (repository.existsByUsuario_Cpf(dto.getDadosPessoais().getCpf())) {
            throw new DuplicateCpfException("Responsável já cadastrado com este CPF.");
        }

        dto.getDadosPessoais().setTipoUsuario(TipoUsuario.RESPONSAVEIS_DE_ALUNO);

        Usuario usuarioSalvo = usuarioService.createUsuario(dto.getDadosPessoais());

        ResponsavelAluno entity = mapper.toEntity(dto);
        entity.setUsuario(usuarioSalvo);

        return repository.save(entity);
    }

    @Override
    @Transactional
    public ResponsavelAluno updateResponsavel(Long id, ResponsavelAlunoDTO dto) {
        ResponsavelAluno entity = findResponsavelById(id);

        if(dto.getDadosPessoais() != null) {
            dto.getDadosPessoais().setTipoUsuario(TipoUsuario.RESPONSAVEIS_DE_ALUNO);
        }

        mapper.updateEntityFromDto(dto, entity);
        return repository.save(entity);
    }

    @Override
    public List<ResponsavelAluno> findAllResponsaveis() {
        return repository.findAll();
    }

    @Override
    public ResponsavelAluno findResponsavelById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponsavelAlunoNotFoundException("Responsável não encontrado com ID: " + id));
    }

    @Override
    @Transactional
    public void deleteResponsavel(Long id) {
        ResponsavelAluno entity = findResponsavelById(id);
        usuarioService.deleteUsuario(entity.getUsuario().getIdUsuario());
    }

    @Override
    @Transactional
    public void addAlunoToResponsavel(Long idResponsavel, Long idAluno) {
        ResponsavelAluno responsavel = findResponsavelById(idResponsavel);
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com ID: " + idAluno));

        responsavel.getAlunos().add(aluno);
        repository.save(responsavel);
    }

    @Override
    @Transactional
    public void removeAlunoFromResponsavel(Long idResponsavel, Long idAluno) {
        ResponsavelAluno responsavel = findResponsavelById(idResponsavel);
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com ID: " + idAluno));

        responsavel.getAlunos().remove(aluno);
        repository.save(responsavel);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Aluno> findAlunosByResponsavel(Long idResponsavel) {
        ResponsavelAluno responsavel = findResponsavelById(idResponsavel);
        responsavel.getAlunos().size();
        return responsavel.getAlunos();
    }
}
