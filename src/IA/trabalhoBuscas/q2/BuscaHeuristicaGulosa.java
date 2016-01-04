package IA.trabalhoBuscas.q2;

import java.util.ArrayList;
import java.util.List;
import IA.trabalhoBuscas.BuscaTemplate;

public class BuscaHeuristicaGulosa extends BuscaTemplate<EstadoOitoRainhas> {

//    private final int estadoInicial[] = {7, 5, 3, 1, 6, 8, 2, 4};
    private final int estadoInicial[] = {8, 5, 3, 1, 6, 8, 2, 5};
    private static int ataques = 0;
    static final int QUANTIDADE_RAINHAS = 8; // N rainhas do jogo de xadrez em um tabuleiro N x N (Gauss)

    /**
     * Construtor da Classe
     */
    public BuscaHeuristicaGulosa() {
//        super(EstadoOitoRainhas.class);
        listaAberta = new ArrayList<>();
        listaFechada = new ArrayList<>();
        listaCaminho = new ArrayList<>();
        estadoAtual = new EstadoOitoRainhas();
    }

    /**
     * Ordena os Estados encontrados por seu custo, de acordo com a
     * 'funcaoHeuristica' feita em cada um deles. Com a lista em ordem
     * crescente, caso ocorra igualdade entre custos de estados distintos, tem
     * prioridade aquele que é Nó-Pai
     */
    void ordenarFila(List<EstadoOitoRainhas> lista) {
        EstadoOitoRainhas key;
        int i, j;
        for (j = 1; j < lista.size(); j++) { // ordena de forma crescente os nós por seus custos
            key = lista.get(j);
            for (i = j - 1; (i >= 0) && (lista.get(i).getCusto()
                    /**
                     * '>' em ordem crescente
                     */
                    > /**
                     * '<' em ordem decrescente
                     */
                    key.getCusto()); i--) {
                lista.set(i + 1, lista.get(i));
            }
            lista.set(i + 1, key);
        }
    }

    /**
     * Expande os 7 Nós derivados da mudança de linha de uma rainha dentro de
     * uma única coluna. A cada interação do laço no algoritmo, muda-se a rainha
     * da coluna adjacente (aumenta um nível de profundidade) na árvore de Nós
     * expandidos. Até a profundidade nível 8, quando reinicia o nível de
     * profundidade na árvore de buscas para 1
     */
    void expandir(EstadoOitoRainhas temp) {
        int novoTabuleiro[];
        for (int i = 1; i < 9; i++) {
            if (i != temp.getTabuleiro()[profundidade]) {// expande apenas a coluna atual (7 filhos)
                novoTabuleiro = new int[8];
                System.arraycopy(temp.getTabuleiro(), 0, novoTabuleiro, 0, QUANTIDADE_RAINHAS);
                novoTabuleiro[profundidade] = i;
                listaAberta.add(new EstadoOitoRainhas(novoTabuleiro, temp, profundidade));
            }
        }
    }

    /**
     * Tambem atribui um valor inteiro para o atributo 'custo' do objeto
     * 'EstadoOitoRainhas' passado como parametro
     */
    int funcaoHeuristica(EstadoOitoRainhas temp) {
        ataques = 0;
        /**
         * verificar diagonais
         */
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (Math.abs(i - j) == Math.abs(temp.getTabuleiro()[i] - temp.getTabuleiro()[j])) {
                    ataques++;
                }
            }
        }
        /**
         * verificar horizontal
         */
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (temp.getTabuleiro()[i] == temp.getTabuleiro()[j]) {
                    ataques++;
                }
            }
        }
        /**
         * verificar vertical: não é necessário pois cada coluna deve conter
         * obrigatoriamente uma única rainha, cada posição do array
         * 'estadoInicial[]' representa uma rainha
         */

//        System.out.print(ataques + " <-> ");
//        temp.tabuleiroToString();
        return ataques;
    }

    @Override
    public void preLoop() {
        listaAberta.add(new EstadoOitoRainhas(estadoInicial, null, profundidade));
        listaAberta.get(0).setCusto(funcaoHeuristica(listaAberta.get(0)));
    }

    @Override
    public void loop() { //erro aki
        if (estadoAtual == null) {
            estadoAtual = listaAberta.remove(0);
            profundidade = estadoAtual.getNivel();
        } else if (listaAberta.get(0).getCusto() < estadoAtual.getCusto()) {
            System.out.println("| Novo nó-pai |");
            estadoAtual = listaAberta.remove(0);
            profundidade = estadoAtual.getNivel();
            listaFechada.addAll(listaAberta);
            listaAberta.clear();
        } else {
            System.out.println("| Repete nó-pai |");
//            listaFechada.remove(estadoAtual);
            listaFechada.addAll(listaAberta);
            listaAberta.clear();
//            listaAberta.add(estadoAtual);
        }
        estadoAtual.tabuleiroToString();
        System.out.println("<-> " + estadoAtual.getCusto());
        listaFechada.add(estadoAtual);
        System.out.println("------ Expandir nó: coluna " + (profundidade + 1) + " ------");
        expandir(estadoAtual);
        for (EstadoOitoRainhas no : listaAberta) { // atribui custo aos nós
            if (no.getCusto() == -1) {             // apenas aos q ñ possuem ainda
                no.setCusto(funcaoHeuristica(no));
            }
            no.tabuleiroToString();
            System.out.println("<-> " + no.getCusto());
        }
        System.out.println("------ > ------- < ------");
        ordenarFila(listaAberta);
        atualizarColuna();
//        }
    }

    @Override
    public void posLoop() {
        if (estadoAtual != null) {
            refazerCaminho(estadoAtual);
            System.out.println("Sequência de passos para solução:");
            for (int i = listaCaminho.size() - 1; i >= 0; i--) { // imprime o caminho para a solução encontrada
                System.out.println(listaCaminho.get(i).toString());
            }
        }
    }

    @Override
    public boolean condicaoParada() {
        if (listaAberta.isEmpty()) {
            estadoAtual = null;
            System.out.println("--- Resultado NÃO encontrado! ---");
            System.out.println("Total de estados escontrados: " + (listaAberta.size() + listaFechada.size()));
            return false;
        } else if (listaAberta.get(0).getCusto() == 0) {
            estadoAtual = listaAberta.remove(0);
            System.out.println("--- Resultado encontrado! ---");
            System.out.println("Total de estados escontrados: " + (listaAberta.size() + listaFechada.size()));
            return false;
        }
        return true;
    }

    /**
     * Modifica o valor da variavel 'profundidade' para percorrer todas as
     * colunas do tabuleiro, e entao repetir o processo ciclicamente
     */
    static void atualizarColuna() {
        if (profundidade < 7) {
            profundidade++;
//            return profundidade;
        } else {
            profundidade = 0;
//            return profundidade;
        }
    }

    /**
     * preenche a 'listaCaminho' com os estados na sequência do início à solução
     */
    public void refazerCaminho(EstadoOitoRainhas estado) {
        EstadoOitoRainhas temp = estado;
        do {
            listaCaminho.add(temp);
            temp = temp.getEstadoPai();
        } while (temp != null);
    }

}
