package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.globalHandler.exeption.AlunoNotFoundException;
import br.com.mba.spring.colegio.globalHandler.exeption.BusinessException;
import br.com.mba.spring.colegio.usuarios.dto.AlunoDTO;
import br.com.mba.spring.colegio.usuarios.interfaces.CreateEntityInterface;
import br.com.mba.spring.colegio.usuarios.interfaces.UpdateEntityInterface;
import br.com.mba.spring.colegio.usuarios.mapper.AlunoMapper;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService implements CreateEntityInterface<Aluno, AlunoDTO>, UpdateEntityInterface<Aluno, AlunoDTO> {

    private final AlunoRepository alunoRepository;
    private final UsuarioService usuarioService;
    private final AlunoMapper alunoMapper;

    @Override
    @Transactional
    public Aluno create(AlunoDTO dto) {
        Usuario usuarioSalvo = usuarioService.create(dto.getDadosPessoais());
        Aluno novoAluno = alunoMapper.toEntity(dto);
        novoAluno.setUsuario(usuarioSalvo);
        return alunoRepository.save(novoAluno);
    }

    @Override
    @Transactional
    public Aluno update(Long id, AlunoDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com ID: " + id));

        alunoMapper.updateEntityFromDto(dto, aluno);
        return alunoRepository.save(aluno);
    }

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Aluno findById(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com ID: " + id));
    }

    @Transactional
    public void delete(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new AlunoNotFoundException("Aluno não encontrado para exclusão.");
        }
        alunoRepository.deleteById(id);
    }

}
