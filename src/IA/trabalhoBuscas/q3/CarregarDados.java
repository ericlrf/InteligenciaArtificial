package IA.trabalhoBuscas.q3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class CarregarDados {

    static Scanner scannerTL;
    static Scanner scannerDR;
    static Scanner scannerDLR;

    static void on(int opcao) {
        try {
            scannerTL = new Scanner(new FileReader("src\\IA\\trabalhoBuscas\\q3\\tipos de linhas.txt")).useDelimiter("\\n");
            scannerDR = new Scanner(new FileReader("src\\IA\\trabalhoBuscas\\q3\\distancia real.txt")).useDelimiter(" |\\n");
            scannerDLR = new Scanner(new FileReader("src\\IA\\trabalhoBuscas\\q3\\distancia em linha reta.txt")).useDelimiter(" |\\n");
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        if (opcao == 1) {
            while (scannerTL.hasNext()) {
                Scanner temp = new Scanner(scannerTL.next()).useDelimiter(" ");
                Estacao e = new Estacao(temp.next());
                while (temp.hasNext()) {
                    e.addLinhas(temp.next());
                }
                BuscaHeuristicaAestrela.linhasEstacoes.add(e);
            }
        }
        if (opcao == 2) {
            while (scannerDR.hasNext()) {
                BuscaHeuristicaAestrela.distanciasReais.add(new EstadoMetroParis(buscarEstacao(scannerDR.next()), buscarEstacao(scannerDR.next()), Double.parseDouble(scannerDR.next())));
            }
        }
        if (opcao == 3) {
            while (scannerDLR.hasNext()) {
                BuscaHeuristicaAestrela.distanciasDiretas.add(new EstadoMetroParis(buscarEstacao(scannerDLR.next()), buscarEstacao(scannerDLR.next()), Double.parseDouble(scannerDLR.next())));
            }
        }
    }

    static Estacao buscarEstacao(String nome) {
        for (Estacao e : BuscaHeuristicaAestrela.linhasEstacoes) {
            if (e.getNome().equals(nome)) {
                return e;
            }
        }
        return null;
    }
}
