import scala.util.Random
import scala.collection.mutable.ListBuffer
import Models._ //:load models.scala
import Polyline.decode //:load Polyline.scala
import io.plasmap.parser.OsmParser
//import org.apache.spark.SparkContext
//import org.apache.spark.SparkContext._
//import org.apache.spark.SparkConf

// read the network from https://s3.amazonaws.com/metro-extracts.mapzen.com/los-angeles_california.osm.bz2
// with https://github.com/plasmap/geow

//Car/Bike/Ped Route - drive/ride/walk until you hit an intersection, maybe change lanes. at intersection stopLight, stopSign, or go: stright, left, right. Repeat.
//Freeway Route - enter, drive until you exit.

object Main {
  val routesFileName = System.getProperty("user.home") + "/Desktop/maps/routes.polylines"
  val routes = scala.io.Source.fromFile(routesFileName).getLines.toList
  //val sparkRoutes = sc.textFile(routesFileName)
  //val routesDecoded = routes.map(decode)
  val routesDecoded = routes.map(Polyline.decode)

  var mutablePeople = new ListBuffer[Models.Person]
  var mutableCars = new ListBuffer[Models.Car]
  var mutableBicycles = new ListBuffer[Models.Bicycle]
 
  for ( r <- routesDecoded ) {
    val p = new Models.Person(r)
    mutablePeople += p
    // Replace WalkCarBike with Safety, Time, Comfort, and Cost Choice based on the Person
    val WalkCarBike = Random.nextInt(100)
    if (WalkCarBike <= 80) {
      val c = new Models.Car(p.route{0})
      p.vehicle = Some(c)
      c.driver = Some(p)
      mutableCars += c
    }
    if (80 < WalkCarBike && WalkCarBike < 83) {
      val b = new Models.Bicycle(p.route{0})
      p.vehicle = Some(b)
      b.driver = Some(p)
      mutableBicycles += b
    }
  }

  val People = mutablePeople.toList
  val Cars = mutableCars.toList
  val Bicycles = mutableBicycles.toList

  for (a <- 1 to 10) {
    println("\n\nRound " + a + "\n")
    People.map(p => p.view)
    People.map(p => p.transport)
    Cars.map(c => c.view)
    Bicycles.map(b => b.view)
    //Thread.sleep(1000)
  }
}
