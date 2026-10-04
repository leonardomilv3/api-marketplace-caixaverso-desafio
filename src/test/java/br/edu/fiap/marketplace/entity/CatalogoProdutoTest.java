package br.edu.fiap.marketplace.entity;


import br.edu.fiap.marketplace.exception.AlteracaoEstoqueInvalidaException;
import br.edu.fiap.marketplace.exception.PrecoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


/**
 * TODO implementar um teste unitário da regra de estoque ou preço.
 * Não iniciar o Spring e não usar repository neste arquivo.
 */
class CatalogoProdutoTest {

    private CatalogoProduto produto;

    @BeforeEach
    void setup() {
        produto = new CatalogoProduto(
                "Notebook",
                "Notebook Dell",
                new BigDecimal("5000.00"),
                10,
                true
        );
    }


    @Test
    @DisplayName("Deve alterar o preço com valor positivo.")
    void deveAlterarPrecoValidoTest() {
        BigDecimal novoPreco = new BigDecimal("6500.00");
        produto.alterarPreco(novoPreco);
        assertEquals(novoPreco, produto.getPreco());
    }

    @Test
    @DisplayName("Deve falhar quando preço for nulo.")
    void deveFalharAlteracaoPrecoNuloTest() {
        assertThrows(
                PrecoInvalidoException.class,
                () -> produto.alterarPreco(null)
        );
    }

    @Test
    @DisplayName("Deve falhar quando preço for zero.")
    void deveFalharAlteracaoPrecoZeroTest() {

        assertThrows(
                PrecoInvalidoException.class,
                () -> produto.alterarPreco(BigDecimal.ZERO)
        );
    }

    @Test
    @DisplayName("Deve falhar quando preço for negativo.")
    void deveFalharAlteracaoPrecoNegativoTest() {

        assertThrows(
                PrecoInvalidoException.class,
                () -> produto.alterarPreco(new BigDecimal("-1.00"))
        );
    }

    @Test
    @DisplayName("Deve baixar estoque com quantidade válida.")
    void deveBaixarEstoqueComQuantidadeValidaTest() {
        produto.baixarEstoque(3);
        assertEquals(7, produto.getEstoque());
    }


    @Test
    @DisplayName("Deve baixar estoque até zerar.")
    void deveBaixarEstoqueAteZeroTest() {
        produto.baixarEstoque(10);
        assertEquals(0, produto.getEstoque());
    }


    @Test
    @DisplayName("Deve falhar quando baixar estoque com quantidade zero.")
    void deveFalharBaixaEstoqueComQuantidadeZeroTest() {
        assertThrows(
                AlteracaoEstoqueInvalidaException.class,
                () -> produto.baixarEstoque(0)
        );
    }


    @Test
    @DisplayName("Deve falhar quando baixar estoque com quantidade negativa.")
    void deveFalharBaixaEstoqueComQuantidadeNegativaTest() {
        assertThrows(
                AlteracaoEstoqueInvalidaException.class,
                () -> produto.baixarEstoque(-1)
        );
    }

    @Test
    @DisplayName("Deve falhar quando baixar quantidade maior que o estoque.")
    void deveFalharBaixaEstoqueMaiorQueSaldoDisponivelTest() {
        assertThrows(
                AlteracaoEstoqueInvalidaException.class,
                () -> produto.baixarEstoque(11)
        );
    }

    @Test
    @DisplayName("Deve repor estoque com quantidade positiva.")
    void deveReporEstoqueComQuantidadeValidaTest() {
        produto.reporEstoque(5);
        assertEquals(15, produto.getEstoque());
    }


    @Test
    @DisplayName("Deve falhar quando repor estoque com quantidade zero.")
    void deveFalharReposicaoEstoqueComQuantidadeZeroTest() {

        assertThrows(
                AlteracaoEstoqueInvalidaException.class,
                () -> produto.reporEstoque(0)
        );
    }

    @Test
    @DisplayName("Deve falhar quando repor estoque com quantidade negativa.")
    void deveFalharReposicaoEstoqueComQuantidadeNegativaTest() {
        assertThrows(
                AlteracaoEstoqueInvalidaException.class,
                () -> produto.reporEstoque(-1)
        );
    }


    @Test
    @DisplayName("Deve ativar produto.")
    void deveAtivarProdutoTest() {
        produto.desativar();
        produto.ativar();
        assertTrue(produto.isAtivo());
    }


    @Test
    @DisplayName("Deve desativar produto.")
    void deveDesativarProdutoTest() {
        produto.desativar();
        assertFalse(produto.isAtivo());
    }

}