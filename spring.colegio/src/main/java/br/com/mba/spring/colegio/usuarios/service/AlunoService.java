package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.globalHandler.exeption.AlunoNotFoundException;
import br.com.mba.spring.colegio.usuarios.dto.AlunoDTO;
import br.com.mba.spring.colegio.usuarios.enums.Turno;
import br.com.mba.spring.colegio.usuarios.mapper.AlunoMapper;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.AlunoRepository;
import br.com.mba.spring.colegio.usuarios.service.impl.AlunoServiceImpl;
import br.com.mba.spring.colegio.usuarios.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService implements AlunoServiceImpl {

    private final AlunoRepository alunoRepository;

    // CORREÇÃO: Injeção da Interface ao invés da Classe Concreta
    private final UsuarioServiceImpl usuarioService;

    private final AlunoMapper alunoMapper;

    @Override
    @Transactional
    public Aluno createAluno(AlunoDTO dto) {
        // 1. Cria o Usuário Base através do serviço de usuário (já valida CPF, Email, etc.)
        Usuario usuarioSalvo = usuarioService.createUsuario(dto.getDadosPessoais());

        // 2. Mapeia e salva o Aluno vinculado
        Aluno novoAluno = alunoMapper.toEntity(dto);
        novoAluno.setUsuario(usuarioSalvo);

        return alunoRepository.save(novoAluno);
    }

    @Override
    @Transactional
    public Aluno updateAluno(Long id, AlunoDTO dto) {
        Aluno aluno = findAlunoById(id);

        // O Mapper atualiza os campos do Aluno e delega a atualização do Usuario interno
        alunoMapper.updateEntityFromDto(dto, aluno);

        return alunoRepository.save(aluno);
    }

    @Override
    public List<Aluno> findAllAlunos() {
        return alunoRepository.findAll();
    }

    @Override
    public Aluno findAlunoById(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com ID: " + id));
    }

    @Override
    public List<Aluno> findAlunosBySala(String sala) {
        // Exemplo simplificado filtrando em memória
        return alunoRepository.findAll().stream()
                .filter(a -> a.getSala().equalsIgnoreCase(sala))
                .toList();
    }

    @Override
    public List<Aluno> findAlunosByTurno(Turno turno) {
        // Mesma lógica, idealmente deve haver um método findByTurno no Repository
        return alunoRepository.findAll().stream()
                .filter(a -> a.getTurno() == turno)
                .toList();
    }

    @Override
    @Transactional
    public void deleteAluno(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new AlunoNotFoundException("Aluno não encontrado para exclusão.");
        }
        alunoRepository.deleteById(id);
    }
}