package br.edu.fiap.marketplace.exception;

public class ProdutoNaoEncontradoException extends ConflitoDeNegocioException {
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException() {
        super("Produto não encontrado");
    }
}
