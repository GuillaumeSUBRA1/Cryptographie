package com.example.projet;

public class ascii {

    //fonction qui retourne le caractère correspondant au code ascii passé en argument et ramené sur la plage 0-94
    public static char toChar(int i) {
        while(i<0){i+=95;}
        while(i>95){i-=95;}
        return (char)(i+32);
    }

    //fonction qui retourne le code ascii correspondant au carctère passéen argument -32 pour le ramaner sur la plage 0-94
    public static int toCode(char i){return i-32;}
}
