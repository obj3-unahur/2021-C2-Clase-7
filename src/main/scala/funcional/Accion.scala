package ar.edu.unahur
package funcional

sealed trait Accion

case class Sumar(qty: Int) extends Accion
case object Vaciar extends Accion
case object Intercambiar extends Accion
case class CargarContenedor(id: Int) extends Accion
case class GuardarContenedor(id: Int) extends Accion