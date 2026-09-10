// Un decorador es una función que recibe la clase y la modifica
function Injectable(target: any) {
    target.prototype.injectable = true;
    console.log(`${target.name} marcado como injectable`);
}

function Component(options: { selector: string; template: string }) {
    return function(target: any) {
        target.prototype.selector = options.selector;
        target.prototype.template = options.template;
    }
}

// Uso (ya verás esto en Angular):
@Injectable
class UserService {
    getUsers() { ... }
}

@Component({ selector: 'app-task-list', template: '<ul>...</ul>' })
class TaskListComponent {
    @Input() tasks: Task[] = [];  // @Input también es un decorador
}


// 1. EL DECORADOR (La función con la lógica)
function EnMayusculas(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
    /*
    target: La clase a la que pertenece el método (Saludo).
    propertyKey: El nombre del método ("obtenerMensaje").
    descriptor: Es un objeto que contiene los detalles de la función, incluyendo la función real en descriptor.value.
    */
    const metodoOriginal = descriptor.value; // Guardamos la función original

    descriptor.value = function () { //Reemplazamos el método original por una nueva función wrapper.
        const resultado = metodoOriginal.apply(this); // Ejecutamos la función original para obtener su respuesta ("hola mundo...").
        return resultado.toUpperCase();               // Cogemos ese texto devuelto y lo transformamos a mayúsculas antes de entregárselo al usuario.
    };
}

// 2. USO DEL DECORADOR
/*
La clase solo tiene un método simple que devuelve una cadena en minúsculas.
Al poner @EnMayusculas justo encima, le dices a TypeScript:
"Antes de entregar la respuesta de obtenerMensaje, pásasela a la función EnMayusculas
*/
class Saludo {
  @EnMayusculas
  obtenerMensaje() {
    return "hola mundo desde typescript";
  }
}

// 3. PRUEBA
const miSaludo = new Saludo();
console.log(miSaludo.obtenerMensaje()); 
// Imprime: "HOLA MUNDO DESDE TYPESCRIPT"