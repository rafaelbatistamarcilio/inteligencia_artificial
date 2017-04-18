/*
 * 
 */
package br.com.algoritmogenetico.main;

import br.com.algoritmogenetico.utils.Evolucao;

/**
 *
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class AlgoritmoGenetico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Evolucao evolucao = new Evolucao(0, 31, 4);
        
        evolucao.definirPopulacaoInicial();
    }
    
}
