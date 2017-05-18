
/**
  * Objeto singleton para probar la funcionalidad del triangulo
  * de Pascal
  */
object Main{

  /**
    * Metodo main: en realidad no es necesario porque el desarrollo
    * deberia guiarse por los tests de prueba
    *
    * @param args
    */
  def main(args: Array[String]) {
    println("................... Triangulo de Pascal ...................")

    // Se muestran 10 filas del trinagulo de Pascal
    for (row <- 0 to 10) {
      // Se muestran 10 10 columnas
      for (col <- 0 to row)
        print(calcularValorTrianguloPascal(col, row) + " ")

      // Salto de linea final para mejorar la presentacion
      println()
    }

    // Se muestra el valor que debe ocupar la columna 5 en la fila 10
    println(calcularValorTrianguloPascal(0,2) == 1)
    println(calcularValorTrianguloPascal(1,2) == 2)
    println(calcularValorTrianguloPascal(1,3) == 3)
    println(calcularValorTrianguloPascal(5,10) == 252)
    println(calcularValorTrianguloPascal(10,15) == 3003)
    println(calcularValorTrianguloPascal(0,0) == 1)



    println(chequearBalance("(if (zero? x) max (/ 1 x))".toList))
    println(chequearBalance("Te lo dije (eso no esta (todavia) hecho).\n(Pero el no estaba escuchando)".toList))
    println(!chequearBalance(":-)".toList))
    println(!chequearBalance("())(".toList))
    println(chequearBalance("(if (a > b) (b/a) else (a/(b*b)))".toList))
    println(chequearBalance("(ccc(ccc)cc((ccc(c))))".toList))
    println(!chequearBalance("(if (a > b) b/a) else (a/(b*b)))".toList))
    println(!chequearBalance("(ccc(ccccc((ccc(c))))".toList))
    println(!chequearBalance("())()())".toList))

    println(contarCambiosPosibles(4,List(1,2)) == 3)
    println(contarCambiosPosibles(300,List(5,10,20,50,100,200,500)) == 1022)
    println(contarCambiosPosibles(301,List(5,10,20,50,100,200,500)) == 0)
    println(contarCambiosPosibles(300,List(500,5,50,100,20,200,10)) == 1022)


    println(contarCambiosPosibles(4, List(1,2)))

    val array1:Array[Int]=Array(1, 5, 20, 35, 57, 99, 123, 188, 211, 215, 444, 445, 449, 520)

    val res1 = busquedaBinaria(array1, (x:Int, y:Int) => (x < y), (x:Int, y:Int) => (x == y), 211)

    println(s"Ocupa la posición $res1")


  }

  /**
    * Ejercicio 1: funcion para generar el triangulo de Pascal
    *
    * El triángulo de Pascal es un triángulo de números enteros, infinito y simétrico Se empieza con un 1 en la
    * primera fila, y en las filas siguientes se van colocando números de forma que cada uno de ellos sea la suma
    * de los dos números que tiene encima.
    *
    * @param columna La posición en la columna del valor buscado
    * @param fila La posición en la fila del valor buscado
    * @return entero que representa el valor buscado
    */
  def calcularValorTrianguloPascal(columna: Int, fila: Int): Int = {

    /*
    if(fila == 0) return 1


    if(columna == fila) return 1

    if(columna == 0) return 1
    */

    // Cuando el valor a buscar se encuentre en la cima de la pirámide o en un extremo, se devuelve 1
    if (fila == 0 || columna == fila || columna == 0)
      return 1

    // En otro caso buscamos los dos valores de encima de la posición a buscar y los sumamos para obtener
    // el elemento buscado.
    else
      return calcularValorTrianguloPascal(columna, fila-1) + calcularValorTrianguloPascal(columna -1, fila-1)

  }



