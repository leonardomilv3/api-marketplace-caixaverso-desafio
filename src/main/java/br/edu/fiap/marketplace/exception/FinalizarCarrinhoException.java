package br.edu.fiap.marketplace.exception;

public class FinalizarCarrinhoException extends ConflitoDeNegocioException {

    public FinalizarCarrinhoException() {
        super("Finalização do carrinho inválida");
    }
    
}
