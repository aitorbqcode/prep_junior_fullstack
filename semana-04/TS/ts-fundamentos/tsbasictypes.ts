// Tipos primitivos
let nombre: string = "Ana";
let edad: number = 25;
let activo: boolean = true;

// Arrays
let numeros: number[] = [1, 2, 3];
let nombres: Array<string> = ["Ana", "Luis"];

// Union types: puede ser uno u otro
let id: string | number = "abc123";
id = 42;  // también válido

// Literal types: solo puede ser ese valor exacto
let estado: "pendiente" | "completada" | "cancelada" = "pendiente";

// any (evítalo siempre que puedas — rompe el sistema de tipos)
// unknown (mejor que any: obliga a comprobar el tipo antes de usarlo)
let valorDesconocido: unknown = "Hola mundo";
if (typeof valorDesconocido === "string") {
    console.log(valorDesconocido.toUpperCase());  // aquí TypeScript ya sabe que es string
}

// null y undefined
let namesnpx: string | null = null;  // con strict: true, null no es string por defecto

let num: number = 12;

