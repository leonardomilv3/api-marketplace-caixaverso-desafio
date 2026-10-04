package br.edu.fiap.marketplace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlterarSenhaRequest(
    @Schema (description = "Senha atual do usuário", example = "senha123")
    @NotBlank (message = "A senha atual não pode ser nula ou vazia")
    @Size (min = 8, max = 20, message = "A senha atual deve ter entre 8 e 20 caracteres")
    String novaSenha
) {
    
}
