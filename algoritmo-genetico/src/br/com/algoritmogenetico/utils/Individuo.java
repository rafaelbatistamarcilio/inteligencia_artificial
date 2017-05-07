package br.com.algoritmogenetico.utils;

/**
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class Individuo {

    /**
     * descrição do indivíduo Ex: 0
     */
    private int descricao;
    private String cadeia;
    private double fitness;
    private double participacao;
    private double acumuladoMinimo;
    private double acumuladoMaximo;

    public Individuo() {

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
    public double getAcumuladoMinimo() {
        return acumuladoMinimo;
    }

    /**
     * @param acumuladoMinimo the acumulado to set
     */
    public void setAcumuladoMinimo(double acumuladoMinimo) {
        this.acumuladoMinimo = acumuladoMinimo;
    }

    public double getAcumuladoMaximo() {
        return acumuladoMaximo;
    }

    public void setAcumuladoMaximo(double acumuladoMaximo) {
        this.acumuladoMaximo = acumuladoMaximo;
    }

    /**
     * dado um intervalo [m,n], retorna a instância de um indivíduo com
     * descrição e cadeia de bits
     *
     * @param inicioIntervalo
     * @param fimIntervalo
     * @return
     */
    public static Individuo gerarIndividuoAleatorio(int inicioIntervalo, int fimIntervalo) {
        Individuo individuo = new Individuo();

        int aleatorio = GerenciadorDePopulacao.getAleatorioInt(inicioIntervalo,fimIntervalo);

        individuo.setDescricao(aleatorio);
        
        String descricaoEmBinario = Integer.toBinaryString(aleatorio);
        String zerosAEsquerda = "";
        int bitsNecessarios = (int) Math.sqrt(fimIntervalo - inicioIntervalo + 1) ;
        //completar os zeros a esquerda da cadeia
        for (int i = 0; i < bitsNecessarios - descricaoEmBinario.length(); i++) {
            zerosAEsquerda+="0";
        }
        String cromossomos = zerosAEsquerda+descricaoEmBinario;
        
        individuo.setCadeia(cromossomos);

        return individuo;
    }

    public Individuo evoluir() {
        Individuo novoIndividuo = new Individuo();

        return novoIndividuo;
    }
}
