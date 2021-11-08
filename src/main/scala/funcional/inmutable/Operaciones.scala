package ar.edu.unahur
package funcional.inmutable

import funcional.{CargarContenedor, GuardarContenedor, Sumar}

object Operaciones {

  type Acciones = Seq[Accion]

  def ejecutar(acciones: Acciones, depositoInicial: Deposito): Deposito = {
    var deposito = depositoInicial

    acciones.foreach {
      case Sumar(qty) => deposito = deposito.copy(cantidadA = deposito.cantidadA - qty,
                                                  cantidadB = deposito.cantidadB + qty)
      case Vaciar => deposito = deposito.copy(cantidadB = deposito.cantidadB + deposito.cantidadA,
                                              cantidadA = 0)
      case Intercambiar => deposito = deposito.copy(cantidadA = deposito.cantidadB, cantidadB = deposito.cantidadA)
      case CargarContenedor(id) => deposito = deposito.copy(cantidadB = deposito.contenedores(id),
                                                            contenedores = deposito.contenedores.updated(id, 0))
      case GuardarContenedor(id) => deposito = deposito.copy(cantidadB = 0,
                                                              contenedores = deposito.contenedores.updated(id, deposito.cantidadB))
    }

    deposito
  }
}
