package model;

import java.util.ArrayList;
import java.util.List;

public class Sandwich {
    private String nomFr;
    private String nomNl;
    private double price;
    private List<Ingredient> ingredients;

    public Sandwich(String nomFr, String nomNl, double price, List<Ingredient> ingredients) {
        this.nomFr = nomFr;
        this.nomNl = nomNl;
        this.price = price;
        if(ingredients != null) this.ingredients = new ArrayList<>(ingredients);
    }

    public String getNomFr() { return nomFr; }
    public void setNomFr(String nomFr) { this.nomFr = nomFr; }

    public String getNomNl() { return nomNl; }
    public void setNomNl(String nomNl) { this.nomNl = nomNl; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = new ArrayList<>(ingredients); }

    @Override
    public String toString() {
        return nomFr + " (" + String.format("%.2f €", price) + ")";
    }
}