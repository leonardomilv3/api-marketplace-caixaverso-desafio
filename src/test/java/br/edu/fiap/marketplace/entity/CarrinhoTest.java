package br.edu.fiap.marketplace.entity;

import br.edu.fiap.marketplace.exception.AlteracaoQuantidadeCarrinhoException;
import br.edu.fiap.marketplace.exception.FinalizarCarrinhoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO implementar um teste unitário de quantidade, total ou estado do carrinho.
 * Instanciar somente objetos Java reais.
 */
class CarrinhoTest {


    private Usuario usuario;
    private CatalogoProduto catalogoProduto;


    @BeforeEach
    void setup(){
        usuario = new Usuario(
                "João",
                "joao@email.com",
                "hash"
        );
        catalogoProduto = new CatalogoProduto(
                "Notebook",
                "Notebook Dell",
                new BigDecimal("5000.00"),
                10,
                true
        );
    }


    @Test
    @DisplayName("Deve alterar quantidade com carrinho aberto;")
    void deveAlterarQuantidadeComCarrinhoAbertoTest() {
        Carrinho carrinho =
                new Carrinho(usuario, catalogoProduto, 1);
        carrinho.alterarQuantidade(5);
        assertEquals(5, carrinho.getQuantidade());
    }


    @Test
    @DisplayName("Deve falhar quando alteração quantidade carrinho for zero.")
    void deveFalharAlteracaoQuantidadeZeroTest() {
        Carrinho carrinho =
                new Carrinho(usuario, catalogoProduto, 1);

        assertThrows(
                AlteracaoQuantidadeCarrinhoException.class,
                () -> carrinho.alterarQuantidade(0)
        );
    }


    @Test
    @DisplayName("Deve falhar quando alteração quantidade carrinho for negativa.")
    void deveFalharAlteracaoQuantidadeNegativaTest() {
        Carrinho carrinho =
                new Carrinho(usuario, catalogoProduto, 1);
        assertThrows(
                AlteracaoQuantidadeCarrinhoException.class,
                () -> carrinho.alterarQuantidade(-1)
        );
    }


    @Test
    @DisplayName("Deve falhar quando alteração no carrinho finalizado.")
    void deveFalharAlteracaoQuantidadeComCarrinhoNaoAbertoTest() {
        Carrinho carrinho =
                new Carrinho(usuario, catalogoProduto, 1);
        carrinho.finalizar();
        assertThrows(
                AlteracaoQuantidadeCarrinhoException.class,
                () -> carrinho.alterarQuantidade(2)
        );
    }


    @Test
    @DisplayName("Deve falhar quando alteração no carrinho cancelado.")
    void deveFalharAlteracaoQuantidadeComCarrinhoCanceladoTest() {
        Carrinho carrinho =
                new Carrinho(usuario, catalogoProduto, 1);
        carrinho.cancelar();
        assertThrows(
                AlteracaoQuantidadeCarrinhoException.class,
                () -> carrinho.alterarQuantidade(2)
        );
    }


    @Test
    @DisplayName("Deve calcular total do carrinho com quantidade.")
    void deveCalcularTotalTest() {
        Carrinho carrinho =
                new Carrinho(usuario, catalogoProduto, 2);
        BigDecimal total = carrinho.calcularTotal();
        assertEquals(
                new BigDecimal("10000.00"),
                total
        );
    }


    @Test
    void deveFinalizarCarrinhoAbertoTest() {
        Carrinho carrinho =
                new Carrinho(usuario, catalogoProduto, 1);
        carrinho.finalizar();
        assertEquals(
                StatusCarrinho.FINALIZADO,
                carrinho.getStatus()
        );
    }


    @Test
    void deveFalharFinalizacaoCarrinhoJaFinalizadoTest() {
        Carrinho carrinho =
                new Carrinho(usuario, catalogoProduto, 1);
        carrinho.finalizar();
        assertThrows(
                FinalizarCarrinhoException.class,
                carrinho::finalizar
        );
    }


    @Test
    void deveFalharFinalizacaoCarrinhoCanceladoTest() {
        Carrinho carrinho =
                new Carrinho(usuario, catalogoProduto, 1);
        carrinho.cancelar();
        assertThrows(
                FinalizarCarrinhoException.class,
                carrinho::finalizar
        );
    }

    @Test
    void deveCancelarCarrinhoTest() {
        Carrinho carrinho =
                new Carrinho(usuario, catalogoProduto, 1);
        carrinho.cancelar();
        assertEquals(
                StatusCarrinho.CANCELADO,
                carrinho.getStatus()
        );
    }

}
