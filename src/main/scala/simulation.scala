import scala.util.Random
import scala.collection.mutable.ListBuffer
import models.Models._

// read the network from https://s3.amazonaws.com/metro-extracts.mapzen.com/los-angeles_california.osm.bz2
// with https://github.com/plasmap/geow
// read the routes from routeModel.py with https://github.com/trifectalabs/polyline-scala

val routes = io.Source.fromFile(System.getProperty("user.home") + "/Desktop/maps/routes.polylines").getLines.toList

//Car/Bike/Ped Route - drive/ride/walk until you hit an intersection, maybe change lanes. at intersection stopLight, stopSign, or go: stright, left, right. Repeat.
//Freeway Route - enter, drive until you exit.

object Main {
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
    //p.x = p.x + Random.nextInt
    //p.y = p.y + Random.nextInt
  }

  def ViewPosition(p: Person) {
    p.view
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
