package br.com.mba.spring.colegio.usuarios.mapper;

import br.com.mba.spring.colegio.usuarios.dto.AlunoDTO;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlunoMapper {

    private final UsuarioMapper usuarioMapper;

    public Aluno toEntity(AlunoDTO dto) {
        if (dto == null) return null;

        Usuario usuario = usuarioMapper.toEntity(dto.getDadosPessoais());

        return Aluno.builder()
                .usuario(usuario)
                .nomeResponsavel(dto.getNomeResponsavel())
                .historicoNotas(dto.getHistoricoNotas())
                .sala(dto.getSala())
                .turno(dto.getTurno())
                .build();
    }

    public void updateEntityFromDto(AlunoDTO dto, Aluno entity) {
        if (dto == null || entity == null) return;

        // Atualiza campos específicos do Aluno
        entity.setNomeResponsavel(dto.getNomeResponsavel());
        entity.setSala(dto.getSala());
        entity.setTurno(dto.getTurno());
        entity.setHistoricoNotas(dto.getHistoricoNotas());

        usuarioMapper.updateEntityFromDto(dto.getDadosPessoais(), entity.getUsuario());
    }

}
