package br.edu.fiap.marketplace.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.edu.fiap.marketplace.dto.LoginRequest;
import br.edu.fiap.marketplace.dto.TokenResponse;
import br.edu.fiap.marketplace.entity.Usuario;
import br.edu.fiap.marketplace.exception.CredenciaisInvalidasException;
import br.edu.fiap.marketplace.repository.UsuarioRepository;
import br.edu.fiap.marketplace.security.JwtService;
import jakarta.transaction.Transactional;

/** TODO localizar usuário, comparar BCrypt e solicitar a emissão do JWT. */
@Service
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoderService;
    private final JwtService jwtService;

    public AutenticacaoService(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoderService,
        JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoderService = passwordEncoderService;
        this.jwtService = jwtService;
    }

    @Transactional 
    public TokenResponse autenticar(LoginRequest request) {
    
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(request.email())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        boolean senhaValida = passwordEncoderService.matches(request.senha(), usuario.getSenha());

        if ( !usuario.isAtivo() || !senhaValida ) {
            throw new CredenciaisInvalidasException();

        }

        return jwtService.gerarToken(usuario);
    }
}
