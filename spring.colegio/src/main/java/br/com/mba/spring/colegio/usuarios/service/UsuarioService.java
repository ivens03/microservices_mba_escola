package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.globalHandler.exeption.BusinessException;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.interfaces.CreateEntityInterface;
import br.com.mba.spring.colegio.usuarios.interfaces.UpdateEntityInterface;
import br.com.mba.spring.colegio.usuarios.mapper.UsuarioMapper;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.UsuarioRepository;
import br.com.mba.spring.colegio.usuarios.service.impl.UsuarioServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioServiceImpl {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional
    public Usuario createUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("CPF já cadastrado no sistema.");
        }
        if (usuarioRepository.existsByMatricula(dto.getMatricula())) {
            throw new BusinessException("Matrícula já existente.");
        }
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado.");
        }

        Usuario novoUsuario = usuarioMapper.toEntity(dto);
        // Regra de negócio: Usuário nasce ativo
        novoUsuario.setAtivo(true);

        return usuarioRepository.save(novoUsuario);
    }

    @Override
    @Transactional
    public Usuario updateUsuario(Long id, UsuarioDTO dto) {
        Usuario usuario = findUsuarioById(id);
        usuarioMapper.updateEntityFromDto(dto, usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com ID: " + id));
    }

    @Override
    public Usuario findUsuarioByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com CPF: " + cpf));
    }

    @Override
    @Transactional
    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new BusinessException("Usuário não encontrado para exclusão.");
        }
        usuarioRepository.deleteById(id);
    }

}
