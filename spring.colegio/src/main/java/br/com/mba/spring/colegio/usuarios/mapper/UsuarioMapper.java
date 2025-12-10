package br.com.mba.spring.colegio.usuarios.mapper;

import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.model.Endereco;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UsuarioMapper {

    /**
     * Converte DTO para Entidade (Usado no CREATE)
     */
    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        // Mapeamento manual do Endereço (se existir)
        Endereco endereco = null;
        if (dto.getEndereco() != null) {
            endereco = Endereco.builder()
                    .logradouro(dto.getEndereco().getLogradouro())
                    .numero(dto.getEndereco().getNumero())
                    .complemento(dto.getEndereco().getComplemento())
                    .bairro(dto.getEndereco().getBairro())
                    .cidade(dto.getEndereco().getCidade())
                    .estado(dto.getEndereco().getEstado())
                    .cep(dto.getEndereco().getCep())
                    .build();
        }

        return Usuario.builder()
                .nome(dto.getNome())
                .dataNascimento(dto.getDataNascimento())
                .cpf(dto.getCpf())
                .matricula(dto.getMatricula())
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .genero(dto.getGenero())
                .contatoEmergencia(dto.getContatoEmergencia())
                .endereco(endereco)
                .status(true) // Padrão ao criar
                .build();
    }

    /**
     * Atualiza Entidade existente com dados do DTO (Usado no PATCH)
     * Apenas campos não nulos são atualizados.
     */
    public void updateEntityFromDto(UsuarioDTO dto, Usuario entity) {
        if (dto == null || entity == null) return;

        if (StringUtils.hasText(dto.getNome())) {
            entity.setNome(dto.getNome());
        }

        if (StringUtils.hasText(dto.getTelefone())) {
            entity.setTelefone(dto.getTelefone());
        }

        if (StringUtils.hasText(dto.getEmail())) {
            entity.setEmail(dto.getEmail());
        }

        // Verifica null pois LocalDate não é texto
        if (dto.getDataNascimento() != null) {
            entity.setDataNascimento(dto.getDataNascimento());
        }

        if (dto.getGenero() != null) {
            entity.setGenero(dto.getGenero());
        }

        if (StringUtils.hasText(dto.getContatoEmergencia())) {
            entity.setContatoEmergencia(dto.getContatoEmergencia());
        }

        // Lógica para Endereço (Objeto Aninhado)
        if (dto.getEndereco() != null && entity.getEndereco() != null) {
            var endDto = dto.getEndereco();
            var endEntity = entity.getEndereco();

            if (StringUtils.hasText(endDto.getLogradouro())) endEntity.setLogradouro(endDto.getLogradouro());
            if (StringUtils.hasText(endDto.getNumero())) endEntity.setNumero(endDto.getNumero());
            if (StringUtils.hasText(endDto.getComplemento())) endEntity.setComplemento(endDto.getComplemento());
            if (StringUtils.hasText(endDto.getBairro())) endEntity.setBairro(endDto.getBairro());
            if (StringUtils.hasText(endDto.getCidade())) endEntity.setCidade(endDto.getCidade());
            if (StringUtils.hasText(endDto.getEstado())) endEntity.setEstado(endDto.getEstado());
            if (StringUtils.hasText(endDto.getCep())) endEntity.setCep(endDto.getCep());
        }
    }
}
