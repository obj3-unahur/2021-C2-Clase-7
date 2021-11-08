package ar.edu.unahur
package funcional

object Operaciones {

  type Acciones = Seq[Accion]

  // -------------------------------------------------------------
  // EJECUTAR
  // -------------------------------------------------------------

  def ejecutar(acciones: Acciones, deposito: Deposito): Unit = {
    acciones.foreach {
      case Sumar(cantidadASacarDeAParaPonerEnB) =>
        deposito.cantidadA -= cantidadASacarDeAParaPonerEnB
        deposito.cantidadB += cantidadASacarDeAParaPonerEnB
      case Vaciar =>
        deposito.cantidadB += deposito.cantidadA
        deposito.cantidadA = 0
      case Intercambiar =>
        val temp = deposito.cantidadA
        deposito.cantidadA = deposito.cantidadB
        deposito.cantidadB = temp
      case CargarContenedor(id) =>
        deposito.cantidadB = deposito.contenedores(id)
        deposito.contenedores(id) = 0
      case GuardarContenedor(id) =>
        deposito.contenedores(id) = deposito.cantidadB
        deposito.cantidadB = 0
    }
  }

  // -------------------------------------------------------------
  // DESCRIBIR
  // -------------------------------------------------------------

  def describir(acciones: Acciones): String = acciones.map {
    case Sumar(qty)             => s"SUMAR{$qty}"
    case Vaciar                 => "VACIAR"
    case Intercambiar           => "INTERCAMBIAR"
    case CargarContenedor(id)   => s"CARGAR_CONTENEDOR{$id}"
    case GuardarContenedor(id)  => s"GUARDAR_CONTENEDOR{$id}"
  }.mkString("\n")

  // -------------------------------------------------------------
  // SIMPLIFICAR
  // -------------------------------------------------------------

  def simplificar(acciones: Acciones): Acciones = acciones match {
    case Nil  => Nil
    case Sumar(qty1) :: Sumar(qty2) :: otrasAcciones  => simplificar(Sumar(qty1 + qty2) :: otrasAcciones)
    case Vaciar :: Vaciar :: otrasAcciones  => simplificar(Vaciar :: otrasAcciones)
    case Vaciar :: Sumar(_) :: otrasAcciones  => simplificar(Vaciar :: otrasAcciones)
    case Intercambiar :: Intercambiar :: otrasAcciones  => simplificar(otrasAcciones)

    case CargarContenedor(_) :: (cargarContenedor: CargarContenedor) :: otrasAcciones => simplificar(cargarContenedor :: otrasAcciones)
    case CargarContenedor(idCargado) :: GuardarContenedor(idGuardado) :: otrasAcciones
      if idCargado == idGuardado  => simplificar(otrasAcciones)

    case GuardarContenedor(_) :: (guardarContenedor: GuardarContenedor) :: otrasAcciones  => simplificar(guardarContenedor :: otrasAcciones)
    case GuardarContenedor(idGuardado) :: CargarContenedor(idCargado) :: otrasAcciones
      if idGuardado == idCargado => simplificar(otrasAcciones)

    case accion :: otrasAcciones  => Seq(accion) ++ simplificar(otrasAcciones)
  }
}
