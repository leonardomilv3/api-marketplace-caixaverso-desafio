package br.edu.fiap.marketplace.service;

import br.edu.fiap.marketplace.dto.AtualizarQuantidadeRequest;
import br.edu.fiap.marketplace.dto.CarrinhoRequest;
import br.edu.fiap.marketplace.dto.CarrinhoResponse;
import br.edu.fiap.marketplace.dto.ConfirmacaoPagamentoResponse;
import br.edu.fiap.marketplace.entity.Carrinho;
import br.edu.fiap.marketplace.entity.CatalogoProduto;
import br.edu.fiap.marketplace.entity.Usuario;
import br.edu.fiap.marketplace.exception.*;
import br.edu.fiap.marketplace.repository.CarrinhoRepository;
import br.edu.fiap.marketplace.repository.CatalogoProdutoRepository;
import br.edu.fiap.marketplace.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/** TODO coordenar usuário, produto, estoque, quantidade e estado do carrinho. */
@Service
public class CarrinhoService {

    private CarrinhoRepository repository;
    private UsuarioRepository usuarioRepository;
    private CatalogoProdutoRepository catalogoProdutoRepository;

    public CarrinhoService(
            CarrinhoRepository carrinhoRepository,
            UsuarioRepository usuarioRepository,
            CatalogoProdutoRepository catalogoProdutoRepository
    ){
        this.repository = carrinhoRepository;
        this.usuarioRepository = usuarioRepository;
        this.catalogoProdutoRepository = catalogoProdutoRepository;
    }


    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    private CatalogoProduto buscarProduto(Long id) {
        return catalogoProdutoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException());
    }

    private Carrinho buscarCarrinho(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new CarrinhoNaoEncontradoException());
    }

    @Transactional
    public CarrinhoResponse criar(CarrinhoRequest request){
        Usuario usuario = buscarUsuario(request.usuarioId());
        CatalogoProduto catalogoProduto = buscarProduto(request.produtoId());

        if (request.quantidade() <=0 ){
            throw new AlteracaoQuantidadeCarrinhoException();
        }

        Carrinho carrinho = new Carrinho(usuario, catalogoProduto, request.quantidade());

        return CarrinhoResponse.de(repository.save(carrinho));
    }


    @Transactional
    public CarrinhoResponse buscar(Long id) {
        return CarrinhoResponse.de(buscarCarrinho(id));
    }

    @Transactional
    public List<CarrinhoResponse> buscarCarrinhoDoUsuario(Long usuarioId){
        buscarUsuario(usuarioId);
        return repository.findByUsuario_IdOrderByCriadoEmDesc(usuarioId)
                .stream().map(CarrinhoResponse::de).toList();
    }

    @Transactional
    public CarrinhoResponse atualizarQuantidade(Long id, AtualizarQuantidadeRequest request){
        Carrinho carrinho = buscarCarrinho(id);
        try {
            int quantidade = request.quantidade();
            carrinho.alterarQuantidade(quantidade);
        } catch (ConflitoDeNegocioException err){
            throw new AlteracaoQuantidadeCarrinhoException();
        }
        repository.save(carrinho);
        return CarrinhoResponse.de(carrinho);
    }


    @Transactional
    public CarrinhoResponse cancelar(Long id){
        Carrinho carrinho = buscarCarrinho(id);
        carrinho.cancelar();
        repository.save(carrinho);
        return CarrinhoResponse.de(carrinho);
    }


}
