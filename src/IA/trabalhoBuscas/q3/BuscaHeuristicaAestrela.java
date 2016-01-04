package IA.trabalhoBuscas.q3;

import IA.trabalhoBuscas.BuscaTemplate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuscaHeuristicaAestrela extends BuscaTemplate<Estacao> {

    static List<Estacao> linhasEstacoes = new ArrayList<Estacao>(0);
    static List<EstadoMetroParis> distanciasReais = new ArrayList<EstadoMetroParis>(0);
    static List<EstadoMetroParis> distanciasDiretas = new ArrayList<EstadoMetroParis>(0);
    static final int VELOCIDADE_TREM = 30; // km/h
    static final int TEMPO_BALDIACAO = 5; // minutos
    static int tempo_trajeto; // segundos
    static Estacao ESTACAO_INICIAL;
    static Estacao ESTACAO_FINAL;

    public BuscaHeuristicaAestrela() {
        listaAberta = new ArrayList<>();
        listaFechada = new ArrayList<>();
        listaCaminho = new ArrayList<>();
        estadoAtual = new Estacao();
    }

    @Override
    public boolean condicaoParada() {
        if (listaAberta.isEmpty()) {
            return false;
        }
        if (listaAberta.get(0).equals(ESTACAO_FINAL)) {
            System.out.println("--->Destino encontrado<---");
            return false;
        }
        return true;
    }

    @Override
    public void preLoop() {
        CarregarDados.on(1);
        CarregarDados.on(2);
        CarregarDados.on(3);
        ESTACAO_INICIAL = linhasEstacoes.get(12);
        ESTACAO_FINAL = linhasEstacoes.get(4);
        listaAberta.add(ESTACAO_INICIAL);
        listaAberta.get(0).setCusto(funcaoHeuristica(listaAberta.get(0)));

    }

    @Override
    public void loop() {
        if (estadoAtual == null) { //tratar situacao especial do primeiro nó
            estadoAtual = listaAberta.remove(0);
        } else if (estadoAtual == ESTACAO_INICIAL) { //tratar situacao especial do segundo nó
            estadoAtual = listaAberta.remove(0);
//            listaFechada.addAll(listaAberta);
//            listaAberta.clear();            
        } else if (listaAberta.get(0).getCusto() < estadoAtual.getCusto()) {
            estadoAtual = listaAberta.remove(0);
//            listaFechada.addAll(listaAberta);
//            listaAberta.clear();
        } else {
            System.out.println("Estacao " + estadoAtual + " removida: sem acesso ao destino");
            estadoAtual = listaAberta.remove(0);

        }

        System.out.println("Estacao " + estadoAtual + " - custo: " + estadoAtual.getCusto() + " - pai: " + estadoAtual.getEstacaoPai());
        listaFechada.add(estadoAtual);
        System.out.println("--->expandir Estacao " + estadoAtual + "<---");
        expandir(estadoAtual);
        System.out.println("--->custo das estacoes vizinhas<---");
        for (Estacao no : listaAberta) {
            if (no.getCusto() == -1) {
                no.setCusto(funcaoHeuristica(no));
            }
            System.out.println("Estacao " + no + " - custo: " + no.getCusto() + " - pai: " + no.getEstacaoPai());
        }
        System.out.println("--->---<---");
        ordenarFila(listaAberta);
    }

    @Override
    public void posLoop() {
        refazerCaminho(listaAberta.get(0));
        System.out.println("Sequência de estacões para o destino:");
        for (int i = listaCaminho.size() - 1; i >= 0; i--) { // imprime o caminho para a solução encontrada
            System.out.print(" -> " + listaCaminho.get(i).toString());
        }
        System.out.println("");
        System.out.println("Tempo de trajeto " + tempo_trajeto / 60 + " minutos");
    }

    private void expandir(Estacao estacao) {
        Estacao temp;
        for (EstadoMetroParis emp : distanciasReais) {
            if (emp.getOrigem().equals(estacao)) {
                if (verificaEstadoExistente(emp.getDestino())) {
                    temp = new Estacao();
                    temp = emp.getDestino();
                    temp.setEstacaoPai(estacao);
                    listaAberta.add(temp);

                }
            }
            if (emp.getDestino().equals(estacao)) {
                if (verificaEstadoExistente(emp.getOrigem())) {
                    temp = new Estacao();
                    temp = emp.getOrigem();
                    temp.setEstacaoPai(estacao);
                    listaAberta.add(temp);

                }
            }
        }

    }

    double funcaoHeuristica(Estacao estacao) {
        //aqui converte o valor de distancia em km para tempo em minuto
        double d = g(estacao) * 1000; // metros
        double b = TEMPO_BALDIACAO * 60; // segundos
        double v = VELOCIDADE_TREM / 3.6;
        double t = d / v; // segundos
//        t/60
//        return g(estacao) + h(estacao);
        return g(estacao) + h(estacao);
    }

    double calcularTempo(Estacao estacao) {
        //aqui converte o valor de distancia em km para tempo em minuto
        double d = g(estacao) * 1000; // metros
        double v = VELOCIDADE_TREM / 3.6; // m/s
        double t = d / v; // segundos
//        t/60 // minutos
//        return g(estacao);
        return t;
    }

    double g(Estacao estacao) {
        Estacao temp = estacao;
        double h = 0;
        do {
            for (EstadoMetroParis emp : distanciasReais) {
                if (emp.getOrigem().equals(temp) && emp.getDestino().equals(temp.getEstacaoPai())) {
                    h = emp.getDistancia();
                }
                if (emp.getDestino().equals(temp) && emp.getOrigem().equals(temp.getEstacaoPai())) {
                    h = emp.getDistancia();
                }
            }
            if (temp.getEstacaoPai() != null) {
                temp = temp.getEstacaoPai();
            }
        } while (temp.getEstacaoPai() != null);
        return h;
    }

    double h(Estacao estacao) {
        for (EstadoMetroParis emp : distanciasDiretas) {
            if (emp.getOrigem().equals(estacao) && emp.getDestino().equals(ESTACAO_FINAL)) {
                return emp.getDistancia();
            }
            if (emp.getDestino().equals(estacao) && emp.getOrigem().equals(ESTACAO_FINAL)) {
                return emp.getDistancia();
            }
        }
        return 0;
    }

    void ordenarFila(List<Estacao> lista) {
        Estacao key;
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

    public void refazerCaminho(Estacao estacao) {
        Estacao temp = estacao;
        List linhas = estacao.getLinhas();
        double b = TEMPO_BALDIACAO * 60; // segundos

        do {
//            linhas = temp.getLinhas().contains(null);
            tempo_trajeto += calcularTempo(temp);
            listaCaminho.add(temp);
            temp = temp.getEstacaoPai();
        } while (temp != null);
    }

}
