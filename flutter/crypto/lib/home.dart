import 'package:crypto/techniques/cesar.dart';
import 'package:flutter/material.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CryptApp',
            style: TextStyle(fontSize: 30, fontWeight: FontWeight.bold)),
      ),
      body: const Cesar(),
    );
  }
}
