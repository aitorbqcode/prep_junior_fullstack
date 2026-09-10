class TaskService {
    // Modificadores de acceso: public (por defecto), private, protected, readonly
    private tasks: Task[] = [];

    // Shorthand de constructor: declara y asigna a la vez
    constructor(
        private readonly apiUrl: string,
        private readonly maxTasks: number = 100
    ) {}

    // Método público
    addTask(title: string, user: User): Task {
        if (this.tasks.length >= this.maxTasks) {
            throw new Error("Límite de tareas alcanzado");
        }
        const task = createTask(title, user);
        this.tasks.push(task);
        return task;
    }

    // Getter (como una propiedad computada)
    get pendingTasks(): Task[] {
        return this.tasks.filter(t => !t.completed);
    }

    // Método estático
    static validateTitle(title: string): boolean {
        return title.trim().length > 0 && title.length <= 100;
    }
}

// Herencia (igual que Java)
class PriorityTaskService extends TaskService {
    addPriorityTask(title: string, user: User, priority: string): PriorityTask {
        const task = super.addTask(title, user);
        return { ...task, priority } as PriorityTask;
    }
}