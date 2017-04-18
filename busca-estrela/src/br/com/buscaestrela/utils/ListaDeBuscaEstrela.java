package br.com.buscaestrela.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/** @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com> */
public class ListaDeBuscaEstrela extends ListaDeBuscaOrdenada{
    
    private HashMap<String,Integer> heuristicas;
    
    public ListaDeBuscaEstrela(){
        super();
        this.heuristicas = new HashMap<>();
    }
    
    public void carregarHeuristicas(String path){
        try {
            FileReader arq = new FileReader(path);
            BufferedReader lerArq = new BufferedReader(arq);

            String heuristica = lerArq.readLine();

            while (heuristica != null) {
                //extraindo informações do nó 
                String no = heuristica.split("=")[0];
                Integer valor = Integer.parseInt(heuristica.split("=")[1]);

                this.heuristicas.put(no, valor);
                // lê até a última linha
                heuristica = lerArq.readLine();
            }

            arq.close();
        } catch (Exception e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }
    
    @Override
    protected int calcularCusto(No pai, No filho){
        //se for o nó pai não precisa remover a heuristica porque o custo é 0
        int custoDoPai = pai.getCusto()== 0 ? 0 : pai.getCusto() - this.heuristicas.get(pai.getNome());
        
        return  custoDoPai + filho.getCusto() + this.heuristicas.get(filho.getNome()) ;
    }
}
