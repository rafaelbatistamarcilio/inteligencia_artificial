package br.com.algoritmogenetico.utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * classe que defini a evolução de uma população para resolver o problema do
 * limite de uma função f(x) = (-X ^ 2) + 33 * X
 *
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class GerenciadorDePopulacao {

    /**
     * inicio do intervalo da populacao
     */
    private int incioDoIntervaloDaPupulacao;

    /**
     * fim do intervalo da populacao Ex [0,35]
     */
    private int fimDoIntervaloDaPupulacao;

    /**
     * quantidade de indivíduos da população inicial
     */
    private int numeroDeIndividuosDaPopulacaoInicial;

    /**
     * percentual de indivíduos que irá reproduzir
     */
    private double percentualDaPopulacaoQueIraReproduzir;

    /**
     * ponto de crossover dos cromossomos
     */
    private int pontoDeCrossOver;

    /**
     *
     */
    private double probabilidadeDeMutacao;

    /**
     * histórico de populações
     */
    private ArrayList<Populacao> populacoes;

    /**
     * Para instanciar uma evolução deve-se informar os dados básicos para gerar
     * a população inicial
     *
     * OBS: passar ponto de crossover e percentual de mutacao, quantas gerações
     *
     * @param incioDoIntervaloDaPupulacao
     * @param fimDoIntervaloDaPupulacao
     * @param numeroDeIndividuosDaPopulacaoInicial
     * @param percentualDaPopulacaoQueIraReproduzir
     * @param pontoDeCrossOver
     * @param probabilidadeDeMutacao
     */
    public GerenciadorDePopulacao(
            int incioDoIntervaloDaPupulacao,
            int fimDoIntervaloDaPupulacao,
            int numeroDeIndividuosDaPopulacaoInicial,
            double percentualDaPopulacaoQueIraReproduzir,
            int pontoDeCrossOver,
            double probabilidadeDeMutacao) throws Exception{

        this.incioDoIntervaloDaPupulacao = incioDoIntervaloDaPupulacao;
        this.fimDoIntervaloDaPupulacao = fimDoIntervaloDaPupulacao;
        this.numeroDeIndividuosDaPopulacaoInicial = numeroDeIndividuosDaPopulacaoInicial;
        this.percentualDaPopulacaoQueIraReproduzir = percentualDaPopulacaoQueIraReproduzir;
        this.pontoDeCrossOver = pontoDeCrossOver;
        this.probabilidadeDeMutacao = probabilidadeDeMutacao;
        this.populacoes = new ArrayList<>();
        
        try {
            this.definirPopulacaoInicial();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }   
    
    /**
     * recupera a população com maior fitnnes
     * @return 
     */
    public Populacao getPopulacaoMaisApta(){
        Populacao maisApta = this.populacoes.get(0);

        for (Populacao populacao : this.populacoes) {
            maisApta = maisApta.getFitnessDaPopulacao()> populacao.getFitnessDaPopulacao() ? maisApta : populacao;
        }
        
        return maisApta;
    }

    /**
     * define a população inicial gerando indivíduos aleatoriamente a quantidade
     * de indivíduos gerados é informada no construtor da classe
     */
    public void definirPopulacaoInicial() throws Exception{
        Populacao populacao = new Populacao();
        ArrayList<Individuo> individuos = new ArrayList<>();

        try {
            for (int i = 0; i < this.numeroDeIndividuosDaPopulacaoInicial; i++) {
                individuos.add(Individuo.gerarIndividuoAleatorio(this.incioDoIntervaloDaPupulacao, this.fimDoIntervaloDaPupulacao));
            }

            populacao.setIndividuos(individuos);
            populacao.executarCalculos(numeroDeIndividuosDaPopulacaoInicial, percentualDaPopulacaoQueIraReproduzir, pontoDeCrossOver, probabilidadeDeMutacao);
            this.populacoes.add(populacao);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * gera a próxima geração de a partir da última geração
     */
    public void gerarProximaGeracao() throws Exception {
        int ultimaGeracao = this.populacoes.size();
        Populacao proximaGeracao = null;
        try {
            proximaGeracao = this.populacoes.get(ultimaGeracao - 1).getProximaGeracao();
            proximaGeracao.executarCalculos(numeroDeIndividuosDaPopulacaoInicial, percentualDaPopulacaoQueIraReproduzir, pontoDeCrossOver, probabilidadeDeMutacao);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        this.populacoes.add(proximaGeracao);
    }

    /**
     * avança a população na quantidade de gerações passada por parâmetro
     *
     *
     * @param numeroDeGeracoes - quantidade de gerações que devem ser avançadas
     */
    public void avancarGeracoes(int numeroDeGeracoes) throws Exception {
        try {
            for (int i = 0; i < numeroDeGeracoes; i++) {
                this.gerarProximaGeracao();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * @return lista de populações
     */
    public ArrayList<Populacao> getPopulacoes() {
        return this.populacoes;
    }

    public static int getAleatorioInt(int inicioIntervalo, int fimIntervalo) {
        Random rand = new Random();
        return rand.nextInt(fimIntervalo - inicioIntervalo) + inicioIntervalo;
    }

    public static double getAleatorioDouble(int inicioIntervalo, int fimIntervalo) {
        Random rand = new Random();
        return inicioIntervalo + rand.nextDouble() * (fimIntervalo - inicioIntervalo);
    }
}
