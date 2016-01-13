// from https://github.com/trifectalabs/polyline-scala

import Models.Point

object Polyline {

  def decode(polyline: String): List[Point] = {
    decodeDifferences(polyline, Nil).foldRight[List[Point]](Nil)({(diff, acc) =>
      acc match {
        case Nil => List(Point(diff._1, diff._2))
        case coordinates => Point(coordinates.head.lat + diff._1, coordinates.head.lon + diff._2)::coordinates
      }
    }).reverse
  }

  def decodeDifferences(polyline: String, differences: List[(BigDecimal, BigDecimal)]): List[(BigDecimal, BigDecimal)] = {
    if (polyline.length > 0) {
      val (latDiff, pl1) = decodeDifference(polyline)
      val (lonDiff, pl2) = decodeDifference(pl1)
      decodeDifferences(pl2, (BigDecimal(latDiff/100000.0), BigDecimal(lonDiff/100000.0))::differences)
    } else {
      differences
    }
  }

  def decodeDifference(polyline: String, shift: Int = 0, result: Int = 0): (Int, String) = {
    val byte = polyline(0).toInt - 63
    val newResult = result | ((byte & 0x1f) << shift)
    if (byte >= 0x20) {
      decodeDifference(polyline.drop(1), shift+5, newResult)
    } else {
      val endResult =
        if ((newResult & 0x01) == 0x01)
          ~(newResult >> 1)
        else
          (newResult >> 1)
      (endResult, polyline.drop(1))
    }
  }

}
