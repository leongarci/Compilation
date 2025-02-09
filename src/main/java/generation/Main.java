package generation;

import fr.ul.miashs.compil.arbre.*;
import tds.TableDesSymboles;
import tds.Entree;

public class Main {
    public static void main(String[] args) {
        // Crée le programme principal
        Prog programme = new Prog();

        // Crée la fonction h avec une variable locale
        Fonction fonctionH = new Fonction("h");
        Bloc blocH = new Bloc();

        // Ajouter une variable locale à la TDS
        GenerateurCode generateur = new GenerateurCode();
        Entree varI = new Entree("i", "int", "local");
        varI.setScope("h");

        generateur.getTds().ajouterSymbole("i", varI);

        // Construire une itération : for (i = 0; i < 10; i = i + 1)
        Affectation initialisation = new Affectation();
        initialisation.setFilsGauche(new Idf("i"));
        initialisation.setFilsDroit(new Const(0));

        Inferieur condition = new Inferieur();
        condition.setFilsGauche(new Idf("i"));
        condition.setFilsDroit(new Const(10));

        Plus incrementation = new Plus();
        incrementation.setFilsGauche(new Idf("i"));
        incrementation.setFilsDroit(new Const(1));

        Affectation miseAJour = new Affectation();
        miseAJour.setFilsGauche(new Idf("i"));
        miseAJour.setFilsDroit(incrementation);

        Bloc blocIteration = new Bloc();
        Ecrire ecrireI = new Ecrire();
        ecrireI.setLeFils(new Idf("i"));
        blocIteration.ajouterUnFils(ecrireI);

        TantQue boucle = new TantQue();
        boucle.setCondition(condition);
        boucle.setBloc(blocIteration);

        // Ajouter les instructions au bloc de la fonction h
        blocH.ajouterUnFils(initialisation);
        blocH.ajouterUnFils(boucle);
        blocH.ajouterUnFils(miseAJour);

        // Ajouter le bloc à la fonction h
        fonctionH.ajouterUnFils(blocH);

        // Ajouter la fonction h au programme
        programme.ajouterUnFils(fonctionH);

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
