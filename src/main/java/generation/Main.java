package generation;

import fr.ul.miashs.compil.arbre.*;

public class Main {
    public static void main(String[] args) {
        // Crée le programme principal
        Prog programme = new Prog();

        // Crée la fonction principale "main"
        Fonction mainFonction = new Fonction("main");
        Bloc blocPrincipal = new Bloc();

        // Exemple : int x = 3 + 5;
        Plus addition = new Plus();
        addition.setFilsGauche(new Const(3));
        addition.setFilsDroit(new Const(5));

        Affectation affectation = new Affectation();
        affectation.setFilsGauche(new Idf("x"));
        affectation.setFilsDroit(addition);

        // Exemple : ecrire(x);
        Ecrire ecrire = new Ecrire();
        ecrire.setLeFils(new Idf("x"));

        // Ajoute les instructions dans le bloc principal
        blocPrincipal.ajouterUnFils(affectation);
        blocPrincipal.ajouterUnFils(ecrire);

        // Ajoute le bloc à la fonction main
        mainFonction.ajouterUnFils(blocPrincipal);

        // Ajoute la fonction main au programme
        programme.ajouterUnFils(mainFonction);

        // Génère le code assembleur
        GenerateurCode generateur = new GenerateurCode();
        String codeAssembleur = generateur.genererProgramme(programme);

        // Affiche le code assembleur généré
        System.out.println(codeAssembleur);
        //hello test
    }
}
