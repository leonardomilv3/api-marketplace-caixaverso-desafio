package br.edu.fiap.marketplace.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import br.edu.fiap.marketplace.dto.ConfirmacaoPagamentoRequest;
import br.edu.fiap.marketplace.dto.ConfirmacaoPagamentoResponse;
import br.edu.fiap.marketplace.entity.Carrinho;
import br.edu.fiap.marketplace.entity.ConfirmacaoPagamento;
import br.edu.fiap.marketplace.entity.Usuario;
import br.edu.fiap.marketplace.exception.CarrinhoNaoEncontradoException;
import br.edu.fiap.marketplace.exception.ConfirmacaoPagamentoNaoEncontradaException;
import br.edu.fiap.marketplace.exception.UsuarioNaoEncontradoException;
import br.edu.fiap.marketplace.repository.CarrinhoRepository;
import br.edu.fiap.marketplace.repository.ConfirmacaoPagamentoRepository;
import br.edu.fiap.marketplace.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

/** TODO implementar criação, aprovação, recusa e consulta do pagamento. */
@Service
public class ConfirmacaoPagamentoService {


    private ConfirmacaoPagamentoRepository confirmacaoPagamentoRepository;
    private CarrinhoRepository carrinhoRepository;
    private UsuarioRepository usuarioRepository;

    public ConfirmacaoPagamentoService(ConfirmacaoPagamentoRepository confirmacaoPagamentoRepository,
                                        CarrinhoRepository carrinhoRepository,
                                        UsuarioRepository usuarioRepository) {
        this.confirmacaoPagamentoRepository = confirmacaoPagamentoRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.usuarioRepository = usuarioRepository;
    }


    @Transactional
    public ConfirmacaoPagamentoResponse pagar(ConfirmacaoPagamentoRequest request) {
        
        if (!carrinhoRepository.existsById(request.carrinhoId())) {
            throw new CarrinhoNaoEncontradoException();
        }

        if (!usuarioRepository.existsById(request.usuarioId())) {
            throw new UsuarioNaoEncontradoException();
        }

        Optional<Carrinho> carrinho = carrinhoRepository.findById(request.carrinhoId());
        Optional<Usuario> usuario = usuarioRepository.findById(request.usuarioId());

        ConfirmacaoPagamento confirmacaoPagamento = new ConfirmacaoPagamento(
            carrinho.get(),
            usuario.get(),
            request.idPagamento(),
            carrinho.get().calcularTotal()
        );

        return ConfirmacaoPagamentoResponse.de(
            confirmacaoPagamentoRepository.save(confirmacaoPagamento));
    }


    @Transactional 
    public ConfirmacaoPagamentoResponse buscar(Long id) {
        return ConfirmacaoPagamentoResponse.de(buscarEntidade(id));
    }


    @Transactional 
    public ConfirmacaoPagamentoResponse aprovar(Long id) {
        ConfirmacaoPagamento confirmacaoPagamento = buscarEntidade(id);
        confirmacaoPagamento.aprovar();
        confirmacaoPagamentoRepository.save(confirmacaoPagamento);
        return ConfirmacaoPagamentoResponse.de(confirmacaoPagamento);
    }

    @Transactional
    public ConfirmacaoPagamentoResponse recusar(Long id) {
        ConfirmacaoPagamento confirmacaoPagamento = buscarEntidade(id);
        confirmacaoPagamento.recusar();
        confirmacaoPagamentoRepository.save(confirmacaoPagamento);
        return ConfirmacaoPagamentoResponse.de(confirmacaoPagamento);
    }


    private ConfirmacaoPagamento buscarEntidade(Long id) {
        return confirmacaoPagamentoRepository.findById(id)
            .orElseThrow(() -> new ConfirmacaoPagamentoNaoEncontradaException(id));
    }


}
