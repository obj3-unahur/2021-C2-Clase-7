package ar.edu.unahur

class Deposito {
  private val CANTIDAD_CONTENEDORES = 10
  private val CAPACIDAD_MAXIMA = 1000

  private var _cantidadA: Int = 0
  private var _cantidadB: Int = 0
  private val _contenedores: Array[Int] = new Array(CANTIDAD_CONTENEDORES)

  def cantidadA: Int = _cantidadA
  def cantidadA_=(value: Int): Unit = {
    require(value <= CAPACIDAD_MAXIMA)
    _cantidadA = value
  }

  def cantidadB: Int = _cantidadB
  def cantidadB_=(value: Int): Unit = {
    require(value <= CAPACIDAD_MAXIMA)
    _cantidadB = value
  }

  def contenedores: Array[Int] = _contenedores
}
