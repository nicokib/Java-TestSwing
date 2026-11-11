package model;

public enum BreadType {
    WHITE("Blanc"),
    GREY("Gris");

    private final String displayText;

    // Le constructeur d'un enum est toujours privé
    BreadType(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }

    @Override
    public String toString() {
        return displayText;
    }
}