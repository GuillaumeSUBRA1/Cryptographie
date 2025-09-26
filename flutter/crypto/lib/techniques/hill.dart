import 'package:flutter/material.dart';

import '../shared/decoration.dart';
import '../shared/utils.dart';

class Hill extends StatefulWidget {
  const Hill({super.key});

  @override
  State<Hill> createState() => _HillState();
}

class _HillState extends State<Hill> {
  TextEditingController messageDechiffreController = TextEditingController();
  TextEditingController messageChiffreController = TextEditingController();
  TextEditingController aCleController = TextEditingController();
  TextEditingController bCleController = TextEditingController();
  TextEditingController cCleController = TextEditingController();
  TextEditingController dCleController = TextEditingController();

  List<List<int>> cle = [];

  bool chiffre = true;

  //fonction qui retourne la matrice clé
  List<List<int>> getKey() {
    return [
      [int.parse(aCleController.text), int.parse(bCleController.text)],
      [int.parse(cCleController.text), int.parse(dCleController.text)],
    ];
  }

  bool canCalculate() {
    return messageDechiffreController.text.isNotEmpty &&
        aCleController.text.isNotEmpty &&
        bCleController.text.isNotEmpty &&
        cCleController.text.isNotEmpty &&
        dCleController.text.isNotEmpty;
  }

  // Multiplie une matrice clé par un vecteur de texte (modulo 26)
  List<int> matrixMultiply(List<List<int>> key, List<int> block) {
    int size = key.length;
    List<int> result = List.filled(size, 0);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        result[i] += key[i][j] * block[j];
      }
      result[i] %= 26;
    }

    return result;
  }

  // Chiffre un message avec l'algorithme de Hill
  calculate(bool crypt) {
    final messageAChiffrer =
        messageDechiffreController.text; // le message à chiffrer
    StringBuffer msgChiffre = StringBuffer(); // le message chiffré

    //si un des champ de texte de la clé est vide, on arrête la fonction
    if (!canCalculate()) return;

    //on récupère la clé
    cle = crypt ? getKey() : inverseMatrix2x2(getKey());

    int blockSize = cle.length;
    String clean =
        messageAChiffrer.toUpperCase().replaceAll(RegExp(r'[^A-Z]'), '');

    // Complète le message si sa longueur n'est pas un multiple du bloc
    while (clean.length % blockSize != 0) {
      clean += 'X';
    }

    for (int i = 0; i < clean.length; i += blockSize) {
      List<int> block =
          List.generate(blockSize, (j) => charToInt(clean[i + j]));
      List<int> encryptedBlock = matrixMultiply(cle, block);
      msgChiffre.writeAll(encryptedBlock.map(intToChar));
    }

    setState(() {
      messageChiffreController.text = msgChiffre.toString();
    });
  }

  textCle(TextEditingController c, String t) {
    return Text(c.text.isNotEmpty ? c.text : t,
        textAlign: TextAlign.center,
        style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold));
  }

  textFieldCle(TextEditingController c, String t) {
    return TextField(
      controller: c,
      onTapOutside: (_) => FocusManager.instance.primaryFocus?.unfocus(),
      keyboardType: TextInputType.number,
      onChanged: (value) {
        calculate(chiffre);
        setState(() {});
      },
      decoration: InputDecoration(
        hintText: t,
        labelText: t,
        prefixText: "$t : ",
        border: InputBorder.none,
      ),
    );
  }

  cleanKey() {
    setState(() {
      aCleController.clear();
      bCleController.clear();
      cCleController.clear();
      dCleController.clear();
    });
  }

  @override
  Widget build(BuildContext context) {
    final width = MediaQuery.of(context).size.width;
    return Padding(
        padding: const EdgeInsets.all(10),
        child: SingleChildScrollView(
            child: Column(children: [
          const Row(
            children: [
              Expanded(
                  child: Text(
                "Hill",
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
                        calculate(chiffre);
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
          Card(
              child: Padding(
                  padding: const EdgeInsets.all(5),
                  child: Row(children: [
                    Expanded(
                        child: Column(children: [
                      Row(children: [
                        Expanded(
                            child: textFieldDecoration(
                                textFieldCle(aCleController, "A"), width)),
                      ]),
                      Row(children: [
                        Expanded(
                            child: textFieldDecoration(
                                textFieldCle(bCleController, "B"), width)),
                      ]),
                      Row(children: [
                        Expanded(
                            child: textFieldDecoration(
                                textFieldCle(cCleController, "C"), width))
                      ]),
                      Row(children: [
                        Expanded(
                            child: textFieldDecoration(
                                textFieldCle(dCleController, "D"), width))
                      ]),
                    ])),
                    const SizedBox(width: 10),
                    Expanded(
                        child: Card(
                            color: Colors.black12,
                            child: Padding(
                                padding: const EdgeInsets.all(5),
                                child: Column(
                                    mainAxisSize: MainAxisSize.max,
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceEvenly,
                                    crossAxisAlignment:
                                        CrossAxisAlignment.stretch,
                                    children: [
                                      const Text(
                                        "Clé",
                                        style: TextStyle(
                                            fontSize: 20,
                                            fontWeight: FontWeight.bold),
                                        textAlign: TextAlign.center,
                                      ),
                                      const SizedBox(height: 20),
                                      SizedBox(
                                          width: MediaQuery.of(context)
                                                  .size
                                                  .width /
                                              2,
                                          child: GridView(
                                              shrinkWrap: true,
                                              gridDelegate:
                                                  const SliverGridDelegateWithFixedCrossAxisCount(
                                                      childAspectRatio: 1.2,
                                                      mainAxisSpacing: 2,
                                                      crossAxisSpacing: 2,
                                                      crossAxisCount: 2),
                                              children: [
                                                textCle(aCleController, "A"),
                                                textCle(bCleController, "B"),
                                                textCle(cCleController, "C"),
                                                textCle(dCleController, "D"),
                                              ])),
                                      const SizedBox(height: 10),
                                      Row(children: [
                                        Expanded(
                                            child: GestureDetector(
                                                child: const Card(
                                                    color: Colors.blue,
                                                    child: Padding(
                                                        padding: EdgeInsets
                                                            .symmetric(
                                                                vertical: 12),
                                                        child: Icon(Icons
                                                            .restore_from_trash_outlined))),
                                                onTap: () {
                                                  cleanKey();
                                                }))
                                      ])
                                    ]))))
                  ]))),
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
          const SizedBox(height: 10),
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
                        calculate(chiffre);
                      }))
            ],
          )
        ])));
  }
}
