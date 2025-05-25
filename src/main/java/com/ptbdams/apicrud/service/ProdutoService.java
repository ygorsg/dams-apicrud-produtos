package com.ptbdams.apicrud.service;

import com.ptbdams.apicrud.exception.RecursoNaoEncontradoException;
import com.ptbdams.apicrud.model.Produto;
import com.ptbdams.apicrud.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void atualizar(Long id, Produto produto) throws Exception{
        Produto produtoExistente = produtoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID" + id + "não encontrado"));
        produtoExistente.setId(produto.getId());
        produtoExistente.setNome(produto.getNome());
        produtoExistente.setPreco(produto.getPreco());
        produtoRepository.save(produtoExistente);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID " + id + " não encontrado."));
    }

    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Produto com ID " + id + " não encontrado.");
        }
        produtoRepository.deleteById(id);
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }
}
