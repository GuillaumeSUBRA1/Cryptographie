package com.example.projet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Vigenère extends Activity {
    Button Chiffrer, Dechiffrer;
    EditText clef,msgchiffre,msgdechiffre;

    String msgChiffre,msgDechiffre,Clef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vigenere);
        Chiffrer=findViewById(R.id.chiffrerVigenere);
        Dechiffrer=findViewById(R.id.dechiffrerVigenere);
        msgchiffre=findViewById(R.id.msg1);
        msgdechiffre=findViewById(R.id.msg2);
        clef=findViewById(R.id.clef);
        init();
    }


    //fonction de chiffrement du message
    public void chiffrer(View v){

        //on récupère la clef et le message à chiffrer
        msgChiffre=msgchiffre.getText().toString();
        Clef=clef.getText().toString();

        //on vérifie que la clef et le message à chuiffrer ne sont pas vides
        if(!msgChiffre.isEmpty()&&!Clef.isEmpty()){

            //on parcourt les caractères du message
            for (int i=0 ,a=0; i < msgChiffre.length();a++,i++) {
                //s'il s'agit d'un espace, on l'ajoute au message chiffré et on décremente a
                if(msgChiffre.charAt(i)==' '){
                    msgDechiffre+=" ";
                    a--;
                    //sinon, on effectue l'opérationd e Vigénère et on ajoute la caractère obtenu au message
                }else {
                    int pos = ascii.toCode(msgChiffre.charAt(i))+ascii.toCode(Clef.charAt(a % Clef.length()));
                    msgDechiffre += ascii.toChar(pos);
                }
            }
        }
        msgdechiffre.setText(msgDechiffre);
        init();
    }

    public void dechiffrer(View v){

        //on récupère la clef et le message à dechiffrer
        msgDechiffre=msgdechiffre.getText().toString();
        Clef=clef.getText().toString();

        //on vérifie que la clef et le message à dechiffrer ne sont pas vides
        if(!msgDechiffre.isEmpty()&&!Clef.isEmpty()){

            //on parcourt les caractères du message
            for (int i = 0,a=0; i < msgDechiffre.length();a++,i++) {
                //s'il s'agit d'un espace, on l'ajoute au message chiffré et on décremente a
                if(msgDechiffre.charAt(i)==' '){
                    msgChiffre+=" ";
                    a--;
                    //sinon, on effectue l'opérationd e Vigénère et on ajoute la caractère obtenu au message
                }else {
                    int pos = ascii.toCode(msgDechiffre.charAt(i))-(ascii.toCode(Clef.charAt(a % Clef.length())));
                    msgChiffre += ascii.toChar(pos);
                }
            }
        }
        msgchiffre.setText(msgChiffre);
        init();
    }

    //fonction qui instancie les variables
    public void init(){
        msgChiffre="";
        msgDechiffre="";
    }

    //fonction pour reoturner à l'accueil
    public void Retour(View v){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
