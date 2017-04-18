/*
 * 
 */
package br.com.algoritmogenetico.main;

import br.com.algoritmogenetico.utils.GerenciadorDePopulacao;

/**
 *
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class AlgoritmoGenetico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GerenciadorDePopulacao gerenciadorDePopulacao = new GerenciadorDePopulacao(0, 31, 4);
        
        gerenciadorDePopulacao.definirPopulacaoInicial();
    }
    
}
