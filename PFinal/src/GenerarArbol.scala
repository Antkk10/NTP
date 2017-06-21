import java.util.Collections


/**
  * Created by antoniomfc90 on 1/6/17.
  */
object GenerarArbol {


  import  scala.io.Source

  /**
    * Comprueba si la lista recibida como parámetro contiene un elemento.
    * @param lista de tipo List[NodoArbolHuffman] contiene una lista con nodos.
    * @return true cuando la lista contiene un elemento, false en otro caso.
    */
  def singleton(lista:List[NodoArbolHuffman]) = lista.size == 1

  /**
    * Método que recibe un texto (en forma de lista de carácteres) y que calcula la frencuencia
    * que aparece cada carácter.
    * @param lista de tipo List[Char] contiene una lista de carácteres.
    * @return Devuelve una list de tupas (char, int), donde cada char tiene asociado el número de veces que
    *         aparece en el texto.
    */
  def getCalcularFrecuencias(lista:List[Char]):List[(Char, Int)] = {

    /**
      * Función recursiva para calcular las veces que aparece un carácter en el texto.
      * @param l contiene la lista con los carácteres a los que se debe de calcular la frecuencia de aparición.
      * @param nueva Contiene la lista de los carácteres que han sido calculados con su frecuencia de aparición.
      * @return Una vez finalizada la función recursiva, devuelve la lista creada que en este caso es nueva.
      */
    @annotation.tailrec
    def recursivo(l:List[Char], nueva:List[(Char, Int)]):List[(Char, Int)] = {

      // Comprueba si la lista no está vacia.
      if( !l.isEmpty ){
        // Obtenemos el primer carácter de la lista.
        val caracter = l.head

        // Compruebo si el carácter ya está en la lista nueva
        if( nueva.exists(p => (p._1 == caracter)) ){
          // Obtenemos la tupla para posteriormente poder actualizar el dato del contador.
          val tupla = nueva.find(p => (p._1 == caracter)).get

          // Llamada recursiva. Paso la lista de carácteres sin el primer elemento, y el segundo
          // argumento actualizo el contador de veces que se repite ese carácter.
          recursivo(l.tail, nueva.updated(nueva.indexOf(tupla), (tupla._1, tupla._2 + 1) ))
        }
        // El carácter no está en la lista
        else
        // lo añado a la lista nueva, con el contador a 1
          recursivo(l.tail, (caracter, 1)::nueva)
      }
        // Si la lista está vacía, devuelvo la nueva lista creada.
      else nueva
    }

    // Llamada a la función recursiva.
    recursivo(lista, Nil)


  }


  /**
    * Función para generar los nodos hoja del árbol. Este método recibe una lista que contiene tuplas, donde el primer
    * elemento de la tupla es un carácter y su segundo elemento el número de apariciones del carácter. Con cada tupla se
    * crea un nodo hoja. Uso del poliformismo NodoArbolHuffman para crear cada NodoHojaArbolHuffman.
    * @param lista contiene la lista de tuplas con el carácter y su número de apariciones.
    * @return devuelve una List[NodoArbolHuffman] con cada nodo hoja creado.
    */
  def generarNodosHoja(lista:List[(Char, Int)]):List[NodoArbolHuffman] = {

    // Almacenamos en la nueva lista todos los nodos hojas creados. Con la función map genera una List nueva con todas
    // las tuplas almacenadas en la variable lista. Cada elemento de la List nueva es un nodo hoja, donde el primer
    // parámetro es el carácter representado en la tupla, y el segundo elemento el número de repeticiones almacenada
    // en el segundo elemento de la tupla.
    val nueva:List[NodoArbolHuffman] = lista.map(p => new NodoHojaArbolHuffman(p._1, p._2))
    // Ordenamos la nueva lista, como argumento le pasamos la función calcularPeso que la utilizará para ordenar la lista.
    nueva.sortBy(calcularPeso)
  }

  /**
    * Función que devuelve el peso del nodo recibido como parámetro
    * @param n1 de tipo NodoArbolHuffman.
    * @return un entero que representa el peso del nodo.
    */
  def calcularPeso(n1:NodoArbolHuffman):Int = {

    // Uso de match (en otros lenguajes equivale al switch) para ver de que tipo de Nodo es n1. Devuelve el peso del nodo.
    n1 match{
      case NodoInternoArbolHuffman(_,_,_,peso ) => peso
      case NodoHojaArbolHuffman(_,peso) => peso
    }
  }

  /**
    * Función que devuelve la lista de carácteres que representa el nodo.
    * @param n1 de tipo NodoArbolHuffman, con el que se pretende extraer la lista de carácteres del nodo.
    * @return
    */
  def obtenerListaCaracteres(n1:NodoArbolHuffman):List[Char] = {
    // Comprobamos si es un nodo hoja o interno.
    n1 match {
        // Si es Interno, devuelvo la lista de carácteres que representa.
      case NodoInternoArbolHuffman(_,_,lista,_) => lista
        // Si es Hoja, meto el carácter es una lista, ya que el primer atributo de la clase es de tipo char, y necesitamos devolver
        // List[Char]
      case NodoHojaArbolHuffman(caracter,_) => List(caracter)
    }
  }

