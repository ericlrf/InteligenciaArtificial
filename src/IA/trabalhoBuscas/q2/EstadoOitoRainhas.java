package IA.trabalhoBuscas.q2;

public class EstadoOitoRainhas {

    private int tabuleiro[];
    private EstadoOitoRainhas estadoPai;
    private int custo;
    private int nivel;

    public EstadoOitoRainhas() {
    }

    public EstadoOitoRainhas(int[] tabuleiro, EstadoOitoRainhas estadoPai, int nivel) {
        this.tabuleiro = tabuleiro;
        this.estadoPai = estadoPai;
        this.nivel = nivel;
        this.custo = -1; // -1 identifica um estado ainda sem o custo calculado
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int[] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(int[] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public EstadoOitoRainhas getEstadoPai() {
        return estadoPai;
    }

    public void setEstadoPai(EstadoOitoRainhas estadoPai) {
        this.estadoPai = estadoPai;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) { // verificar se esta condicional atrapalha a verificacao
            return false;
        }
        final EstadoOitoRainhas other = (EstadoOitoRainhas) obj;
        for (int i = 0; i < 8; i++) {
            if (this.tabuleiro[i] != other.tabuleiro[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if (this.tabuleiro != null) {
            System.out.println("||---+---+---+---+---+---+---+---|");
            for (int i = 0; i < 8; i++) {
                System.out.print(i + 1 + "|");
                for (int j = 0; j < 8; j++) {
                    if (this.tabuleiro[j] - 1 == i) {
                        System.out.print(" X |");
                    } else {
                        System.out.print("   |");
                    }
                }
                System.out.println("\r\n||---+---+---+---+---+---+---+---|");
            }
            System.out.println("\r||-1-|-2-|-3-|-4-|-5-|-6-|-7-|-8-|\n");
        } else {
            return null;
        }
        return "";
    }

    public void tabuleiroToString() {
        for (int t : this.tabuleiro) {
            System.out.print(t);
        }
        System.out.print(" ");
    }

}
