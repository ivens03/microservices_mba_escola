package br.com.mba.spring.colegio.usuarios.mapper;

import br.com.mba.spring.colegio.usuarios.dto.ResponsavelAlunoDTO;
import br.com.mba.spring.colegio.usuarios.model.ResponsavelAluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponsavelAlunoMapper {

    private final UsuarioMapper usuarioMapper;

    public ResponsavelAluno toEntity(ResponsavelAlunoDTO dto) {
        if (dto == null) return null;

        Usuario usuario = usuarioMapper.toEntity(dto.getDadosPessoais());

        return ResponsavelAluno.builder()
                .usuario(usuario)
                .historicoAluno(dto.getHistoricoAluno())
                .pagamento(dto.getPagamento())
                .build();
    }

    public void updateEntityFromDto(ResponsavelAlunoDTO dto, ResponsavelAluno entity) {
        if (dto == null || entity == null) return;

        if (dto.getHistoricoAluno() != null) {
            entity.setHistoricoAluno(dto.getHistoricoAluno());
        }
        if (dto.getPagamento() != null) {
            entity.setPagamento(dto.getPagamento());
        }

        if (dto.getDadosPessoais() != null) {
            usuarioMapper.updateEntityFromDto(dto.getDadosPessoais(), entity.getUsuario());
        }
    }

}
