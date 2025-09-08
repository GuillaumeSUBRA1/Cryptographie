import 'package:flutter/material.dart';

class Cesar extends StatefulWidget {
  const Cesar({super.key});

  @override
  State<Cesar> createState() => _CesarState();
}

class _CesarState extends State<Cesar> {
  TextEditingController messageController = TextEditingController();
  TextEditingController messageChiffreController = TextEditingController();
  TextEditingController cleController = TextEditingController();

  //fonction de chiffrement

  //###########################//
  //        A TESTER           //
  //###########################//
  crypter() {
    String messageAChiffrer = messageController.text; // le message à chiffrer
    String msgChiffre = ""; // le message chiffré

    //si le champ de texte de la clé est vide, on arrête la fonction
    if (cleController.text.isEmpty) {
      return;
    }

    //on récupère la clé
    int cle = int.parse(cleController.text);

    //on vérifie que la clef est supérieure à 0 et que le message à chiffrer n'est pas vide
    if (messageAChiffrer.isNotEmpty && cle > 0) {
      //pour chaque caractère du message
      for (int i = 0; i < messageAChiffrer.length; i++) {
        //on récupère le code ascii de la lettre à l'index i
        int ascii = letterToAscii(messageAChiffrer[i]);

        //si ce n'est pas une lettre, on ajoute le caractère à la suite du message chiffré
        //et passe à la suivante
        if (!isLetter(ascii)) {
          msgChiffre += messageAChiffrer[i];
          continue;
        }

        //variable pour vérifier s'il s'agit d'une majuscule
        bool maj = ascii >= 65 && ascii <= 90;

        //on ajoute la clé au code ascii
        int toCesar = ascii + cle % 26;

        //s'il dépasse la limite, on le ramène au début en soustrayant 26 jusqu'à
        // ce qu'il soit inférieur à la limite
        while (maj && toCesar > 90 || !maj && toCesar > 122) {
          toCesar = toCesar - 26;
        }

        //enfin, on ajoute au message chiffre le caractère correspondant au code ascii
        msgChiffre += asciiToLetter(toCesar);
      }
    }

    setState(() {
      messageChiffreController.text = msgChiffre;
    });
  }

  //convertir la lettre en code ascii
  int letterToAscii(String lettre) {
    return lettre.codeUnitAt(0);
  }

  //récupérer la lettre correspondante au code ascii
  String asciiToLetter(int ascii) {
    return String.fromCharCode(ascii);
  }

  //vérifier si le code ascii correspond à une lettre
  bool isLetter(int ascii) {
    return (ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122);
  }

  @override
  Widget build(BuildContext context) {
    return Center(
        child: Padding(
            padding: const EdgeInsets.all(10),
            child: Column(children: [
              const Row(
                children: [Expanded(child: Text("Cesar"))],
              ),
              Row(children: [
                Expanded(
                    child: TextField(
                  controller: messageController,
                  onChanged: (value) {
                    crypter();
                  },
                  decoration: const InputDecoration(
                      hintText: "Votre message", labelText: "Votre message"),
                ))
              ]),
              Row(children: [
                Expanded(
                    child: TextField(
                  controller: cleController,
                  onChanged: (value) {
                    crypter();
                  },
                  decoration:
                      const InputDecoration(hintText: "clé", labelText: "clé"),
                )),
              ]),
              Row(
                children: [
                  Expanded(
                      child: TextField(
                    controller: messageChiffreController,
                    decoration: const InputDecoration(
                        hintText: "Message chiffré",
                        labelText: "Message chiffré"),
                  ))
                ],
              )
            ])));
  }
}
