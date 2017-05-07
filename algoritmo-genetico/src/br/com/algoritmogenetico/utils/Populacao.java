package br.com.algoritmogenetico.utils;

import java.util.ArrayList;

public class Populacao {

    private ArrayList<Individuo> individuos;
    private ArrayList<Individuo> selecionadosParaSobreviver;
    private ArrayList<Individuo[]> casais = new ArrayList<>();
    private ArrayList<Individuo> filhos = new ArrayList<>();

    private double fitnessDaPopulacao;

    public Populacao() {

    }

    /**
     * @return the individuos
     */
    public ArrayList<Individuo> getIndividuos() {
        return individuos;
    }

    /**
     * @param individuos the individuos to set
     */
    public void setIndividuos(ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }

    public double getFitnessDaPopulacao() {
        return fitnessDaPopulacao;
    }

    /**
     * @return indivíduo com maior fitnnes
     */
    public Individuo getIndividuoMaisApto() {

        Individuo maisApto = this.selecionadosParaSobreviver.get(0);

        for (Individuo individuo : this.selecionadosParaSobreviver) {
            maisApto = maisApto.getFitness() > individuo.getFitness() ? maisApto : individuo;
        }

        for (Individuo individuo : this.filhos) {
            maisApto = maisApto.getFitness() > individuo.getFitness() ? maisApto : individuo;
        }

        return maisApto;
    }
    
