import scala.util.Random
import scala.collection.mutable.ListBuffer

//Car/Bike/Ped Route - drive/ride/walk until you hit an intersection, maybe change lanes. at intersection stopLight, stopSign, or go: stright, left, right. Repeat.
//Freeway Route - enter, drive until you exit.
/*
object WaySegment1 {
  val NorthLanes(Sidewalk, ParkingLane, BikeLane, CarLane, CarLane)
  val SouthLanes(Sidewalk, ParkingLane, BikeLane, CarLane, CarLane)
}

object WaySegment2 {
  val NorthLanes(FreewayLane, FreewayLane, FreewayLane, FreewayLane)
  val SouthLanes(FreewayLane, FreewayLane, FreewayLane, FreewayLane)
}
*/

object Main {

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
}

val nullPerson: Person = null
val p1 = new Person
p1.getClass.getName

abstract class Vehicle {
  def driver: Person
  def passengers = ListBuffer[Person]()
  def maxPassengers: Int
  def width: Double
  def length: Double
}

class Bicycle extends Vehicle {
  var driver = nullPerson
  val maxPassengers = 1
  val width = 2.0 + Random.nextDouble
  val length = 6.0 + Random.nextDouble
}

class Car extends Vehicle {
  var driver = nullPerson
  val maxPassengers = 4
  val width = 6.0 + Random.nextDouble
  val length = 12.0 + Random.nextDouble
}

val nullCar: Car = null

abstract class Bus extends Vehicle {
  var driver = nullPerson
  val maxPassengers = 84
  val width = 8.0
  val length = 40.0
}

abstract class Train extends Vehicle {
}

abstract class LightRail extends Train {
  val maxPassengers = 220
}

abstract class HeavyRail extends Train {
  val maxPassengers = 800
}

class parkingSpace {
  val width = 8.0
  val length = 16.0
  val x = Random.nextInt
  val y = Random.nextInt
  var occupant = nullCar
}

  def generatePeople {
    var mutablePeople = new ListBuffer[Person]
    for( p <- 0 to 17000000){
      var P = new Person
      mutablePeople += P
    }  
    mutablePeople.toList
  }
  val People = generatePeople
  

  def Transport(p: Person) {
    p.x = p.x + Random.nextInt
    p.y = p.y + Random.nextInt
  }

  def ViewPosition(p: Person) {
    println(p.x,p.y)
  }

  for (a <- 1 to 10) {
    println("Round " + a)
   // People.map(ViewPosition)
   // People.map(Transport)
  }
}

/*
#routing engine
  ModeChoice = Safety, Speed, Comfort, Cost
    BicycleInBikeLane = 90,speed,90
    BicycleInProtectedBikeLane = 99,speed,90
    Bus = 100, speed, 90
    Car = 90, speed, 90
*/
