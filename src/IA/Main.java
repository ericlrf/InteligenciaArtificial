package IA;

import IA.trabalhoAprendizagem.q1.AlgoritmoGenetico;
import IA.trabalhoBuscas.q1.BuscaCegaEmLargura;
import IA.trabalhoBuscas.q2.BuscaHeuristicaGulosa;
import IA.trabalhoBuscas.q3.BuscaHeuristicaAestrela;

public class Main {

    public static void main(String[] args) {
        //TRABALHO BUSCAS
//        BuscaCegaEmLargura bcl = new BuscaCegaEmLargura();
//        bcl.algoritmoBusca();
//        BuscaHeuristicaGulosa bhg = new BuscaHeuristicaGulosa();
//        bhg.algoritmoBusca();
        BuscaHeuristicaAestrela bha = new BuscaHeuristicaAestrela();
        bha.algoritmoBusca();
        
        //TRABALHO APRENDIZAGEM
//        AlgoritmoGenetico ag = new AlgoritmoGenetico();
//        ag.algoritmo();
    }

}
