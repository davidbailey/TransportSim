// from https://github.com/trifectalabs/polyline-scala

package main

import models.Models.Point

class Polyline {

  def encode(coordinates: List[Point]): String = {
    coordinates.foldLeft[List[(BigDecimal,BigDecimal)]](Nil)({(acc, coordinate) =>
      acc match {
        case Nil => List((coordinate.lat, coordinate.lon))
        case differences =>
          val currentPos = differences.reduceLeft((pos, diff) => (pos._1 + diff._1, pos._2 + diff._2))
          (coordinate.lat - currentPos._1, coordinate.lon - currentPos._2)::differences
      }
    }).reverse.map{ case (latDiff, lonDiff) =>
      encodeDifference(latDiff) + encodeDifference(lonDiff)
    }.mkString
  }

  def encodeDifference(diff: BigDecimal): String = {
    val value = if (diff < 0) {
      ~((diff * 100000).toInt << 1)
    } else {
      (diff * 100000).toInt << 1
    }
    encodeFiveBitComponents(value, "")
  }

  def encodeFiveBitComponents(value: Int, str: String): String = {
    if (value != 0) {
      val fiveBitComponent = if (value >= 0x20) {
        ((value & 0x1f) | 0x20) + 63
      } else {
        (value & 0x1f) + 63
      }
      encodeFiveBitComponents(value >> 5, str + fiveBitComponent.toChar)
    } else {
      str
    }
  }

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
