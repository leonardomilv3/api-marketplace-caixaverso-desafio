package br.edu.fiap.marketplace.exception;

public class EmailJaCadastradoException extends ConflitoDeNegocioException {

    public EmailJaCadastradoException() {
        super("Email já cadastrado");
    }
    
}
