import scala.util.Random
import scala.collection.mutable.ListBuffer

class Person {
  var arrived = false
  var travelTime = 0
  var route = List()
  var currentRouteSegment = 0
  var x = Random.nextInt
  var y = Random.nextInt
  var width = 2
  var length = 2
}

def generatePeople {
  var mutablePeople = new ListBuffer[Person]
  for( p <- 0 to 255){
    var P = new Person
    mutablePeople += P
  }  
  mutablePeople.toList
}
val People = generatePeople

def Transport(p: Person) {
  p.x = p.x + 1
  p.y = p.y + 1
}

def ViewPosition(p: Person) {
  println(p.x,p.y)
}

for (a <- 1 to 10) {
  println("Round " + a)
  People.map(ViewPosition)
  People.map(Transport)
}


#routing engine
  ModeChoice = Safety, Speed, Comfort, Cost
    BicycleInBikeLane = 90,speed,90
    BicycleInProtectedBikeLane = 99,speed,90
    Bus = 100, speed, 90
    Car = 90, speed, 90
