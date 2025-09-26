import 'package:crypto/shared/enum/technique_crypto.dart';
import 'package:crypto/techniques/atbash.dart';
import 'package:crypto/techniques/cesar.dart';
import 'package:crypto/techniques/hill.dart';
import 'package:crypto/techniques/vigenere.dart';
import 'package:flutter/material.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  Widget page = const Cesar();
  int pageIndex = 0;

  updatePage(int index) {
    pageIndex = index;
    switch (index) {
      case 0:
        page = const Cesar();
        break;
      case 1:
        page = const Atbash();
        break;
      case 2:
        page = const Vigenere();
        break;
      case 3:
        page = const Hill();
        break;
    }
    setState(() {});
  }

  drawer() {
    return Drawer(
        backgroundColor: Colors.transparent,
        child: Padding(
            padding: const EdgeInsets.all(20),
            child: ListView(
              children: TechniqueCrypto.values
                  .map(
                    (e) => Padding(
                        padding: const EdgeInsets.all(10),
                        child: GestureDetector(
                            onTap: () => updatePage(e.index),
                            child: Container(
                                decoration: BoxDecoration(
                                    color: pageIndex == e.index
                                        ? Colors.white
                                        : Colors.black,
                                    borderRadius: const BorderRadius.all(
                                        Radius.circular(25)),
                                    border: Border.fromBorderSide(BorderSide(
                                        color: pageIndex == e.index
                                            ? Colors.black
                                            : Colors.white,
                                        width: 2))),
                                child: Padding(
                                    padding: const EdgeInsets.symmetric(
                                        horizontal: 20),
                                    child: Text(e.titre,
                                        textAlign: TextAlign.center,
                                        style:
                                            TextStyle(color: pageIndex == e.index ? Colors.black : Colors.white, fontWeight: FontWeight.bold, fontSize: 25, fontFamily: 'Shrikhand')))))),
                  )
                  .toList(),
            )));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CryptApp',
            style: TextStyle(fontSize: 30, fontWeight: FontWeight.bold)),
      ),
      body: page,
      drawer: drawer(),
    );
  }
}
