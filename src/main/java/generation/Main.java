package generation;

import fr.ul.miashs.compil.arbre.*;

public class Main {
    public static void main(String[] args) {
        // Crée le programme principal
        Prog programme = new Prog();

        // Crée la fonction principale "main"
        Fonction mainFonction = new Fonction("main");

        // Ajoute la fonction main au programme
        programme.ajouterUnFils(mainFonction);

        // Affiche l'arbre abstrait
        GenerateurCode generateur = new GenerateurCode();
        generateur.afficherArbre(programme);

        // Génère le code assembleur
        String codeAssembleur = generateur.genererProgramme(programme);

        // Affiche le code assembleur généré
        System.out.println(codeAssembleur);

        // Affiche la table des symboles
        generateur.getTds().afficherTDS();
    }
}
