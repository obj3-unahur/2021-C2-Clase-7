package ar.edu.unahur
package funcional

object Aplicacion extends App {
//  val accionesCon2Intercambiar = Seq(Intercambiar, Intercambiar)
//  println(Operaciones.simplificar(accionesCon2Intercambiar))
  val accionesCon3Intercambiar = Seq(Sumar(10), Intercambiar, Intercambiar, Intercambiar, CargarContenedor(1))
  println(Operaciones.simplificar(accionesCon3Intercambiar))
}
