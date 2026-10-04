package br.edu.fiap.marketplace.exception;

public class AlteracaoQuantidadeCarrinhoException extends ConflitoDeNegocioException {
    
    public AlteracaoQuantidadeCarrinhoException() {
        super("Alteração de quantidade no carrinho inválida");
    }

}
