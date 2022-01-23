val mapCellection = Map[String, Seq[String]]("a" -> Seq("Adrian", "Adam"), "b" ->  Seq("Bartek"))

println(mapCellection)

val strings = Seq("Adrian", "Paweł", "Zdzisiek", "Amela", "Kowalski")

val mapByFirstLetter = strings.foldLeft(Map[String, Seq[String]]()) { (f, n) =>
  val k = f.get(n.head.toString) match {
    case Some(value) =>
      val r = f + (n.head.toString -> (value :+ n))
      r

    case None =>
      val p = f + (n.head.toString -> Seq(n))
      p
  }
  k
}

println(mapByFirstLetter)