package br.com.mba.spring.colegio.usuarios.service.impl;

import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface UsuarioServiceImpl {

    @Operation(summary = "Criar Usuário", description = "Cria um novo usuário base no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou duplicidade")
    })
    Usuario createUsuario(UsuarioDTO dto);

    @Operation(summary = "Atualizar Usuário", description = "Atualiza dados de um usuário existente.")
    Usuario updateUsuario(Long id, UsuarioDTO dto);

    @Operation(summary = "Listar todos", description = "Retorna todos os usuários ativos.")
    List<Usuario> findAllUsuarios();

    @Operation(summary = "Buscar por ID", description = "Retorna um usuário pelo seu ID.")
    Usuario findUsuarioById(Long id);

    @Operation(summary = "Buscar por CPF", description = "Retorna um usuário pelo CPF.")
    Usuario findUsuarioByCpf(String cpf);

    @Operation(summary = "Deletar Usuário", description = "Inativa ou remove um usuário do sistema.")
    void deleteUsuario(Long id);

}
