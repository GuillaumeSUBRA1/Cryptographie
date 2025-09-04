package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button atbash,cesar,vigenere,hill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        atbash=findViewById(R.id.Atbash);
        cesar=findViewById(R.id.Cesar);
        vigenere=findViewById(R.id.Vigenere);
        hill=findViewById(R.id.Hill);
    }

    //accéder à la page du chifffre de césar
    public void Cesar(View V){
        Intent intent= new Intent(this,Cesar.class);
        startActivity(intent);
        finish();
    }


    //accéder à la page de Atbash
    public void Atbash(View V){
        Intent intent= new Intent(this,Atbash.class);
        startActivity(intent);
        finish();

    }

    //accéder à la page du chifffrement de Vigenère
    public void Vigenere(View V){
        Intent intent= new Intent(this,Vigenère.class);
        startActivity(intent);
        finish();

    }

    //accéder à la page du chifffrement de hill
    public void Hill(View V){
        Intent intent= new Intent(this,Hill.class);
        startActivity(intent);
        finish();

    }

    //accéder à la page de la transposition rectangulaire
    public void TranspositionRectangulaire(View V){
        Intent intent= new Intent(this,TranspoRect.class);
        startActivity(intent);
        finish();

    }

    //accéder à la page du chifffrement de Delastelle
    public void Delastelle(View V){
        Intent intent= new Intent(this,Delastelle.class);
        startActivity(intent);
        finish();

    }

    //quitter le jeu
    public void Quitter(View v){
        System.exit(0);
    }

}