     /**
     * executa a seleção, o cruzamento e mutação dos indivíduos da população
     *
     * @param numeroDeIndividuosParaProximaGeracao
     * @param percentualParaCruzar
     * @param pontoDeCorteDeCrossover
     * @param percentualDeMutacao
     * @throws Exception
     */
    public void executarCalculos(
            int numeroDeIndividuosParaProximaGeracao,
            double percentualParaCruzar,
            int pontoDeCorteDeCrossover,
            double percentualDeMutacao
    ) throws Exception {

        try {
            this.calcularFuncaoObjetivo();
            this.selecionarIndividuosCandidatosASobreviver(numeroDeIndividuosParaProximaGeracao);
            this.reproduzir(percentualParaCruzar, pontoDeCorteDeCrossover);
            this.mutar(percentualDeMutacao);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        
    }

   
    public Populacao getProximaGeracao() throws Exception {

        Populacao proximaGeracao = new Populacao();

        try {
            /**
             * após a mutação, a lista de selecionados contem somente os
             * indivíduos que não cruzaram e a lista de filhos contem os
             * indivíduos resultantes do cruzamento por isso é necessário
             * adicionar a lista de filhos e a lista de selecionados a lista de
             * indivíduos da próxima geração
             */
            ArrayList<Individuo> individuosDaproximaGeracao = new ArrayList<>();
            individuosDaproximaGeracao.addAll(this.selecionadosParaSobreviver);
            individuosDaproximaGeracao.addAll(this.filhos);

            proximaGeracao.setIndividuos(individuosDaproximaGeracao);
            proximaGeracao.calcularFuncaoObjetivo();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return proximaGeracao;
    }

    /**
     * calcula a função objetivo de cada individuo e o total da população
     *
     * @throws Exception - caso a lista de indivíduos esteja vazia
     */
    public void calcularFuncaoObjetivo() throws Exception {

        if (this.individuos.isEmpty()) {
            throw new Exception("A lista de indivíduos está vazia! Gere a população inicial!");
        }

        //calculando o fitnnes de cada indivíduo e o fitnnes total
        for (int i = 0; i < this.individuos.size(); i++) {
            this.fitnessDaPopulacao += this.calcularFitnessDoIndividuo(i);
        }

        //calculando a participação e o acumulado de cada indivíduo
        for (int i = 0; i < this.individuos.size(); i++) {
            this.calcularParticipacaoDoIndividuo(i);
            this.calcularAcumuladoDoIndividuo(i);
        }
    }

    /**
     * calcula o fitnnes de um indivíduo da lista
     ** limite de uma função f(x) = -X² + 33X
     * @param individuo
     * @return fitnnes calculado
     */
    private double calcularFitnessDoIndividuo(int individuo) {
        double fitnes = 0;

        fitnes = - (this.individuos.get(individuo).getDescricao() * this.individuos.get(individuo).getDescricao()) + 33 * this.individuos.get(individuo).getDescricao();

        this.individuos.get(individuo).setFitness(fitnes);
        return fitnes;
    }

    /**
     * calcula a participação
     *
     * @param individuo
     */
    private void calcularParticipacaoDoIndividuo(int individuo) {
        double participacao = (this.individuos.get(individuo).getFitness() / this.fitnessDaPopulacao) * 100;
        this.individuos.get(individuo).setParticipacao(participacao);
    }

    /**
     * calcula o acumulado de cada indivíduo
     *
     * @param individuo
     */
    private void calcularAcumuladoDoIndividuo(int individuo) {
        if (individuo == 0) {

            this.individuos.get(individuo).setAcumuladoMinimo(0.0);
            this.individuos.get(individuo).setAcumuladoMaximo(this.individuos.get(individuo).getParticipacao());

        } else {
            //somando 1 a ultima casa decimal
            double acumuladoMinimo = this.individuos.get(individuo - 1).getAcumuladoMaximo();
            double acumuladoMaximo = this.individuos.get(individuo - 1).getAcumuladoMaximo() + this.individuos.get(individuo).getParticipacao();

            this.individuos.get(individuo).setAcumuladoMinimo(acumuladoMinimo);
            this.individuos.get(individuo).setAcumuladoMaximo(acumuladoMaximo);
        }
    }

    /**
     * executa o método da roleta para selecionar os indivíduos candidatos a
     * sobreviver
     *
     * @param quantidadeDeSelecionados - quantos indivíduos vão sobreviver
     */
    public void selecionarIndividuosCandidatosASobreviver(int quantidadeDeSelecionados) {

        this.selecionadosParaSobreviver = new ArrayList<>();

        while (this.selecionadosParaSobreviver.size() < quantidadeDeSelecionados) {

            double aleatorio = GerenciadorDePopulacao.getAleatorioDouble(0, 100);

            for (Individuo individuo : this.individuos) {
                //se o percentual aleatório estiver dentro do acumulado do indivíduo, o indivíduo é selecionado
                if (aleatorio >= individuo.getAcumuladoMinimo() && aleatorio <= individuo.getAcumuladoMaximo()) {

                    Individuo selecionado = new Individuo();
                    selecionado.setDescricao(individuo.getDescricao());
                    selecionado.setCadeia(individuo.getCadeia());
                    selecionado.setFitness(individuo.getFitness());

                    if (this.selecionadosParaSobreviver.size() < quantidadeDeSelecionados) {
                        this.selecionadosParaSobreviver.add(selecionado);
                    }

                    break;
                }
            }
        }
    }

    /**
     * executa a reprodução
     *
     * @param percentualDeCasaisParaReproduzir - percentual de indivíduos dentre
     * os selecionadosParaSobreviver que vão reproduzir
     * @param pontoDeCorte - ponto de corte da cadeia de cromossomos
     */
    private void reproduzir(double percentualDeCasaisParaReproduzir, int pontoDeCorte) throws Exception {
        try {
            this.gerarCasais(percentualDeCasaisParaReproduzir);
            this.executarCrossover(pontoDeCorte);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * gera os casais aleatorios, a quantidade de casais é baseada no
     * percentualDeCasaisParaReproduzir dentre os indicíduos selecionados para
     * sobreviver indivíduos que vão reproduzir são removidos da lista de
     * selecionadosParaSobreviver para não participar do sorteio da mutação
     *
     * @param percentualDeCasaisParaReproduzir
     */
    private void gerarCasais(double percentualDeCasaisParaReproduzir) {

        //gerando a quantidade de indivíduos que vão cruzar
        int totalParaCruzar = (int) (this.selecionadosParaSobreviver.size() * percentualDeCasaisParaReproduzir);

        while (this.casais.size() * 2 < totalParaCruzar) {
            Individuo[] casal = new Individuo[2];

            //primeiro elemento do casal é removendo da lista de selecionados
            int aleatorio = GerenciadorDePopulacao.getAleatorioInt(0, individuos.size() - 1);
            casal[0] = this.selecionadosParaSobreviver.remove(aleatorio);

            //segundo elemento do casal é removendo da lista de selecionados
            aleatorio = GerenciadorDePopulacao.getAleatorioInt(0, individuos.size() - 1);
            casal[1] = this.selecionadosParaSobreviver.remove(aleatorio);

            this.casais.add(casal);
        }
    }

    /**
     * executa o crossover com os indivíduos selecionados para reproduzir os
     * filhos são adicionados a lista de filhos
     *
     * @param pontoDeCorte - ponto de corte da cadeia de cromossomos
     */
    private void executarCrossover(int pontoDeCorte) {

        for (Individuo[] casal : this.casais) {
            Individuo filho1 = new Individuo();
            String cadeiaInicialPai = casal[0].getCadeia().substring(0, pontoDeCorte);
            String cadeiaFinalPai = casal[0].getCadeia().substring(pontoDeCorte);

            Individuo filho2 = new Individuo();
            String cadeiaInicialMae = casal[1].getCadeia().substring(0, pontoDeCorte);
            String cadeiaFinalMae = casal[1].getCadeia().substring(pontoDeCorte);

            filho1.setCadeia(cadeiaInicialPai + cadeiaFinalMae);
            filho1.setDescricao(Integer.parseInt(cadeiaInicialPai + cadeiaFinalMae, 2));

            filho2.setCadeia(cadeiaInicialMae + cadeiaFinalPai);
            filho2.setDescricao(Integer.parseInt(cadeiaInicialMae + cadeiaFinalPai, 2));

            this.filhos.add(filho1);
            this.filhos.add(filho2);
        }
    }

    /**
     * executa a mutação da cadeia de cromossomos de um indivíduo selecionado
     * aleatoriamente
     *
     * @param probabilidadeDemutacao
     */
    private void mutar(double probabilidadeDemutacao) throws Exception {

        if (this.selecionadosParaSobreviver.isEmpty()) {
            throw new Exception("LISTA DE SELECIONADOS PARA SOBREVIVER VAZIA");
        }

        //sorteando um indivíduo para mutar dentre os indivíduos selecionados para sobreviver que não reproduziram
        int individuoAleatorio = GerenciadorDePopulacao.getAleatorioInt(0, this.selecionadosParaSobreviver.size() - 1);
        char[] cromossomos = this.selecionadosParaSobreviver.get(individuoAleatorio).getCadeia().toCharArray();
        String cadeia = "";
        //mutando cromossomos
        for (int i = 0; i < cromossomos.length; i++) {
            int aleatorioParaMutacao = (int) (Math.random() * (0 - 100)) + 100;

            if (aleatorioParaMutacao > probabilidadeDemutacao) {
                cadeia += cromossomos[i] == '0' ? '1' : '0';
            }
        }

        this.selecionadosParaSobreviver.get(individuoAleatorio).setCadeia(cadeia);
        this.selecionadosParaSobreviver.get(individuoAleatorio).setDescricao(Integer.parseInt(cadeia, 2));
    }

}
