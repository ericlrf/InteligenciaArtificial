/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA.trabalhoBuscas.q1;

/**
 *
 * @author Proprietário
 */
public class EstadoPuzzleSlide {

    int tabuleiro[][];
    EstadoPuzzleSlide estadoPai;

    public EstadoPuzzleSlide() {
    }

    public EstadoPuzzleSlide(int[][] tabuleiro, EstadoPuzzleSlide estadoPai) {
        this.tabuleiro = tabuleiro;
        this.estadoPai = estadoPai;
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(int[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public EstadoPuzzleSlide getEstadoPai() {
        return estadoPai;
    }

    public void setEstadoPai(EstadoPuzzleSlide estadoPai) {
        this.estadoPai = estadoPai;
    }
}
