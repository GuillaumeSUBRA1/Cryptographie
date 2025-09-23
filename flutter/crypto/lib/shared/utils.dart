import 'package:flutter/material.dart';

/// Supprime les accents des lettres
String removeAccents(String input) {
  final accentsMap = {
    'à': 'a',
    'â': 'a',
    'ä': 'a',
    'é': 'e',
    'è': 'e',
    'ê': 'e',
    'ë': 'e',
    'î': 'i',
    'ï': 'i',
    'ô': 'o',
    'ö': 'o',
    'ù': 'u',
    'û': 'u',
    'ü': 'u',
    'ç': 'c',
    'À': 'A',
    'Â': 'A',
    'Ä': 'A',
    'É': 'E',
    'È': 'E',
    'Ê': 'E',
    'Ë': 'E',
    'Î': 'I',
    'Ï': 'I',
    'Ô': 'O',
    'Ö': 'O',
    'Ù': 'U',
    'Û': 'U',
    'Ü': 'U',
    'Ç': 'C',
  };

  return input.characters.map((char) => accentsMap[char] ?? char).join();
}

/// vérifier si le code ascii correspond à une lettre
bool isLetter(int ascii) =>
    (ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122);

/// verifier si le code ascii correspond à une lettre majuscule
bool isUpper(int char) => char >= 65 && char <= 90;

/// verifier si le code ascii correspond à une lettre minuscule
bool isLower(int char) => char >= 97 && char <= 122;