  def comprobarBalance(cadena: List[Char]): Boolean = {

    // Primero comprobamos si existe el paréntesis dentro de la cadena recibida como parámetro
    if(cadena.exists(_ == ')') ){

      // Obtenemos la posición del paréntesis dentro de la cadena
      val i = cadena.indexOf(')')

      // Pasamos el flujo de la cadena sin el paréntesis encontrado. Esto es así porque
      // la lista es inmutable, entonces no podemos eliminar ningún elemento de la cadena
      // por eso pasamos el flujo sin el elemento encontrado ")" que es el que estábamos buscando
      return chequearBalance(cadena.take(i):::cadena.takeRight(cadena.length - i - 1))

    }
    // En caso de que no exista ")", sabemos que la cadena no está balanceada.
    else false

  }

  /**
    * Ejercicio 2: funcion para chequear el balance de parentesis
    *
    * @param cadena cadena a analizar
    * @return valor booleano con el resultado de la operacion
    */
  def chequearBalance(cadena: List[Char]): Boolean = {

    // Si la cadena está vacía entonces está balanceada
    if(cadena.isEmpty) return true
    // Si el primer elemento que encontramos es el ")" sabemos que no está balanceada
    if(cadena.head == ')') return false;
    // Si es un caracter, pasamos el flujo sin el carácter encontrado
    if(cadena.head != '(') return chequearBalance(cadena.tail)
    // Primer parámetro es "(" ahora tenemos que encontrar a su correspondiente ")" llamando a la función
    // comprobarBalance que lo encontrará, quitando del flujo el primer elemento.
    else return comprobarBalance(cadena.tail)

  }

  /**
    * Ejercicio 3: funcion para determinar las posibles formas de devolver el
    * cambio de una determinada cantidad con un conjunto de monedas
    *
    * @param cantidad
    * @param monedas
    * @return contador de numero de vueltas posibles
    */
  def contarCambiosPosibles(cantidad: Int, monedas: List[Int]): Int = {
    // Caso base, cantidad de monedas sea 0
    if(cantidad.equals(0)) return 1
    // La moneda utilizada para el cambio nos da una cantidad negativa, por lo tanto no válida.
    if(cantidad < 0) return 0
    // No nos quedan monedas para el cambio, por lo tanto no válida.
    if(monedas.isEmpty) return 0
    // Comprobamos para una cantidad quitando la primera moneda de la lista, y sumamos el resultado
    // de volver a llamar a la función restando a la cantidad a devolver la moneda de la cabecera de la lista.
    else
      return contarCambiosPosibles(cantidad, monedas.tail) + contarCambiosPosibles(cantidad - monedas.head, monedas)
  }

  def ordenado[A](array:Array[A], comparar:(A,A) => Boolean) : Boolean = {
    @annotation.tailrec
    def iterar(indice:Int) : Boolean = {
      // Caso base 1: no pasarse de longitud
      if(indice == array.length-2) comparar(array(indice), array(indice+1))
      // Caso base 2: en cuanto encuentro un par de elementos no ordenados,
      // se devuelve false
      else if(!comparar(array(indice), array(indice+1))) false
      else iterar(indice+1)
    }

    // Desencadenamos la ejecución
    iterar(0)
  }

  def busquedaBinaria[A](array:Array[A], comparar:(A,A) => Boolean, iguales:(A,A) => Boolean, objetivo:A) : Int = {

    @annotation.tailrec
    def busqueda(izqd:Int, drch:Int) : Int = {

      val centro = (drch + izqd) / 2

      // Caso base, hemos encontrado el elemento, devolvemos la posición donde se encuentra
      if(iguales(array(centro), objetivo)) centro

      // El índice izquierdo es mayor que el derecho, no hemos encontrado el valor objetivo
      else if(izqd > drch) -1
      // Vemos si está en la parte de la izquierda del centro, en ese caso acotamos la búsqueda entre
      // izquierda y centro -1
      else if(comparar(objetivo, array(centro))) busqueda(izqd, centro-1)

      // Está en la parte derecha del centro, acotamos la búsqueda entre centro +1 y drch
      else
        return busqueda(centro + 1, drch)

    }

    if (ordenado(array, comparar)) {
      return busqueda(0, array.length - 1)

    }
    else -1
  }
}
