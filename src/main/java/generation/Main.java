package generation;

import fr.ul.miashs.compil.arbre.*;
import tds.TableDesSymboles;
import tds.Entree;

public class Main {
    public static void main(String[] args) {
        // Crée le programme principal
        Prog programme = new Prog();

        // Crée la fonction g avec deux paramètres
        Fonction fonctionG = new Fonction("g");
        Bloc blocG = new Bloc();

        // Ajouter les paramètres à la TDS
        GenerateurCode generateur = new GenerateurCode();
        Entree paramA = new Entree("a", "int", "param");
        paramA.setRang(0);
        paramA.setScope("g");
        Entree paramB = new Entree("b", "int", "param");
        paramB.setRang(1);
        paramB.setScope("g");

        generateur.getTds().ajouterSymbole("a", paramA);
        generateur.getTds().ajouterSymbole("b", paramB);

        // Construire une conditionnelle : if (a > b) x = a; else x = b;
        Superieur condition = new Superieur();
        condition.setFilsGauche(new Idf("a"));
        condition.setFilsDroit(new Idf("b"));

        Bloc blocAlors = new Bloc();
        Affectation affectationXAlors = new Affectation();
        affectationXAlors.setFilsGauche(new Idf("x"));
        affectationXAlors.setFilsDroit(new Idf("a"));
        blocAlors.ajouterUnFils(affectationXAlors);

        Bloc blocSinon = new Bloc();
        Affectation affectationXSinon = new Affectation();
        affectationXSinon.setFilsGauche(new Idf("x"));
        affectationXSinon.setFilsDroit(new Idf("b"));
        blocSinon.ajouterUnFils(affectationXSinon);

        Si conditionnelle = new Si();
        conditionnelle.setCondition(condition);
        conditionnelle.setBlocAlors(blocAlors);
        conditionnelle.setBlocSinon(blocSinon);

        // Ajouter la conditionnelle au bloc de la fonction g
        blocG.ajouterUnFils(conditionnelle);

        // Ajouter le bloc à la fonction g
        fonctionG.ajouterUnFils(blocG);

        // Ajouter la fonction g au programme
        programme.ajouterUnFils(fonctionG);

        // Vérification : l'arbre abstrait est-il complet ?
        if (programme.getFils().isEmpty()) {
            throw new RuntimeException("L'arbre abstrait est vide ou incomplet !");
        }

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
