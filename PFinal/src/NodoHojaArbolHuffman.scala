/**
  * Created by antoniomfc90 on 30/5/17.
  */
case class NodoHojaArbolHuffman(val caracter:Char, val peso:Int) extends NodoArbolHuffman{


  override def toString: String = this.caracter +  " " + this.peso


}
