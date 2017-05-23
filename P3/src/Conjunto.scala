/**
  * Created by antoniomfc90 on 8/5/17.
  */
class Conjunto(val funcionCaracteristica: Int => Boolean) {
  /**
    * Crea una cadena con el contenido completo del conjunto
    *
    * @return
    *
    */
  override def toString(): String = {
    val elementos = for(i <- -Conjunto.LIMITE to Conjunto.LIMITE
                        if funcionCaracteristica(i)) yield i
    elementos.mkString("{", ",", "}")
  }

  /**
    * Método para determinar la pertenencia de un elemento al conjunto
    * @param elemento
    * @return valor booleano indicando si el elemento cumple la función característica
    *         o no.
    */
  def apply(elemento:Int): Boolean = {
    funcionCaracteristica(elemento)
  }

}
/**
  * Objecto companion que ofrece metodos para trabajar con
  * conjuntos
  */
object Conjunto{

  /**
    * Limite para la iteracion necesaria algunas operaciones,
    * entre -1000 y 1000
    */
  private final val LIMITE = 1000
  /**
    * Metodo que permite crear objetos de la clase Conjunto
    * de forma sencilla
    * @param f
    * @return
    */
  def apply(f: Int => Boolean): Conjunto = {
    new Conjunto(f)
  }

  /**
    * Método que crea un conjunto con un elemento
    * @param elemento para construir el conjunto con un entero.
    * @return instancia del conjunto construido.
    */
  def conjuntoUnElemento(elemento: Int):Conjunto = {

    this.apply( (x:Int) => x==elemento)
  }

  /**
    * Método que crea un nuevo conjunto con la unión de dos conjuntos ya construidos.
    * @param c1 primer conjunto
    * @param c2 segundo conjunto
    * @return instancia del conjunto construido.
    */
  def union(c1:Conjunto, c2:Conjunto): Conjunto = {

    this.apply((x:Int) => ( c1(x) || c2(x) ) )
  }

  /**
    * Método que crea un nuevo conjunto con la intersección de dos conjuntos ya construidos, es decir, con elementos
    * que se encuentran en ambos conjuntos.
    * @param c1 primer conjunto.
    * @param c2 segundo conjunto.
    * @return instancia del conjunto construido.
    */
  def interseccion(c1:Conjunto, c2:Conjunto):Conjunto = {

    this.apply( (x:Int) => ( c1(x) && c2(x) ) )
  }

  /**
    * Método que recibe como parámetros dos conjuntos, y crea uno nuevo, con el resultado de quitar los elementos del
    * conjunto c1 que están en el conjunto c2. Como resultado contiene los los elementos restantes en el conjunto c1.
    * A \ B
    * @param c1 primer conjunto.
    * @param c2 segundo conjunto
    * @return instancia del conjunto construido.
    */
  def diferencia(c1:Conjunto, c2:Conjunto):Conjunto = {

    this.apply( (x:Int ) => ( c1(x) && !c2(x) ) )
  }

  /**
    * Método que crea un nuevo conjunto con los elementos que están en el conjunto c y que cumplen la condición del
    * predicado.
    * @param c conjunto
    * @param predicado condición que tienen que cumplir los elementos del conjunto.
    * @return instancia del conjunto construido.
    */
  def filtrar(c: Conjunto, predicado:Int => Boolean):Conjunto = {

    //this.apply( (x:Int) => ( c(x) && this.apply(predicado)(x) ) )
    this.apply( (x:Int) => ( c(x) && predicado(x) ) )
  }

  /**
    * Método que devuelve true si todos los elementos del conjunto cumplen la condición del predicado.
    * @param conjunto, contiene todos los elementos a comprobar
    * @param predicado condiciones que deben de cumplir los elementos
    * @return true si todos los elementos cumplen la condición del predicado. False si al menos uno no lo cumple.
    */
  def paraTodo(conjunto:Conjunto, predicado:Int => Boolean):Boolean = {

    def iterar(elemento:Int): Boolean = {
      if(elemento > LIMITE) true // Llegado al límite, todos los elementos comprobados cumplen la condición. True
      else if(!conjunto(elemento)) iterar(elemento + 1) // No está dentro del conjunto, seguimos iterando
      else predicado(elemento) && iterar(elemento + 1)
    }

    iterar(-LIMITE)
  }

  /**
    * Método que comprueba si al menos un elemento del conjunto cumple la condición del predicado.
    * @param c conjunto de elementos
    * @param predicado condición que debe de cumplir al menos un elemento
    * @return true si hay un elemento que cumple la condición. False si no hay ningún elemento.
    */
  def existe(c:Conjunto, predicado:Int => Boolean):Boolean = {

    def iterar(elemento:Int):Boolean = {

      if(elemento > LIMITE) false // Llegado al límite, no lo cumple ningún elemento. False.
      else if( c(elemento) && predicado(elemento)) true // Dentro del conjunto y el elemento cumple la condición. True
      else iterar(elemento + 1) // Seguimos iterando por el siguiente elemento
    }

    iterar(-LIMITE)
  }

  /**
    * Método que transforma un método en otro, con una función pasada como parámetro.
    * @param c conjunto
    * @param funcion función que transforma un conjunto a otro.
    * @return instancia del conjunto construido.
    */
  def map(c : Conjunto, funcion : Int => Int) : Conjunto = {

    // Creamos el conjunto utilizando el método existe que indica si hay algún elemento del conjunto que
    // al realizar la suma sea igual al número pasado como parámetro (cuando se usa en el main).
    this.apply( (x:Int) => existe(c, num => funcion(num) == x) )
  }

}
