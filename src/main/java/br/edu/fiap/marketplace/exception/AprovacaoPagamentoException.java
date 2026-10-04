package br.edu.fiap.marketplace.exception;

public class AprovacaoPagamentoException extends ConflitoDeNegocioException {

    public AprovacaoPagamentoException() {
        super("Aprovação de pagamento inválida");
    }
    
}
