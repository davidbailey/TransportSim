import numericalunits

class Point(object):
  def __init__(self, point):
    self.x = point[0]
    self.y = point[1]

class Person(object):
  def __init__(self):
    self.inVehicle = False
    self.isDriver = False
    self.arrived = False
    self.travelTime = 0
    self.route = []
    self.currentRouteSegment = 0
    self.centroid = Point((0,0))
    self.width = 1.5 * numericalunits.foot
    self.length = 1.5 * numericalunits.foot
  def view(self):
    print "{\"type\":\"Person\", \"x\":\"" + str(self.centroid.x) + "\", \"y\":\"" + str(self.centroid.y) + "\", \"width\":\"" + str(self.width) + "\", \"length\":\"" + str(self.length) + "\"}"

class Vehicle(object):
  def __init__(self):
    self.driver = None
    self.passengers = []
  def view(self): 
    print "{\"type\":\"Vehicle\", \"subtype\":\"" + self.subtype + "\", \"x\":\"" + str(self.centroid.x) + "\", \"y\":\"" + str(self.centroid.y) + "\", \"width\":\"" + str(self.width) + "\", \"length\":\"" + str(self.length) + "\"}"

class Bicycle(Vehicle):
  def __init__(self):
    super(Bicycle, self).__init__()
    self.subtype = "Bicycle"
    self.maxPassengers = 1
    self.width = 2 * numericalunits.foot
    self.length = 6 * numericalunits.foot
    self.centroid = Point((0,0))

p1 = Person()
p2 = Person()
p3 = Person()

b1 = Bicycle()
b2 = Bicycle()
b3 = Bicycle()

b1.driver = p1
b2.driver = p2
b3.driver = p3

Bicycles = [b1,b2,b3]
#sparkBicycles = sc.parallelize(Bicycles)

def mapBicycle(b,p):
  if (b.driver == p): return b
  else: return None

def reduceBicycle(b1,b2):
  if (b1): return b1
  else: return b2

result = reduce(reduceBicycle,map(lambda x: mapBicycle(x,p2),Bicycles))
#sparkResult = sparkBicycles.map(lambda x: mapBicycle(x,p2)).reduce(reduceBicycle)
