
import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BusquedaBinariaSuite extends FunSuite{

  import Main.busquedaBinaria

  test("Lista: 4, 7, 11, 16, 25, 33, 44, 55, 56; Buscamos el valor 211"){
    assert(busquedaBinaria(Array(4, 7, 11, 16, 25, 33, 44, 55, 56), (x:Int, y:Int) => (x < y),
      (x:Int, y:Int) => (x == y), 211) === -1)
  }


  test("Lista: 4, 7, 11, 16, 25, 33, 44, 55, 56; Buscamos el valor 33"){
    assert(busquedaBinaria(Array(4, 7, 11, 16, 25, 33, 44, 55, 56), (x:Int, y:Int) => (x < y),
      (x:Int, y:Int) => (x == y), 33) === 5)
  }


  test("Lista: 4, 7, 11, 16, 25, 33, 44, 55, 56; Buscamos el valor 3"){
    assert(busquedaBinaria(Array(4, 7, 11, 16, 25, 33, 44, 55, 56), (x:Int, y:Int) => (x < y),
      (x:Int, y:Int) => (x == y), 3) === -1)
  }


  test("Lista: 1, 5, 7, 99, 120; Buscamos el valor 1"){
    assert(busquedaBinaria(Array(1, 5, 7, 99, 120), (x:Int, y:Int) => (x < y),
      (x:Int, y:Int) => (x == y), 1) === 0)
  }

  test("Lista: 4, 7, 11, 16, 25, 33, 44, 55, 56; Buscamos el valor 56"){
    assert(busquedaBinaria(Array(4, 7, 11, 16, 25, 33, 44, 55, 56), (x:Int, y:Int) => (x < y),
      (x:Int, y:Int) => (x == y),56) === 8)
  }

  test("Lista: 4, 7, 11; Buscamos el valor 8"){
    assert(busquedaBinaria(Array(4, 7, 11), (x:Int, y:Int) => (x < y),
      (x:Int, y:Int) => (x == y), 8) === -1)
  }

  test("Lista: 4, 7, 11, 16, 25, 33, 44, 55, 56, 75, 88, 99, 211, 2312, 3333, 4453, 9821; Buscamos el valor 56"){
    assert(busquedaBinaria(Array(4, 7, 11, 16, 25, 33, 44, 55, 56, 75, 88, 99, 211, 2312, 3333, 4453, 9821), (x:Int, y:Int) => (x < y),
      (x:Int, y:Int) => (x == y), 9821) === 16)
  }
}
