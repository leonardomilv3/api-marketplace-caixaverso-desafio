package br.edu.fiap.marketplace.exception;

public class ConfirmacaoPagamentoNaoEncontradaException extends RecursoNaoEncontradoException {

    public ConfirmacaoPagamentoNaoEncontradaException(Long id) {
        super("Confirmação de pagamento com ID " + id + " não encontrada");
    }

}
