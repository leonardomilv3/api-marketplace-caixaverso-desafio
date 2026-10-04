package br.edu.fiap.marketplace.exception;

public class PrecoInvalidoException extends ConflitoDeNegocioException {

    public PrecoInvalidoException() {
        super("Preço inválido");
    }
    
}
