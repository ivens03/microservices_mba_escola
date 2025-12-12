package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.globalHandler.exeption.DuplicateCpfException;
import br.com.mba.spring.colegio.globalHandler.exeption.ResponsavelAlunoNotFoundException;
import br.com.mba.spring.colegio.usuarios.dto.ResponsavelAlunoDTO;
import br.com.mba.spring.colegio.usuarios.enums.TipoUsuario;
import br.com.mba.spring.colegio.usuarios.mapper.ResponsavelAlunoMapper;
import br.com.mba.spring.colegio.usuarios.model.ResponsavelAluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.ResponsavelAlunoRepository;
import br.com.mba.spring.colegio.usuarios.service.impl.ResponsavelAlunoServiceImpl;
import br.com.mba.spring.colegio.usuarios.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsavelAlunoService implements ResponsavelAlunoServiceImpl {

    private final ResponsavelAlunoRepository repository;
    private final UsuarioServiceImpl usuarioService;
    private final ResponsavelAlunoMapper mapper;

    @Override
    @Transactional
    public ResponsavelAluno createResponsavel(ResponsavelAlunoDTO dto) {
        // Validação prévia de CPF específica do domínio de Responsável (regra extra)
        if (repository.existsByUsuario_Cpf(dto.getDadosPessoais().getCpf())) {
            throw new DuplicateCpfException("Responsável já cadastrado com este CPF.");
        }

        // Força o Tipo de Usuário correto antes de chamar o UsuarioService
        dto.getDadosPessoais().setTipoUsuario(TipoUsuario.RESPONSAVEIS_DE_ALUNO);

        // 1. Cria o Usuário base (passando pelo UsuarioService para validações globais e persistência)
        Usuario usuarioSalvo = usuarioService.createUsuario(dto.getDadosPessoais());

        // 2. Cria a entidade ResponsavelAluno vinculada
        ResponsavelAluno entity = mapper.toEntity(dto);
        entity.setUsuario(usuarioSalvo); // Substitui pelo objeto gerenciado (persistido)

        return repository.save(entity);
    }

    @Override
    @Transactional
    public ResponsavelAluno updateResponsavel(Long id, ResponsavelAlunoDTO dto) {
        ResponsavelAluno entity = findResponsavelById(id);

        // Garante que não alterem o tipo de usuário indevidamente via update
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
        // Soft Delete no usuário vinculado (conforme padrão do projeto)
        usuarioService.deleteUsuario(entity.getUsuario().getIdUsuario());
    }
}
