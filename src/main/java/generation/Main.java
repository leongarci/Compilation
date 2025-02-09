package generation;

import fr.ul.miashs.compil.arbre.*;
import tds.TableDesSymboles;
import tds.Entree;

public class Main {
    public static void main(String[] args) {
        // Crée le programme principal
        Prog programme = new Prog();

        // Définir les variables globales
        Entree varA = new Entree("a", "int", "global");
        Entree varB = new Entree("b", "int", "global");
        Entree varX = new Entree("x", "int", "global");

        // Ajouter les variables globales à la TDS
        GenerateurCode generateur = new GenerateurCode();
        generateur.getTds().ajouterSymbole("a", varA);
        generateur.getTds().ajouterSymbole("b", varB);
        generateur.getTds().ajouterSymbole("x", varX);

        // Crée la fonction principale "main"
        Fonction mainFonction = new Fonction("main");
        Bloc blocPrincipal = new Bloc();

        // Construire l'expression x = a * 2 + (b - 5) / 3;
        Multiplication multiplication = new Multiplication();
        multiplication.setFilsGauche(new Idf("a"));
        multiplication.setFilsDroit(new Const(2));

        Moins soustraction = new Moins();
        soustraction.setFilsGauche(new Idf("b"));
        soustraction.setFilsDroit(new Const(5));

        Division division = new Division();
        division.setFilsGauche(soustraction);
        division.setFilsDroit(new Const(3));

        Plus addition = new Plus();
        addition.setFilsGauche(multiplication);
        addition.setFilsDroit(division);

        Affectation affectation = new Affectation();
        affectation.setFilsGauche(new Idf("x"));
        affectation.setFilsDroit(addition);

        // Ajouter l'instruction au bloc principal
        blocPrincipal.ajouterUnFils(affectation);

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
