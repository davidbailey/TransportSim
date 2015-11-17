import scala.util.Random
import scala.collection.mutable.ListBuffer
//import io.plasmap.parser.OsmParser
import models.Models._
import scala.io.Source
//import org.apache.spark.SparkContext
//import org.apache.spark.SparkContext._
//import org.apache.spark.SparkConf
import Polyline._ //:load Polyline.scala

// read the network from https://s3.amazonaws.com/metro-extracts.mapzen.com/los-angeles_california.osm.bz2
// with https://github.com/plasmap/geow
// read the routes from routeModel.py with https://github.com/trifectalabs/polyline-scala

//Car/Bike/Ped Route - drive/ride/walk until you hit an intersection, maybe change lanes. at intersection stopLight, stopSign, or go: stright, left, right. Repeat.
//Freeway Route - enter, drive until you exit.

object Main {
  val routesFileName = System.getProperty("user.home") + "/Desktop/maps/routes.polylines"
  val routes = io.Source.fromFile(routesFileName).getLines.toList
  val sparkRoutes = sc.textFile(routesFileName)
  val routesDecoded = routes.map(Polyline.decode)

  var mutablePeople = new ListBuffer[Person]
  var mutableCars = new ListBuffer[Car]
  for ( r <- routesDecoded ) {
    val p = new Person(LineString(r))
    val c = new Car
    c.driver = Some(p)
    mutablePeople += p
    mutableCars += c
  }

  val People = mutablePeople.toList
  val Cars = mutableCars.toList
  

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
