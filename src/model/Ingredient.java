package model;

public class Ingredient {
    private Long id;
    private String nomFr;
    private String nomNl;

    public Ingredient(Long id, String nomFr, String nomNl) {
        this.id = id;
        this.nomFr = nomFr;
        this.nomNl = nomNl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFr() { return nomFr; }
    public void setNomFr(String nomFr) { this.nomFr = nomFr; }

    public String getNomNl() { return nomNl; }
    public void setNomNl(String nomNl) { this.nomNl = nomNl; }

    @Override
    public String toString() {
        return nomFr + " / " + nomNl;
    }
}