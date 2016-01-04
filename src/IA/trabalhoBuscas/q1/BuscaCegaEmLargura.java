package IA.trabalhoBuscas.q1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

public class BuscaCegaEmLargura {

//    final int estadoInicial[][] = {{4, 6, 2}, {8, 1, 3}, {7, 5, 0}}; // RESULTADO NÃO EXISTE
//    final int estadoInicial[][] = {{6, 4, 2}, {8, 1, 3}, {7, 5, 0}}; // RESULTADO EM LONGO TEMPO
    final int estadoInicial[][] = {{1, 4, 2}, {6, 3, 5}, {0, 7, 8}}; // RESULTADO EM CURTO TEMPO
//    final int estadoInicial[][] = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}}; // RESULTADO JÁ ENCONTRADO
    final int estadoObjetivo[][] = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    List<EstadoPuzzleSlide> listaAberta = new ArrayList<>(4);
    List<EstadoPuzzleSlide> listaFechada = new ArrayList<>();
    List<EstadoPuzzleSlide> listaCaminho = new ArrayList<>();
    EstadoPuzzleSlide estadoAtual;
    int[] espacoVazio = new int[2]; //[0]=linha(cima-baixo);[1]=coluna(esquerda-direita)

    public void algoritmoBusca() { // aqui é que deve ser verificado se já existe um estado expandido
        System.out.println("Realizando buscas...");
        long startTime = System.currentTimeMillis();
        listaAberta.add(new EstadoPuzzleSlide(estadoInicial, null));
        while (!listaAberta.isEmpty()) {
            estadoAtual = listaAberta.remove(0);
            if (verificaEstadoExistente(estadoAtual.getTabuleiro())) {
                listaFechada.add(estadoAtual);
                if (equals(estadoAtual.getTabuleiro(), estadoObjetivo)) {
                    System.out.println("Total de estados escontrados: " + (listaAberta.size() + listaFechada.size()));
                    listaAberta.clear(); // forçar saída do laço 'while'
                } else {
                    expandir(estadoAtual);
                }
            }
        } // fim do laço
        long elapsedTime = System.currentTimeMillis() - startTime;
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(elapsedTime);
        if (equals(estadoAtual.getTabuleiro(), estadoObjetivo)) {
            refazerCaminho(estadoAtual);
            System.out.println("--- Resultado encontrado! ---");
            System.out.println("Tempo decorrido para encontrar solução: " + c.get(Calendar.MINUTE) + "m " + c.get(Calendar.SECOND) + "s " + c.get(Calendar.MILLISECOND) + "ms");
            System.out.println("Número de passos (profundidade) para solução: " + listaCaminho.size());
            System.out.println("Sequência de passos para solução:");
            for (int i = listaCaminho.size() - 1; i >= 0; i--) {
                toString(listaCaminho.get(i).tabuleiro);
            }
        } else {
            System.out.println("--- Resultado NÃO foi encontrado ---");
            System.out.println("Tempo decorrido para verificar todos os nós: " + c.get(Calendar.MINUTE) + "m " + c.get(Calendar.SECOND) + "s " + c.get(Calendar.MILLISECOND) + "ms");
            System.out.println("Como todos os nós de estados possíveis não satisfazem a solução:");
            System.out.println("Pode-se afirmar com certeza que não há solução.");
        }
        System.out.println("-FIM-");
    }

    void refazerCaminho(EstadoPuzzleSlide estado) {
        EstadoPuzzleSlide temp = estado;
        do {
            listaCaminho.add(temp);
            temp = temp.getEstadoPai();
        } while (temp != null);
    }

    boolean equals(int[][] a, int[][] a2) {
//        boolean igual = true;
        if (a == null || a2 == null) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (a[i][j] != a2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    void expandir(EstadoPuzzleSlide no) {
        encontrarEspacoVazio(no.getTabuleiro());
        if (espacoVazio[1] - 1 >= 0) { // esquerda
            listaAberta.add(moverPeca(no, 1, -1));
        }
        if (espacoVazio[0] - 1 >= 0) { // cima
            listaAberta.add(moverPeca(no, 0, -1));
        }
        if (espacoVazio[1] + 1 <= 2) { // direita
            listaAberta.add(moverPeca(no, 1, 1));
        }
        if (espacoVazio[0] + 1 <= 2) { // baixo
            listaAberta.add(moverPeca(no, 0, 1));
        }
    }

    boolean verificaEstadoExistente(int[][] temp) {
        for (EstadoPuzzleSlide lf : listaFechada) {
            if (equals(lf.getTabuleiro(), temp)) {
                return false; // estado já existente na lista de verificados
            }
        }
        return true; // estado nao é existente na lista de verificados
    }

    void encontrarEspacoVazio(int[][] estado) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (estado[i][j] == 0) {
                    espacoVazio[0] = i;
                    espacoVazio[1] = j;
                    i = 3;// forcar saida do laço
                    j = 3;// forcar saida do laço
                }
            }
        }
    }

    EstadoPuzzleSlide moverPeca(EstadoPuzzleSlide noFilho, int linhaColuna, int acimaAbaixoDireitaEsquerda) {
        int temp[][] = new int[3][3];
        int valorTemp = 0;
        if (linhaColuna == 0) { //[0]=linha(cima-baixo)
            valorTemp = noFilho.getTabuleiro()[espacoVazio[0] + acimaAbaixoDireitaEsquerda][espacoVazio[1]];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    temp[i][j] = noFilho.getTabuleiro()[i][j];
                    if (i == (espacoVazio[0] + acimaAbaixoDireitaEsquerda) && j == espacoVazio[1]) {
                        temp[i][j] = 0;
                    }
                    if (i == espacoVazio[0] && j == espacoVazio[1]) {
                        temp[i][j] = valorTemp;
                    }
                }
            }
        } else { //[1]=coluna(esquerda-direita)
            valorTemp = noFilho.getTabuleiro()[espacoVazio[0]][espacoVazio[1] + acimaAbaixoDireitaEsquerda];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    temp[i][j] = noFilho.getTabuleiro()[i][j];
                    if (i == espacoVazio[0] && j == (espacoVazio[1] + acimaAbaixoDireitaEsquerda)) {
                        temp[i][j] = 0;
                    }
                    if (i == espacoVazio[0] && j == espacoVazio[1]) {
                        temp[i][j] = valorTemp;
                    }
                }
            }
        }
        return new EstadoPuzzleSlide(temp, noFilho);
    }

    void toString(int[][] estado) {
        String representacaoPuzzle = "";
        if (estado != null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    representacaoPuzzle += "|" + estado[i][j];
                }
                representacaoPuzzle += "|\n";
            }
            System.out.println(representacaoPuzzle.replace("0", " "));
        }
    }

}
