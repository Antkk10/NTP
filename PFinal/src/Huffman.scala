/**
  * Created by antoniomfc90 on 16/6/17.
  */
object Huffman {

  type TablaCodigo=List[(Char, List[Int])]

  /**
    * Función para codificar una lista de caracteres, usando un árbol de codificación. Este árbol está contenido
    * en el parámetro nodo que contiene la raíz del árbol. Una vez codificada la lista de caracteres, devuelve
    * la lista de enteros (contiene 0 y 1).
    * @param lista de tipo List[Char] contiene los caracteres a codificar.
    * @param nodo de tipo NodoArbolHuffman contiene el nodo raíz.
    * @return devuelve un List[Int] que contiene la codificación del texto (el parámetro de tipo List[Char).
    */
  def codificar(lista:List[Char], nodo:NodoArbolHuffman) = {


    /**
      * Función recursiva para codificar la lista de caracteres. La lista de enteros que se devuelve con tiene los bits (0 y 1),
      * donde el 0 representa que el carácter se encuentra en el hijo izquierdo de un nodo, y el 1 representa que el carácter
      * se encuentra en el hijo derecho de un nodo.
      * @param raiz contiene el nodo raíz del árbol.
      * @param n contiene el nodo por donde va la codificación
      * @param codificacion contiene la lista con los enteros con valor 0 ó 1.
      * @param caracteres contiene la lista de carácteres que todavía no han sido codificados.
      * @return devuelve un List[Int] con la codificación del texto.
      */
    def recursivo(raiz:NodoArbolHuffman, n:NodoArbolHuffman, codificacion:List[Int], caracteres:List[Char]):List[Int] = {

      // Primero compruebo si la lista de carácteres a codificar está vacia. En caso de true, devuelvo la codificación
      // ya que no quedan carácteres por codificar.
      if(caracteres.isEmpty) codificacion
      else {
        // Compruebo que tipo de nodo es
        n match {
            // El nodo es de tipo Hoja, por lo tanto se ha llegado al final de la codificación del carácteres que se
            // estaba codificando, por lo tanto llamada recursiva pasandole como primer argumento la raíz del nodo
            // (esto nunca cambia), como segundo argumento la raíz del árbol, que es donde comienza la codificación
            // del siguiente carácter a codificar. El tercer argumento es la codificación que llevo hasta ese momento.
            // Por último pasamos la lista de carácteres que quedan por codificar sin el primer elemento que es el que
            // acaba de terminar de ser codificado.
          case NodoHojaArbolHuffman(_, _) => recursivo(raiz, raiz, codificacion, caracteres.tail)
          // El nodo es de tipo Interno. Necesito obtener el nodo hijo izquierdo y derecho para ver cual
            // de los dos contiene el carácter a codificar.
          case NodoInternoArbolHuffman(izqd, drch, _, _) =>
            // Si el hijo izquierdo contiene el carácter a codificar, debo de añadir a la lista de codificación un 0.
            if (GenerarArbol.obtenerListaCaracteres(izqd).contains(caracteres.head))
              // Llamada recursiva, pasamos como primer argumento la raíz del árbol, segundo argumento el nodo hijo izquierdo
              // que es el nodo que contiene el carácter y debemos de seguir iterando por sus hijos. El tercer argumento es
              // la lista de codificación que llevo hasta ese momento más la concatenación bit 0 añadido a la lista. Por último
              // la lista de carácteres que quedan por codificar.
              recursivo(raiz, izqd, codificacion++List(0), caracteres)
            else
              // En el caso de que el carácter se encuentre entre el nodo del hijo derecho, se pasan los mismo argumentos
              // explicados en el if anterior, solo que en vez de concatenar con el bit 0, se concatena con el bit 1.
              recursivo(raiz, drch, codificacion++List(1), caracteres)


        }
      }


    }
    recursivo(nodo, nodo, List(), lista)


  }

