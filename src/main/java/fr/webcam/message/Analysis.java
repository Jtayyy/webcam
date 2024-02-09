package fr.webcam.message;
public enum Analysis {
    DESC_1("N'hésite pas à arrêter de spammer mattermost"),
    DESC_2("Next time maybe ¯\\_( ͠° ͟ʖ °͠ )_/¯"),
    DESC_3("Petit tips : kill wildfly"),
    DESC_4("99,71% des développeurs Takima sont passés par cette erreur"),
    DESC_5("NULL ! (pointException)"),
    DESC_6("Vas faire une pause, mon ami..."),
    DESC_7("Jamais 6 sans 7 ( ͡ᵔ ͜ʖ ͡ᵔ )"),
    DESC_8("Relance, on sait jamais");

    private final String description;

    Analysis(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
