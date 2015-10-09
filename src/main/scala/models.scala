package models

object Models {

import scala.util.Random
import scala.collection.mutable.ListBuffer

  class Distance (distance: Double) {
    def asFeet = distance
    def asMeters = distance * 0.3048
  }

  class Point (x_in: Double, y_in: Double) { // OSM: Node
    def x = x_in
    def y = y_in
  }

  def RandomPoint = new Point(Random.nextInt + Random.nextDouble, Random.nextInt + Random.nextDouble)

  class LineString (points_in: List[Point]) {
    def points = points_in
  }

  class Person { // basic agent
    var inVehicle = false
    var isDriver = false
    var arrived = false
    var travelTime: Int = 0
    var route = List()
    var currentRouteSegment = 0
    var centroid = RandomPoint
    var width = new Distance (1.5 + Random.nextDouble)
    var length = new Distance (0.5 + Random.nextDouble)
    def view {
      print("{\"type\":\"person\", \"x\":\"" + centroid.x + "\", \"y\":\"" + centroid.y + "\", \"width\":\"" + width.asFeet + "\", \"length\":\"" + length.asFeet + "\"}");
    }
  }

  abstract class Vehicle {
    val nullPerson: Person = null
    var driver = nullPerson
    def subtype: String
    def passengers = ListBuffer[Person]()
    def maxPassengers: Int
    def width: Distance
    def length: Distance
    def centroid: Point
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

  class Track {
    val width = 4.708
  }

  abstract class Railway {
    val tracks: List[Track]
  }

  class Lane { 
    val width = 10.0
  }

  class FreewayLane { 
    val width = 13.0
  }

  class ParkingLane { 
    val width = 10.0
  }

  class BikeLane { 
    val width = 5.0
  }

  class BusLane { 
    val width = 10.0
  }

  class Sidewalk { 
    val width = 4.0
  }

  abstract class HalfRoad { // OSM: Way w/ oneway != yes
    val lanes = List[Lane]()
    val parkingLane: ParkingLane
    val bikeLane: BikeLane
    val busLane: BusLane
    val sidewalk: Sidewalk
  }

  abstract class TwoWayRoad { // OSM: Way w/ oneway = yes
    val wayOne: HalfRoad
    val wayTwo: HalfRoad
    val centerLine: LineString
  }

  abstract class OneWayRoad { // OSM: lanes (only sometimes)
    val wayOne: HalfRoad
    val centerLine: LineString
  }

  class OneWayFreeway {
    val freewayLanes = List[Lane]()
  }

  abstract class Freeway {
    val wayOne: OneWayFreeway
    val wayTwo: OneWayFreeway
  }

  class ParkingSpace {
    val width = 8.0
    val length = 16.0
    val x = Random.nextInt
    val y = Random.nextInt
    val nullCar: Car = null
    var occupant = nullCar
    def view {
      print("{\"type\"=\"parkingSpace\", \"x\":\"" + x + "\", y:\"" + y + "\"}");
    }
  }

}
