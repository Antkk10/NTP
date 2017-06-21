/**
  * Created by antoniomfc90 on 30/5/17.
  */
case class NodoInternoArbolHuffman(Hizqd:NodoArbolHuffman, Hdrch:NodoArbolHuffman, lista:List[Char], peso:Int) extends NodoArbolHuffman{


  override def toString = "Hijo izquierda: " + Hizqd.toString + " Hijo derecha: " + Hdrch.toString + "\n"
}
