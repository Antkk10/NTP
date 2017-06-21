/**
  * Created by antoniomfc90 on 1/6/17.
  */
object Main {


  def main(args: Array[String]): Unit = {

    val codigoHuffmanFrances: NodoArbolHuffman = NodoInternoArbolHuffman(
      NodoInternoArbolHuffman(
        NodoInternoArbolHuffman(
          NodoHojaArbolHuffman('s', 121895),
          NodoInternoArbolHuffman(
            NodoHojaArbolHuffman('d', 56269),
            NodoInternoArbolHuffman(
              NodoInternoArbolHuffman(
                NodoInternoArbolHuffman(
                  NodoHojaArbolHuffman('x', 5928),
                  NodoHojaArbolHuffman('j', 8351),
                  List('x', 'j'), 14279),
                NodoHojaArbolHuffman('f', 16351),
                List('x', 'j', 'f'), 30630),
              NodoInternoArbolHuffman(
                NodoInternoArbolHuffman(
                  NodoInternoArbolHuffman(
                    NodoInternoArbolHuffman(
                      NodoHojaArbolHuffman('z', 2093),
                      NodoInternoArbolHuffman(
                        NodoHojaArbolHuffman('k', 745),
                        NodoHojaArbolHuffman('w', 1747),
                        List('k', 'w'), 2492),
                      List('z', 'k', 'w'), 4585),
                    NodoHojaArbolHuffman('y', 4725),
                    List('z', 'k', 'w', 'y'), 9310),
                  NodoHojaArbolHuffman('h', 11298),
                  List('z', 'k', 'w', 'y', 'h'), 20608),
                NodoHojaArbolHuffman('q', 20889),
                List('z', 'k', 'w', 'y', 'h', 'q'), 41497),
              List('x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 72127),
            List('d', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 128396),
          List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 250291),
        NodoInternoArbolHuffman(
          NodoInternoArbolHuffman(
            NodoHojaArbolHuffman('o', 82762),
            NodoHojaArbolHuffman('l', 83668),
            List('o', 'l'), 166430),
          NodoInternoArbolHuffman(
            NodoInternoArbolHuffman(
              NodoHojaArbolHuffman('m', 45521),
              NodoHojaArbolHuffman('p', 46335),
              List('m', 'p'), 91856),
            NodoHojaArbolHuffman('u', 96785),
            List('m', 'p', 'u'),
            188641),
          List('o', 'l', 'm', 'p', 'u'), 355071),
        List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q', 'o', 'l', 'm', 'p', 'u'), 605362),
      NodoInternoArbolHuffman(
        NodoInternoArbolHuffman(
          NodoInternoArbolHuffman(
            NodoHojaArbolHuffman('r', 100500),
            NodoInternoArbolHuffman(
              NodoHojaArbolHuffman('c', 50003),
              NodoInternoArbolHuffman(
                NodoHojaArbolHuffman('v', 24975),
                NodoInternoArbolHuffman(
                  NodoHojaArbolHuffman('g', 13288),
                  NodoHojaArbolHuffman('b', 13822),
                  List('g', 'b'), 27110),
                List('v', 'g', 'b'), 52085),
              List('c', 'v', 'g', 'b'), 102088),
            List('r', 'c', 'v', 'g', 'b'), 202588),
          NodoInternoArbolHuffman(
            NodoHojaArbolHuffman('n', 108812),
            NodoHojaArbolHuffman('t', 111103),
            List('n', 't'), 219915),
          List('r', 'c', 'v', 'g', 'b', 'n', 't'), 422503),
        NodoInternoArbolHuffman(
          NodoHojaArbolHuffman('e', 225947),
          NodoInternoArbolHuffman(
            NodoHojaArbolHuffman('i', 115465),
            NodoHojaArbolHuffman('a', 117110),
            List('i', 'a'), 232575),
          List('e', 'i', 'a'), 458522),
        List('r', 'c', 'v', 'g', 'b', 'n', 't', 'e', 'i', 'a'), 881025),
      List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q', 'o', 'l', 'm', 'p', 'u', 'r', 'c', 'v', 'g', 'b', 'n', 't', 'e', 'i', 'a'), 1486387)


    // Mensaje secreto a decodificar
    val mensajeSecretoFrances: List[Int] = List(0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0,
      0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1,
      0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1)
    println("Primera prueba:")
    println("\tConsiste en generar el árbol con los carácteres AAAAAAAABBBCDEFGH...")
    val primerNodo = GenerarArbol.crearArbol2("AAAAAAAABBBCDEFGH".toList)
    println("\tUna vez tenemos generado el árbol, códifico el mensaje ADF")
    println("\tCodificando...")
    val cod = Huffman.codificar("ADF".toList, primerNodo)
    println("\t" + cod)
    println("\tAhora decodificamos el código codificado y debe ser ADF")
    println("\t" + Huffman.decodificar(primerNodo, cod))
    println()
    println()


    println("Segunda prueba, tenemos un árbol de codificación en Frances y queremos decodificar un mensaje secreto:")
    println("\tEl mensaje codificado es el siguiente:")
    println("\t" + mensajeSecretoFrances)
    println("\tDecodificando...")
    println("\t" + Huffman.decodificar(codigoHuffmanFrances, mensajeSecretoFrances))
    println("\tAhora lo que queremos es codificar el mensaje huffmanestcool para posteriormente comprobar si genera el mismo código " +
      "de codificación usando además codificación rápida")
    println("\tCodificando: huffmanestcool")
    val codificar = Huffman.codificarRapido(codigoHuffmanFrances, "huffmanestcool".toList)
    println("\tEl mensaje codificado es el siguiente:")
    println("\t" + codificar)
    println("\tLos dos códigos codificados son iguales?")
    println(mensajeSecretoFrances==codificar)
    println("\t Ahora decodifico el mensaje codificado con codificación rápida:")
    println("\t" + Huffman.decodificar(codigoHuffmanFrances, codificar))


    val mensajeSecretoEsp = List(0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1,
      0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0,
      1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
      0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1,
      1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0,
      0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1,
      1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1)


    println()
    println()
    println("Tercera prueba: consiste en descifrar un mensaje en español generando un nuevo árbol de codificación para la lengua española.")
    println("\tEl primer paso consiste en descifrar este mensaje:")
    println("\t" + mensajeSecretoEsp)
    println("\tDescifrando...")
    val nodoEsp = GenerarArbol.crearArbol("./src/regenta.txt")
    println("\t" + Huffman.decodificar(nodoEsp, mensajeSecretoEsp))
    println("\tAhora voy a codificar el mensaje: La regenta de Benito Perez Galdos")
    println("\tUtilizo codificación rápida.")
    println("\tCodificando...")
    val codificar2 = Huffman.codificarRapido(nodoEsp, "La regenta de Benito Perez Galdos".toList)
    println("\tLos dos códigos codificados son iguales?")
    println(mensajeSecretoEsp==codificar2)
    println("\tDecodificamos el mensaje codificado.")
    println("\tDecodificando...")
    println("\t" + Huffman.decodificar(nodoEsp, codificar2))
    println()
    println("Terminadas las pruebas.")



  }

}
