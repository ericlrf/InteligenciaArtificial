package IA.trabalhoBuscas.q3;

import java.util.Objects;

public class EstadoMetroParis {

    private Estacao origem;
    private Estacao destino;
    private double distancia;

    public EstadoMetroParis() {
    }

    public EstadoMetroParis(Estacao origem, Estacao destino, double distancia) {
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
    }

    public Estacao getOrigem() {
        return origem;
    }

    public void setOrigem(Estacao origem) {
        this.origem = origem;
    }

    public Estacao getDestino() {
        return destino;
    }

    public void setDestino(Estacao destino) {
        this.destino = destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        return origem + "-" + destino + "(" + distancia + ')';
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
        final EstadoMetroParis other = (EstadoMetroParis) obj;
        if (!(this.origem.getNome().equalsIgnoreCase(other.origem.getNome()))) {
            return false;
        }
        if (!(this.destino.getNome().equalsIgnoreCase(other.destino.getNome()))) {
            return false;
        }
        if (Double.doubleToLongBits(this.distancia) != Double.doubleToLongBits(other.distancia)) {
            return false;
        }
        return true;
    }

}
