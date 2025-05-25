package com.ptbdams.apicrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Schema(description = "Representa um produto")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 60, message = "Nome deve ter entre 3 e 60 caracteres")
    private String nome;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    @DecimalMax(value = "10000.00", message = "Preço não pode ser maior que R$ 10.000,00")
    private Double preco;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    Categoria categoria;

    public Categoria getCategoria() { return this.categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) throws Exception {
        if(this.nome.contains("Promoção")&& preco > 500) {
        throw new Exception("Produto em promoção: não pode ter valor superior a R$500,00");
        } else { this.preco = preco;}
    }
}