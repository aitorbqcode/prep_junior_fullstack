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
interface PriorityTask extends Task {
    priority: "alta" | "media" | "baja";
}

type TaskWithStatus = Task & { status: TaskStatus };  // intersection type

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
    tasks: Task[],
    completed?: boolean,
    userId: number = 0
): Task[] {
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