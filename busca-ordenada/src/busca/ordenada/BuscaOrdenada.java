/*
 * 
 */
package busca.ordenada;

import br.com.buscaordenada.utils.ListaDeBuscaOrdenada;

/**
 *
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class BuscaOrdenada {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ListaDeBuscaOrdenada listaDeBuscaOrdenada = new ListaDeBuscaOrdenada();

        try {
            listaDeBuscaOrdenada.carregarGrafo("grafo-2.txt");

            String[] destino = new String[1];
            destino[0] = "f";
            listaDeBuscaOrdenada.carregarMelhorCaminho(destino);
        } catch (Exception e) {
            System.err.println("ERRO:" + e.getMessage());
        }

        if (listaDeBuscaOrdenada.getCaminho().size() > 0) {
            for (String no : listaDeBuscaOrdenada.getCaminho()) {
                System.out.print(no + "->");
            }
            System.out.println("");
            System.out.println("CUSTO: "+listaDeBuscaOrdenada.getCaminhoEncontrado().getCusto());
        }
    }

}
