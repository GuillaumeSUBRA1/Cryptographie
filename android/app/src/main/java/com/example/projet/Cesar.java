package com.example.projet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/// chiffrer un message en décalant tous les codes ascii de N rangs vers la droite

public class Cesar extends Activity {

    Button Chiffrer, Dechiffrer;
    EditText clef,msgchiffre,msgdechiffre;

    int Clef=0;
    String msgChiffre,msgDechiffre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cesar);
        Chiffrer=findViewById(R.id.chiffrerCesar);
        Dechiffrer=findViewById(R.id.dechiffrerCesar);
        msgchiffre=findViewById(R.id.msg1);
        msgdechiffre=findViewById(R.id.msg2);
        clef=findViewById(R.id.clef);
        init();
    }


    //fonction de chiffrement
    public void chiffrer(View v){

        //on récupère le mesage à chiffrer
        msgChiffre=msgchiffre.getText().toString();

        //on récupère la clef de chiffrement
        Clef=Integer.parseInt(clef.getText().toString());

        //on vérifie que la clef est inférieure ou égale à 95 et que le message à chiffrer n'est pas vide
        if(!msgChiffre.equals("")&&Clef>0&&Clef<=95){

            //pour chaque caractère du message
            for(int i=0;i<msgChiffre.length();i++){

                //on récupère son code ascii
                int pos=ascii.toCode(msgChiffre.charAt(i));

                //on le décale de N rang (N est valeur de la clé) puis on ajoute le caractère correspondant au message
                msgDechiffre+=ascii.toChar(pos+Clef);
            }
        }
        msgdechiffre.setText(msgDechiffre);

        //réinitialisation des variables
        init();
    }

    //fonction de dechiffrement
    public void dechiffrer(View v){

        //on récupère le mesage à chiffrer
        msgDechiffre=msgdechiffre.getText().toString();

        //on récupère la clef de chiffrement
        Clef=Integer.parseInt(clef.getText().toString());

        //on vérifie que la clef est inférieure ou égale à 95 et que le message à chiffrer n'est pas vide
        if(!msgDechiffre.equals("")&&Clef>0&&Clef<=95){
            for(int i=0;i<msgDechiffre.length();i++){

                //on récupère son code ascii
                int pos=ascii.toCode(msgDechiffre.charAt(i));

                //on le décale de N rang (N est valeur de la clé) puis on ajoute le caractère correspondant au message
                msgChiffre+=ascii.toChar(pos-Clef);
            }
        }
        msgchiffre.setText(msgChiffre);

        //réinitialisation des variables
        init();
    }

    //fonction d'initialisation des variables
    public void init(){
        msgChiffre="";
        msgDechiffre="";
    }


    //fonction de reotur à l'accueil
    public void Retour(View v){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
