import scala.util.Random
import scala.collection.mutable.ListBuffer
import Models._ //:load models.scala
import Polyline.decode //:load Polyline.scala
import io.plasmap.parser.OsmParser // https://github.com/plasmap/geow
//import org.apache.spark.{SparkContext, SparkConf}
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord} // http://kafka.apache.org/090/javadoc/org/apache/kafka/clients/producer/KafkaProducer.html


object Main {
  val parser = OsmParser("https://s3.amazonaws.com/metro-extracts.mapzen.com/los-angeles_california.osm.bz2")
  val routesFileName = System.getProperty("user.home") + "/Desktop/maps/routes.polylines"
  val routes = scala.io.Source.fromFile(routesFileName).getLines.toList
  val routesDecoded = routes.map(Polyline.decode)
  //val sparkRoutes = sc.textFile(routesFileName)

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

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
  val producer = new KafkaProducer[AnyRef, AnyRef](props)
  
  for (a <- 1 to 10) {
    println("\n\nRound " + a + "\n")
    People.map(p => p.transport)
    val peopleView = People.map(p => p.view)
    val carView = Cars.map(c => c.view)
    val bicycleView = Bicycles.map(b => b.view)
    producer.send(new ProducerRecord("people", a.toString, peopleView))
    producer.send(new ProducerRecord("cars", a.toString, carView))
    producer.send(new ProducerRecord("bicycles", a.toString, bicycleView))
    //Thread.sleep(1000)
  }
  producer.close()

}
