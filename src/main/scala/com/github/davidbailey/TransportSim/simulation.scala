import scala.util.Random
import scala.collection.mutable.ListBuffer
import models.Models._
import Polyline._
//import io.plasmap.parser.OsmParser
//import org.apache.spark.SparkContext
//import org.apache.spark.SparkContext._
//import org.apache.spark.SparkConf

// read the network from https://s3.amazonaws.com/metro-extracts.mapzen.com/los-angeles_california.osm.bz2
// with https://github.com/plasmap/geow

//Car/Bike/Ped Route - drive/ride/walk until you hit an intersection, maybe change lanes. at intersection stopLight, stopSign, or go: stright, left, right. Repeat.
//Freeway Route - enter, drive until you exit.

object Main {
  val routesFileName = System.getProperty("user.home") + "/Desktop/maps/routes.polylines"
  val routes = io.Source.fromFile(routesFileName).getLines.toList
  val sparkRoutes = sc.textFile(routesFileName)
  val routesDecoded = routes.map(Polyline.decode)

/*
#routing engine
  ModeChoice = Safety, Speed, Comfort, Cost
    BicycleInBikeLane = 90,speed,90
    BicycleInProtectedBikeLane = 99,speed,90
    Bus = 100, speed, 90
    Car = 90, speed, 90
*/

  var mutablePeople = new ListBuffer[Person]
  var mutableCars = new ListBuffer[Car]
  var mutableBicycles = new ListBuffer[Bicycle]
  for ( r <- routesDecoded ) {
    val p = new Person(r)
    mutablePeople += p
      val c = new Car
      c.driver = Some(p)
      mutableCars += c
      val b = new Bicycle 
      b.driver = Some(p)
      mutableBicycles += b
  }

  val People = mutablePeople.toList
  val Cars = mutableCars.toList
  val Bicycles = mutableBicycles.toList

  for (a <- 1 to 10) {
    println("Round " + a)
    People.map(p => p.view)
    People.map(p => p.transport)
  }
}
