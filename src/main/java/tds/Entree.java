package tds;

/**
 * Classe pour représenter une entrée dans la Table des Symboles.
 */
public class Entree {
    private String nom;
    private String type;
    private String categorie;
    private Integer valeur; // Optionnel pour les constantes globales
    private Integer nbParam; // Optionnel pour les fonctions
    private Integer nbVar; // Optionnel pour les fonctions
    private Integer rang; // Optionnel pour les paramètres
    private String scope; // Nom de la fonction si c'est un paramètre

    public Entree(String nom, String type, String categorie) {
        this.nom = nom;
        this.type = type;
        this.categorie = categorie;
    }

    public void setValeur(Integer valeur) {
        this.valeur = valeur;
    }

    public void setNbParam(Integer nbParam) {
        this.nbParam = nbParam;
    }

    public void setNbVar(Integer nbVar) {
        this.nbVar = nbVar;
    }

    public void setRang(Integer rang) {
        this.rang = rang;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{nom=").append(nom)
                .append(";type=").append(type)
                .append(";cat=").append(categorie);
        if (valeur != null) {
            builder.append(";val=").append(valeur);
        }
        if (nbParam != null) {
            builder.append(";nnb_param=").append(nbParam);
        }
        if (nbVar != null) {
            builder.append(";nb_var=").append(nbVar);
        }
        if (rang != null) {
            builder.append(";rang=").append(rang);
        }
        if (scope != null) {
            builder.append(";scope=").append(scope);
        }
        builder.append("}");
        return builder.toString();
    }
}
