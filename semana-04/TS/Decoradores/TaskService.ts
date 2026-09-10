// 1. DEFINICIÓN DEL DECORADOR
function Logged(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
  // Guardamos la función original (addTask)
  const metodoOriginal = descriptor.value;

  // Reemplazamos la función original por una nueva versión con el log
  descriptor.value = function (...args: any[]) {
    // A) Imprimimos el log con el nombre del método y sus argumentos
    console.log(`[LOG] ${propertyKey} llamado con:`, args);

    // B) Ejecutamos el método original y devolvemos su resultado
    return metodoOriginal.apply(this, args);
  };
}

// 2. USO EN LA CLASE
class TaskService {
  @Logged
  addTask(title: string, userId: number) {
    return { id: Math.random(), title, userId };
  }
}

// 3. PRUEBA DE EJECUCIÓN
const service = new TaskService();
service.addTask("Mi tarea", 1);