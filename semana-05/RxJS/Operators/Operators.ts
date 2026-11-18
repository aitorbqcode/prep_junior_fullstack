import { of, from, interval, combineLatest, delay } from 'rxjs';
import { map, filter, take, tap, switchMap, catchError, debounceTime } from 'rxjs/operators';

// map: transforma cada valor (igual que Stream.map en Java)
of(1, 2, 3, 4, 5).pipe(
    map(n => n * 2)
).subscribe(console.log);  // 2, 4, 6, 8, 10

// filter: filtra valores (igual que Stream.filter en Java)
of(1, 2, 3, 4, 5).pipe(
    filter(n => n % 2 === 0)
).subscribe(console.log);  // 2, 4

// take: toma solo los primeros N valores y completa
interval(500).pipe(
    take(3)
).subscribe(console.log);  // 0, 1, 2 → completa

// tap: ejecuta un efecto secundario sin modificar el valor (útil para debug)
of(1, 2, 3).pipe(
    tap(n => console.log('Antes del map:', n)),
    map(n => n * 10),
    tap(n => console.log('Después del map:', n))
).subscribe();

// catchError: maneja errores (crucial para peticiones HTTP)
import { throwError } from 'rxjs';
throwError(() => new Error('Algo falló')).pipe(
    catchError(err => {
        console.error('Error capturado:', err.message);
        return of(null);  // devuelve un valor por defecto en vez de fallar
    })
).subscribe(console.log);  // null

// switchMap: el más importante para Angular
// Cuando llega un nuevo valor, CANCELA la suscripción anterior y crea una nueva
// Caso de uso típico: búsqueda en tiempo real
//   El usuario escribe "a" → petición HTTP
//   El usuario escribe "an" → CANCELA la petición de "a", lanza la de "an"
//   El usuario escribe "ang" → CANCELA la de "an", lanza la de "ang"
const searchTerm$ = of('angular');
searchTerm$.pipe(
    switchMap(term => from(fetch(`/api/search?q=${term}`)))
).subscribe();

// Simulamos que el usuario escribe 'a', luego 'an', luego 'ang' de golpe
const searchTerms$ = of('a', 'an', 'ang');

// Función que simula una petición al servidor que tarda 1 segundo (1000ms)
function buscarEnServidor(termino: string) {
  console.log(`📡 Enviando petición al servidor para: "${termino}"...`);
  return of(`Resultados de "${termino}"`).pipe(
    delay(1000) // Simula el tiempo de espera de la red
  );
}

searchTerms$.pipe(
  switchMap(term => buscarEnServidor(term))
).subscribe(respuesta => {
  console.log(`✅ Pintando en pantalla:`, respuesta);
});


// debounceTime: espera N ms desde el último valor antes de emitir
// Caso de uso: no lanzar una petición por CADA tecla que pulsa el usuario
interval(100).pipe(
    debounceTime(300),  // solo emite si pasan 300ms sin nuevos valores
    take(3)
).subscribe(console.log);