package br.edu.fiap.marketplace.entity;


import br.edu.fiap.marketplace.exception.AprovacaoPagamentoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmacaoPagamentoTest {

    private Usuario usuario;
    private CatalogoProduto produto;
    private Carrinho carrinho;
    private ConfirmacaoPagamento confirmacaoPagamento;

    @BeforeEach
    void setup() {

        usuario = new Usuario(
                "João Silva",
                "joao@email.com",
                "senha"
        );

        produto = new CatalogoProduto(
                "Notebook",
                "Notebook Dell",
                new BigDecimal("5000.00"),
                10,
                true
        );

        carrinho = new Carrinho(
                usuario,
                produto,
                2
        );

        confirmacaoPagamento = new ConfirmacaoPagamento(
                carrinho,
                usuario,
                "PAG-123",
                new BigDecimal("10000.00")
        );
    }

    @Test
    @DisplayName("Deve aprovar pagamento pendente.")
    void deveAprovarPagamentoPendenteTest() {
        confirmacaoPagamento.aprovar();
        assertEquals(
                StatusPagamento.PAGO,
                confirmacaoPagamento.getStatus()
        );
    }


    @Test
    @DisplayName("Deve finalizar carrinho ao aprovar pagamento.")
    void deveFinalizarCarrinhoAoAprovarPagamentoTest() {

        confirmacaoPagamento.aprovar();
        assertEquals(
                StatusCarrinho.FINALIZADO,
                carrinho.getStatus()
        );
    }


    @Test
    @DisplayName("Deve retornar verdadeiro quando pagamento estiver pago.")
    void deveRetornarVerdadeiroQuandoPagamentoEstiverPagoTest() {

        confirmacaoPagamento.aprovar();

        assertTrue(
                confirmacaoPagamento.estaPago()
        );
    }

    @Test
    @DisplayName("Deve retornar falso quando pagamento estiver pendente.")
    void deveRetornarFalsoQuandoPagamentoEstiverPendenteTest() {

        assertFalse(
                confirmacaoPagamento.estaPago()
        );
    }


    @Test
    @DisplayName("Deve recusar pagamento pendente.")
    void deveRecusarPagamentoPendenteTest() {

        confirmacaoPagamento.recusar();

        assertEquals(
                StatusPagamento.RECUSADO,
                confirmacaoPagamento.getStatus()
        );
    }

    @Test
    @DisplayName("Deve retornar falso quando pagamento estiver recusado.")
    void deveRetornarFalsoQuandoPagamentoEstiverRecusadoTest() {
        confirmacaoPagamento.recusar();
        assertFalse(
                confirmacaoPagamento.estaPago()
        );
    }


    @Test
    @DisplayName("Deve falhar quando aprovar pagamento já aprovado.")
    void deveFalharAprovacaoPagamentoJaAprovadoTest() {

        confirmacaoPagamento.aprovar();

        assertThrows(
                AprovacaoPagamentoException.class,
                () -> confirmacaoPagamento.aprovar()
        );
    }


    @Test
    @DisplayName("Deve falhar quando aprovar pagamento recusado.")
    void deveFalharAprovacaoPagamentoRecusadoTest() {

        confirmacaoPagamento.recusar();

        assertThrows(
                AprovacaoPagamentoException.class,
                () -> confirmacaoPagamento.aprovar()
        );
    }


    @Test
    @DisplayName("Deve falhar quando recusar pagamento já recusado.")
    void deveFalharRecusaPagamentoJaRecusadoTest() {

        confirmacaoPagamento.recusar();

        assertThrows(
                AprovacaoPagamentoException.class,
                () -> confirmacaoPagamento.recusar()
        );
    }


    @Test
    @DisplayName("Deve falhar quando recusar pagamento já aprovado.")
    void deveFalharRecusaPagamentoJaAprovadoTest() {

        confirmacaoPagamento.aprovar();

        assertThrows(
                AprovacaoPagamentoException.class,
                () -> confirmacaoPagamento.recusar()
        );
    }
}