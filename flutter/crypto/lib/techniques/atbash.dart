import 'package:flutter/material.dart';

import '../shared/decoration.dart';
import '../shared/utils.dart';

class Atbash extends StatefulWidget {
  const Atbash({super.key});

  @override
  State<Atbash> createState() => _Atbash();
}

class _Atbash extends State<Atbash> {
  TextEditingController messageDechiffreController = TextEditingController();
  TextEditingController messageChiffreController = TextEditingController();

  bool chiffre = true;

  //fonction de chiffrement
  crypter() {
    final messageAChiffrer =
        messageDechiffreController.text; // le message à chiffrer
    StringBuffer msgChiffre = StringBuffer(); // le message chiffré

    if (messageAChiffrer.isEmpty) {
      setState(() {
        messageChiffreController.clear();
      });
      return;
    }

    for (var char in messageAChiffrer.codeUnits) {
      if (isUpper(char)) {
        // Majuscules A-Z → Z-A
        msgChiffre.writeCharCode(65 + (90 - char));
      } else if (isLower(char)) {
        // Minuscules a-z → z-a
        msgChiffre.writeCharCode(97 + (122 - char));
      } else {
        // Autres caractères (chiffres, ponctuation, etc.)
        msgChiffre.writeCharCode(char);
      }
    }

    setState(() {
      messageChiffreController.text = msgChiffre.toString();
    });
  }

  @override
  Widget build(BuildContext context) {
    final width = MediaQuery.of(context).size.width;
    return Padding(
        padding: const EdgeInsets.all(10),
        child: Column(children: [
          const Row(
            children: [
              Expanded(
                  child: Text(
                "Atbash",
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
                      onChanged: (_) => crypter(),
                      decoration: const InputDecoration(
                        hintText: "Votre message",
                        labelText: "Votre message",
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
                          labelText: "Message ${chiffre ? "" : "dé"}chiffré",
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
                              padding: const EdgeInsets.symmetric(vertical: 12),
                              child: Text(
                                "${chiffre ? "dé" : ""}chiffrer",
                                textAlign: TextAlign.center,
                                style: const TextStyle(
                                    fontSize: 20, fontWeight: FontWeight.bold),
                              ))),
                      onTap: () {
                        chiffre = !chiffre;
                        crypter();
                      }))
            ],
          )
        ]));
  }
}