  /**
    * Función para decodificar un mensaje utilizando además el árbol de codificación. Esta función devuelve la cadena
    * de texto decodificada.
    * @param nodo de tipo NodoArbolHuffman, raíz del árbol para decodificar el mensaje.
    * @param lista de tipo List[Int], contiene la codificación de la cadena de texto.
    * @return devuelve el texto decodificado.
    */
  def decodificar(nodo:NodoArbolHuffman, lista:List[Int]):List[Char] = {

    /**
      * Función recursiva para decodificar el mensaje. Devuelve el texto en forma de List[Char]
      * @param raiz de tipo NodoArbolHuffman, contiene la raíz del árbol.
      * @param n de tipo NodoArbolHuffman, contiene el nodo por el que se está haciendo la recursividad en un momento determinado.
      * @param decodificar de tipo List[Int], contiene 0 y 1 que representan el mensaje codificado y que se pretende decodificar.
      * @param mensaje de tipo List[Char] contiene el mensaje a devolver.
      * @return devuelve el mensaje completo decodificado.
      */
    def recursivo(raiz:NodoArbolHuffman, n:NodoArbolHuffman, decodificar:List[Int], mensaje:List[Char]):List[Char] ={

        // Compruebo de que tipo de nodo es (hoja o interno)
        n match {
            // En caso de que sea nodo hoja, obtenermos el carácter que representa el nodo hoja.
          case NodoHojaArbolHuffman(caracter, _) =>
            // Si no queda ningún número más por decodificar, quiere decir que ya no necesito hacer más llamadas recursivas
            // por lo tanto devuelvo el mensaje que llevaba decodificado junto al nuevo carácter obtenido.
            if (decodificar.isEmpty) mensaje ::: List(caracter)
              // Todavía quedan elementos por decodificar.
            else
              // Llamada recursiva, el primer elemento es la raíz del árbol (esto no cambia), el segundo elemento tambiéjn
              // es la raíz del árbol, que al obtener el carácter, debemos de continuar con la decodificación del siguiente carácter
              // por la raíz del árbol. El tercer argumento es la lista de 0 y 1 que queda por decodificar. El último parámetro
              // es la concatenación del mensaje que llevamos decodificado junto al nuevo carácter obtenido.
              recursivo(raiz, raiz, decodificar, mensaje ::: List(caracter))
            // De tipo nodo interno, necesito el hijo izquierdo y derecho para continuar con la decodificación
          case NodoInternoArbolHuffman(izqd, drch, _, _) =>
            // Si el bit es un 0, quiere decir que el carácter se encuentra por la parte del hijo izquierdo.
            if (decodificar.head == 0)
              // Llamada recursiva pasando como primer argumento la raiz, como segundo argumento el hijo izquierdo por donde se está
              // el carácter a decodificar. El tercer argumento es la lista de bits a decodificar sin el primer elemento, y el último
              // argumento es el mensaje decodificado hasta ese momento.
              recursivo(raiz, izqd, decodificar.tail, mensaje)

            else // El bit es 1, el carácter se encuentra en el nodo derecho.
              // Se pasa los mismos argumentos que el if anterior, excepto que en vez de pasarse el nodo izquierdo se pasa el derecho.
              recursivo(raiz, drch, decodificar.tail, mensaje)
        }

    }

    recursivo(nodo, nodo, lista, List())

  }


  /**
    * Función para codificar un caracter usando la tabla de codificación. Devuelve la lista de enteros (0 y 1) que representa
    * el carácter codificado.
    * @param tabla de tipo TablaCodigo que contiene la tabla de codificación de cada carácter.
    * @param caracter de tipo Char, representa el carácter a codificar.
    * @return devuelve List[Int] que contiene el mensaje codificado.
    */
  def codificarConTabla(tabla:TablaCodigo)(caracter:Char):List[Int] ={
    // Obtiene la lista de enteros que representan al caracter dentro de la tabla.
    // Filtramos la lista obteniendo una nueva lista. Realmente esta nueva lista solo tiene un elemento, por lo tanto obtenemos
    // su segundo parámetro y lo devolvemos al que invoca la función. Este segundo parámetro contiene la lista de bits
    // que representan el caracter.
    tabla.filter(p => p._1 == caracter).head._2
  }

