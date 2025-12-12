package br.com.mba.spring.colegio.funcionarios.mapper;

import br.com.mba.spring.colegio.funcionarios.dto.ProfessorDTO;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ProfessorMapper {

    public Professor toEntity(ProfessorDTO dto) {
        if (dto == null) return null;
        return Professor.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .especialidade(dto.getEspecialidade())
                .formacao(dto.getFormacao())
                .build();
    }

    public void updateEntityFromDto(ProfessorDTO dto, Professor entity) {
        if (dto == null || entity == null) return;
        if (StringUtils.hasText(dto.getNome())) entity.setNome(dto.getNome());
        if (StringUtils.hasText(dto.getEspecialidade())) entity.setEspecialidade(dto.getEspecialidade());
        if (StringUtils.hasText(dto.getFormacao())) entity.setFormacao(dto.getFormacao());
        // CPF geralmente não se altera, mas se necessário, adicione aqui.
    }
}
