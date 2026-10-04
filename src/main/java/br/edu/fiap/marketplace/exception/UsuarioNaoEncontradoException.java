package br.edu.fiap.marketplace.exception;

public class UsuarioNaoEncontradoException extends RecursoNaoEncontradoException {
    
    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado");
    }

    public UsuarioNaoEncontradoException(Long usuarioId) {
        super("Usuário com ID " + usuarioId + " não encontrado");
    }

}
