package br.edu.fiap.marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.fiap.marketplace.dto.AlterarSenhaRequest;
import br.edu.fiap.marketplace.dto.UsuarioRequest;
import br.edu.fiap.marketplace.dto.UsuarioResponse;
import br.edu.fiap.marketplace.service.UsuarioService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



/** TODO criar as rotas públicas de cadastro e protegidas de consulta. */
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários")
public class UsuarioController {


    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping
    @Operation (summary = "Cadastro de usuário")
    @ApiResponses ({
        @ApiResponse (responseCode = "201", description = "Usuário cadastrado com sucesso"),
        @ApiResponse (responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse (responseCode = "409", description = "Usuário já cadastrado com o mesmo e-mail")
    })
    public ResponseEntity<UsuarioResponse> cadastrar(
        @Valid @RequestBody UsuarioRequest request) {
        
        UsuarioResponse response = usuarioService.cadastrar(request);

        return ResponseEntity
                .created(URI.create("/api/usuarios" + response.id()))
                .body(response);   
    }


    @GetMapping("/{id}")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Consulta de usuário")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse (responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscar(id));
    }


    @PatchMapping ("/{id}/ativar")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Ativação de usuário")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Usuário ativado com sucesso"),
        @ApiResponse (responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponse> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.ativar(id));
    }


    @PatchMapping ("/{id}/desativar")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Desativação de usuário")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Usuário desativado com sucesso"),
        @ApiResponse (responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponse> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.desativar(id));
    }


    @PatchMapping ("/{id}/alterarSenha")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Alteração de senha de usuário")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Senha alterada com sucesso"),
        @ApiResponse (responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponse> alterarSenha(
        @PathVariable Long id, 
        @Valid @RequestBody AlterarSenhaRequest request) {

        return ResponseEntity.ok(usuarioService.alterarSenha(id, request.novaSenha()));
    }

}
