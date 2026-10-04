package br.edu.fiap.marketplace.exception;

public class CarrinhoNaoEncontradoException extends RecursoNaoEncontradoException {

    public CarrinhoNaoEncontradoException() {
        super("Carrinho não encontrado");
    }

    public CarrinhoNaoEncontradoException(Long carrinhoId) {
        super("Carrinho com ID " + carrinhoId + " não encontrado");
    }
    
}
