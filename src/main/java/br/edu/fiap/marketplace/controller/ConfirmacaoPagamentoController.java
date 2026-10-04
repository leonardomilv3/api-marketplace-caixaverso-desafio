package br.edu.fiap.marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.fiap.marketplace.dto.ConfirmacaoPagamentoRequest;
import br.edu.fiap.marketplace.dto.ConfirmacaoPagamentoResponse;
import br.edu.fiap.marketplace.service.ConfirmacaoPagamentoService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



/** TODO criar as rotas protegidas de confirmação e consulta do pagamento. */
@RestController
@RequestMapping("/api/pagamentos")
@Tag(name = "Pagamentos")
@SecurityRequirement(name = "bearerAuth")
public class ConfirmacaoPagamentoController {


    private final ConfirmacaoPagamentoService confirmacaoPagamentoService;


    public ConfirmacaoPagamentoController(ConfirmacaoPagamentoService confirmacaoPagamentoService) {
        this.confirmacaoPagamentoService = confirmacaoPagamentoService;
    }

    @PostMapping
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Confirmação de pagamento")
    @ApiResponses ({
        @ApiResponse (responseCode = "201", description = "Pagamento confirmado com sucesso"),
        @ApiResponse (responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse (responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<ConfirmacaoPagamentoResponse> pagar(
        @Valid @RequestBody ConfirmacaoPagamentoRequest request) {
        
        ConfirmacaoPagamentoResponse response = confirmacaoPagamentoService.pagar(request);
        
        return ResponseEntity
               .created(URI.create("/api/pagamentos" + response.id()))
               .body(response);
    }


    @GetMapping("/{id}")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Consulta de confirmação de pagamento")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Confirmação de pagamento encontrada"),
        @ApiResponse (responseCode = "404", description = "Confirmação de pagamento não encontrada")
    })
    public ResponseEntity<ConfirmacaoPagamentoResponse> buscar(
        @PathVariable Long id) {

        return ResponseEntity.ok(confirmacaoPagamentoService.buscar(id));
    }



    @GetMapping("{id}/aprovar")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Aprovação de confirmação de pagamento")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Confirmação de pagamento aprovada com sucesso"),
        @ApiResponse (responseCode = "404", description = "Confirmação de pagamento não encontrada")
    })
    public ResponseEntity<ConfirmacaoPagamentoResponse> aprovar(
        @PathVariable Long id) {

        return ResponseEntity.ok(confirmacaoPagamentoService.aprovar(id));
    }



    @GetMapping("{id}/recusar")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Recusa de confirmação de pagamento")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Confirmação de pagamento recusada com sucesso"),
        @ApiResponse (responseCode = "404", description = "Confirmação de pagamento não encontrada")
    })
    public ResponseEntity<ConfirmacaoPagamentoResponse> recusar(
        @PathVariable Long id) {

        return ResponseEntity.ok(confirmacaoPagamentoService.recusar(id));
    }
    



}
