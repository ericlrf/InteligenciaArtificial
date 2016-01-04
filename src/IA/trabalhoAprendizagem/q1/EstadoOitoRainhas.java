package IA.trabalhoAprendizagem.q1;

public class EstadoOitoRainhas {

    private int cromossomo[];
    private EstadoOitoRainhas pai1;
    private EstadoOitoRainhas pai2;
    private int custo;

    public EstadoOitoRainhas() {
    }

    public EstadoOitoRainhas(int[] cromossomo, EstadoOitoRainhas pai1, EstadoOitoRainhas pai2) {
        this.cromossomo = cromossomo;
        this.pai1 = pai1;
        this.pai2 = pai2;
        this.custo = -1; // -1 identifica um estado ainda sem o custo calculado
    }

    public int[] getCromossomo() {
        return cromossomo;
    }

    public void setCromossomo(int[] cromossomo) {
        this.cromossomo = cromossomo;
    }

    public EstadoOitoRainhas getPai1() {
        return pai1;
    }

    public void setPai1(EstadoOitoRainhas pai1) {
        this.pai1 = pai1;
    }

    public EstadoOitoRainhas getPai2() {
        return pai2;
    }

    public void setPai2(EstadoOitoRainhas pai2) {
        this.pai2 = pai2;
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
            if (this.cromossomo[i] != other.cromossomo[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if (this.cromossomo != null) {
            System.out.println("||---+---+---+---+---+---+---+---|");
            for (int i = 0; i < 8; i++) {
                System.out.print(i + 1 + "|");
                for (int j = 0; j < 8; j++) {
                    if (this.cromossomo[j] - 1 == i) {
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

    public void cromossomoToString() {
        for (int c : cromossomo) {
            System.out.print(c);
        }
        System.out.println("");
    }
}
