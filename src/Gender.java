public enum Gender {
    MALE("Homme"),
    FEMALE("Femme");

    private final String displayText;

    // Le constructeur d'un enum est toujours privé
    Gender(String displayText) {
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