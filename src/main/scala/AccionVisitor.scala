package ar.edu.unahur

// -------------------------------------------------------------
// VISITOR
// -------------------------------------------------------------

trait AccionVisitor {
  def visitVaciar(accion: Vaciar.type): Unit
  def visitSumar(accion: Sumar): Unit
  def visitIntercambiar(accion: Intercambiar.type): Unit
  def visitCargarContenedor(accion: CargarContenedor): Unit
  def visitGuardarContenedor(accion: GuardarContenedor): Unit
}

// -------------------------------------------------------------
// EJECUTAR VISITOR
// -------------------------------------------------------------

class EjecutarVisitor(deposito: Deposito) extends AccionVisitor {

  override def visitVaciar(accion: Vaciar.type): Unit = {
    deposito.cantidadB = deposito.cantidadA + deposito.cantidadB
    deposito.cantidadA = 0
  }

  override def visitSumar(accion: Sumar): Unit = {
    require(accion.qty <= deposito.cantidadA)
    deposito.cantidadA -= accion.qty
    deposito.cantidadB += accion.qty
  }

  override def visitIntercambiar(accion: Intercambiar.type): Unit = {
    val temp = deposito.cantidadA
    deposito.cantidadA = deposito.cantidadB
    deposito.cantidadB = temp
  }

  override def visitCargarContenedor(accion: CargarContenedor): Unit = {
    deposito.cantidadB = deposito.contenedores(accion.id)
    deposito.contenedores(accion.id) = 0
  }

  override def visitGuardarContenedor(accion: GuardarContenedor): Unit = {
    deposito.contenedores(accion.id) = deposito.cantidadB
    deposito.cantidadB = 0
  }
}

// -------------------------------------------------------------
// DESCRIBIR VISITOR
// -------------------------------------------------------------

class DescribirVisitor extends AccionVisitor {
  private var text = ""

  override def visitVaciar(accion: Vaciar.type): Unit = text += "VACIAR\n"

  override def visitSumar(accion: Sumar): Unit = text += s"SUMAR{${accion.qty}}\n"

  override def visitIntercambiar(accion: Intercambiar.type): Unit = text += "INTERCAMBIAR\n"

  override def visitCargarContenedor(accion: CargarContenedor): Unit = text += s"CARGAR_CONTENEDOR{${accion.id}}\n"

  override def visitGuardarContenedor(accion: GuardarContenedor): Unit = text += s"GUARDAR_CONTENEDOR{${accion.id}}\n"

  def result: String = text
}

// -------------------------------------------------------------
// SIMPLIFICAR VISITOR
// -------------------------------------------------------------

class SimplificarVisitor extends AccionVisitor {
  private var simplificado = Seq[Accion]()

//  private var ultimaAccionSumar = false
//  private var ultimaCantidadSumada = 0
//
//  override def visitVaciar(accion: Vaciar.type): Unit = ???
//
//  override def visitSumar(accion: Sumar): Unit = {
//    if (ultimaAccionSumar) {
//      ultimaCantidadSumada += accion.qty
//      simplificado = simplificado.dropRight(1) :+ new Sumar(ultimaCantidadSumada)
//    } else {
//      simplificado = simplificado :+ accion
//      ultimaCantidadSumada = accion.qty
//      ultimaAccionSumar = true
//    }
//  }
//
//  override def visitIntercambiar(accion: Intercambiar.type): Unit = ???
//
//  override def visitCargarContenedor(accion: CargarContenedor): Unit = ???
//
//  override def visitGuardarContenedor(accion: GuardarContenedor): Unit = ???















  private var ultimaAccionSumar = false
  private var ultimaCantidadSumada = -1
  private var ultimaAccionVaciar = false
  private var ultimaAccionIntercambiar = false
  private var ultimaAccionCargar = false
  private var ultimoContenedorCargado = -1
  private var ultimaAccionGuardar = false
  private var ultimoContenedorGuardado = -1


  override def visitVaciar(accion: Vaciar.type): Unit = {
    if (!ultimaAccionVaciar) {
      simplificado = simplificado :+ accion

      ultimaAccionSumar = false
      ultimaAccionVaciar = true
      ultimaAccionIntercambiar = false
      ultimaAccionCargar = false
      ultimaAccionGuardar = false
    }
  }

  override def visitSumar(accion: Sumar): Unit = {
    if (ultimaAccionSumar) {
      ultimaCantidadSumada += accion.qty
      simplificado = simplificado.dropRight(1) :+ new Sumar(ultimaCantidadSumada)
    } else {
      ultimaCantidadSumada = accion.qty
      simplificado = simplificado :+ accion

      ultimaAccionSumar = true
      ultimaAccionVaciar = false
      ultimaAccionIntercambiar = false
      ultimaAccionCargar = false
      ultimaAccionGuardar = false
    }
  }

  override def visitIntercambiar(accion: Intercambiar.type): Unit = {
    if (ultimaAccionIntercambiar) {
      ultimaAccionIntercambiar = false
      simplificado = simplificado.dropRight(1)
    } else {
      simplificado = simplificado :+ accion

      ultimaAccionSumar = false
      ultimaAccionVaciar = false
      ultimaAccionIntercambiar = true
      ultimaAccionCargar = false
      ultimaAccionGuardar = false
    }
  }

  override def visitCargarContenedor(accion: CargarContenedor): Unit = {
    if (ultimaAccionCargar) {
      if (ultimoContenedorCargado != accion.id) {
        simplificado = simplificado.dropRight(1) :+ accion
        ultimoContenedorCargado = accion.id
      }
    } else if (ultimaAccionGuardar && ultimoContenedorGuardado == accion.id) {
        simplificado = simplificado.dropRight(1)

        ultimaAccionSumar = false
        ultimaAccionVaciar = false
        ultimaAccionIntercambiar = false
        ultimaAccionCargar = false
        ultimaAccionGuardar = false
    } else {
      simplificado = simplificado :+ accion

      ultimaAccionSumar = false
      ultimaAccionVaciar = false
      ultimaAccionIntercambiar = false
      ultimaAccionCargar = true
      ultimoContenedorCargado = accion.id
      ultimaAccionGuardar = false
    }
  }

  override def visitGuardarContenedor(accion: GuardarContenedor): Unit = {
    if (ultimaAccionGuardar) {
      if (ultimoContenedorGuardado != accion.id) {
        simplificado = simplificado.dropRight(1) :+ accion
        ultimoContenedorGuardado = accion.id
      }
    } else if (ultimaAccionCargar && ultimoContenedorCargado == accion.id) {
        simplificado = simplificado.dropRight(1)

        ultimaAccionSumar = false
        ultimaAccionVaciar = false
        ultimaAccionIntercambiar = false
        ultimaAccionCargar = false
        ultimaAccionGuardar = false
    } else {
      simplificado = simplificado :+ accion

      ultimaAccionSumar = false
      ultimaAccionVaciar = false
      ultimaAccionIntercambiar = false
      ultimaAccionCargar = false
      ultimaAccionGuardar = true
      ultimoContenedorGuardado = accion.id
    }
  }

  def result: Seq[Accion] = simplificado
}