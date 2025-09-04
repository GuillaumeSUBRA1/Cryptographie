package com.example.projet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TranspoRect extends Activity {
    Button Chiffrer, Dechiffrer;
    EditText msgchiffre, msgdechiffre, clef;
    String msgChiffre, msgDechiffre, Clef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transporect);
        Chiffrer = findViewById(R.id.chiffrer);
        Dechiffrer = findViewById(R.id.dechiffrer);
        msgchiffre = findViewById(R.id.msg1);
        msgdechiffre = findViewById(R.id.msg2);
        clef = findViewById(R.id.clef);
        reset();
    }

    public void chiffrer(View v){

        //on appelle la méthode initChiffre pour récupérer le message à crypter et la clef
        //si le message à crypter ou la clef est vide, la méthode retourne false et un message d'erreur est renvoyé
        initChiffre();
        if(initChiffre()){

            //on appelle la méhtode iniTab qui retourne le tableau complété pour le cryptage
            String[][] tab=initTab(msgChiffre,Clef,1);

            //on appelle la méthode Transpostion qui effectue le cryptage du message et stocke le résultat dans msgDechiffre
            Transposition(tab,Clef);
        }else{
            msgDechiffre="Erreur";
        }

        //on affiche la version cryptée du message dans le champ prévu à cet effet
        msgdechiffre.setText(msgDechiffre);
    }

    public void dechiffrer(View v){

        //on appelle la méthode initDechiffre pour récupérer le message à décrypter et la clef
        //si le message à décrypter ou la clef est vide, la méthode retourne false et un message d'erreur est renvoyé
        initDechiffre();
        if(initDechiffre()){

            //on appelle la méhtode iniTab qui retourne le tableau complété pour le décryptage
            String[][] tab=initTab(msgDechiffre,Clef,-1);

            //on appelle la méthode Detranspostion qui effectue le cryptage du message et stocke le résultat dans msgDechiffre
            Detransposition(tab);
        }else{
            msgChiffre="Erreur";
        }

        //on affiche la version décryptée du message dans le champ prévu à cet effet
        msgchiffre.setText(msgChiffre);

    }

    //fonction qui crypte un message à partir d'une clef et stocke le résultat dans msgDechiffre
    public void Transposition(String[][] tab,String clef){

        //on stocke une chaîne de acarctère vide dans msgDechiffre pour s'assurer que cette variable est bien vide
        msgDechiffre="";

        //on initialise le compteur
        for(int i=1;i<=clef.length();i++){

            //on parcourt les cases de la 2e ligne à la recherche du nombre correspondant à l'état du compteur
            for(int j=0;j<clef.length();j++){

                //si le caractère stocké dans le tableau à la colonne j correspond au compteur
                if(Integer.parseInt(tab[1][j])==i){

                    //on ajoute à msgDechiffre le reste des cases de la colonne
                    for(int k=2;k<tab.length;k++) {
                        if(tab[k][j]!=null) msgDechiffre +=tab[k][j];
                    }
                }
            }
        }
    }

    //fonction qui décrypte un message à partir d'une clef et stocke le résultat dans msgChiffre
    public void Detransposition(String[][] tab){

        //on stocke une chaîne de acarctère vide dans msgDechiffre pour s'assurer que cette variable est bien vide
        msgChiffre="";

        //on parcourt le tableau et ajoute chaque caractère, à partir de la 3e ligne première colonne, dans msgChiffre
        for(int i=2;i<tab.length;i++){
            for(int j=0;j<tab[0].length;j++){
                if(tab[i][j]!=null) {msgChiffre+=tab[i][j];}
            }
        }
    }

    //fonction permettant de fabriquer le tableau à partir du message et de la clef
    public String[][] initTab(String msg,String clef,int sens){

        //on commence par enlever d'eventuels espaces dans le message
        String msgSansEspace = MsgSansEspace(msg,clef);

        //on crée un tableau qui stocke le classement des caractères de la clef dans la table ascii
        String[] ordre=ordre(clef);

        //on calcule le nombre de lignes nécessaires au tableau
        int lignes = nbLignes(msgSansEspace,clef);

        //on crée le tableau à partir du nombre de lignes calculées précédemment et de la taille de la clef
        String [][] tab=new String[lignes][clef.length()];

        //on instancie la variable qui permettra d'accéder aux caractères du message
        int pos=0;

        //oin vérifie que le message sans espace et la clef ne sont pas vides
        if(!msgSansEspace.equals("")&&!clef.equals("")){
            for(int i=0;i<2;i++){
                for(int j=0;j<clef.length();j++) {
                    //on ajoute un à un les caractères de la clef sur la première ligne
                    if (i == 0) {
                        tab[i][j] = Character.toString(clef.charAt(j));

                    //on ajoute un à un les caractères de l'ordre
                    } else {
                        tab[i][j] = ordre[j];
                    }
                }
            }
            //si on crypte un message, on remplit les lignes du tableau une par une
            //sens = 1 si on crypte un message
            //sens = 0 si on decrypte un message
            if(sens==1){
                for(int i=2;i<lignes;i++) {
                    for (int j = 0; j < clef.length(); j++) {
                        if (pos<msgSansEspace.length()) {
                            tab[i][j] = Character.toString(msgSansEspace.charAt(pos));
                            pos++;
                        }
                    }
                }
            //si on decrypte un message, on remplit le tableau colonne par colonne
            }else{
                //on initie le compte
                for(int i=1;i<=clef.length();i++){
                    //on parcourt les cases du tableau
                    for(int j=0;j<tab[0].length;j++){
                        //si on toruve la case qui correspond à l'état du compteur
                        if(Integer.parseInt(tab[1][j])==i) {
                            //s'il s'gait d'une des colonnes contenant une case de la dernière ligne du tableau
                            //on remplit toutes les cases de la colonne
                            if (j < nbCasesDerniereLigne(msgSansEspace, clef)) {
                                for (int k = 2; k < tab.length; k++) {
                                    if (pos < msgSansEspace.length()) {
                                        tab[k][j] = Character.toString(msgSansEspace.charAt(pos));
                                        pos++;
                                    }
                                }
                            //sinon, on remplit toutes les cases suad la dernière
                            } else {
                                for (int k = 2; k < tab.length-1; k++) {
                                    if (pos < msgSansEspace.length()) {
                                        tab[k][j] = Character.toString(msgSansEspace.charAt(pos));
                                        pos++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return tab;
    }

    //fonction permettant de construire un tableau d'une seule dimension contenant le classement de achaque caractère de la clef en fonction de sa position dans la table ascii
    public String[] ordre(String clef){
        String[] ordre=new String[clef.length()];
        int nb=1; // variable perttant de numéroter l'ordre des caractères présents dans le clef
        for(int i=32;i<127;i++) {
            //on vérifie si le caractère de code ascii i existe dans la clef
            if(clef.indexOf((char)i)!=-1){
                //on vérifie s'il existe plusieurs occurence du caractère dont le code ascii est i
                if((clef.length()-clef.replaceAll(Character.toString((char)i),"").length())>0){
                    //si il y a plusieurs occurences, on parcourt la clef à la recherche de chaque occurence
                    for(int j=0;j<clef.length();j++){
                        if(clef.charAt(j)==(char)i){
                            //chaque occurence est classée selon son rang dans la chaine de caractère.
                            //par exemple, si le 1er et le 3e caractère sont des a, alors le a à l'indice 0 aura le classement
                            //1 parce que c'est le 1ere occurence et la a à l'indice 3 aura le classement 2 car c'est la 2e occurence
                            ordre[j]=Integer.toString(nb);
                            //on finit par incrémenter nb
                            nb++;
                        }
                    }
                }else{
                    //s'il n'y a qu'une seule occurence, on se contente de la classer et d'incrémenter nb
                    if(ordre[clef.indexOf((char)i)]!=null){
                        ordre[clef.indexOf((char)i)]=Integer.toString(nb);
                        nb++;
                    }
                }
            }
        }
        return ordre;
    }

    //fonction permettant de compter le nombre de lignes nécessaires au tableau
    public int nbLignes(String msg, String clef){
        int nb=0;
        //on compte le nombre de lignes que le message va utiliser
        while((clef.length()*nb)<msg.length()){nb++;}
        //on retourne l nombre de lignes utilisées par le message auxquelles on ajoute la ligne de la clef et celle du code donc +2
        return nb+2;
    }

    //fonction permettant de calculer le nombre de cases de la dernière ligne du tableau
    public int nbCasesDerniereLigne(String msg,String clef){

        //on retourne le résultat du calcul suivant:
        //taille de la clef - ((nombre de lignes utilisées par le message * taille de la clé) - taille de la clé)
        return clef.length()-(((nbLignes(msg,clef)-2)*clef.length())-msg.length());
    }

    //fonction permettant d'enlever des espaces
    public String MsgSansEspace(String msg,String clef){

        //on instancie la varibale du message sans les espaces
        String msgSansEspace="";

        //si le message contient une chaîne de caractère
        if(!msg.isEmpty()){

            //on ajoute à msgSansEspace chaque caractère du message sans les espaces
            for(int i=0;i<msg.length();i++){
                if(msg.charAt(i)!=' ') {
                    msgSansEspace += msg.charAt(i);
                }
            }
        }

        //on retourne le message sans les espaces
        return msgSansEspace;
    }

    //fonction permettant à la fois de verifier si le message à crypter et la clef sonitennent bien une chaîne de caractère chacun
    //si c'ets le cas, on les stocke dans les chaînes de caractère dans les variables prévues à cet effet
    public boolean initChiffre(){
        if(!(msgchiffre.getText().toString().isEmpty()) && !(clef.getText().toString().isEmpty())){
            msgChiffre = msgchiffre.getText().toString();
            Clef=clef.getText().toString();
            return true;
        }

        //si le champ de message à crypter ou la clef est vide, on retourne faux
        return false;
    }

    //fonction permettant à la fois de verifier si le message à decrypter et la clef sonitennent bien une chaîne de caractère chacun
    //si c'ets le cas, on les stocke dans les chaînes de caractère dans les variables prévues à cet effet
    public boolean initDechiffre(){
        if(!(msgdechiffre.getText().toString().isEmpty()) && !(clef.getText().toString().isEmpty())){
            msgDechiffre = msgdechiffre.getText().toString();
            Clef=clef.getText().toString();
            return true;
        }

        //si le champ de message à crypter ou la clef est vide, on retourne faux
        return false;
    }


    //fonction permettant de réinstancier les messages à crypter et à décrypter
    public void reset(){
        msgChiffre="";
        msgDechiffre="";
    }

    //fonction permettant de retourner à l'écran d'accueil
    public void Retour(View v){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}