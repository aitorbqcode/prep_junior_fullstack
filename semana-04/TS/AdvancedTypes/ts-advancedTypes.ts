// Se trata de la misma función pero con distintos tipos de parámetros y sobrecarga de funciones.
// De esta forma, TypeScript puede inferir el tipo de retorno según el tipo de argumento que se le pase.
// En este caso recibe un string y devuelve un string
function format(value: string): string;
// En este caso recibe un number y devuelve un string
function format(value: number, decimals: number): string;
// En este caso recibe un string o un number y devuelve un string. decimal? es opcional
function format(value: string | number, decimals?: number): string {
    // Si el valor de value es un string, lo recorta (trim) y lo devuelve.
    if (typeof value === "string") return value.trim();
    // Si el valor es un number, lo formatea a un número fijo de decimales (decimals) y lo devuelve. Si decimals es undefined, se usa 2 por defecto.
    return value.toFixed(decimals ?? 2);
}

// Optional y default params
function greet(name: string, greeting: string = "Hola"): string {
    // Aquí el valor de final greeting será "Hola" si no se pasa un segundo argumento al llamar a la función.
    return `${greeting}, ${name}!`;
}

// Rest params
// Esta función recibe un número indefinido de argumentos (gracias a los tres puntos ...), y devuelve la suma de todos ellos.
function sum(...numbers: number[]): number {
    return numbers.reduce((acc, n) => acc + n, 0);
}

// Higher-order functions (funciones que reciben o devuelven funciones)
// EL tipo Predicate<T> es una función que recibe un item de tipo T y devuelve un booleano. Se usa para filtrar elementos.
// Un ejemplo de uso sería:"¿Esta tarea está completada?"(Le envias la tarea) -> Devuelve true o false.
type Predicate<T> = (item: T) => boolean;
// El tipo Transformer<T, U> es una función que recibe un item de tipo T y devuelve un item de tipo U. Se usa para transformar elementos.
// Un ejemplo de uso sería: "Convierte una tarea a un string con su título"(Le envias la tarea) -> Devuelve el título de la tarea.
type Transformers<T, U> = (item: T) => U;

// Esta función pipeline recibe un array de items de tipo T, una función transform que transforma cada item de T a U,
// y una función filter que filtra los items de tipo U. Devuelve un array de items de tipo U.
function pipeline<T, U>(items: T[], transform: Transformers<T, U>, filter: Predicate<U>): U[] {
    return items.map(transform).filter(filter);
}


//Ejemplo de uso de pipeline con tareas (Task) y sus títulos (string)

// 1. Definimos nuestra interfaz de Tarea (T)
interface Taskss {
    title: string;
    completed: boolean;
    priority: "baja" | "media" | "alta";
}

const tasks: Taskss[] = [
    { title: "Estudiar TypeScript", completed: false, priority: "alta" },
    { title: "Comprar pan", completed: true, priority: "baja" },
    { title: "Hacer ejercicio", completed: false, priority: "alta" },
    { title: "Limpiar casa", completed: true, priority: "media" }
];

// ==========================================
// APLICANDO EL PIPELINE
// ==========================================

// 2. Creamos el TRANSFORMER (De Task [T] a string [U])
// Su trabajo es coger una tarea y devolver un texto descriptivo.
const formatTask: Transformers<Taskss, string> = (taskss) => {
    return `[${taskss.priority.toUpperCase()}] ${taskss.title}`;
};

// 3. Creamos el PREDICATE (De string [U] a boolean)
// Ojo: Recibe el resultado ya transformado (el string), no la tarea original.
// Su trabajo es decidir si ese texto pasa el filtro o no.
const onlyHighPriority: Predicate<string> = (formattedText) => {
    return formattedText.includes("ALTA");
};

// 4. Ejecutamos la función pipeline
const resultado = pipeline(tasks, formatTask, onlyHighPriority);

console.log(resultado);