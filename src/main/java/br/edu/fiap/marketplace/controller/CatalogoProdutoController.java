package br.edu.fiap.marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.fiap.marketplace.dto.AtualizarEstoqueRequest;
import br.edu.fiap.marketplace.dto.CatalogoProdutoRequest;
import br.edu.fiap.marketplace.dto.CatalogoProdutoResponse;
import br.edu.fiap.marketplace.service.CatalogoProdutoService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;





/** TODO criar as rotas públicas de leitura e protegidas de alteração do catálogo. */
@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Catálogo")
public class CatalogoProdutoController {

    private final CatalogoProdutoService catalogoProdutoService;

    public CatalogoProdutoController(CatalogoProdutoService catalogoProdutoService) {
        this.catalogoProdutoService = catalogoProdutoService;
    }

    @PostMapping
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Criação de produto no catálogo")
    @ApiResponses ({
        @ApiResponse (responseCode = "201", description = "Produto criado"),
        @ApiResponse (responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<CatalogoProdutoResponse> postMethodName(@RequestBody CatalogoProdutoRequest request) {
        CatalogoProdutoResponse response = catalogoProdutoService.cadastrar(request);

        return ResponseEntity
            .created(URI.create("/api/produtos/" + response.id()))
            .body(response);
    }

    @GetMapping
    @Operation (summary = "Consulta de produtos do catálogo")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Produtos encontrados"),
        @ApiResponse (responseCode = "404", description = "Produtos não encontrados")
    })
    public List<CatalogoProdutoResponse> buscar() {
        
        return catalogoProdutoService.buscar().stream()
                .map(CatalogoProdutoResponse::de)
                .toList();
    }


    @GetMapping("/{id}")
    @Operation (summary = "Consulta de produto do catálogo por ID")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Produto encontrado"),
        @ApiResponse (responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<CatalogoProdutoResponse> buscarPorId(
        @PathVariable Long id) {
        return ResponseEntity.ok(catalogoProdutoService.buscar(id));
    }

    
    @PutMapping("/{id}")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Atualização de produto no catálogo")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse (responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse (responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<CatalogoProdutoResponse> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody CatalogoProdutoRequest request) {
        
        CatalogoProdutoResponse response = catalogoProdutoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/{id}/reporEstoque")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Atualização parcial de produto no catálogo")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse (responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse (responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<CatalogoProdutoResponse> reporEstoque(
        @PathVariable Long id,
        @Valid @RequestBody AtualizarEstoqueRequest request) { 
    
        CatalogoProdutoResponse response = catalogoProdutoService.reporEstoque(id, request);
        return ResponseEntity.ok(response);
    }



    @PatchMapping ("/{id}/baixarEstoque")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Atualização parcial de produto no catálogo")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse (responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse (responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<CatalogoProdutoResponse> diminuirEstoque(
        @PathVariable Long id,
        @Valid @RequestBody AtualizarEstoqueRequest request) { 
    
        CatalogoProdutoResponse response = catalogoProdutoService.diminuirEstoque(id, request);
        return ResponseEntity.ok(response);
    }


    @PatchMapping ("/{id}/ativar")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Atualização parcial de produto no catálogo")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse (responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse (responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<CatalogoProdutoResponse> ativar(
        @PathVariable Long id) { 
    
        CatalogoProdutoResponse response = catalogoProdutoService.ativar(id);
        return ResponseEntity.ok(response);
    }


    @PatchMapping ("/{id}/desativar")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Atualização parcial de produto no catálogo")
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse (responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse (responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<CatalogoProdutoResponse> desativar(
        @PathVariable Long id) { 
    
        CatalogoProdutoResponse response = catalogoProdutoService.desativar(id);
        return ResponseEntity.ok(response);
    }

    

}
