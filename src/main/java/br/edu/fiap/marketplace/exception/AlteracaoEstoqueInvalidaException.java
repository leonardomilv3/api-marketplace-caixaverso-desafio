package br.edu.fiap.marketplace.exception;

public class AlteracaoEstoqueInvalidaException extends ConflitoDeNegocioException {

    public AlteracaoEstoqueInvalidaException() {
        super("Alteração de estoque inválida");
    }

    public AlteracaoEstoqueInvalidaException(String message) {
        super(message);
    }
    
}
