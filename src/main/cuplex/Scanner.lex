package generated.fr.ul.miashs.test;

import java_cup.runtime.Symbol;

%%

/* Options */
%public
%cupsym Sym
%cup
%debug

%{
    void erreur() {
        System.out.println("Caractère inattendu :" + yytext());
    }
%}

/* Définition des macros */
SEP         = [ \t\r\n]
ID          = [a-zA-Z_][a-zA-Z0-9_]*
NUM         = [0-9]+
FLOAT       = [0-9]+"."[0-9]+
STRING      = \"[^\"]*\"
COMMENT     = "//".*

%%

/* Mots-clés */
"fonction"  { return new Symbol(Sym.FONCTION); }
"vide"      { return new Symbol(Sym.VIDE); }
"numerique" { return new Symbol(Sym.NUMERIQUE); }
"entier"    { return new Symbol(Sym.ENTIER); }
"texte"     { return new Symbol(Sym.TEXTE); }
"si"        { return new Symbol(Sym.SI); }
"alors"     { return new Symbol(Sym.ALORS); }
"sinon"     { return new Symbol(Sym.SINON); }
"tantQue"   { return new Symbol(Sym.TANTQUE); }
"faire"     { return new Symbol(Sym.FAIRE); }
"afficher"  { return new Symbol(Sym.AFFICHER); }
"renvoie"   { return new Symbol(Sym.RENVOIE); }

/* Symboles spéciaux */
"{" { return new Symbol(Sym.ACCOUV); }
"}" { return new Symbol(Sym.ACCFER); }
"(" { return new Symbol(Sym.PAROUV); }
")" { return new Symbol(Sym.PARFER); }
"=" { return new Symbol(Sym.EGAL); }
"+" { return new Symbol(Sym.PLUS); }
"-" { return new Symbol(Sym.MOINS); }
"*" { return new Symbol(Sym.MUL); }
"/" { return new Symbol(Sym.DIV); }
"," { return new Symbol(Sym.VIRG); }
";" { return new Symbol(Sym.POINTVIRG); }
"<"  { return new Symbol(Sym.INF); }
"<=" { return new Symbol(Sym.INFEG); }
">=" { return new Symbol(Sym.SUPEG); }
">"  { return new Symbol(Sym.SUP); }
"==" { return new Symbol(Sym.EGALLOG); }


/* Identifiants et constantes */
{ID}        { return new Symbol(Sym.ID, yytext()); }
{NUM}       { return new Symbol(Sym.NUM, Integer.valueOf(yytext())); }
{FLOAT}     { return new Symbol(Sym.FLOAT, Double.valueOf(yytext())); }
{STRING}    { return new Symbol(Sym.STRING, yytext().substring(1, yytext().length() - 1)); }

/* Commentaires et espaces */
{COMMENT}   { /* Ignorer les commentaires */ }
{SEP}       { /* 📌 Correction : Ignorer les espaces et sauts de ligne */ }

/* Gestion des erreurs */
[^]         { erreur(); return new Symbol(Sym.error); }
