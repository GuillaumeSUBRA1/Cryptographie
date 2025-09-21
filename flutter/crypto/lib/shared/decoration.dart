import 'package:flutter/material.dart';

// Decoration pour les textfield
textFieldDecoration(Widget child, double size) {
  return Container(
      margin: const EdgeInsets.symmetric(vertical: 10),
      padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 5),
      width: size * 0.8,
      decoration: BoxDecoration(
        color: Colors.black,
        borderRadius: BorderRadius.circular(30),
      ),
      child: child);
}
