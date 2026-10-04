package br.edu.fiap.marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.fiap.marketplace.dto.LoginRequest;
import br.edu.fiap.marketplace.dto.TokenResponse;
import br.edu.fiap.marketplace.exception.ApiErrorResponse;
import br.edu.fiap.marketplace.service.AutenticacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/** TODO criar POST /api/auth/login usando LoginRequest e TokenResponse. */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    @Operation (summary = "Autenticação de usuário")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Usuário autenticado"),
        @ApiResponse (responseCode = "400", 
                    description = "Dados de login inválidos",
                    content = @Content(schema = @Schema (implementation = ApiErrorResponse.class))),
        @ApiResponse (responseCode = "401", 
                    description = "Usuário não autenticado",
                    content = @Content(schema = @Schema (implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<TokenResponse> login(
        @Valid @RequestBody LoginRequest request) {
        //TODO: process POST request
        
        return ResponseEntity.ok(autenticacaoService.autenticar(request));
    }
    
}
