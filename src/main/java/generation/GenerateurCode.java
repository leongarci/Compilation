package generation;

import fr.ul.miashs.compil.arbre.*;
import tds.TableDesSymboles;
import tds.Entree;

public class GenerateurCode {

    private StringBuilder code;
    private TableDesSymboles tds;
    private int adresseCourante;

    public GenerateurCode() {
        this.code = new StringBuilder();
        this.tds = new TableDesSymboles();
        this.adresseCourante = 0;
    }

    public String genererProgramme(Prog programme) {
        initialiserProgramme();
        genererFonctions(programme);
        finaliserProgramme();
        return code.toString();
    }

    private void initialiserProgramme() {
        code.append(".include beta.uasm\n");
        code.append("CMOVE(pile, SP)\n");
        code.append("BR(debut)\n\n");
    }

    private void genererFonctions(Prog programme) {
        for (Noeud fonction : programme.getFils()) {
            if (fonction instanceof Fonction) {
                genererFonction((Fonction) fonction);
            }
        }
    }

    private void genererFonction(Fonction fonction) {
        code.append(fonction.getValeur()).append(":\n");
        code.append("    PUSH(LP)\n    PUSH(BP)\n    MOVE(SP, BP)\n");
        code.append("    ALLOCATE(0)\n");

        for (Noeud instruction : fonction.getFils()) {
            genererInstruction(instruction);
        }

        code.append("    MOVE(BP, SP)\n    POP(BP)\n    POP(LP)\n    RTN()\n\n");
    }

    private void genererInstruction(Noeud instruction) {
        if (instruction instanceof Ecrire) {
            genererEcriture((Ecrire) instruction);
        } else if (instruction instanceof Affectation) {
            genererAffectation((Affectation) instruction);
        } else if (instruction instanceof Si) {
            genererCondition((Si) instruction);
        } else if (instruction instanceof TantQue) {
            genererBoucle((TantQue) instruction);
        } else if (instruction instanceof Retour) {
            genererRetour((Retour) instruction);
        } else if (instruction instanceof Bloc) {
            genererInstructions((Bloc) instruction);
        } else if (instruction instanceof Appel) {
            genererAppel((Appel) instruction);
        }
    }

    private void genererInstructions(Bloc bloc) {
        for (Noeud instruction : bloc.getFils()) {
            genererInstruction(instruction);
        }
    }

    private void genererEcriture(Ecrire ecrire) {
        genererExpression(ecrire.getLeFils());
        code.append("    WRINT()\n");
    }

    private void genererAffectation(Affectation affectation) {
        Noeud gauche = affectation.getFilsGauche();
        Noeud droit = affectation.getFilsDroit();

        if (gauche instanceof Idf) {
            String nomVariable = ((Idf) gauche).getValeur().toString();
            Entree entree = tds.chercherSymbole(nomVariable);

            if (entree == null) {
                tds.ajouterSymbole(nomVariable, "int", adresseCourante);
                entree = tds.chercherSymbole(nomVariable);
                adresseCourante += 4;
            }

            genererExpression(droit);
            code.append("    ST(R0, ").append(entree.getAdresse()).append(")\n");
        }
    }

    private void genererAppel(Appel appel) {
        for (Noeud argument : appel.getFils()) {
            genererExpression(argument);
            code.append("    PUSH(R0)\n");
        }

        code.append("    CALL(").append(appel.getValeur()).append(")\n");

        if (!appel.getFils().isEmpty()) {
            code.append("    ADD(SP, ").append(appel.getFils().size() * 4).append(", SP)\n");
        }
    }

    private void genererRetour(Retour retour) {
        genererExpression(retour.getLeFils());
        code.append("    MOVE(R0, R1)\n    RTN()\n");
    }

    private void genererCondition(Si condition) {
        String labelSinon = "sinon_" + condition.getValeur();
        String labelFin = "fin_si_" + condition.getValeur();

        genererExpression(condition.getCondition());
        code.append("    CMOVE(0, R1)\n");
        code.append("    CMPEQ(R0, R1, R2)\n");
        code.append("    BT(").append(labelSinon).append(")\n");

        genererInstructions(condition.getBlocAlors());
        code.append("    BR(").append(labelFin).append(")\n");

        code.append(labelSinon).append(":\n");
        genererInstructions(condition.getBlocSinon());

        code.append(labelFin).append(":\n");
    }

    private void genererBoucle(TantQue boucle) {
        String labelDebut = "debut_tq_" + boucle.getValeur();
        String labelFin = "fin_tq_" + boucle.getValeur();

        code.append(labelDebut).append(":\n");
        genererExpression(boucle.getCondition());
        code.append("    CMOVE(0, R1)\n");
        code.append("    CMPEQ(R0, R1, R2)\n");
        code.append("    BT(").append(labelFin).append(")\n");

        genererInstructions(boucle.getBloc());
        code.append("    BR(").append(labelDebut).append(")\n");

        code.append(labelFin).append(":\n");
    }

    private void genererExpression(Noeud expression) {
        if (expression instanceof Const) {
            code.append("    CMOVE(").append(((Const) expression).getValeur()).append(", R0)\n");
        } else if (expression instanceof Idf) {
            String nomVariable = ((Idf) expression).getValeur().toString();
            Entree entree = tds.chercherSymbole(nomVariable);

            if (entree != null) {
                code.append("    LD(").append(entree.getAdresse()).append(", R0)\n");
            } else {
                throw new RuntimeException("Variable non déclarée: " + nomVariable);
            }
        } else if (expression instanceof Plus) {
            genererExpression(((Plus) expression).getFilsGauche());
            code.append("    PUSH(R0)\n");
            genererExpression(((Plus) expression).getFilsDroit());
            code.append("    POP(R1)\n    ADD(R1, R0, R0)\n");
        } else if (expression instanceof Moins) {
            genererExpression(((Moins) expression).getFilsGauche());
            code.append("    PUSH(R0)\n");
            genererExpression(((Moins) expression).getFilsDroit());
            code.append("    POP(R1)\n    SUB(R1, R0, R0)\n");
        } else if (expression instanceof Multiplication) {
            genererExpression(((Multiplication) expression).getFilsGauche());
            code.append("    PUSH(R0)\n");
            genererExpression(((Multiplication) expression).getFilsDroit());
            code.append("    POP(R1)\n    MUL(R1, R0, R0)\n");
        } else if (expression instanceof Division) {
            genererExpression(((Division) expression).getFilsGauche());
            code.append("    PUSH(R0)\n");
            genererExpression(((Division) expression).getFilsDroit());
            code.append("    POP(R1)\n    DIV(R1, R0, R0)\n");
        }
    }

    private void finaliserProgramme() {
        code.append("debut:\n    CALL(main)\n    HALT()\n\npile:\n");
    }
}
