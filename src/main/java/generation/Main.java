package generation;

import fr.ul.miashs.compil.arbre.*;
import tds.Entree;

public class Main {
    public static void main(String[] args) {
        // Crée le programme principal
        Prog programme = new Prog();

        // Définir les variables globales
        Entree varA = new Entree("a", "int", "global");
        Entree varB = new Entree("b", "int", "global");

        // Ajouter les variables globales à la TDS
        GenerateurCode generateur = new GenerateurCode();
        generateur.getTds().ajouterSymbole("a", varA);
        generateur.getTds().ajouterSymbole("b", varB);

        // Crée la fonction principale "main"
        Fonction mainFonction = new Fonction("main");
        Bloc blocPrincipal = new Bloc();

        // Construire l'expression ecrire(a);
        Ecrire ecrireA = new Ecrire();
        ecrireA.setLeFils(new Idf("a"));

        // Construire l'expression ecrire(b);
        Ecrire ecrireB = new Ecrire();
        ecrireB.setLeFils(new Idf("b"));

        // Ajouter les instructions au bloc principal
        blocPrincipal.ajouterUnFils(ecrireA);
        blocPrincipal.ajouterUnFils(ecrireB);

        // Ajouter le bloc principal à la fonction main
        mainFonction.ajouterUnFils(blocPrincipal);

        // Ajouter la fonction main au programme
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
