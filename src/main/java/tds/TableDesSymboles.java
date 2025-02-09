package tds;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe pour représenter la Table des Symboles (TDS)
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
     * @param entree L'objet Entree contenant les informations sur l'identifiant.
     * @return true si l'ajout est réussi, false si le symbole existe déjà.
     */
    public boolean ajouterSymbole(String nom, Entree entree) {
        if (symboles.containsKey(nom)) {
            return false; // Le symbole existe déjà dans ce scope
        }
        symboles.put(nom, entree);
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
     * Affiche la table des symboles sous forme formatée.
     */
    public void afficherTDS() {
        for (Map.Entry<String, Entree> entry : symboles.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
