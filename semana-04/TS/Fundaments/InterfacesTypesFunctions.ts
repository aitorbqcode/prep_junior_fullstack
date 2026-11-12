// Interface: define la forma de un objeto
interface Tasks {
    id: number;
    title: string;
    completed: boolean;
    createdAt: Date;
    userId: number;
}

// Type alias: similar a interface pero más flexible
type TaskStatus = "pendiente" | "completada" | "cancelada";

// Diferencia clave: interface se puede extender, type se puede combinar
// Aqui añadimos un campo priority a la interface Tasks mediante herencia
interface PriorityTask extends Tasks {
    priority: "alta" | "media" | "baja";
}

// En cambio con type podemos crear un tipo que combine Tasks con un nuevo campo status
type TaskWithStatus = Tasks & { status: TaskStatus };  // intersection type

// Funciones tipadas
function createTasks(title: string, userId: number): Tasks {
    return {
        id: Math.random(),
        title,
        completed: false,
        createdAt: new Date(),
        userId
    };
}

// Función con parámetro opcional (?) y valor por defecto
function filterTasks(
    tasks: Tasks[],
    completed?: boolean,
    userId: number = 0
): Tasks[] {
    return tasks.filter(t => {
        if (completed !== undefined && t.completed !== completed) return false;
        if (userId !== 0 && t.userId !== userId) return false;
        return true;
    });
}

// Generics en TypeScript (ya los conoces de Java)
function getFirst<T>(array: T[]): T | undefined {
    return array[0];
}
// TypeScript infiere el tipo: getFirst([1, 2, 3]) devuelve number | undefined