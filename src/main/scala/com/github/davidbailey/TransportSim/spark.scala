import models.Models._

// Bicycle Map

val p1 = new Person
val p2 = new Person
val p3 = new Person
val People = Array(p1,p2,p3)

val b1 = new Bicycle
val b2 = new Bicycle
val b3 = new Bicycle
val Bicycles = Array(b1,b2,b3)

b1.driver = p1
b2.driver = p2
b3.driver = p3

val nullBicycle = new Bicycle

def findBicycleMap(b: Bicycle, p: Person): Bicycle = b.driver match {
  case `p` => b
  case _ => nullBicycle
}

def findBicycleReduce(b1: Bicycle, b2: Bicycle): Bicycle = b1 match {
  case `nullBicycle` => b2
  case _ => b1
}

val sparkBicycles = sc.parallelize(Bicycles)
val sparkPeople = sc.parallelize(People)
sparkBicycles.map( b => findBicycleMap(b,p1)).reduce(findBicycleReduce)

def findBicycle(b: Array[Bicycle], p: Person): Bicycle = {
  b.map( b => findBicycleMap(b,p)).reduceLeft(findBicycleReduce)
}

findBicycle(Bicycles,p1).driver


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
