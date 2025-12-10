package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.exception.BusinessException;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.interfaces.CreateEntityInterface;
import br.com.mba.spring.colegio.usuarios.interfaces.UpdateEntityInterface;
import br.com.mba.spring.colegio.usuarios.mapper.UsuarioMapper;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements CreateEntityInterface<Usuario, UsuarioDTO>, UpdateEntityInterface<Usuario, UsuarioDTO> {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional
    public Usuario create(UsuarioDTO dto) {
        if (usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("CPF já cadastrado no sistema.");
        }
        if (usuarioRepository.existsByMatricula(dto.getMatricula())) {
            throw new BusinessException("Matrícula já existente.");
        }

        Usuario novoUsuario = usuarioMapper.toEntity(dto);
        return usuarioRepository.save(novoUsuario);
    }

    @Override
    @Transactional
    public Usuario update(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
        usuarioMapper.updateEntityFromDto(dto, usuario);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
    }

    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new BusinessException("Usuário não encontrado para exclusão.");
        }
        usuarioRepository.deleteById(id);
    }

}
