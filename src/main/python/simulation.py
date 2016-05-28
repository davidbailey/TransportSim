import models
from os.path import expanduser
from polyline import decode
from random import randint
#from kafka import KafkaProducer
import json
import redis

beginDepartures = 21600
endDepartures = 43200

footRoutes = []
with open(expanduser('~/TransportSim/var/foot/routes.polystrings-1700'),'r') as footRoutesFile:
  for route in footRoutesFile:
    footRoutes.append(decode(route.rstrip()))

bicycleRoutes = []
with open(expanduser('~/TransportSim/var/bicycle/routes.polystrings-1700'),'r') as bicycleRoutesFile:
  for route in bicycleRoutesFile:
    bicycleRoutes.append(decode(route.rstrip()))

carRoutes = []
with open(expanduser('~/TransportSim/var/car/routes.polystrings-1700'),'r') as carRoutesFile:
  for route in carRoutesFile:
    carRoutes.append(decode(route.rstrip()))

People = []
Bicycles = []
Cars = []

for a in range(len(carRoutes)):
  footBicycleCar = randint(1,100) 
  departureTime = randint(beginDepartures,endDepartures)
  if footBicycleCar <= 80:
    p = models.Person(carRoutes[a],departureTime)
    People.append(p)
    c = models.Car()
    p.vehicle = c
    c.driver = p
    Cars.append(c)
  elif 80 < footBicycleCar < 83:
    p = models.Person(bicycleRoutes[a],departureTime)
    People.append(p)
    b = models.Bicycle()
    p.vehicle = b
    b.driver = p
    Bicycles.append(c)
  elif 83 < footBicycleCar:
    p = models.Person(footRoutes[a],departureTime)
    People.append(p)

footRoutes = False
bicycleRoutes = False
carRoutes = False

#producer = KafkaProducer(bootstrap_servers='localhost:9092', value_serializer=lambda v: json.dumps(v).encode('utf-8'))
r = redis.StrictRedis(host='localhost', port=6379, db=0)

for a in range(1000):
  print "Round: " + str(a)
  map(models.Person.transport,filter((lambda x: x.crashed and x.arrived),People))
  peopleView = { "people": map(models.Person.view,People) }
  #producer.send('people', peopleView)
  r.set('people',json.dumps(peopleView))
