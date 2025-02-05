package tds;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe pour représenter la Table des Symboles (TDS)
 * Elle enregistre les variables, fonctions et leurs informations.
 */
public class TableDesSymboles {

    private Map<String, Entree> symboles;
    private TableDesSymboles parent;

    // Constructeur pour la TDS globale
    public TableDesSymboles() {
        this.symboles = new HashMap<>();
        this.parent = null;
    }

    // Constructeur pour les scopes locaux (fonctions, blocs)
    public TableDesSymboles(TableDesSymboles parent) {
        this.symboles = new HashMap<>();
        this.parent = parent;
    }

    /**
     * Ajoute un symbole dans la table des symboles.
     * @param nom Le nom de l'identifiant (variable, fonction).
     * @param type Le type de l'identifiant (int, fonction, etc.).
     * @param adresse L'adresse mémoire ou le registre associé.
     * @return true si l'ajout est réussi, false si le symbole existe déjà.
     */
    public boolean ajouterSymbole(String nom, String type, int adresse) {
        if (symboles.containsKey(nom)) {
            return false; // Le symbole existe déjà dans ce scope
        }
        symboles.put(nom, new Entree(nom, type, adresse));
        return true;
    }

    /**
     * Recherche un symbole dans la table des symboles.
     * La recherche remonte les scopes parents si nécessaire.
     * @param nom Le nom de l'identifiant.
     * @return L'entrée correspondante ou null si elle n'existe pas.
     */
    public Entree chercherSymbole(String nom) {
        if (symboles.containsKey(nom)) {
            return symboles.get(nom);
        } else if (parent != null) {
            return parent.chercherSymbole(nom);
        }
        return null;
    }

    /**
     * Affiche la table des symboles pour le débogage.
     */
    public void afficherTDS() {
        System.out.println("Table des Symboles :");
        for (Entree entree : symboles.values()) {
            System.out.println(entree);
        }
    }
}
