package br.com.algoritmogenetico.utils;

import java.util.Random;

/**
 *
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class Individuo {
    
    private int descricao;
    private String cadeia;
    private double fitness;
    private double participacao;
    private double acumulado;
    
    
    public Individuo(){
        
    }

    /**
     * @return the descricao
     */
    public int getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(int descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the cadeia
     */
    public String getCadeia() {
        return cadeia;
    }

    /**
     * @param cadeia the cadeia to set
     */
    public void setCadeia(String cadeia) {
        this.cadeia = cadeia;
    }

    /**
     * @return the fitnes
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitnes to set
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * @return the participacao
     */
    public double getParticipacao() {
        return participacao;
    }

    /**
     * @param participacao the participacao to set
     */
    public void setParticipacao(double participacao) {
        this.participacao = participacao;
    }

    /**
     * @return the acumulado
     */
    public double getAcumulado() {
        return acumulado;
    }

    /**
     * @param acumulado the acumulado to set
     */
    public void setAcumulado(double acumulado) {
        this.acumulado = acumulado;
    }
    
    /**
     * dado um intervalo [m,n], retorna a instância de um indivíduo com descrição e cadeia de bits
     * @param inicioIntervalo
     * @param fimIntervalo
     * @return 
     */
    public static Individuo gerarIndividuoAleatorio(int inicioIntervalo, int fimIntervalo){
        Individuo individuo = new Individuo();        
        
        int aleatorio = (int) (Math.random() * (inicioIntervalo - fimIntervalo)) + fimIntervalo;
        
        individuo.setDescricao(aleatorio);
        individuo.setCadeia( Integer.toBinaryString(aleatorio) );
        
        return individuo;
    }
    
    public Individuo evoluir(){
        Individuo novoIndividuo = new Individuo();
        
        return novoIndividuo;
    }
}
