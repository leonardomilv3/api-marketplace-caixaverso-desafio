package br.edu.fiap.marketplace.service;

import java.util.List;
import org.springframework.stereotype.Service;
import br.edu.fiap.marketplace.dto.AtualizarEstoqueRequest;
import br.edu.fiap.marketplace.dto.CatalogoProdutoRequest;
import br.edu.fiap.marketplace.dto.CatalogoProdutoResponse;
import br.edu.fiap.marketplace.entity.CatalogoProduto;
import br.edu.fiap.marketplace.exception.ProdutoNaoEncontradoException;
import br.edu.fiap.marketplace.repository.CatalogoProdutoRepository;
import jakarta.transaction.Transactional;

/** TODO implementar cadastro, listagem, busca e alterações do catálogo. */
@Service
public class CatalogoProdutoService {

    private final CatalogoProdutoRepository catalogoProdutoRepository;

    public CatalogoProdutoService(CatalogoProdutoRepository catalogoProdutoRepository) {
        this.catalogoProdutoRepository = catalogoProdutoRepository;
    }


    private CatalogoProduto buscarProduto(Long id) {
        return catalogoProdutoRepository.findById(id)
            .orElseThrow(() -> new ProdutoNaoEncontradoException());
    }

    @Transactional 
    public List<CatalogoProduto> buscar() {
        return catalogoProdutoRepository.findAll();
    }

    @Transactional 
    public CatalogoProdutoResponse buscar(Long id) {
        return CatalogoProdutoResponse.de(buscarProduto(id));
    }

    @Transactional 
    public CatalogoProdutoResponse cadastrar(CatalogoProdutoRequest request) {
        CatalogoProduto produto = new CatalogoProduto(
            request.nome(), request.descricao(), request.preco(), 
            request.estoque(), request.ativo());

        return CatalogoProdutoResponse.de(catalogoProdutoRepository.save(produto));
    }

    @Transactional 
    public CatalogoProdutoResponse atualizar(Long id, CatalogoProdutoRequest request) {
        CatalogoProduto produto = buscarProduto(id);
        produto.atualizar(request.nome(), request.descricao(), request.preco());
        return CatalogoProdutoResponse.de(catalogoProdutoRepository.save(produto));
    }


    @Transactional 
    public CatalogoProdutoResponse reporEstoque(Long id, AtualizarEstoqueRequest request) {
        CatalogoProduto produto = buscarProduto(id);
        produto.reporEstoque(request.quantidade());
        return CatalogoProdutoResponse.de(catalogoProdutoRepository.save(produto));
    }


    @Transactional 
    public CatalogoProdutoResponse diminuirEstoque(Long id, AtualizarEstoqueRequest request) {
        CatalogoProduto produto = buscarProduto(id);
        produto.baixarEstoque(request.quantidade());
        return CatalogoProdutoResponse.de(catalogoProdutoRepository.save(produto));
    }


    @Transactional
    public CatalogoProdutoResponse ativar(Long id) {
        CatalogoProduto produto = buscarProduto(id);
        produto.ativar();
        return CatalogoProdutoResponse.de(catalogoProdutoRepository.save(produto));
    }


    @Transactional
    public CatalogoProdutoResponse desativar(Long id) {
        CatalogoProduto produto = buscarProduto(id);
        produto.desativar();
        return CatalogoProdutoResponse.de(catalogoProdutoRepository.save(produto));
    }


}
