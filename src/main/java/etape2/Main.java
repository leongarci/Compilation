package etape2;

import generated.fr.ul.miashs.test.ParserCup;
import generated.fr.ul.miashs.test.Yylex;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage : expr <nom de fichier>");
            System.exit(1);
        }
        try {
            System.out.println("📂 Contenu du fichier "  + " :");
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
            System.out.println("----------------------------------------------------");
            Yylex scanner = new Yylex(new FileReader(args[0]));
            ParserCup parser = new ParserCup(scanner);
            parser.parse();
            System.out.println(parser.res);
        } catch (Exception e) {
            System.err.println("Erreur");
        }
        System.out.println("Terminé !");
    }
}

