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

  override def visitVaciar(accion: Vaciar.type): Unit = ???

  override def visitSumar(accion: Sumar): Unit = ???

  override def visitIntercambiar(accion: Intercambiar.type): Unit = ???

  override def visitCargarContenedor(accion: CargarContenedor): Unit = ???

  override def visitGuardarContenedor(accion: GuardarContenedor): Unit = ???
}