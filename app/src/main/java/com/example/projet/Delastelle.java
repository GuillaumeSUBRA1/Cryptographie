package com.example.projet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Delastelle extends Activity {

    Button Chiffrer, Dechiffrer;
    EditText msgchiffre, msgdechiffre, clef;
    EditText a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,zero,un,deux,trois,quatre,cinq,six,sept,huit,neuf;
    String msgChiffre, msgDechiffre, ClefTranspo;
    String[] caracteres={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "0","1","2","3","4","5","6","7","8","9"};

    ArrayList<EditText> editTexts=new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delastelle);
        Chiffrer = findViewById(R.id.chiffrerDelastelle);
        Dechiffrer = findViewById(R.id.dechiffrerDelastelle);
        msgchiffre = findViewById(R.id.msg1);
        msgdechiffre = findViewById(R.id.msg2);
        clef = findViewById(R.id.clefTransposition);

        a=findViewById(R.id.a);b=findViewById(R.id.b);c=findViewById(R.id.c);
        d=findViewById(R.id.d);e=findViewById(R.id.e);f=findViewById(R.id.f);
        g=findViewById(R.id.g);h=findViewById(R.id.h);i=findViewById(R.id.i);
        j=findViewById(R.id.j);k=findViewById(R.id.k);l=findViewById(R.id.l);
        m=findViewById(R.id.m);n=findViewById(R.id.n);o=findViewById(R.id.o);
        p=findViewById(R.id.p);q=findViewById(R.id.q);r=findViewById(R.id.r);
        s=findViewById(R.id.s);t=findViewById(R.id.t);u=findViewById(R.id.u);
        v=findViewById(R.id.v);w=findViewById(R.id.w);x=findViewById(R.id.x);
        y=findViewById(R.id.y);z=findViewById(R.id.z);

        zero=findViewById(R.id.zero);un=findViewById(R.id.un);deux=findViewById(R.id.deux);
        trois=findViewById(R.id.trois);quatre=findViewById(R.id.quatre);cinq=findViewById(R.id.cinq);
        six=findViewById(R.id.six);sept=findViewById(R.id.sept);huit=findViewById(R.id.huit);
        neuf=findViewById(R.id.neuf);


        editTexts.add(a);editTexts.add(b);editTexts.add(c);editTexts.add(d);editTexts.add(e);editTexts.add(f);
        editTexts.add(g);editTexts.add(h);editTexts.add(i);editTexts.add(j);editTexts.add(k);editTexts.add(l);
        editTexts.add(m);editTexts.add(n);editTexts.add(o);editTexts.add(p);editTexts.add(q);editTexts.add(r);
        editTexts.add(s);editTexts.add(t);editTexts.add(u);editTexts.add(v);editTexts.add(w);editTexts.add(x);
        editTexts.add(y);editTexts.add(z);

        editTexts.add(zero);editTexts.add(un);editTexts.add(deux);editTexts.add(trois);editTexts.add(quatre);
        editTexts.add(cinq);editTexts.add(six);editTexts.add(sept);editTexts.add(huit);editTexts.add(neuf);

        reset();
    }

    //fonction de chiffrement
    public void chiffrer(View v){

        //on v??rifie que la cl?? de substitution soit valide ainsi que la clef de transposition et le message ?? chiffrer ne soient pas vides
        if(CheckClefSub()&&initChiffre()){

            //on fabrique le tableau de la clef de substitution
            String[][] clefSub=clefSub();

            //on supprime les espaces dans le message et on ajoute des "x" pour compl??ter la denri??re partition du message
            String msg=MsgSansEspace(msgchiffre.getText().toString());

            //on r??cup??re un tableau contenant chaque caract??re ainsi que ses coordonn??es dans la cleb de substitution
            String[][] tableauPosition=identifyTabChiffre(msg,clefSub);

            //on r??cup??re la clef de transposition
            int c=Integer.parseInt(ClefTranspo);

            //on instancie une variable qui va stocker les positions
            String pos="";

            //on parcourt la tableau par groupe de caract??res de taille c et on stocke les coordonn??es dans pos
            for(int p=0;p<nbPasses(msg,c);p++){
                for(int i=1;i<tableauPosition.length;i++){
                    for(int j=c*p;j<c*(p+1);j++){
                        pos+=tableauPosition[i][j];
                    }
                }
            }

            //on stocke dans nb le nombre de caract??res "x" ajout??s au message pr??c??demment
            int nb=nbChar((MsgSansEspaceBrut(msgchiffre.getText().toString())).length())-(MsgSansEspaceBrut(msgchiffre.getText().toString())).length();

            //on remplit le message en r??cup??rant les coordonn??es des caract??res dans la clef de substitution par groupe.
            //les coordonn??es dont la position dans pos est paire donc les lignes et celles dont les positions sont impaires sont les colonnes
            for(int i=0;i<pos.length()-(nb*2);i++) {
                if(i%2==1) {
                    msgDechiffre += clefSub[Integer.parseInt(Character.toString(pos.charAt(i-1)))][Integer.parseInt(Character.toString(pos.charAt(i)))];
                }
            }
        }else{
            //sinon on affiche un message d'erreur
            msgdechiffre.setText("Erreur");
        }

        //on affiche le message chiffr??
        msgdechiffre.setText(msgDechiffre);
    }

    public void dechiffrer(View v){
        //on v??rifie que la cl?? de substitution soit valide ainsi que la clef de transposition et le message ?? d??chiffrer ne soient pas vides
        if(CheckClefSub()&&initDechiffre()) {

            //on fabrique le tableau de la clef de substitution
            String[][] clefSub = clefSub();

            //on supprime les espaces dans le message et on ajoute des "x" pour compl??ter la denri??re partition du message
            String msg = MsgSansEspace(msgdechiffre.getText().toString());

            //on r??cup??re un tableau contenant chaque caract??re ainsi que ses coordonn??es dans la cleb de substitution
            Integer[][] tableauPosition=identifyTabDechiffre(msg,clefSub);

            //on r??cup??re la clef de transposition
            int c=Integer.parseInt(ClefTranspo);

            //on stocke dans nb le nombre de caract??res "x" ajout??s au message pr??c??demment
            int nb=nbChar((MsgSansEspaceBrut(msgchiffre.getText().toString())).length())-(MsgSansEspaceBrut(msgchiffre.getText().toString())).length();

            //on r??cup??re chaque carat??re par sa ligne esur la 1ere ligne de tableau et la colonne sur la 2e ligne du tableau
            for(int i=0;i<tableauPosition[0].length-nb;i++) {
                msgChiffre += clefSub[tableauPosition[0][i]][tableauPosition[1][i]];
            }
        }else{
            //sinon on affiche un message d'erreur
            msgchiffre.setText("Erreur");
        }
        //on affiche le message d??chiffr??
        msgchiffre.setText(msgChiffre);
    }

    //fonction qui parcourt la cl?? de transposition et qui r??cup??re les poistion i et j du caract??re r??cherch??
    //cette fonction stocke ces coordonn??es sous forme de chaine de caract??re concat??n??e.
    public String pos(String[][] t,String c){
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                if(t[i][j].equals(c)){
                    String p=Integer.toString(i)+Integer.toString(j);
                    return p;
                }
            }
        }

        return null;
    }

    //fonction qui retourne le 1er caract??re de la fonction pos. Ce caract??re correspond ?? la ligne du caract??re c entr?? en param??tre
    public int i(String[][] t,String c){
        return Integer.parseInt(Character.toString(pos(t,c).charAt(0)));
    }

    //fonction qui retourne le 2e caract??re de la fonction pos. Ce caract??re correspond ?? la colonne du caract??re c entr?? en param??tre
    public int j(String[][] t,String c){
        return Integer.parseInt(Character.toString(pos(t,c).charAt(1)));
    }

    //fonction qui d??termine le nombre de divisions effectu??es sur le message ?? l'aide de la clef de transposition
    public int nbPasses(String m,int c){
        return m.length()/c;
    }

    //fonction qui remplit le tableau de coordonn??es des caract??res du message ?? chiffrer
    public String[][] identifyTabChiffre(String msg,String[][] clefSub){
        String[][] tableauPosition=new String[3][msg.length()];
        for(int i=0;i<msg.length();i++){

            //caract??re sur la 1ere ligne
            tableauPosition[0][i]=Character.toString(msg.charAt(i));

            //ligne sur la 2e ligne
            tableauPosition[1][i]=Integer.toString(i(clefSub,Character.toString(msg.charAt(i))));

            //colonne sur la 3e ligne
            tableauPosition[2][i]=Integer.toString(j(clefSub,Character.toString(msg.charAt(i))));
        }
        return tableauPosition;
    }

    //fonction qui remplit le tableau de coordonn??es des caract??res du message ?? d??chiffrer
    public Integer[][] identifyTabDechiffre(String msg,String[][] clefSub){
        Integer[][] tableauPosition=new Integer[2][msg.length()];
        int c=Integer.parseInt(ClefTranspo); // r??cup??ration de clef de transposition
        int k=0; //varaible permettant de parcourir le message
        int x=0; //variable permettant de s'assurer que le nombnre de caract??res pr??c??dent le dernier couple de caract??res et bien 2
        int y=tableauPosition[0].length;

        //pour chaque portion du tableau
        for(int p=0;p<nbPasses(msg,c);p++){

            //on parcourt les lignes
            for(int i=0;i<tableauPosition.length;i++){

                //on parcourt les colonnes
                for(int j=c*p;j<c*(p+1);j++){

                    //si x<1 alors un seul caract??re ?? ??t?? pass??, il faut donc en passer un 2e pour avoir la num??ro de colonne
                    if(x<1){
                        x++;
                    }else {
                        //sinon on r??mplit le tableau en v??rifiant ?? chaque remplissage que le k reste inf??ireur ?? y

                        //si on est sur la 2e ligne et la 1e colonne d'une porytion de message
                        if (i == 1 && j == c * p) {

                            //on ajoute le num??ro de ligne du caract??re correpsond ?? la 1ere ligne derni??re colonne
                            if(k<y) {
                                tableauPosition[0][(c * (p + 1)) - 1] = i(clefSub, Character.toString(msg.charAt(k)));
                            }

                            //puis on ajoute le num??ro de colonne du caract??re correpsond ?? la 2e ligne premi??re colonne
                            if(k<y) {
                                tableauPosition[1][c * p] = j(clefSub, Character.toString(msg.charAt(k)));
                                k++;
                            }

                        //sinon, on ajoute la ligne dans la colonne pr??cedente et la colonne dans la colonne actuelle
                        } else {
                            if(k<y) {
                                tableauPosition[i][j - 1] = i(clefSub, Character.toString(msg.charAt(k)));
                            }
                            if(k<y) {
                                tableauPosition[i][j] = j(clefSub, Character.toString(msg.charAt(k)));
                                k++;
                            }
                        }

                        //on remet x ?? 0 pour passer ?? un nouveau couple
                        x=0;
                    }
                }
            }
        }

        return tableauPosition;
    }


    //fonction qui r??cup??re la valeur de chaque edit text de la Clef de substitution et qui stockes ces valeurs dans un tableau
    public String[][] clefSub() {
        String[][] c = new String[6][6];
        int pos = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                c[i][j] = editTexts.get(pos).getText().toString();
                pos++;
            }
        }
        return c;
    }

    //fonction qui g??n??re un cl?? de substitution au m??langeant au hasard les lettre sd l'alphabet avec les 10 chiffres
    public void GenererClef(View v){
        ArrayList<Integer> ordre=GenererOrdre();
        for(int i=0;i<36;i++){
            editTexts.get(i).setText(caracteres[ordre.get(i)]);
        }
    }

    //fonction qui v??rifie si la clef de substitution est bine valide
    public boolean CheckClefSub(){
        for(int i=0;i<36;i++){
            String x=editTexts.get(i).getText().toString();
            if(x.isEmpty()){
                return false;
            }
        }
        return true;
    }

    //fonction qui m??lange l'ordre des carat??res
    public ArrayList<Integer> GenererOrdre(){
        ArrayList<Integer> ordre=new ArrayList<>();
        for(int i=0;i<36;i++){
            ordre.add(i);
        }
        Collections.shuffle(ordre);
        return ordre;
    }

    //fonction permettant d'enlever des espaces
    public String MsgSansEspace(String msg){

        //on instancie la varibale du message sans les espaces
        String msgSansEspace="";

        //si le message contient une cha??ne de caract??re
        if(!msg.isEmpty()){

            //on ajoute ?? msgSansEspace chaque caract??re du message sans les espaces
            for(int i=0;i<msg.length();i++){
                if (msg.charAt(i) != ' ') {
                    msgSansEspace += msg.charAt(i);
                }
            }

            //on compte le nombre total de caract??res que doit compter le message pour ??tre divisible en portions de taille c
            int nb=nbChar(msgSansEspace.length());

            //on ajoute au message sans espace la diff??rence des "x" jusqu'?? atteindre la taille nb
            for(int i=msgSansEspace.length();i<nb;i++){
                msgSansEspace += "x";
            }
        }

        //on retourne le message sans les espaces
        return msgSansEspace;
    }

    //fonction permettant d'enlever des espaces
    public String MsgSansEspaceBrut(String msg){

        //on instancie la varibale du message sans les espaces
        String msgSansEspace="";

        //si le message contient une cha??ne de caract??re
        if(!msg.isEmpty()){

            //on ajoute ?? msgSansEspace chaque caract??re du message sans les espaces
            for(int i=0;i<msg.length();i++){
                if (msg.charAt(i) != ' ') {
                    msgSansEspace += msg.charAt(i);
                }
            }
        }

        //on retourne le message sans les espaces
        return msgSansEspace;
    }

    //fonction qui calcule le nombre de caract??re n??cessaires pour que le message soit divibles en portions enti??res de taille ClefTranspo
    public int nbChar(int l){
        int nb=0;

        //tant que le nombre de portions nb n'offre pas suffisament de caract??re pour sotcker l'int??gralit?? du message on incr??mente nb
        while((nb*Integer.parseInt(ClefTranspo))<l){nb++;}

        return nb*Integer.parseInt(ClefTranspo);
    }

    //fonction qui instancie les variables n??cvessaries au chiffrement et qui v??rifie si elles ne sont pas vides
    public boolean initChiffre(){
        if(!(msgchiffre.getText().toString().isEmpty()&&clef.getText().toString().isEmpty())) {
            msgChiffre = msgchiffre.getText().toString();
            ClefTranspo = clef.getText().toString();
            msgDechiffre="";
            return true;
        }
        return false;
    }

    //fonction qui instancie les variables n??cvessaries au d??chiffrement et qui v??rifie si elles ne sont pas vides
    public boolean initDechiffre(){
        if(!(msgdechiffre.getText().toString().isEmpty()&&clef.getText().toString().isEmpty())) {
            msgDechiffre = msgdechiffre.getText().toString();
            ClefTranspo = clef.getText().toString();
            msgChiffre="";
            return true;
        }
        return false;
    }

    //fonction permettant de retourner ?? l'??cran d'accueil
    public void Retour(View v){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    //fonction qui r??instancie les variables
    public void reset(){
        msgDechiffre="";
        msgChiffre="";
        ClefTranspo="";
    }
}
