package br.com.buscaordenada.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * classe que define o mapeamento de um grafo
 *
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class ListaDeBuscaOrdenada {

    /**
     * grafo default
     */
    public static final String PATH = "grafo.txt";

    private List<No> nos;
    private List<No> nosAbertos;
    private List<No> nosFechados;
    private No caminhoEncontrado;

    public ListaDeBuscaOrdenada() {
        this.nos = new ArrayList<>();
        this.nosAbertos = new ArrayList<>();
        this.nosFechados = new ArrayList<>();
    }

    public List<No> getNos() {
        return nos;
    }

    public void setNos(List<No> nos) {
        this.nos = nos;
    }

    public void adicionarNo(No no) {
        this.nos.add(no);
    }

    public List<No> getNosAbertos() {
        return nosAbertos;
    }

    public void setNosAbertos(List<No> nosAbertos) {
        this.nosAbertos = nosAbertos;
    }

    public List<No> getNosFechados() {
        return nosFechados;
    }

    public void setNosFechados(List<No> nosFechados) {
        this.nosFechados = nosFechados;
    }

    public No getCaminhoEncontrado() {
        return caminhoEncontrado;
    }

    public void setCaminhoEncontrado(No caminhoEncontrado) {
        this.caminhoEncontrado = caminhoEncontrado;
    }

    /**
     * carrega o grafo com o arquivo passado pelo path
     *
     * @param path - caminho para o arquivo TXT com o grafo por default, há um
     * arquivo txt na raiz do projeto ("grafo.txt") o formato do arquivo é:
     * linha 1 : nomeDoPonto;filho1=custo;filhoN=custo linha n :
     * nomeDoPonto;filho1=custo;filhoN=custo
     */
    public void carregarGrafo(String path) {
        try {
            FileReader arq = new FileReader(path);
            BufferedReader lerArq = new BufferedReader(arq);

            // lê a primeira linha do grafo
            String informacoesDoNo = lerArq.readLine();

            while (informacoesDoNo != null) {
                //extraindo informações do nó 
                List<String> info = new ArrayList<>(Arrays.asList(informacoesDoNo.split(";")));

                this.adicionarNo(this.carregarNo(info));

                // lê até a última linha
                informacoesDoNo = lerArq.readLine();
            }

            arq.close();
        } catch (Exception e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }

    /**
     * carrega um nó a partir de uma string
     *
     * @param info - informações do nó em string no formato
     * "nomeDono;filho1=custo;filho2=custo... "
     * @return no carregado
     */
    private No carregarNo(List<String> informacoes) {
        No no = new No();
        no.setNome(informacoes.get(0).split("=")[0]);
        no.setCusto(0);
        informacoes.remove(0);
        ArrayList<No> filhos = this.carregarFilhos(informacoes);
        no.setFilhos(filhos);
        return no;
    }

    /**
     * carrega as informações dos filhos de um nó
     *
     * @param informacoes List com informações sobre os filhos de um nó, cada
     * indice possui a informação "nomeDoNo=custo"
     * @return List<No>
     */
    private ArrayList<No> carregarFilhos(List<String> informacoes) {
        List<No> filhos = new ArrayList<No>();

        for (String info : informacoes) {
            No filho = new No();
            filho.setNome(info.split("=")[0]);
            filho.setCusto(Integer.parseInt(info.split("=")[1]));
            filhos.add(filho);
        }

        return (ArrayList<No>) filhos;
    }

    /**
     * monta o melhor caminho
     */
    public void carregarMelhorCaminho(String[] destino) throws Exception {

        if (this.nos.size() > 0) {
            //Faça S Å Base inicial de dados.
            //Ponha S na lista de Abertos,
            this.nosAbertos.add(this.getNos().get(0));
        }

        boolean sucesso = false;

        do {
            //Se a lista de Abertos está vazia,
            if (this.nosAbertos.size() == 0) {
                throw new Exception("FRACASSO");
            }

            //Escolha o vértice N, da lista de Abertos, de custo mínimo;
            No aberto = getAbertoDeMenorCusto();

            //Se N terminal, termine com Sucesso.
            if (chegouAoDestino(aberto.getNome(), destino)) {
                this.caminhoEncontrado = aberto;
                sucesso = true;
            } else {

                //Gere o conjunto M de Filhos de N. Para cada vértice V, de M, faça:
                for (No filho : aberto.getFilhos()) {

                    //Se V está em Fechados, despreze o agora gerado;
                    if (!isInFechados(filho)) {

                        No recemAberto = new No();

                        //Faça custo(V) = custo(N) + custo da aresta de N a V
                        recemAberto.setNome(filho.getNome());
                        recemAberto.setCusto(calcularCusto(aberto, filho));
                        recemAberto.setFilhos(getFilhosPeloNo(recemAberto.getNome()));
                        recemAberto.setPai(aberto);//referenciando o pai para encontrar o melhor caminho

                        //Se V não está nem em Abertos nem em Fechados, coloque V em Abertos;
                        if (!isInFechados(recemAberto) && !isInAbertos(recemAberto)) {
                            this.nosAbertos.add(recemAberto);
                        } else if (isInAbertos(recemAberto)) {
                            
                            //Se V está em Abertos, despreze o de maior custo; desempate de forma arbitrária;
                            for(int i=0;i<this.nosAbertos.size();i++){
                                if (recemAberto.getNome().equals(this.nosAbertos.get(i).getNome()) && recemAberto.getCusto() < this.nosAbertos.get(i).getCusto()) {
                                    this.nosAbertos.set(i, recemAberto);
                                }
                            }
                            
                            for (No no : this.nosAbertos) {
                                if (recemAberto.getNome().equals(no.getNome()) && recemAberto.getCusto() < no.getCusto()) {
                                    no = recemAberto;
                                }
                            }
                        }
                    }
                }
            }

            //colocando o no na lista de fechados
            this.nosAbertos.remove(aberto);
            this.nosFechados.add(aberto);

        } while (!sucesso);
    }
    
    protected int calcularCusto(No pai, No filho){
        return pai.getCusto() + filho.getCusto();
    }

    /**
     * retorna os filhos de um nó do grafo
     *
     * @param nomeDoNo
     * @return
     */
    private ArrayList<No> getFilhosPeloNo(String nomeDoNo) {
        for (No no : this.nos) {
            if (nomeDoNo.equals(no.getNome())) {
                return (ArrayList<No>) no.getFilhos();
            }
        }
        return null;
    }

    private boolean isInAbertos(No no) {

        for (No aberto : this.nosAbertos) {
            if (aberto.getNome().equals(no.getNome())) {
                return true;
            }
        }

        return false;
    }

    private boolean isInFechados(No no) {

        for (No fechado : this.nosFechados) {
            if (fechado.getNome().equals(no.getNome())) {
                return true;
            }
        }

        return false;
    }

    /**
     * verifica se o no passado por parametro é o destino procurado
     *
     * @param noatual
     * @param destinos
     * @return boolean
     */
    private boolean chegouAoDestino(String noatual, String[] destinos) {

        for (String destino : destinos) {
            if (noatual.equals(destino)) {
                return true;
            }
        }

        return false;
    }

    private No getAbertoDeMenorCusto() {

        No noComMenorCusto = this.nosAbertos.get(0);

        for (int i = 1; i < this.nosAbertos.size() - 1; i++) {

            if (noComMenorCusto.getCusto() > this.nosAbertos.get(i).getCusto()) {
                noComMenorCusto = this.nosAbertos.get(i);
            }
        }

        return noComMenorCusto;
    }

    public ArrayList<String> getCaminho() {

        No no = this.caminhoEncontrado;
        ArrayList<String> caminhoInvertido = new ArrayList<>();

        while (no != null) {
            caminhoInvertido.add(no.getNome());
            no = no.getPai();
        }

        ArrayList<String> caminho = new ArrayList<>();

        if (caminhoInvertido.size() > 0) {
            for (int i = caminhoInvertido.size() -1; i >= 0; i--) {
                caminho.add(caminhoInvertido.get(i));
            }
        }

        return caminho;
    }
}
