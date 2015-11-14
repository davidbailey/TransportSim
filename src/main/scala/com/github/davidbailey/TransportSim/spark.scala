import models.Models._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

val conf = new SparkConf().setMaster("local[2]").setAppName("CountingSheep").set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
val sc = new SparkContext(conf)

// Bicycle Map

val p1 = new Person
val p2 = new Person
val p3 = new Person
val People = Array(p1,p2,p3)
val sparkPeople = sc.parallelize(People)

val b0 = new Bicycle
val b1 = new Bicycle
val b2 = new Bicycle
val b3 = new Bicycle
val Bicycles = Array(b1,b2,b3)
val sparkBicycles = sc.parallelize(Bicycles)

b1.driver = Some(p1)
b2.driver = Some(p2)
b3.driver = Some(p3)

def findBicycleMap(b: Bicycle, p: Person): Bicycle = b.driver match {
  case `p` => b
  case _ => b4
}

def findBicycleReduce(b1: Bicycle, b2: Bicycle): Bicycle = b1 match {
  case `b4` => b2
  case _ => b1
}

def findBicycle(b: Array[Bicycle], p: Person): Bicycle = {
  b.map( b => findBicycleMap(b,p)).reduceLeft(findBicycleReduce)
}

findBicycle(Bicycles,p1).driver
sparkBicycles.map( b => findBicycleMap(b,p1)).reduce(findBicycleReduce)

// Any Map
def findBicycleMap(b: Bicycle, p: Person): Any = b.driver match {
  case `p` => b
  case _ => null
}

def findAnyReduce(a1: Any, a2: Any): Any = a1 match {
  case null => a2
  case _ => a1
}

Bicycles.map( b => findBicycleMap(b,p1)).reduceLeft(findAnyReduce).asInstanceOf[Bicycle].driver
