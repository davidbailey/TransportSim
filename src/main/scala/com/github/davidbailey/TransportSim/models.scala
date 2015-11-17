package models

object Models {

import scala.util.Random
import scala.collection.mutable.ListBuffer

  class Distance (distance: Double) extends Serializable {
    def asFeet = distance
    def asMiles = distance * 0.0001893939394
    def asMeters = distance * 0.3048
    def asKilometers = distance * 0.0003048
  }

  case class Point (x: Double, y: Double) extends Serializable // OSM: Node

  def RandomPoint = new Point(Random.nextInt + Random.nextDouble, Random.nextInt + Random.nextDouble)
  val origin = Point(0,0)

  case class LineString (points: List[Point]) extends Serializable
  def RandomLineString = LineString(List(origin,RandomPoint))
  
  class Person (route: LineString) extends Serializable { // basic agent
    var inVehicle = false
    var isDriver = false
    var arrived = false
    var travelTime: Int = 0
    var currentRouteSegment = 0
    var centroid = RandomPoint
    var width = new Distance (1.5 + Random.nextDouble)
    var length = new Distance (0.5 + Random.nextDouble)
    def view {
      print("{\"type\":\"person\", \"x\":\"" + centroid.x + "\", \"y\":\"" + centroid.y + "\", \"width\":\"" + width.asFeet + "\", \"length\":\"" + length.asFeet + "\"}");
    }
  }

  abstract class Vehicle extends Serializable {
    var driver = None: Option[Person]
    val subtype: String
    var passengers = ListBuffer[Person]()
    val maxPassengers: Int
    val width: Distance
    val length: Distance
    var centroid: Point
    def view {
      print("{\"type\":\"Vehicle\", \"subtype\":\"" + this.subtype + "\", \"x\":\"" + centroid.x + "\", \"y\":\"" + centroid.y + "\", \"width\":\"" + width.asFeet + "\", \"length\":\"" + length.asFeet + "\"}");
    }
  }

  class Bicycle extends Vehicle {
    val subtype = "Bicycle"
    val maxPassengers = 1
    val width = new Distance (2.0 + Random.nextDouble)
    val length = new Distance (6.0 + Random.nextDouble)
    var centroid = RandomPoint
  }

  class Car extends Vehicle {
    val subtype = "Car"
    val maxPassengers = 4
    val width = new Distance (6.0 + Random.nextDouble)
    val length = new Distance (12.0 + Random.nextDouble)
    var centroid = RandomPoint
  }

  class Bus extends Vehicle {
    val subtype = "Bus"
    val maxPassengers = 84
    val width = new Distance (8.0)
    val length = new Distance (40.0)
    var centroid = RandomPoint
  }

  abstract class LightRail extends Vehicle {
    val subtype = "LightRail"
    val maxPassengers = 220
    var centroid = RandomPoint
  }

  abstract class HeavyRail extends Vehicle {
    val subtype = "HeavyRail"
    val maxPassengers = 800
    var centroid = RandomPoint
  }

  class Track extends Serializable {
    val width = 4.708
  }

  abstract class Railway {
    val tracks: List[Track]
  }

  abstract class Lane extends Serializable {
    val width: Double
  }

  class GeneralLane extends Lane { 
    val width = 10.0
  }

  class FreewayLane extends Lane { 
    val width = 13.0
  }

  class ParkingLane extends Lane { 
    val width = 10.0
  }

  class BikeLane extends Lane { 
    val width = 5.0
  }

  class BusLane extends Lane { 
    val width = 10.0
  }

  class Sidewalk extends Lane { 
    val width = 4.0
  }

  abstract class HalfRoad extends Serializable { // OSM: Way w/ oneway != yes
    val lanes = List[Lane]()
    val parkingLane: ParkingLane
    val bikeLane: BikeLane
    val busLane: BusLane
    val sidewalk: Sidewalk
  }

  abstract class TwoWayRoad extends Serializable { // OSM: Way w/ oneway = yes
    val wayOne: HalfRoad
    val wayTwo: HalfRoad
    val centerLine: LineString
  }

  abstract class OneWayRoad extends Serializable { // OSM: lanes (only sometimes)
    val wayOne: HalfRoad
    val centerLine: LineString
  }

  class OneWayFreeway extends Serializable {
    val freewayLanes = List[Lane]()
  }

  abstract class Freeway extends Serializable {
    val wayOne: OneWayFreeway
    val wayTwo: OneWayFreeway
  }

  class ParkingSpace extends Serializable {
    val width = 8.0
    val length = 16.0
    val x = Random.nextInt
    val y = Random.nextInt
    var occupant = None: Option[Car]
    def view {
      print("{\"type\"=\"parkingSpace\", \"x\":\"" + x + "\", y:\"" + y + "\"}");
    }
  }

}
