/**
  * Created by antoniomfc90 on 12/5/17.
  */
object Main {

  def main(args: Array[String]): Unit = {

    import Conjunto._

    val conj = conjuntoUnElemento(4)

    val s1 = conjuntoUnElemento(1)
    val s2 = conjuntoUnElemento(2)
    val s3 = conjuntoUnElemento(3)

    val s = union(s1, s2)

    println(s(1))
    println(s(2))
    println(s(3))

    val conjunto1 = Conjunto((x: Int) => x > 3)
    val conjunto2 = Conjunto((x: Int) => x > 5)

    val conjuntoUnion:Conjunto = Conjunto.union(conjunto1, conjunto2)

    println()
    println(conjuntoUnion(4))
    println(conjuntoUnion(5))
    println(conjuntoUnion(6))
    println(conjuntoUnion(7))
    println(conjuntoUnion(3))
    println(conjuntoUnion(2))

    val conj1 = Conjunto((x: Int) => x > 3)
    val conj2 = Conjunto((x: Int) => x > 5)

    val conjuntoInterseccion = interseccion(conj1, conj2)

    println()
    println(conjuntoInterseccion(6))
    println(conjuntoInterseccion(4))
    println(conjuntoInterseccion(5))


    val conjunt1 = Conjunto((x: Int) => x > 3)
    val conjunt2 = Conjunto((x: Int) => x < 10)

    val conjuntoDiferencia = diferencia(conjunt1, conjunt2)

    println()
    println(conjuntoDiferencia(6))
    println(conjuntoDiferencia(11))

    val conjuntoFiltrado = filtrar(conjunt1, conjunt2.funcionCaracteristica)

    println("Conjunto filtrado")
    println(conjuntoFiltrado(6))
    println(conjuntoFiltrado(7))
    println(conjuntoFiltrado(8))
    println(conjuntoFiltrado(9))
    println(conjuntoFiltrado(10))
    println(conjuntoFiltrado(11))


    println()
    println("Para todo")
    val conjunto = Conjunto((x: Int) => x < 10)

    println(paraTodo(conjunto, x => x > 0))
    println(paraTodo(conjunto, x => x < 15))

    println()
    println("Existe")
    println(existe(conjunto, x => x > 10))
    println(existe(conjunto, x => x < 15))

    println()
    println("Map")
    val resultado = map(conjunto, (x => x + 25))

    println(resultado(30))
    println(resultado(31))

    println(resultado(125))

  }
}
