package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.globalHandler.exeption.BusinessException;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.mapper.UsuarioMapper;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.UsuarioRepository;
import br.com.mba.spring.colegio.usuarios.service.impl.UsuarioServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioServiceImpl {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

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

        if (dto.getCpf() != null) {
            String senhaPura = dto.getCpf().replaceAll("[^0-9]", "");
            novoUsuario.setSenha(passwordEncoder.encode(senhaPura));
        }

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
        Usuario usuario = findUsuarioById(id);
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

}
