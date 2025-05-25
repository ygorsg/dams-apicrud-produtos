package com.ptbdams.apicrud.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa um desconto aplicado a um produto")
public class Desconto {
    String nome;
    double precoOriginal;
    int descontoPercentual;
    String porcentagem;
    double precoFinal;

    public int getDescontoPercentual() { return descontoPercentual; }
    public void setDescontoPercentual(int percentual) { this.descontoPercentual = percentual; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getPorcentagem() { return porcentagem; }
    public void setPorcentagem(int percentual) { this.porcentagem =  percentual+"%"; }
    public Double getPrecoOriginal() { return precoOriginal; }
    public void setPrecoOriginal(double preco) {this.precoOriginal = preco; }
    public Double getPrecoFinal() { return precoFinal; }
    public void setPrecoFinal(double preco) {this.precoFinal = preco; }

    public void calcularPrecoFinal() {
       double desconto = this.precoOriginal * (this.descontoPercentual / 100.0);
        this.setPrecoFinal(this.precoOriginal - desconto);
    }

}