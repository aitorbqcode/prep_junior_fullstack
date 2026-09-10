// 1. EL DECORADOR DE PARÁMETRO
function Requerido(target: any, propertyKey: string, parameterIndex: number) {
  console.log(`[METADATO] El parámetro en la posición ${parameterIndex} del método '${propertyKey}' es obligatorio.`);
}

// 2. USO
class Buscador {
  buscar(
    @Requerido query: string, // Posición 0
    limite: number            // Posición 1
  ) {
    return `Buscando: ${query} (máx: ${limite})`;
  }
}

// 3. PRUEBA
const b = new Buscador();
console.log(b.buscar("TypeScript", 10));