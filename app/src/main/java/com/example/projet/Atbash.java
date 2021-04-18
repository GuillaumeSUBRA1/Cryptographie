package com.example.projet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Atbash extends Activity {
    Button Chiffrer, Dechiffrer;
    EditText msgchiffre,msgdechiffre;

    String msgChiffre,msgDechiffre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atbash);
        Chiffrer=findViewById(R.id.chiffrerAtbash);
        Dechiffrer=findViewById(R.id.dechiffrerAtbash);
        msgchiffre=findViewById(R.id.msg1);
        msgdechiffre=findViewById(R.id.msg2);
        init();
    }

    //fonction permettant de chiffrer le message
    public void chiffrer(View v){
        msgChiffre=msgchiffre.getText().toString();
        msgdechiffre.setText(Atbash(msgChiffre));
        init();
    }

    //fonction permettant de dechiffrer le message
    public void dechiffrer(View v){
        msgDechiffre=msgdechiffre.getText().toString();
        msgchiffre.setText(Atbash(msgDechiffre));
        init();
    }

    //fonction de chiffrement
    public String Atbash(String msg){
        //on initialise la variable qui accueillera le message codé ou décodé
        String msgInverse="";

        //on vérifie que le message n'est pas vide
        if(!msg.equals("")){

            //pour chaque carctère du message
            for(int i=0;i<msg.length();i++){

                //on récupère le code ascii du caractère
                int pos=ascii.toCode(msg.charAt(i));

                //on ajoute le caractère symétiquement opposé en retranchant de 126 la position du caractère et en ajoutant 32 pour le ramaner à la plage 0-94
                msgInverse+=ascii.toChar(126-pos+32);
            }
        }

        //on renvoie le message
        return msgInverse;
    }

    //fonction d'initialisation des chaines de caractère
    public void init(){
        msgChiffre="";
        msgDechiffre="";
    }

    //fonction de retour à l'accueil
    public void Retour(View v){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
