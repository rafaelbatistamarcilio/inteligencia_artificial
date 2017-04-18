package br.com.buscaestrela.utils;

import java.util.List;

/**
 * definição de um nó do grafo
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class No {
    private String nome;
    
    //usado para montar a lista com o melhor caminho
    private No pai;
    
    //usado para referenciar o custo até o nó
    private int custo;
    private List<No> filhos;

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the custo
     */
    public int getCusto() {
        return custo;
    }

    /**
     * @param custo the custo to set
     */
    public void setCusto(int custo) {
        this.custo = custo;
    }

    /**
     * @return the filhos
     */
    public List<No> getFilhos() {
        return filhos;
    }

    /**
     * @param filhos the filhos to set
     */
    public void setFilhos(List<No> filhos) {
        this.filhos = filhos;
    }

    public No getPai() {
        return pai;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }
}
