package IA.trabalhoBuscas.q3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Estacao {

    private String nome;
    private double custo;
    private Estacao estacaoPai;
    private List<String> linhas;

    public Estacao() {
        this.estacaoPai = null;
    }

    public Estacao(String nome, Estacao estacaoPai) {
        this.nome = nome;
        this.custo = -1;
        this.estacaoPai = estacaoPai;
        this.linhas = new ArrayList<String>();
    }

    public Estacao(String nome) {
        this.nome = nome;
        this.custo = -1;
        this.estacaoPai = null;
        this.linhas = new ArrayList<String>();
    }

    public Estacao getEstacaoPai() {
        return estacaoPai;
    }

    public void setEstacaoPai(Estacao estacaoPai) {
        this.estacaoPai = estacaoPai;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getLinhas() {
        return linhas;
    }

    public void addLinhas(String linha) {
        this.linhas.add(linha);
    }

    public void removLinhas(String linha) {
        this.linhas.remove(linha);
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Estacao other = (Estacao) obj;
        if (!(this.nome.equalsIgnoreCase(other.nome))) {
            return false;
        }
        return true;
    }

}
