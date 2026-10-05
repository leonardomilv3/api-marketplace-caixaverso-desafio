package br.edu.fiap.marketplace.controller;

import br.edu.fiap.marketplace.dto.*;
import br.edu.fiap.marketplace.service.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.PortUnreachableException;
import java.net.URI;
import java.util.List;

/** TODO criar as rotas protegidas de criação, consulta e alteração do carrinho. */
@RestController
@RequestMapping("/api/carrinhos")
@Tag(name = "Carrinhos")
@SecurityRequirement(name = "bearerAuth")
public class CarrinhoController {


    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService){
        this.carrinhoService = carrinhoService;
    }

    @PostMapping
    @SecurityRequirement (name = "bearerAuth")
    @Operation(summary = "Criação de carrinho")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Carrinho criado"),
            @ApiResponse (responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<CarrinhoResponse> criar(
            @Valid @RequestBody CarrinhoRequest request) {
        CarrinhoResponse response = carrinhoService.criar(request);

        return ResponseEntity
                .created(URI.create("/api/carrinhos/" + response.id()))
                .body(response);
    }


    @GetMapping("/{id}")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Consulta de carrinho por ID")
    @ApiResponses ({
            @ApiResponse (responseCode = "200", description = "carrinho encontrado"),
            @ApiResponse (responseCode = "404", description = "carrinho não encontrado")
    })
    public ResponseEntity<CarrinhoResponse> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(carrinhoService.buscar(id));
    }


    @GetMapping("/usuario/{usuarioId}")
    @Operation (summary = "Consulta de produtos do catálogo")
    @ApiResponses ({
            @ApiResponse (responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse (responseCode = "404", description = "Produtos não encontrados")
    })
    public List<CarrinhoResponse> buscarCarrinhoDoUsuario(
            @PathVariable Long usuarioId
    ) {
        return carrinhoService.buscarCarrinhoDoUsuario(usuarioId);
    }



    @PutMapping("/{id}/quantidade")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Atualização de quantidade no carrinho")
    @ApiResponses ({
            @ApiResponse (responseCode = "200", description = "Carrinho atualizado com sucesso"),
            @ApiResponse (responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse (responseCode = "404", description = "Carrinho não encontrado")
    })
    public ResponseEntity<CarrinhoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarQuantidadeRequest request) {

        CarrinhoResponse response = carrinhoService.atualizarQuantidade(id, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("{id}")
    @SecurityRequirement (name = "bearerAuth")
    @Operation (summary = "Cancela carrinho aberto")
    @ApiResponses ({
            @ApiResponse (responseCode = "204", description = "Carrinho atualizado com sucesso"),
            @ApiResponse (responseCode = "404", description = "Carrinho não encontrado")
    })
    public ResponseEntity<CarrinhoResponse> cancelar(
            @PathVariable Long id
    ){
        CarrinhoResponse response = carrinhoService.cancelar(id);
        return ResponseEntity.ok(response);
    }


}