  /**
    * Función para crear un nodo interno.
    * @param n1 de tipo NodoArbolHuffman, será el hijo izquierdo del nodo interno.
    * @param n2 de tipo NodoArbolHuffman, será el hijo derecho del nodo interno.
    * @return Devuelve el nodo interno creado.
    */
  def crearNodoIntermedio(n1:NodoArbolHuffman, n2:NodoArbolHuffman):NodoArbolHuffman = {

    // Creación del nuevo nodo, el primero argumento es el hijo izquierdo, el segundo argumento el hijo derecho.
    // En el tercer argumento se almacena la lista de carácteres a representar, que es la concatenación de los carácteres
    // representados por el hijo izquierdo y derecho.
    // El cuarto argumento es la suma del peso del nodo hijo izquierdo y nodo hijo derecho.
    NodoInternoArbolHuffman(n1, n2, obtenerListaCaracteres(n1):::obtenerListaCaracteres(n2), calcularPeso(n1) + calcularPeso(n2))
  }

  /**
    * Función que combina dos nodos para crear un nuevo nodo interno, que será el padre de estos dos nodos. La función actualiza
    * y devuelve la lista de NodoArbolHuffman pasada como parámetro.
    * @param lista de tipo List[NodoArbolHuffman] contiene los nodos a combinar.
    * @return devuelve la lista con los nodos actualizada.
    */
  def combinar(lista:List[NodoArbolHuffman]):List[NodoArbolHuffman] = {

    // Llamada a la función crearNodoIntermedio pasandole como argumento los dos primeros nodos. Una vez que obtiene
    // el nuevo nodo, lo concatena con la lista de NodoArbolHuffman sin los dos primeros elementos. Después esta concatenación
    // la ordena, que realmente es que el nuevo nodo interno creado ocupe la posición que le corresponda en la lista en función
    // de su peso.
    (crearNodoIntermedio(lista.head, lista.tail.head)::lista.drop(2)).sortBy(calcularPeso)

  }


  /**
    * Función que crea el árbol a representar, almacenado en una variable llamada listaNodos de tipo List[NodoArbolHuffman].
    * Los dos primeros parámetros recibiran dos funciones que se utilizarán para crear el árbol. El tercer parámetro recibe una
    * List[NodoArbolHuffman] que en principio (para esta práctica) son nodos hoja y que se irán combinando para obtener una lista
    * de nodos internos, donde el primer elemento y único elemento (al final) será el nodo raíz del árbol. Recordar que estoy usando poliformismo, por lo tanto
    * un NodoArbolHuffman puede ser un NodoHojaArbolHuffman o NodoInternoArbolHuffman.
    * @param s almacena una función con un parámetro de tipo List[NodoArbolHuffan] y esta función devuelve un booleano.
    * @param c almacena una función con un parámetro de tipo List[NodoArbolHuffan] y una List[NodoArbolHuffman]
    * @param listaNodos de tipo List[NodoArbolHuffman] contiene la lista de nodos a combinar.
    * @return devuelve la lista de nodos combinados.
    */
  def repetir(s: List[NodoArbolHuffman] => Boolean, c: List[NodoArbolHuffman] => List[NodoArbolHuffman])(listaNodos:List[NodoArbolHuffman]):List[NodoArbolHuffman] = {

    // Llamada a la función s (singleton) que comprueba si la listaNodos contiene un elemento.
    // En caso de tener más de un elemento se hace una llamada a repetir (recursivo) pasandole la función contenida en s (singleton),
    // la función contenida en c (combinar) y el tercer parámetro la listaNodos llamando a la función combinar.
    if(!s(listaNodos)) repetir(s, c)(c(listaNodos))

      // Lista con un solo elemento, devolvemos listaNodos.
    else listaNodos

  }


  /**
    * Función para generar el árbol. Recibe una lista de nodos que serán los que se utilicen para generar el árbol.
    * @param lista de tipo List[NodoArbolHuffman] que contiene la lista de nodos para generar el árbol.
    * @return devuelve el primer elemento de la lista de nodos que se recibe de repetir. Este primer elemento es el nodo
    *         raíz.
    */
  def generarArbol(lista:List[NodoArbolHuffman]) = {

    repetir(singleton, combinar)(lista).head
  }

  /**
    * Función que recibe una lista de caracteres y crea el árbol a partir de un archivo de texto. Devuelve el nodo raíz.
    */
  def crearArbol(texto:String):NodoArbolHuffman = {
    // Obtengo la lista de frecuencias de los caracteres del archivo de texto. listaFrecuencias contiene una tupla char - int
    val listaFrecuencias = getCalcularFrecuencias(leerArchivo(texto).toList)
    // Obtengo la raiz del árbol generado
    val raiz = generarArbol(generarNodosHoja(listaFrecuencias))

    raiz
  }

  def crearArbol2(lista:List[Char]):NodoArbolHuffman = {
    val listaFrecuencias = getCalcularFrecuencias(lista)

    val raiz = generarArbol(generarNodosHoja(listaFrecuencias))

    raiz
  }


  /**
    * Recibe el nombre de un archivo de texto y trasforma todo su contenido a un string que es el que devuelve.
    * @param nombreArchivo nombre del archivo de texto.
    * @return devuelve un string con todo el contenido.
    */
  def leerArchivo(nombreArchivo : String) : String = {
    Source.fromFile(nombreArchivo).getLines().mkString
  }



}
