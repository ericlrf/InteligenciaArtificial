package IA.trabalhoAprendizagem.q1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgoritmoGenetico {

    static final int QUANTIDADE_RAINHAS = 8; // N rainhas do jogo de xadrez em um tabuleiro N x N (Gauss)
    static final int MAXIMA_POPULACAO = 10; // Deve ser um número par!
    static final int MAXIMO_GERACOES = 3000; // depois substituir o 9999
    static int geracao_atual;
    static int geracao_melhor_fitness;
    static final int TAXA_CROSSOVER = 100;
    static final int TAXA_MUTACAO = 10; // poderia implementar variacao em tempo de execucao: se nas últimas k gerações não houve melhora da na aptidão maxima, entao aumentar taxa
    static int melhor_fitness;
    static final int FITNESS_SOLUCAO = 0;
    static int ponto_de_corte;
    static int ataques;
    static Random randomico = new Random();
    static List<EstadoOitoRainhas> populacaoInicial = new ArrayList<EstadoOitoRainhas>();
    static EstadoOitoRainhas individuo_mais_apto = null;
    static EstadoOitoRainhas pai_mais_apto = null;

    public void algoritmo() {
        System.out.println("-_-_- Iniciado o Algoritmo Genetico -_-_-");
        geracao_atual = 1;
        melhor_fitness = 999; // inicialmente a funcao de aptidao nao encontrou a solucao
        gerarPopulacaoInicial();
        avaliarAptidao();
        while (criterioParada()) {
            System.out.println("--------- " + geracao_atual + "ª Geracao ---------");
            geracao_atual++;
            selecaoTorneio();
            avaliarAptidao();
            substituicaoGeracional(false);
            crossover();
            avaliarAptidao();
            substituicaoGeracional(true);
            mutacao();
            avaliarAptidao();
            substituicaoGeracional(true);
        }
        individuo_mais_apto.toString();
        System.out.println("Individuo mais apto: Funcao(avaliacao)= " + melhor_fitness);
        System.out.print("Cromossomo = ");
        individuo_mais_apto.cromossomoToString();
        System.out.println("Encontrado na " + geracao_melhor_fitness + "ª geracao.");
        System.out.println("Total de geracoes: " + geracao_atual);
        System.out.println("-_-_- Encerrado Algoritmo Genetico -_-_-");
    }

    private void gerarPopulacaoInicial() {
        System.out.println("->Gerar populacao inicial<-");
        for (int j = 0; j < MAXIMA_POPULACAO; j++) {
            int cromossomo[] = new int[QUANTIDADE_RAINHAS];
            for (int i = 0; i < QUANTIDADE_RAINHAS; i++) {
                cromossomo[i] = randomico.nextInt(QUANTIDADE_RAINHAS) + 1;
            }
            populacaoInicial.add(new EstadoOitoRainhas(cromossomo, null, null));
        }
        imprimirPopulacao();
        System.out.println("---------><---------");
    }

    private void avaliarAptidao() {
        System.out.println("->Avaliar aptidao dos individuos<-");
        for (int i = 0; i < MAXIMA_POPULACAO; i++) {
            populacaoInicial.get(i).setCusto(funcaoHeuristica(populacaoInicial.get(i)));
            System.out.print(populacaoInicial.get(i).getCusto());
            System.out.print(" <-> ");
            populacaoInicial.get(i).cromossomoToString();
            if (populacaoInicial.get(i).getCusto() < melhor_fitness) {
                geracao_melhor_fitness = geracao_atual;
                melhor_fitness = populacaoInicial.get(i).getCusto();
                individuo_mais_apto = populacaoInicial.get(i);
            }
        }
        System.out.println("individuo mais apto: Funcao(avaliacao)= " + melhor_fitness);
        System.out.println("---------><---------");
    }

    private void selecaoTorneio() {
        System.out.println("->Seleção por torneio dos melhores individuos<-");
        List<EstadoOitoRainhas> populacaoNova = new ArrayList<EstadoOitoRainhas>();
        for (int i = 0; i < MAXIMA_POPULACAO; i++) {
            populacaoNova.add(populacaoInicial.get(randomico.nextInt(MAXIMA_POPULACAO)));
            populacaoNova.add(populacaoInicial.get(randomico.nextInt(MAXIMA_POPULACAO)));
        }
        for (int i = 0; i < populacaoNova.size() - 1; i++) {
            if (populacaoNova.get(i).getCusto() < populacaoNova.get(i + 1).getCusto()) {
                populacaoNova.remove(i + 1);
            } else {
                populacaoNova.remove(i);
            }
        }
        populacaoInicial.clear();
        populacaoInicial.addAll(populacaoNova);
        imprimirPopulacao();
        System.out.println("---------><---------");
    }

    private void crossover() {
        System.out.println("->Gerados filhos atraves de crossover<-");
        List<EstadoOitoRainhas> populacaoNova = new ArrayList<EstadoOitoRainhas>();
        EstadoOitoRainhas pai1;
        EstadoOitoRainhas pai2;
        EstadoOitoRainhas filho1;
        EstadoOitoRainhas filho2;
        int[] cromossomo1 = new int[QUANTIDADE_RAINHAS];
        int[] cromossomo2 = new int[QUANTIDADE_RAINHAS];
        for (int i = 0; i < MAXIMA_POPULACAO - 1; i++) {
            pai1 = populacaoInicial.get(i);
            pai2 = populacaoInicial.get(i + 1);
            if (randomico.nextInt(100) <= TAXA_CROSSOVER) {
                cromossomo1 = trocarCaldaCromossomo(pai1.getCromossomo(), pai2.getCromossomo(), true);
                cromossomo2 = trocarCaldaCromossomo(pai1.getCromossomo(), pai2.getCromossomo(), false);
                filho1 = new EstadoOitoRainhas(cromossomo1, pai1, pai2);
                filho2 = new EstadoOitoRainhas(cromossomo2, pai1, pai2);
            } else {
                filho1 = pai1;
                filho2 = pai2;
            }
            populacaoNova.add(filho1);
            populacaoNova.add(filho2);
        }
        populacaoInicial.clear();
        populacaoInicial.addAll(populacaoNova);
        imprimirPopulacao();
        System.out.println("---------><---------");
    }

    /**
     * Enquanto for verdadeiro repete-se a execucao do algoritmo genetico
     */
    private boolean criterioParada() {
        //1-Numero de geracoes
        if (geracao_atual >= MAXIMO_GERACOES) {
            System.out.println("->Criterio de parada encontrado<-");
            System.out.println("causa: numero de geracoes");
            System.out.println("---------><---------");
            return false;
        }
        //2-Encontrou a solução
        if (melhor_fitness == FITNESS_SOLUCAO) {
            System.out.println("->Criterio de parada encontrado<-");
            System.out.println("causa: encontrou a solucao");
            System.out.println("---------><---------");
            return false;
        }
        //3-Perda de diversidade
        int cont = 0;
        for (int i = 0; i < MAXIMA_POPULACAO; i++) {
            if (populacaoInicial.get(i).equals(populacaoInicial.get(0))) {
                cont++;
            }
        }
        if (cont == MAXIMA_POPULACAO) {
            System.out.println("->Criterio de parada encontrado<-");
            System.out.println("causa: perda de diversidade");
            System.out.println("---------><---------");
            return false; // Todos os individuos tornaram-se semelhantes
        }
        //4-Convergencia
        if (geracao_atual - geracao_melhor_fitness > 1000) { // depois substituir o 9999
            System.out.println("->Criterio de parada encontrado<-");
            System.out.println("causa: convergencia");
            System.out.println("---------><---------");
            return false; // nas últimas k gerações não houve melhora da na aptidão maxima 
        }
        //
        return true;
    }

    private int[] trocarCaldaCromossomo(int[] pai1, int[] pai2, boolean trocaCalda) {
        ponto_de_corte = randomico.nextInt(QUANTIDADE_RAINHAS - 1); // -1 para sempre haver caldas com pelo menos 1 elemento
        int[] temp = new int[QUANTIDADE_RAINHAS];
        for (int i = 0; i < QUANTIDADE_RAINHAS; i++) {
            if (trocaCalda) {
                if (i <= ponto_de_corte) {
                    temp[i] = pai1[i];
                } else {
                    temp[i] = pai2[i];
                }
            } else {
                if (i <= ponto_de_corte) {
                    temp[i] = pai2[i];
                } else {
                    temp[i] = pai1[i];
                }
            }
        }
        return temp;
    }

    private void mutacao() {
        System.out.println("->Filhos gerados sofrem mutacao<-");
        for (int j = 0; j < MAXIMA_POPULACAO; j++) {
            if (populacaoInicial.get(j).getCusto() != FITNESS_SOLUCAO) {

                for (int i = 0; i < QUANTIDADE_RAINHAS; i++) {
                    if (randomico.nextInt(100) <= TAXA_MUTACAO) {
                        populacaoInicial.get(j).getCromossomo()[i] = randomico.nextInt(QUANTIDADE_RAINHAS) + 1; // +1 pq os valores variam de 1 - 8
                    }
                }
            }
        }
        imprimirPopulacao();
        System.out.println("---------><---------");
    }

    /**
     * Substituição Geracional com Elitismo
     */
    private void substituicaoGeracional(boolean filhosForamGerados) {
        EstadoOitoRainhas melhor_pai;
        EstadoOitoRainhas pior_pai;
        List<EstadoOitoRainhas> populacaoNova = new ArrayList<EstadoOitoRainhas>();
        populacaoNova.addAll(populacaoInicial);
        EstadoOitoRainhas key;
        int i, j;
        for (j = 1; j < MAXIMA_POPULACAO; j++) { // ordena os nós por seus custos
            key = populacaoNova.get(j);
            for (i = j - 1; (i >= 0) && (populacaoNova.get(i).getCusto()
                    /**
                     * '>' em ordem crescente
                     */
                    > /**
                     * '<' em ordem decrescente
                     */
                    key.getCusto()); i--) {
                populacaoNova.set(i + 1, populacaoNova.get(i));
            }
            populacaoNova.set(i + 1, key);
        }
        melhor_pai = populacaoNova.get(0);
        pior_pai = populacaoNova.get(MAXIMA_POPULACAO - 1);
        if (filhosForamGerados) {
            if (melhor_pai.getCusto() <= pai_mais_apto.getCusto() && !melhor_pai.equals(pai_mais_apto)) {
                populacaoInicial.set(populacaoInicial.indexOf(pior_pai), melhor_pai);
                pai_mais_apto = melhor_pai;
            }
        } else {
            pai_mais_apto = melhor_pai;
        }
    }

    private void imprimirPopulacao() {
        for (int i = 0; i < MAXIMA_POPULACAO; i++) {
            populacaoInicial.get(i).cromossomoToString();
        }
    }

    /**
     * Extraido da questao 2 do 'Trabalho de Buscas'
     */
    private static int funcaoHeuristica(EstadoOitoRainhas temp) {
        ataques = 0;
        /**
         * verificar diagonais
         */
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (Math.abs(i - j) == Math.abs(temp.getCromossomo()[i] - temp.getCromossomo()[j])) {
                    ataques++;
                }
            }
        }
        /**
         * verificar horizontal
         */
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (temp.getCromossomo()[i] == temp.getCromossomo()[j]) {
                    ataques++;
                }
            }
        }
        /**
         * verificar vertical: não é necessário pois cada coluna deve conter
         * obrigatoriamente uma única rainha, cada posição do array
         * 'estadoInicial[]' representa uma rainha
         */
//        System.out.println("Qtd. ataques (estado atual): " + ataques);

        return ataques;
    }

}
