/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA.trabalhoBuscas;

import IA.trabalhoBuscas.q2.EstadoOitoRainhas;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Proprietário
 * @param <T>
 */
public abstract class BuscaTemplate<T> {

    public List<T> listaAberta;
    public List<T> listaFechada;
    public List<T> listaCaminho;
    public T estadoAtual;
    public static int interacao;
    public static int profundidade;

//    private Class<T> typeClass;
//    public BuscaTemplate(Class<T> typeClass) {
//        this.typeClass = typeClass;
//    }
    
    /**
     * Executa o laço se TRUE
     *
     * @return FALSE se acionada a condicao de parada do laço
     */
    public abstract boolean condicaoParada();

    /**
     * Trecho especifico do algoritmo para cada tipo de busca
     */
    public abstract void preLoop();

    /**
     * Trecho especifico do algoritmo para cada tipo de busca
     */
    public abstract void loop();

    /**
     * Trecho especifico do algoritmo para cada tipo de busca
     */
    public abstract void posLoop();

    /**
     * Algoritmo de busca em árvore genérico para ser implementado trechos
     * específicos dos tipos de busca
     */
    public void algoritmoBusca() {
        interacao = 1;
        profundidade = 0;
        estadoAtual = null;
        System.out.println("Realizando buscas...");
        long startTime = System.currentTimeMillis();
        preLoop(); // Definido pelas subclasses
        while (condicaoParada()) {
            System.out.println("------ "+interacao + "ª interação ------");
            loop(); // Definido pelas subclasses
            interacao++;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(elapsedTime);
        System.out.println("Tempo decorrido para encontrar solução: " + c.get(Calendar.MINUTE) + "m " + c.get(Calendar.SECOND) + "s " + c.get(Calendar.MILLISECOND) + "ms");
        posLoop();// Definido pelas subclasses
        System.out.println("-FIM-");
    }

    /**
     * Retorna verdadeiro se o parametro passado ainda não foi verificado pelo
     * algoritmo
     */
    public boolean verificaEstadoExistente(T estado) {
        boolean temp = true;
        for (T lf : listaFechada) {
            if (lf.equals(estado)) {
                temp = false; // estado já existente na lista de verificados
            }
        }
        for (T la : listaAberta) {
            if (la.equals(estado)) {
                temp = false; // estado já existente na lista de verificados
            }
        }
        return temp; // estado nao é existente na lista de verificados
    }


}
