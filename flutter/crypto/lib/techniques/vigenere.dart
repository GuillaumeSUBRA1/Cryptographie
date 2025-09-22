import 'package:flutter/material.dart';

import '../shared/decoration.dart';
import '../shared/utils.dart';

class Vigenere extends StatefulWidget {
  const Vigenere({super.key});

  @override
  State<Vigenere> createState() => _VigenereState();
}

class _VigenereState extends State<Vigenere> {
  TextEditingController messageDechiffreController = TextEditingController();
  TextEditingController messageChiffreController = TextEditingController();
  TextEditingController cleController = TextEditingController();

  bool chiffre = true;

  //fonction de chiffrement
  crypter() {
    final messageAChiffrer =
        removeAccents(messageDechiffreController.text); // le message à chiffrer
    StringBuffer msgChiffre = StringBuffer(); // le message chiffré
    int keyIndex = 0;

    //si le champ de texte de la clé est vide, on arrête la fonction
    if (cleController.text.isEmpty) return;

    final cle = removeAccents(cleController.text);
    final keyCodes = cle.codeUnits; // les codes ASCII de la clé

    //on vérifie que la clef et le message à chiffrer ne sont pas vides
    if (messageAChiffrer.isNotEmpty && cle.isNotEmpty) {
      for (var char in messageAChiffrer.codeUnits) {
        // Vérifie si le caractère est une lettre
        if (isLetter(char)) {
          int base = isUpper(char) ? 65 : 97; // Base ASCII pour A ou a

          // Récupère le caractère de la clé correspondant
          int keyChar = keyCodes[keyIndex % cle.length];

          // Calcule le décalage selon le caractère de la clé
          int keyShift = isUpper(keyChar)
              ? keyChar - 65
              : isLower(keyChar)
                  ? keyChar - 97
                  : 0; // Si le caractère de la clé n'est pas alphabétique

          // Applique le décalage et ajoute le caractère chiffré
          int encryptedChar = ((char - base + keyShift) % 26) + base;
          msgChiffre.writeCharCode(encryptedChar);

          keyIndex++; // Passe au caractère suivant de la clé
        } else {
          // Si ce n'est pas une lettre, on le conserve tel quel
          msgChiffre.writeCharCode(char);
        }
      }
    }

    setState(() {
      messageChiffreController.text = msgChiffre.toString();
    });
  }

  //A TESTER
  //fonction de déchiffrement
  decrypter() {
    final messageADechiffrer = removeAccents(
        messageDechiffreController.text); // le message à déchiffrer
    StringBuffer msgDechiffre = StringBuffer(); // le message déchiffré
    int keyIndex = 0;

    //si le champ de texte de la clé est vide, on arrête la fonction
    if (cleController.text.isEmpty) return;

    //on récupère la clé
    final cle = removeAccents(cleController.text);
    final keyCodes = cle.codeUnits; // les codes ASCII de la clé

    //on vérifie que la clef est supérieure à 0 et que le message à déchiffrer n'est pas vide
    if (messageADechiffrer.isNotEmpty && cle.isNotEmpty) {
      //pour chaque caractère du message chiffré
      for (var char in messageADechiffrer.codeUnits) {
        if (isUpper(char) || isLower(char)) {
          int base = isUpper(char) ? 65 : 97;
          int keyChar = keyCodes[keyIndex % keyCodes.length];
          int keyShift = isUpper(keyChar)
              ? keyChar - 65
              : isLower(keyChar)
                  ? keyChar - 97
                  : 0;

          // Applique le décalage inverse pour déchiffrer
          int decryptedChar = ((char - base - keyShift + 26) % 26) + base;
          msgDechiffre.writeCharCode(decryptedChar);
          keyIndex++;
        } else {
          msgDechiffre
              .writeCharCode(char); // conserve les caractères non alphabétiques
        }
      }
    }

    setState(() {
      messageChiffreController.text = msgDechiffre.toString();
    });
  }

  @override
  Widget build(BuildContext context) {
    final width = MediaQuery.of(context).size.width;
    return Center(
        child: Padding(
            padding: const EdgeInsets.all(10),
            child: Column(children: [
              const Row(
                children: [
                  Expanded(
                      child: Text(
                    "Vigenere",
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                  )),
                ],
              ),
              const SizedBox(height: 10),
              Row(children: [
                Expanded(
                    child: textFieldDecoration(
                        TextField(
                          controller: messageDechiffreController,
                          onTapOutside: (_) =>
                              FocusManager.instance.primaryFocus?.unfocus(),
                          onChanged: (value) {
                            chiffre ? crypter() : decrypter();
                          },
                          decoration: const InputDecoration(
                            hintText: "Votre message",
                            labelText: "Votre message",
                            border: InputBorder.none,
                          ),
                        ),
                        width)),
              ]),
              const SizedBox(height: 10),
              Row(children: [
                Expanded(
                    child: textFieldDecoration(
                        TextField(
                          controller: cleController,
                          onTapOutside: (_) =>
                              FocusManager.instance.primaryFocus?.unfocus(),
                          keyboardType: TextInputType.text,
                          onChanged: (value) {
                            chiffre ? crypter() : decrypter();
                          },
                          decoration: const InputDecoration(
                            hintText: "Clé",
                            labelText: "Clé",
                            border: InputBorder.none,
                          ),
                        ),
                        width)),
              ]),
              const SizedBox(height: 10),
              Row(
                children: [
                  Expanded(
                      child: textFieldDecoration(
                          TextField(
                            controller: messageChiffreController,
                            onTapOutside: (_) =>
                                FocusManager.instance.primaryFocus?.unfocus(),
                            decoration: InputDecoration(
                              hintText: "Message ${chiffre ? "" : "dé"}chiffré",
                              labelText:
                                  "Message ${chiffre ? "" : "dé"}chiffré",
                              border: InputBorder.none,
                            ),
                          ),
                          width))
                ],
              ),
              const SizedBox(height: 20),
              Row(
                children: [
                  Expanded(
                      child: GestureDetector(
                          child: Card(
                              color: Colors.blue,
                              child: Padding(
                                  padding:
                                      const EdgeInsets.symmetric(vertical: 12),
                                  child: Text(
                                    "${chiffre ? "dé" : ""}chiffrer",
                                    textAlign: TextAlign.center,
                                    style: const TextStyle(
                                        fontSize: 20,
                                        fontWeight: FontWeight.bold),
                                  ))),
                          onTap: () {
                            chiffre = !chiffre;
                            chiffre ? crypter() : decrypter();
                          }))
                ],
              )
            ])));
  }
}
