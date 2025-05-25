package com.ptbdams.apicrud.controller;

import com.ptbdams.apicrud.model.Desconto;
import com.ptbdams.apicrud.model.Produto;
import com.ptbdams.apicrud.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Validated
@Tag(name = "Produto", description = "Gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(description = "Cria um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produto));
    }

    @Operation(description = "Lista todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public List<Produto> listar() {
        return produtoService.listarTodos();
    }

    @Operation(description = "Busca um produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public Produto buscar(@Parameter(description = "ID do produto a ser buscado") @PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    @Operation(description = "Deleta um produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @Parameter(description = "ID do produto a ser deletado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Atualiza um produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@Parameter(description = "ID do produto a ser atualizado") @PathVariable Long id, @Valid @RequestBody Produto produto) throws Exception {
        produtoService.atualizar(id, produto);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Busca produtos por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos encontrados"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @GetMapping("/buscar")
    public List<Produto> buscarPorNome(@Parameter(description = "Nome do produto a ser buscado") @RequestParam String nome) {
        return produtoService.buscarPorNome(nome);
    }

    @Operation(description = "Calcula o desconto de um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desconto calculado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @Parameter(description = "ID do produto para o qual o desconto será aplicado")
    @GetMapping("/{id}/desconto")
    public ResponseEntity<Desconto> calculoPrecoDesconto(@PathVariable long id, @RequestParam int percentual) {
        Desconto desconto = new Desconto();
        produtoService.buscarPorId(id);
        if(percentual > 50)
            return ResponseEntity.badRequest().build();
        double preco = produtoService.buscarPorId(id).getPreco();
        desconto.setNome(produtoService.buscarPorId(id).getNome());
        desconto.setPrecoOriginal(preco);
        desconto.setDescontoPercentual(percentual);
        desconto.setPorcentagem(percentual);
        desconto.calcularPrecoFinal();
        return ResponseEntity.ok(desconto);
    }
}
