package tds;

/**
 * Classe pour représenter une entrée dans la Table des Symboles.
 * Contient le nom, le type et l'adresse de l'identifiant.
 */
public class Entree {
    private String nom;
    private String type;
    private int adresse;

    public Entree(String nom, String type, int adresse) {
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public int getAdresse() {
        return adresse;
    }

    @Override
    public String toString() {
        return "Nom: " + nom + ", Type: " + type + ", Adresse: " + adresse;
    }
}
