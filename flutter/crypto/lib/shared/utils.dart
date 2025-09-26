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

/// Convertit une lettre majuscule en entier (A=0, B=1, ..., Z=25)
int charToInt(String c) => c.codeUnitAt(0) - 65;

/// Convertit un entier en lettre majuscule
String intToChar(int i) => String.fromCharCode((i % 26) + 65);

/// Calcule le déterminant d'une matrice 2x2
int determinant2x2(List<List<int>> m) =>
    (m[0][0] * m[1][1] - m[0][1] * m[1][0]) % 26;

/// Calcule l'inverse modulaire d'un entier modulo 26
int modInverse(int a, int m) {
  for (int x = 1; x < m; x++) {
    if ((a * x) % m == 1) return x;
  }
  throw Exception("Pas d'inverse modulaire pour $a modulo $m");
}

/// Calcule l'inverse d'une matrice 2x2 modulo 26
List<List<int>> inverseMatrix2x2(List<List<int>> m) {
  int det = determinant2x2(m);
  int invDet = modInverse(det, 26);

  return [
    [(m[1][1] * invDet) % 26, (-m[0][1] * invDet) % 26],
    [(-m[1][0] * invDet) % 26, (m[0][0] * invDet) % 26],
  ];
}
