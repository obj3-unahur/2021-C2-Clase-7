package ar.edu.unahur

object Aplicacion extends App {
  val deposito = new Deposito
  deposito.cantidadA = 500

  val acciones: Seq[Accion] = Seq(new CargarContenedor(1),
    new Sumar(100),
    new GuardarContenedor(1),
    new CargarContenedor(2),
    Vaciar,
    new GuardarContenedor(2))

  println("Describir Visitor")
  println("------------------------------")
  val describirVisitor = new DescribirVisitor
  acciones.foreach(_.accept(describirVisitor))
  println(describirVisitor.result)

  println()

  println("Ejecutar Visitor")
  println("------------------------------")
  val ejecutarVisitor = new EjecutarVisitor(deposito)
  acciones.foreach(accion => accion.accept(ejecutarVisitor))
  println(s"A: ${deposito.cantidadA}, B: ${deposito.cantidadB}")
  println(s"Contenedores: ${deposito.contenedores.mkString("Array(", ", ", ")")}")
}

