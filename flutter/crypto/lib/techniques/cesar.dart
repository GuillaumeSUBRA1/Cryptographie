import 'package:flutter/material.dart';

class Cesar extends StatefulWidget {
  const Cesar({super.key});

  @override
  State<Cesar> createState() => _CesarState();
}

class _CesarState extends State<Cesar> {
  TextEditingController messageController = TextEditingController();
  TextEditingController cleController = TextEditingController();

  String message = "";

  crypter() {
    List<int> messageAscii = stringToAscii(messageController.text);
    for (int i = 0; i < messageAscii.length; i++) {
      messageAscii[i] = messageAscii[i] + int.parse(cleController.text);
    }
    setState(() {
      message = String.fromCharCodes(messageAscii);
    });
  }

  List<int> stringToAscii(String message) {
    return message.codeUnits;
  }

  @override
  Widget build(BuildContext context) {
    return Center(
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
        ))
      ]),
      Row(children: [
        TextField(
          controller: cleController,
          onChanged: (value) {
            crypter();
          },
        ),
      ]),
      Row(
        children: [Expanded(child: Text(message))],
      )
    ]));
  }
}
