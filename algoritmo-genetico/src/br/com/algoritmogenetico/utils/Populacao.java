package br.com.algoritmogenetico.utils;

import java.util.ArrayList;

/**
 *
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class Populacao {

    private ArrayList<Individuo> individuos;
    private ArrayList<Individuo> selecionadosParaSobreviver;
    private ArrayList<Individuo[]> casais = new ArrayList<>();
    private ArrayList<Individuo> filhos = new ArrayList<>();

    private double fitness;

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

    public Populacao gerarProximaGeracao(int numeroDeIndividuos, int pontoDeCorteDeCrossover, double percentualDeMutacao) {
        Populacao populacao = new Populacao();

        this.calcularFuncaoObjetivo();
        this.selecionarIndividuos(numeroDeIndividuos);
        this.reproduzir(pontoDeCorteDeCrossover);
        this.mutar(percentualDeMutacao);
        return populacao;
    }

    public void calcularFuncaoObjetivo() {
        for (int i = 0; i < this.individuos.size(); i++) {
            this.fitness += this.calcularFitnessDoIndividuo(i);
            this.calcularParticipacaoDoIndividuo(i);
            this.calcularAcumuladoDoIndividuo(i);
        }
    }

    private double calcularFitnessDoIndividuo(int individuo) {
        double fitnes = 0;

        fitnes = -(this.individuos.get(individuo).getDescricao() ^ 2) + 33 * this.individuos.get(individuo).getDescricao();

        this.individuos.get(individuo).setFitness(fitnes);
        return fitnes;
    }

    private void calcularParticipacaoDoIndividuo(int individuo) {
        double participacao = (this.individuos.get(individuo).getFitness() / this.fitness) * 100;
        this.individuos.get(individuo).setParticipacao(participacao);
    }

    private void calcularAcumuladoDoIndividuo(int individuo) {
        if (individuo == 0) {
            this.individuos.get(individuo).setAcumulado(this.individuos.get(individuo).getParticipacao());
        } else {
            double acumulado = this.individuos.get(individuo - 1).getAcumulado() + this.individuos.get(individuo).getParticipacao();
            this.individuos.get(individuo).setAcumulado(acumulado);
        }
    }

    public void selecionarIndividuos(int quantidadeDeSelecionados) {

        for (int i = 0; i < this.individuos.size(); i++) {
            double aleatorio = (Math.random() * 100) + 1;

            if (i == 0) {
                if (this.individuos.get(0).getAcumulado() <= aleatorio) {
                    this.selecionadosParaSobreviver.add(this.individuos.get(0));
                }
            } else if (this.individuos.get(i - 1).getAcumulado() > aleatorio && this.individuos.get(i).getAcumulado() <= aleatorio) {
                this.selecionadosParaSobreviver.add(this.individuos.get(i));
            }

        }
    }
    
    /**
     * a reprodução é em uma certa porcentagem da população
     * @param pontoDeCorte 
     */
    private void reproduzir(int pontoDeCorte) {
        this.gerarCasais();
        this.executarCrossover(pontoDeCorte);
    }

    private void gerarCasais(){
        ArrayList<Individuo> individuos = new ArrayList<>();

        //copiando individuos selecionados para filtrar casais
        for (Individuo selecionado : this.selecionadosParaSobreviver) {
            Individuo individuo = new Individuo();
            individuo.setDescricao(selecionado.getDescricao());
            individuo.setCadeia(selecionado.getCadeia());
            individuo.setAcumulado(selecionado.getAcumulado());
            individuo.setParticipacao(selecionado.getParticipacao());
            individuo.setFitness(selecionado.getFitness());
            individuos.add(individuo);
        }

        while (individuos.size() > 0) {
            //primeiro elemento do casal
            int aleatorio = (int) (Math.random() * (0 - individuos.size())) + individuos.size();

            Individuo[] casal = new Individuo[2];
            casal[0] = individuos.get(aleatorio);
            individuos.remove(individuos.get(aleatorio));

            //segundo elemento do casal
            aleatorio = (int) (Math.random() * (0 - individuos.size())) + individuos.size();
            casal[1] = individuos.get(aleatorio);
            individuos.remove(individuos.get(aleatorio));

            this.casais.add(casal);
        }        
    }
    
    private void executarCrossover(int pontoDeCorte){
        
        for(Individuo[] casal : this.casais){
            Individuo filho1 = new Individuo();
            String cadeiaInicialPai = casal[0].getCadeia().substring(0,pontoDeCorte-1);
            String cadeiaFinalPai = casal[0].getCadeia().substring(pontoDeCorte);
            
            
            Individuo filho2 = new Individuo();
            String cadeiaInicialMae = casal[1].getCadeia().substring(0,pontoDeCorte-1);
            String cadeiaFinalMae = casal[1].getCadeia().substring(pontoDeCorte);
            
            filho1.setCadeia(cadeiaInicialPai+cadeiaFinalMae);
            filho1.setDescricao(Integer.parseInt(cadeiaInicialPai+cadeiaFinalMae, 2));
            
            filho2.setCadeia(cadeiaInicialMae+cadeiaFinalPai);
            filho2.setDescricao(Integer.parseInt(cadeiaInicialMae+cadeiaFinalPai, 2));
            
            this.filhos.add(filho1);
            this.filhos.add(filho2);
        }
    }
    
    private void mutar(double percentuarDemutacao) {
        int aleatorio = (int) (Math.random() * (0 - this.filhos.size())) + this.filhos.size();
        
        char[] cadeia = this.filhos.get(aleatorio).getCadeia().toCharArray();
        
        for(char cromossomo : cadeia){
            int pm = (int) (Math.random() * (0 - 100)) + 100;
        }
    }
}

//-> MUTAÇÃO:
//	- SETAR PROBABILIDADE DE MUTAÇÃO (PM)
//	- SORTEAR UMA CADEIA PARA MUTAR
//	- PARA CADA ELEMENTO DA CADEIA:
//		- GERAR UM NUMERO ALEATORIO ENTRE 1 E 100
//		- SE O NUMERO GERADO FOR MENOR OU = QUE PM, ENTÃO
//			INVERTER BIT
//	- GERAR LISTA INDIVÍDUOS APÓS A MUTAÇÃO
//
//-> GERAR PRÓXIMA POPULAÇÃO COM A LISTA DE INDIVÍDUOS DA LISTA DE MUTAÇÃO   
