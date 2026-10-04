package br.edu.fiap.marketplace.exception;

public class CancelarCarrinhoException extends ConflitoDeNegocioException {

    public CancelarCarrinhoException() {
        super("Cancelamento do carrinho inválido");
    }
    
}
