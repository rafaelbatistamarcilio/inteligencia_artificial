package br.com.algoritmogenetico.utils;

import java.util.ArrayList;

/**
 * classe que defini a evolução de uma população para resolver o problema do
 * limite de uma função f(x) = 
 *
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class GerenciadorDePopulacao {

    /**
     * intervalo da populacao
     */
    private int incioDoIntervaloDaPupulacao;
    private int fimDoIntervaloDaPupulacao;
    private int numeroDeIndividuosDaPopulacaoInicial;

    /** histórico de populações */
    private ArrayList<Populacao> populacoes;

    /**
     * Para instanciar uma evolução deve-se informar os dados básicos para gerar a população inicial
     * 
     * OBS: passar ponto de crossover e percentual de mutacao, quantas gerações
     * @param incioDoIntervaloDaPupulacao
     * @param fimDoIntervaloDaPupulacao
     * @param numeroDeIndividuosDaPopulacaoInicial 
     */
    public GerenciadorDePopulacao(
            int incioDoIntervaloDaPupulacao,
            int fimDoIntervaloDaPupulacao,
            int numeroDeIndividuosDaPopulacaoInicial) {
            //passar ponto de crossover 
            //percentual de mutacao
            //quantas gerações
            //quantos % da população vai cruzar
        
        this.incioDoIntervaloDaPupulacao = incioDoIntervaloDaPupulacao;
        this.fimDoIntervaloDaPupulacao = fimDoIntervaloDaPupulacao;
        this.numeroDeIndividuosDaPopulacaoInicial = numeroDeIndividuosDaPopulacaoInicial;
        this.populacoes = new ArrayList<>();
    }
    
    /** define a população inicial */
    public void definirPopulacaoInicial() {
        Populacao populacao = new Populacao();
        ArrayList<Individuo> individuos = new ArrayList<>();

        for (int i = 0; i < this.numeroDeIndividuosDaPopulacaoInicial; i++) {
            individuos.add( Individuo.gerarIndividuoAleatorio(this.incioDoIntervaloDaPupulacao, this.fimDoIntervaloDaPupulacao) );
        }
        
        populacao.setIndividuos(individuos);
        this.populacoes.add(populacao);
    }
    
    /** evolui a população uma geração */
    public void evoluirPopulacao(){
        
    }
    
    /**
     * evolui a população em numeroDeGeracoes
     * @param numeroDeGeracoes - quantidade de gerações que devem ser evoluidas
     */
    public void evoluirGeracoes(int numeroDeGeracoes){
        
    }
}
