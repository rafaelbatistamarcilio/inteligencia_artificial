/*
 * 
 */
package br.com.algoritmogenetico.main;

import br.com.algoritmogenetico.utils.GerenciadorDePopulacao;
import br.com.algoritmogenetico.utils.Individuo;
import br.com.algoritmogenetico.utils.Populacao;
import java.util.ArrayList;

/**
 *
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class AlgoritmoGenetico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            //fitnnes de 0 a 32, os maiores são 16 e 17        
//        for(int i=0;i<32;i++){
//            int ca = - (i * i) + 33 * i;
//            System.out.println(i+"= "+ca);
//        }
        try {
            GerenciadorDePopulacao gerenciadorDePopulacao = new GerenciadorDePopulacao(0, 31, 5, 0.5, 3, 0.3);
            gerenciadorDePopulacao.avancarGeracoes(500);

            AlgoritmoGenetico.gerarRelatorioGeral(gerenciadorDePopulacao.getPopulacoes());
            AlgoritmoGenetico.gerarRelatorioDaPupulacaoMaisApta(gerenciadorDePopulacao.getPopulacaoMaisApta());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void gerarRelatorioGeral(ArrayList<Populacao> populacoes) {
        int i = 1;

        for (Populacao populacao : populacoes) {
            System.out.println("-> POPULAÇÃO: " + i);
            System.out.println("- FITNNES TOTAL DA POPULAÇÃO: " + populacao.getFitnessDaPopulacao());
            System.out.println("- INDIVIDUO COM MAIOR FITNNES: " + "- INDIVIDUO : "+ populacao.getIndividuoMaisApto().getDescricao() + " - FITNNES:"+populacao.getIndividuoMaisApto().getFitness());

            for (Individuo individuo : populacao.getIndividuos()) {
                System.out.println("- INDIVIDUO : " + individuo.getDescricao() + " - FITNNES:" + individuo.getFitness());
            }

            System.out.println("  ");
            System.out.println("  ");

            i++;
        }
    }
    
    private static void gerarRelatorioDaPupulacaoMaisApta(Populacao populacaomaisApta){    
        
            System.out.println("-> POPULAÇÃO MAIS APTA: ");
            System.out.println("- FITNNES TOTAL DA POPULAÇÃO: " + populacaomaisApta.getFitnessDaPopulacao());
            System.out.println("- INDIVIDUO COM MAIOR FITNNES: " + "- INDIVIDUO : "+ populacaomaisApta.getIndividuoMaisApto().getDescricao() + " - FITNNES:" + populacaomaisApta.getIndividuoMaisApto().getFitness());

            for (Individuo individuo : populacaomaisApta.getIndividuos()) {
                System.out.println("- INDIVIDUO : " + individuo.getDescricao() + " - FITNNES:" + individuo.getFitness());
            }
    }

}
