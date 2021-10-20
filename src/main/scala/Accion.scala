package ar.edu.unahur

// -------------------------------------------------------------
// ACCIONES
// -------------------------------------------------------------

trait Accion {
  def accept(visitor: AccionVisitor): Unit
}

class Sumar(val qty: Int) extends Accion {
  override def accept(visitor: AccionVisitor): Unit = visitor.visitSumar(this)
}

object Vaciar extends Accion {
  override def accept(visitor: AccionVisitor): Unit = visitor.visitVaciar(this)
}

object Intercambiar extends Accion {
  override def accept(visitor: AccionVisitor): Unit = visitor.visitIntercambiar(this)
}

class CargarContenedor(val id: Int) extends Accion {
  override def accept(visitor: AccionVisitor): Unit = visitor.visitCargarContenedor(this)
}

class GuardarContenedor(val id: Int) extends Accion {
  override def accept(visitor: AccionVisitor): Unit = visitor.visitGuardarContenedor(this)
}