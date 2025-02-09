package generation;

import fr.ul.miashs.compil.arbre.*;
import tds.Entree;

public class Main {
    public static void main(String[] args) {
        // Crée le programme principal
        Prog programme = new Prog();

        // Définir les variables globales
        Entree varI = new Entree("i", "int", "global");
        varI.setValeur(10);
        Entree varJ = new Entree("j", "int", "global");
        varJ.setValeur(20);
        Entree varK = new Entree("k", "int", "global");
        Entree varL = new Entree("l", "int", "global");

        // Ajouter les variables globales à la TDS
        GenerateurCode generateur = new GenerateurCode();
        generateur.getTds().ajouterSymbole("i", varI);
        generateur.getTds().ajouterSymbole("j", varJ);
        generateur.getTds().ajouterSymbole("k", varK);
        generateur.getTds().ajouterSymbole("l", varL);

        // Crée la fonction principale "main"
        Fonction mainFonction = new Fonction("main");

        // Ajoute la fonction main au programme
        programme.ajouterUnFils(mainFonction);

        // Affiche l'arbre abstrait
        generateur.afficherArbre(programme);

        // Génère le code assembleur
        String codeAssembleur = generateur.genererProgramme(programme);

        // Affiche le code assembleur généré
        System.out.println(codeAssembleur);

        // Affiche la table des symboles
        generateur.getTds().afficherTDS();
    }
}
