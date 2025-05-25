package com.ptbdams.apicrud.controller;

import com.ptbdams.apicrud.model.Categoria;
import com.ptbdams.apicrud.model.Produto;
import com.ptbdams.apicrud.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categoria", description = "Gerenciamento de categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Lista todas as categorias")
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso")
    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listarTodos();
    }

    @Operation(summary = "Cria uma nova categoria")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso")
    @PostMapping
   public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.salvar(categoria));
    }

    @Operation(description = "Lista uma categoria por ID")
    @ApiResponse(responseCode = "200", description = "Categoria listada com sucesso")
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscar(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @Operation(summary = "Lista todos os produtos de uma categoria")
    @ApiResponse(responseCode = "200", description = "Lista de produtos da categoria retornada com sucesso")
    @GetMapping("/{id}/produtos")
    public ResponseEntity<List<Produto>> listarProdutosDaCategoria(@PathVariable Long id) {
        List<Produto> produtos = categoriaService.listarProdutosDaCategoria(id);
        return ResponseEntity.ok(produtos);
    }

    @Operation(description = "Deleta uma categoria")
    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}