from shapely.geometry import Point
from random import randint
from random import random
from sys import maxint
from math import sqrt
from math import atan
from math import sin
from math import cos

class Person:
  def __init__(self, routeIn, departureTimeIn):
    self.route = routeIn
    self.departureTime = departureTimeIn
    self.id = randint(0,maxint)
    self.width = 1.5 + random()
    self.length = 0.5 + random()
    self.arrived = False
    self.crashed = False
    self.travelTime = 0
    self.speed = 0.0
    self.currentRouteSegment = 0
    self.centroid = self.route[0]
    self.vehicle = False
  def vehicleType(self):
    if self.vehicle: return self.vehicle.subtype
    else: return("Person")
  def view(self):
    return("{\"type\": \"Feature\", \"id\": " + str(self.id) + ", \"geometry\": {\"type\": \"Point\", \"coordinates\": [" + str(self.centroid[1]/10) + "," + str(self.centroid[0]/10) + "]}, \"properties\": {\"arrived\": \"" +  str(self.arrived) + "\", \"crashed\": \"" +  str(self.crashed) + "\", \"vehicle\": \"" + str(self.vehicleType()) + "\"}}") 
# Car/Bike/Ped Route - drive/ride/walk until you hit an intersection, maybe change lanes. at intersection stopLight, stopSign, or go: stright, left, right. Repeat.
# Freeway Route - enter, drive until you exit.
  def transport(self):
    self.travelTime += 1
    nextRouteSegment = self.currentRouteSegment + 1
    xDelta = self.route[nextRouteSegment][0] - self.route[self.currentRouteSegment][0]
    yDelta = self.route[neytRouteSegment][1] - self.route[self.currentRouteSegment][1]
    if sqrt(xDelta**2+yDelta**2) > self.speed: # straightaway logic
      theta = atan(xDelta / yDelta)
      self.centroid = Point(self.centroid[0] + sin(theta) * self.speed, self.centroid[1] + cos(theta) * self.speed)
    else: # intersection logic
      self.currentRouteSegment = nextRouteSegment
      self.centroid = self.route[self.currentRouteSegment]
      if self.currentRouteSegment == len(self.route) - 1:
        self.arrived = True

class Vehicle:
  def __init__(self):
    self.id = randint(0,maxint) 
    self.driver = False
    self.subtype = "Vehicle"
    self.passengers = []
    self.maxPassengers = 0
    self.width = 0.0
    self.length = 0.0
    self.crashed = False
    self.centroid = Point(0,0)
    
class Bicycle(Vehicle):
  def __init__(self):
    self.subtype = "Bicycle"
    self.maxPassengers = 1
    self.width = 2.0 + random()
    self.length = 6.0 + random()
  
class Car(Vehicle):
  def __init__(self):
    self.subtype = "Car"
    self.maxPassengers = 5
    self.width = 6.0 + random()
    self.length = 12.0 + random()
  
class Bus(Vehicle):
  def __init__(self):
    self.subtype = "Bus"
    self.maxPassengers = 84
    self.width = 8.0 + random()
    self.length = 40.0 + random()
  
class LightRail(Vehicle):
  def __init__(self):
    self.subtype = "LightRail"
    self.maxPassengers = 220
  
class HeavyRail(Vehicle):
  def __init__(self):
    self.subtype = "HeavyRail"
    self.maxPassengers = 800
  
class Track:
  def __init__(self):
    self.width = 4.708

class Railway:
  def __init__(self):
    self.tracks = []

class Lane:
  def __init__(self):
    self.width = 0.0

class GeneralLane(Lane):
  def __init__(self):
    self.width = 10.0
    self.allowedVehicles = ["Bicycle,Car,Bus"]

class FreewayLane(Lane):
  def __init__(self):
    self.width = 13.0
    self.allowedVehicles = ["Car,Bus"]

class ParkingLane(Lane):
  def __init__(self):
    self.width = 10.0
    self.allowedVehicles = ["Bicycle,Car"]

class BicycleLane(Lane):
  def __init__(self):
    self.width = 5.0
    self.allowedVehicles = ["Bicycle"]

class BusLane(Lane):
  def __init__(self):
    self.width = 5.0
    self.allowedVehicles = ["Bicycle,Bus"]

class Sidewalk(Lane):
  def __init__(self):
    self.width = 4.0

class Way: # OSM Way
  def __init__(self):
    pass

class HalfRoad:
  def __init__(self):
    self.lanes = []
    self.parkingLane = ParkingLane()
    self.bicycleLane = BicycleLane()
    self.busLane = BusLane()
    self.sidewalk = Sidewalk()

class TwoWayRoad: 
  def __init__(self):
    self.wayOne = HalfRoad()
    self.wayTwo = HalfRoad()
    self.centerLine = []

class OneWayRoad:
  def __init__(self):
    self.wayOne = HalfRoad()
    self.centerLine = []

class HalfFreeway:
  def __init__(self):
    self.freewayLanes = []

class Freeway:
  def __init__(self):
    self.wayOne = HalfFreeway()
    self.wayTwo = HalfFreeway()
    self.centerLine = []
    
class ParkingSpace:
  def __init__(self):
    self.id = randint(0,maxint)
    self.width = 8.0
    self.length = 16.0
    self.centroid = (0.0,0.0)
    self.occupant = False
  def view(self):
    return("{\"type\": \"Feature\", \"id\": " + str(self.id) + ", \"geometry\": {\"type\": \"Point\", \"coordinates\": [" + str(self.centroid[0]) + "," + str(self.centroid[1]) + "]}, \"properties\": {\"vehicle\": \"" + str(self.occupant) + "\"}}")

