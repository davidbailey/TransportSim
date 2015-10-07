import scala.util.Random
import scala.collection.mutable.ListBuffer
package models;

object Models {
  class Person { // basic agent
    var inVehicle = false
    var isDriver = false
    var arrived = false
    var travelTime: Int = 0
    var route = List()
    var currentRouteSegment = 0
    var x = Random.nextInt
    var y = Random.nextInt
    var width = 1.5 + Random.nextDouble
    var length = 1 + Random.nextDouble
    def view {
      print("{\"type\"=\"person\", \"x\":\"" + x + "\", y:\"" + y + "\"}");
    }
  }

  val nullPerson: Person = null
  val p1 = new Person
  p1.view
  p1.getClass.getName

  abstract class Vehicle {
    def driver: Person
    def subtype: String
    def passengers = ListBuffer[Person]()
    def maxPassengers: Int
    def width: Double
    def length: Double
    var x = Random.nextInt
    var y = Random.nextInt
    def view {
      print("{\"type\":\"Vehicle\", \"subtype:\"" + this.subtype + "\", \"x\":\"" + x + "\", y:\"" + y + "\"}");
    }
  }

  class Bicycle extends Vehicle {
    val subtype = "Bicycle"
    var driver = nullPerson
    val maxPassengers = 1
    val width = 2.0 + Random.nextDouble
    val length = 6.0 + Random.nextDouble
  }

  val b1 = new Bicycle
  b1.view

  class Car extends Vehicle {
    val subtype = "Car"
    var driver = nullPerson
    val maxPassengers = 4
    val width = 6.0 + Random.nextDouble
    val length = 12.0 + Random.nextDouble
  }

  val nullCar: Car = null

  abstract class Bus extends Vehicle {
    val subtype = "Bus"
    var driver = nullPerson
    val maxPassengers = 84
    val width = 8.0
    val length = 40.0
  }

  abstract class LightRail extends Vehicle {
    val subtype = "LightRail"
    val maxPassengers = 220
  }

  abstract class HeavyRail extends Vehicle {
    val subtype = "HeavyRail"
    val maxPassengers = 800
  }

  class parkingSpace {
    val width = 8.0
    val length = 16.0
    val x = Random.nextInt
    val y = Random.nextInt
    var occupant = nullCar
    def view {
      print("{\"type\"=\"parkingSpace\", \"x\":\"" + x + "\", y:\"" + y + "\"}");
    }
  }

  val ps1 = new parkingSpace
}
