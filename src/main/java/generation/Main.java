package generation;

import fr.ul.miashs.compil.arbre.*;
import tds.TableDesSymboles;
import tds.Entree;

public class Main {
    public static void main(String[] args) {
        // Crée le programme principal
        Prog programme = new Prog();

        // Crée la fonction fact pour calculer la factorielle récursivement
        Fonction fonctionFact = new Fonction("fact");
        Bloc blocFact = new Bloc();

        // Ajouter les paramètres et la variable locale à la TDS
        GenerateurCode generateur = new GenerateurCode();
        Entree paramN = new Entree("n", "int", "param");
        paramN.setRang(0);
        paramN.setScope("fact");
        Entree varResult = new Entree("result", "int", "local");
        varResult.setScope("fact");

        generateur.getTds().ajouterSymbole("n", paramN);
        generateur.getTds().ajouterSymbole("result", varResult);

        // Construire une conditionnelle : if (n <= 1) result = 1; else result = n * fact(n - 1);
        InferieurEgal condition = new InferieurEgal();
        condition.setFilsGauche(new Idf("n"));
        condition.setFilsDroit(new Const(1));

        // Bloc alors : result = 1
        Bloc blocAlors = new Bloc();
        Affectation affectationResultAlors = new Affectation();
        affectationResultAlors.setFilsGauche(new Idf("result"));
        affectationResultAlors.setFilsDroit(new Const(1));
        blocAlors.ajouterUnFils(affectationResultAlors);

        // Bloc sinon : result = n * fact(n - 1)
        Bloc blocSinon = new Bloc();
        Moins decrementation = new Moins();
        decrementation.setFilsGauche(new Idf("n"));
        decrementation.setFilsDroit(new Const(1));

        Appel appelFact = new Appel("fact");
        appelFact.ajouterUnFils(decrementation);

        Multiplication multiplication = new Multiplication();
        multiplication.setFilsGauche(new Idf("n"));
        multiplication.setFilsDroit(appelFact);

        Affectation affectationResultSinon = new Affectation();
        affectationResultSinon.setFilsGauche(new Idf("result"));
        affectationResultSinon.setFilsDroit(multiplication);
        blocSinon.ajouterUnFils(affectationResultSinon);

        // Construire la conditionnelle
        Si conditionnelle = new Si();
        conditionnelle.setCondition(condition);
        conditionnelle.setBlocAlors(blocAlors);
        conditionnelle.setBlocSinon(blocSinon);

        // Construire le retour : return result
        Retour retourResult = new Retour(new Idf("result"));

        // Ajouter les instructions au bloc de la fonction fact
        blocFact.ajouterUnFils(conditionnelle);
        blocFact.ajouterUnFils(retourResult);

        // Ajouter le bloc à la fonction fact
        fonctionFact.ajouterUnFils(blocFact);

        // Ajouter la fonction fact au programme
        programme.ajouterUnFils(fonctionFact);

        // Vérification : l'arbre abstrait est-il complet ?
        if (programme.getFils().isEmpty()) {
            throw new RuntimeException("L'arbre abstrait est vide ou incomplet !");
        }

        // Affiche l'arbre abstrait

        // Génère le code assembleur
        String codeAssembleur = generateur.genererProgramme(programme);

        // Affiche le code assembleur généré
        System.out.println(codeAssembleur);

        // Affiche la table des symboles
        generateur.getTds().afficherTDS();
    }
}