  /**
    * Función que obtiene una tabla a partir del árbol de codificación. Esta tabla es de tipo TablaCodigo que internamente
    * una lista de tuplas, donde el primer elemento de la tupla es un carácter y el segundo elemento es la lista de bits
    * que representan al carácter codificado.
    * @param arbolCodificacion de tipo NodoArbolHuffman que contiene la raíz del árbol.
    * @return devuelve la tabla generada a partir del árbol.
    */
  def convertirArbolTabla(arbolCodificacion:NodoArbolHuffman):TablaCodigo = {

    /** Función recursiva para crear la tabla de codificación. Bajada en profundida a través de los nodos del árbol.
      * @param nodo de tipo NodoArbolHuffman.
      * @return devuelve la tabla creada.
      */
    def recursivo(nodo:NodoArbolHuffman, lista:List[Int]):TablaCodigo = {
      // Compruebo de que tipo es el nodo.
      nodo match {
          // Si es de tipo hoja, hemos llegado al final por lo tanto devuelve una tupla en una lista que representa el
          // carácter del nodo hoja y la lista de codificación para ese árbol.
        case NodoHojaArbolHuffman(caracter, _) => List((caracter, lista))

          // Si el nodo es de tipo interno, se hacen dos llamadas recursivas y se concatena los dos resultados obtenido de dichas llamadas.
          // La primera llamada recursiva se pasa el nodo hijo izquierdo, junto a la concatenación de la lista de codificación
          // con el valor 0 que este bit se le asigna al nodo izquierdo del árbol.
          // La segunda llamada recursiva es con el nodo derecho del árbol y la concatenación de la lista de bits (List[Int]) con
          // el bit 1 que se utiliza para los nodos hijo derecho del árbol.
        case NodoInternoArbolHuffman(izqd, drch, _, _) => recursivo(izqd, lista ::: List(0)) ::: recursivo(drch, lista ::: List(1))
      }
    }

    recursivo(arbolCodificacion, List())
  }

  /**
    * Función que sirve para realizar la codificación rápida de un mensaje. Primero obtenemos la tabla de codificación
    * a partir del árbol existente. Después obtenemos y devolvemos la lista de carácteres codificados con la tabla.
    * @param arbolCodificacion de tipo NodoArbolHuffman contiene la raíz del árbol.
    * @param lista de tipo List[Char] contiene el texto a codificar.
    * @return devuelve el mensaje codificado (List[Int])
    */
  def codificarRapido(arbolCodificacion:NodoArbolHuffman, lista:List[Char]):List[Int] = {

    // Obtengo la tabla.
    val tabla = convertirArbolTabla(arbolCodificacion)

    // Asignación parcial de argumentos. La tabla siempre es la misma, lo que cambia es el carácter para obtener su codificación
    val codificar = codificarConTabla(tabla)(_)

    // Funcion auxiliar que la usaremos de forma recursiva para obtener la codificación del texto.
    def generarLista(lista:List[List[Int]]):List[Int] = {

      // Comprobamos si la lista tiene un elemento. Devolvemos el único elemento de la lista.
      if (lista.size == 1) lista.head
        // En otro caso concatenamos los valores de la primera lista con el resultado de la llamada recursiva a la misma función
        // pasandole la lista sin el primer elemento.
      else lista.head:::generarLista(lista.tail)
    }

    // Con el uso de map sobre la lista, estamos codificando cada carácter. El problema de map es que nos devuelve una List[List[Int]],
    // y no es el resultado que tengo que obtener, por lo tanto llamada a la función auxiliar generarListta(...) para transformar la
    // List[List[Int]] en List[Int]
    generarLista(lista.map(p => codificar(p)))


  }

}
