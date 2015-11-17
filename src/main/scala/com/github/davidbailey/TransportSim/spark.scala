import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

val conf = new SparkConf().setAppName("TransportSim")
val sc = new SparkContext(conf)

val p1 = new Person(RandomLineString)
val p2 = new Person(RandomLineString)
val p3 = new Person(RandomLineString)
val People = Array(p1,p2,p3)
val sparkPeople = sc.parallelize(People)

val b1 = new Bicycle
val b2 = new Bicycle
val b3 = new Bicycle

b1.driver = Some(p1)
b2.driver = Some(p2)
b3.driver = Some(p3)

val Bicycles = Array(b1,b2,b3)
val sparkBicycles = sc.parallelize(Bicycles)

def findBicycleMap(b: Bicycle, p: Person) = {
  if (b.driver.get == p) { b } 
  else { None }
}

def findReduce(a1: Any, a2: Any) = a1 match {
  case None => a2
  case _ => a1
}

def findBicycle(b: Array[Bicycle], p: Person): Any = {
  b.map( b => findBicycleMap(b,p)).reduceLeft(findReduce)
}

findBicycle(Bicycles,p1)
sparkBicycles.map( b => findBicycleMap(b,p1)).reduce(findReduce)

val find = Bicycles.filter(b => b.driver.get == p1)
val sparkfind = sparkBicycles.filter(b => b.driver.get == p1).collect()
val sparkfind = sparkBicycles.filter(b => true).collect()
sparkBicycles.cache()

/*
def findBicycle (b: Array[Bicycle], p: Person) = {
  b.filter { case b => b.driver.get == p }  
}

def findBicycleMap (b: Bicycle, p: Person) = b.driver.get match{
  case `p` => b
  case _ => None 
}

sparkBicycles.map( b => b match { case b => b.driver.get == p })

def findBicycleMap(b: Bicycle, p: Person) = {
  if (b.driver.filter(_ == p) == p) { b} 
  else { None }
}

def findBicycleMap(b: Option[Bicycle], p: Person) = b match {
  case Some(bicycle) if bicycle.driver == p1 => b
  case _ => None
}

def findBicycleMap(b: Bicycle, p: Person): Any = b.driver match {
  case `p` => b
  case _ => null
}

def findAnyReduce(a1: Any, a2: Any): Any = a1 match {
  case null => a2
  case _ => a1
}

Bicycles.map( b => findBicycleMap(b,p1)).reduceLeft(findAnyReduce).asInstanceOf[Bicycle].driver
*/
