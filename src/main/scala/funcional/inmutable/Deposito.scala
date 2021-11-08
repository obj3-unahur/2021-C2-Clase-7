package ar.edu.unahur
package funcional.inmutable

object Deposito {
  val CANTIDAD_CONTENEDORES = 10
  val CANTIDAD_MAXIMA = 1000
}

case class Deposito(cantidadA: Int,
                    cantidadB: Int,
                    contenedores: Array[Int] = Array.fill(Deposito.CANTIDAD_CONTENEDORES)(0)) {
  require(cantidadA <= Deposito.CANTIDAD_MAXIMA)
  require(cantidadB <= Deposito.CANTIDAD_MAXIMA)
  require(contenedores.length == Deposito.CANTIDAD_CONTENEDORES)
}

