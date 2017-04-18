package busca.estrela;

import br.com.buscaestrela.utils.ListaDeBuscaEstrela;

/**
 *
 * @author Rafael Marcílio <rafaelbatistamarcilio@gmail.com>
 */
public class BuscaEstrela {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ListaDeBuscaEstrela listaDeBuscaEstrela = new ListaDeBuscaEstrela();

        try {
            listaDeBuscaEstrela.carregarGrafo("grafo-estrela.txt");
            listaDeBuscaEstrela.carregarHeuristicas("heuristicas.txt");
            String[] destino = new String[1];
            destino[0] = "f";
            listaDeBuscaEstrela.carregarMelhorCaminho(destino);
        } catch (Exception e) {
            System.err.println("ERRO:" + e.getMessage());
        }

        if (listaDeBuscaEstrela.getCaminho().size() > 0) {
            for (String no : listaDeBuscaEstrela.getCaminho()) {
                System.out.print(no + "->");
            }
            System.out.println("");
            System.out.println("CUSTO: "+listaDeBuscaEstrela.getCaminhoEncontrado().getCusto());
        }
    }
    
}
