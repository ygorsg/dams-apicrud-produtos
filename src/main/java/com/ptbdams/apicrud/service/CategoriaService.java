package com.ptbdams.apicrud.service;

import com.ptbdams.apicrud.exception.RecursoNaoEncontradoException;
import com.ptbdams.apicrud.model.Categoria;
import com.ptbdams.apicrud.model.Produto;
import com.ptbdams.apicrud.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria com ID " + id + " não encontrada."));
    }

    public List<Produto> listarProdutosDaCategoria(Long categoriaId) {
        Categoria categoria = buscarPorId(categoriaId);
        return categoria.getProdutos();
    }

    public void deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Categoria com ID " + id + " não encontrada.");
        }
        categoriaRepository.deleteById(id);
    }
}
