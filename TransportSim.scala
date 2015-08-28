import scala.util.Random
import scala.collection.mutable.ListBuffer

class Person {
  var position = (Random.nextInt,Random.nextInt)
}

var People = new ListBuffer[Person]
for( p <- 0 to 255){
  var P = new Person
  People += P
}

def Transport(p: Person) = p

while(true){
  People.map(Transport)
}


#routing engine
  ModeChoice = Safety, Speed, Comfort, Cost
    BicycleInBikeLane = 90,speed,90
    BicycleInProtectedBikeLane = 99,speed,90
    Bus = 100, speed, 90
    Car = 90, speed, 90
