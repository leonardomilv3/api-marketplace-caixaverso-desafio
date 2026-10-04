package br.edu.fiap.marketplace.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.edu.fiap.marketplace.dto.UsuarioRequest;
import br.edu.fiap.marketplace.dto.UsuarioResponse;
import br.edu.fiap.marketplace.entity.Usuario;
import br.edu.fiap.marketplace.exception.EmailJaCadastradoException;
import br.edu.fiap.marketplace.exception.UsuarioNaoEncontradoException;
import br.edu.fiap.marketplace.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

/** TODO implementar cadastro, consulta e proteção da senha com PasswordEncoder. */
@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }


    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    private void validarEmail(String email) {
        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailJaCadastradoException();
        }
    }

    @Transactional 
    public UsuarioResponse cadastrar(UsuarioRequest request) {
        validarEmail(request.email());
        String senhaCriptografada = passwordEncoder.encode(request.senha());
        Usuario usuario = new Usuario(request.nome(), request.email(), senhaCriptografada);
        usuarioRepository.save(usuario);
        return UsuarioResponse.de(usuario);
    }


    @Transactional 
    public UsuarioResponse buscar(Long id) {
        return UsuarioResponse.de(buscarUsuario(id));
    }

    @Transactional 
    public UsuarioResponse ativar(Long id) {
        Usuario usuario = buscarUsuario(id);
        usuario.ativar();
        return UsuarioResponse.de(usuario);
    }

    @Transactional
    public UsuarioResponse desativar(Long id) {
        Usuario usuario = buscarUsuario(id);
        usuario.desativar();
        return UsuarioResponse.de(usuario);
    }


    @Transactional
    public UsuarioResponse alterarSenha(Long id, String novaSenha) {
        Usuario usuario = buscarUsuario(id);
        String novaSenhaCriptografada = passwordEncoder.encode(novaSenha);
        usuario.atualizarSenhaHash(novaSenhaCriptografada);
        return UsuarioResponse.de(usuario); 
    }


}
