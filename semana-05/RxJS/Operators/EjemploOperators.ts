import { Observable, of, throwError, timer } from 'rxjs';
import { switchMap, map, filter, catchError, retry, delay } from 'rxjs/operators';

interface Task { id: number; title: string; completed: boolean; }

// Simula una llamada HTTP que a veces falla
function fetchTasks(userId: number): Observable<Task[]> {
    if (Math.random() < 0.3) {  // 30% de probabilidad de error
        return throwError(() => new Error('Error de red'));
    }
    return of([
        { id: 1, title: 'Tarea 1', completed: false },
        { id: 2, title: 'Tarea 2', completed: true }
    ]).pipe(delay(500));  // simula latencia
}

// Flujo completo:
of(1)  // userId
    .pipe(
        switchMap(userId => fetchTasks(userId)),
        retry(2),                              // reintenta hasta 2 veces si falla
        map(tasks => tasks.filter(t => !t.completed)),  // solo pendientes
        catchError(err => {
            console.error('Error final:', err);
            return of([]);                     // devuelve lista vacía si falla todo
        })
    )
    .subscribe({
        next: tasks => console.log('Tareas pendientes:', tasks),
        complete: () => console.log('Carga completada')
    });