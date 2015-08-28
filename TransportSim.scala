import scala.util.Random

object Person {
  var position = (Random.nextInt,Random.nextInt)
  def Transport: Int = 0
  ModeChoice = Safety, Speed, Comfort, Cost
    BicycleInBikeLane = 90,speed,90
    BicycleInProtectedBikeLane = 99,speed,90
    Bus = 100, speed, 90
    Car = 90, speed, 90
}
#http://www.scala-lang.org/api/current/index.html#scala.collection.mutable.LazyBuilder
val P1 = Person
val P2 = Person

val People = List(P1,P2)

People.map(Transport)
