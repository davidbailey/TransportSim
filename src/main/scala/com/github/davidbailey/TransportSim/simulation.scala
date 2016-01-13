import scala.util.Random
import scala.collection.mutable.ListBuffer
import Models._ //:load models.scala
import Polyline.decode //:load Polyline.scala
import io.plasmap.parser.OsmParser // https://github.com/plasmap/geow
import org.apache.spark.{SparkContext, SparkConf}
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord} // http://kafka.apache.org/090/javadoc/org/apache/kafka/clients/producer/KafkaProducer.html

object Main extends App {
  val beginDepartures = 21600
  val endDepartures = 43200
  val mapFileName = System.getProperty("user.home") + "/TransportSim/var/los-angeles_california.osm.bz2"
  val parser = OsmParser(mapFileName) // https://s3.amazonaws.com/metro-extracts.mapzen.com/los-angeles_california.osm.bz2
  val footRoutesFileName = System.getProperty("user.home") + "/TransportSim/var/foot/routes.polystrings-1700"
  val footRoutes = scala.io.Source.fromFile(footRoutesFileName).getLines.toList
  val bicycleRoutesFileName = System.getProperty("user.home") + "/TransportSim/var/bicycle/routes.polystrings-1700"
  val bicycleRoutes = scala.io.Source.fromFile(bicycleRoutesFileName).getLines.toList
  val carRoutesFileName = System.getProperty("user.home") + "/TransportSim/var/car/routes.polystrings-1700"
  val carRoutes = scala.io.Source.fromFile(carRoutesFileName).getLines.toList

  val sc = new SparkContext("local","TransportSim")

  val sparkFootRoutes = sc.textFile(footRoutesFileName)
  val sparkBicycleRoutes = sc.textFile(bicycleRoutesFileName)
  val sparkCarRoutes = sc.textFile(carRoutesFileName)

  var mutablePeople = new ListBuffer[Models.Person]
  var mutableCars = new ListBuffer[Models.Car]
  var mutableBicycles = new ListBuffer[Models.Bicycle]
 
  for ( a <- 0 to footRoutes.length - 1 ) {
    // Create alternative to WalkCarBike with Safety, Time, Comfort, and Cost Choice based on the Person
    val WalkCarBike = Random.nextInt(100)
    if (WalkCarBike <= 80) {
      val departureTime = Random.nextInt(endDepartures-beginDepartures) + beginDepartures
      val p = new Models.Person(Polyline.decode(carRoutes{a}),departureTime)
      mutablePeople += p
      val c = new Models.Car(p.route{0})
      p.vehicle = Some(c)
      c.driver = Some(p)
      mutableCars += c
    }
    else if (80 < WalkCarBike && WalkCarBike < 83) {
      val departureTime = Random.nextInt(endDepartures-beginDepartures) + beginDepartures
      val p = new Models.Person(Polyline.decode(bicycleRoutes{a}),departureTime)
      mutablePeople += p
      val b = new Models.Bicycle(p.route{0})
      p.vehicle = Some(b)
      b.driver = Some(p)
      mutableBicycles += b
    }
    else {
      val departureTime = Random.nextInt(endDepartures-beginDepartures) + beginDepartures
      val p = new Models.Person(Polyline.decode(footRoutes{a}),departureTime)
      mutablePeople += p
    }
  }

  val People = mutablePeople.toList
  val Cars = mutableCars.toList
  val Bicycles = mutableBicycles.toList

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("producer.type", "async") // not sure if this helps issue of slow producing after ~138 rounds

  val producer = new KafkaProducer[AnyRef, AnyRef](props)
  
  for (a <- 1 to 100000) {
    println("\n\nRound " + a + "\n")
    //val mb = 1024*1024
    //val runtime = Runtime.getRuntime
    //println("** Used Memory:  " + (runtime.totalMemory - runtime.freeMemory) / mb)
    //println("** Free Memory:  " + runtime.freeMemory / mb)
    //println("** Total Memory: " + runtime.totalMemory / mb)
    //println("** Max Memory:   " + runtime.maxMemory / mb)
    People.map(p => p.transport)
    val peopleView = People.map(p => p.view).reduce((a, b) => a + "," + b)
    //val carView = Cars.map(c => c.view).reduce((a, b) => a + "," + b)
    //val bicycleView = Bicycles.map(b => b.view).reduce((a, b) => a + "," + b)
    //println(carView)
    producer.send(new ProducerRecord("people", a.toString, "{\"people\": [" + peopleView + "]}"))
    //producer.send(new ProducerRecord("cars", a.toString, "{\"cars\": [" + carView + "]}"))
    //producer.send(new ProducerRecord("bicycles", a.toString, "{\"bicycles\": [" + carView + "]}"))
    Thread.sleep(500)
  }
  producer.close()
}
