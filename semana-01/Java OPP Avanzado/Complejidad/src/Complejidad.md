1. Dado este fragmento, calcula su complejidad temporal justificando cada línea:

for (int i = 0; i < n; i++) {
    for (int j = i; j < n; j++) {
        System.out.println(i + j);
    }
}

En este caso tenemos el bucle externo de i donde el coste del bucle es n, ya que se recorre n veces. Luego el segundo bucle si nos fijamos
tenemos que cuando el valor de i sube, en los siguientes loops del loop interno se recorren una vez menos, ya que j coge el valor de i
por lo que el numero de veces que se recorre este loop seria lo siguiente:

Total = n + (n - 1) + (n - 2) + ... + 2 + 1

La suma total de iteraciones sigue la progresión de la suma de Gauss:Complejidad Final: (n^2).


2. Ordena estas funciones de menor a mayor complejidad asintótica: (n³, log n, n log n, 2ⁿ, n, 1, n²):

    1, log n, n, n * log n ,n² ,n³, 2ⁿ


3. ¿Cuál es la diferencia entre O(n) y Θ(n)? ¿Por qué en la práctica casi siempre hablamos de O y no de Θ?

    En el caso de O(n), este nos indica el coste del algoritmo en el peor de los casos, pero cuando hablamos de Θ(n) este nos dice
    el coste exacto del algoritmo. 
    En la practica hablamos de O, ya que es mas facil predecir el maximo coste de un algoritmo, que el coste exacto, en el primer ejemplo
    si que se puede llegar a predecir ya que es una función simple, pero en casos mas complejos, se vuelve mas facil dar el coste maximo
    que dar el coste exacto ya que puede depender de muchos factores