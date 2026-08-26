"use strict";
// Tipos primitivos
let nombre = "Ana";
let edad = 25;
let activo = true;
// Arrays
let numeros = [1, 2, 3];
let nombres = ["Ana", "Luis"];
// Union types: puede ser uno u otro
let id = "abc123";
id = 42; // también válido
// Literal types: solo puede ser ese valor exacto
let estado = "pendiente";
// any (evítalo siempre que puedas — rompe el sistema de tipos)
// unknown (mejor que any: obliga a comprobar el tipo antes de usarlo)
let valorDesconocido = fetchData();
if (typeof valorDesconocido === "string") {
    console.log(valorDesconocido.toUpperCase()); // aquí TypeScript ya sabe que es string
}
// null y undefined
let name = null; // con strict: true, null no es string por defecto
let num = "abc";
