import { Observable, of, from, interval } from 'rxjs';

// Observable que emite un único valor y completa
const uno$ = of(42);
uno$.subscribe({
    next: (value) => console.log('Valor:', value),   // se ejecuta por cada valor
    error: (err) => console.error('Error:', err),    // si hay un error
    complete: () => console.log('Completado')        // cuando termina
});

// Observable que emite varios valores
const varios$ = of(1, 2, 3, 4, 5);
varios$.subscribe(v => console.log(v));  // forma corta: solo el next
// error y complete no se definen, así que no hacen nada especial

// Observable desde un array
const fromArray$ = from([10, 20, 30]);

// Observable que emite cada segundo (infinito)
const timer$ = interval(1000);
const subscription = timer$.subscribe(n => console.log(`Segundo ${n}`));
// Para cancelar:
setTimeout(() => subscription.unsubscribe(), 5000);  // para después de 5s