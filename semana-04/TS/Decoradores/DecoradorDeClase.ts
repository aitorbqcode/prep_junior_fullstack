// 1. EL DECORADOR
function Entidad(constructor: Function) {
  // Añadimos una propiedad al prototipo para que todas las instancias la tengan
  constructor.prototype.fechaCreacion = new Date();
  
  console.log(`[LOG] Clase ${constructor.name} registrada como Entidad.`);
}

// 2. USO
@Entidad
class Usuario {
  constructor(public nombre: string) {}
}

// 3. PRUEBA
const usuario = new Usuario("Carlos");
console.log(usuario.nombre); // "Carlos"
console.log((usuario as any).fechaCreacion); // Muestra la fecha actual