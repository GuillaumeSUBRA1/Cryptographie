package com.example.projet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Hill extends Activity {
    Button Chiffrer, Dechiffrer;
    EditText msgchiffre, msgdechiffre,A,B,C,D;
    String msgChiffre, msgDechiffre, Clef;
    int a,b,c,d; //variables de la matrice
    int aI,bI,cI,dI; //variables de la matrice inversée


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hill);
        Chiffrer = findViewById(R.id.chiffrer);
        Dechiffrer = findViewById(R.id.dechiffrer);
        msgchiffre = findViewById(R.id.msg1);
        msgdechiffre = findViewById(R.id.msg2);
        A=findViewById(R.id.a);
        B=findViewById(R.id.b);
        C=findViewById(R.id.c);
        D=findViewById(R.id.d);
        reset();
    }

    public void chiffrer(View v) {

        String msg="";
        initChiffre();

        //on vérifie que le message n'est pas vide
        if(checkMsg()) {

            //on parcourt le message
            for (int i=0;i< msgChiffre.length();i++){

                //pour chaque càouple de caractères
                if(i%2==1){

                    //on récupère le code ascii des deux caractère
                    int x1=ascii.toCode(msgChiffre.charAt(i-1));
                    int x2=ascii.toCode(msgChiffre.charAt(i));

                    //on effectue les opérations du chiffre de hill.
                    //la fonction ascii.toChar se charge elle même d'effectuer le modulo
                    msg+=ascii.toChar((x1*a+x2*b));
                    msg+=ascii.toChar((x1*c+x2*d));
                }
            }

            //on affiche le message
            msgdechiffre.setText(msg);

        }else{
            //si la clé est vide on affiche un emssage d'erreur
            msgdechiffre.setText("Erreur");
        }
    }


    public void dechiffrer(View v){
        String msg=""; initDechiffre();

        //on vérifie que le message n'est pas vide et que la matrice peut être inversée
        if(checkMsg()&&inverserMatrice()){

            //si c'est le cas, on effectue les même opérations que la fonction de chiffrement mais avec les valeurs
            //de la matrice inversée
            for (int i=0;i< msgDechiffre.length();i++){
                if(i%2==1){
                    int x1=ascii.toCode(msgDechiffre.charAt(i-1));
                    int x2=ascii.toCode(msgDechiffre.charAt(i));
                    msg+=ascii.toChar((x1*aI+x2*bI));
                    msg+=ascii.toChar((x1*cI+x2*dI));
                }
            }
            msgchiffre.setText(msg);
        }else {msg ="Erreur";msgdechiffre.setText(msg);}
        reset();
    }

    //fonction qui vérifie si les 4 variables de la matrice ne sont pas vide
    public boolean checkMsg(){
        return (!msgchiffre.getText().toString().isEmpty()
                &&!A.getText().toString().isEmpty()
                &&!B.getText().toString().isEmpty()
                &&!C.getText().toString().isEmpty()
                &&!D.getText().toString().isEmpty());
    }

    //fonction qui récupère le message à chiffrer
    public void initChiffre(){
        if(!(msgchiffre.getText().toString().isEmpty())){
            msgChiffre = msgchiffre.getText().toString();
        }
        initMatrice();
    }

    //fonction qui récupère le message à déchiffrer
    public void initDechiffre(){
        if(!(msgdechiffre.getText().toString().isEmpty())){
                msgDechiffre = msgdechiffre.getText().toString();
        }
    }

    //on récupère les variables de la matrice
    public void initMatrice(){
        a = Integer.parseInt(A.getText().toString());
        b = Integer.parseInt(B.getText().toString());
        c = Integer.parseInt(C.getText().toString());
        d = Integer.parseInt(D.getText().toString());
    }

    public void reset(){
        msgChiffre="";
        msgDechiffre="";
    }

    //fonction qui détermine si la matrcie est inversible et qui l'inverse si c'est le cas
    public boolean inverserMatrice(){
        initMatrice();

        //calcul du déterminant de la matrice
        int det=determinant(a,b,c,d);

        //si le déterminant n'est pas divisible par les diviseurs de 95
        if(det%2!=0 && det%47!=0){

            //on cherche i tel que i*det=1 mod 95
            int i=0;
            while((i*det)%95!=1){ i++; }

            //on inverse chaque variable avec la valeur de i trouvée
            aI=d*i;
            dI=a*i;
            cI=(c*-1)*i;
            bI=(b*-1)*i;

            //on reoturne vrai pour spécifier que la matrice est inversible et inversée
            return true;
        }

        //sinon on retourne faux
        return false;
    }

    //fonction qui calcule le déterminant de la matrice
    public int determinant(int a,int b,int c,int d){
        return a*d-b*c;
    }

    //fonction de retour à l'écran d'accueil
    public void Retour(View v){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
