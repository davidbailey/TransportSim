from os.path import expanduser
from subprocess import Popen
from bottle import route, run, template
#from kafka import KafkaConsumer
import json
import redis

r = redis.StrictRedis(host='localhost', port=6379, db=0)
#carsConsumer = KafkaConsumer('cars', group_id='kafka-python-default-group', bootstrap_servers=['localhost:9092'])
#peopleConsumer = KafkaConsumer('people', group_id='kafka-python-default-group', bootstrap_servers=['localhost:9092'])
previousCarView = '{}'
previousPeopleView = '{}'

@route('/')
def index():
  f = open(expanduser('~/TransportSim/src/main/python/templates/index.tpl'), 'r')
  return f.read()
  f.close()

@route('/startsimulation')
def startsimulation():
  #Popen(["sbt", "run"])
  return {'started': 'true'}

@route('/simulation')
def simulation():
  f = open(expanduser('~/TransportSim/src/main/python/templates/simulation.tpl'), 'r')
  return f.read()
  f.close()

@route('/api/getCars')
def getCars():
  global previousCarView
#  try:
#    carView = list(carsConsumer.poll())[-1].value
#    previousCarView = carView
#  except:
#    carView = previousCarView
  #print carView
  return json.loads(carView)

@route('/api/getPeople')
def getPeople():
  global previousPeopleView
#  try:
#    peopleView = list(peopleConsumer.poll())[-1].value
#    previousPeopleView = peopleView
#  except:
#    peopleView = previousPeopleView
  #print peopleView
  try:
    peopleView = r.get('people')
  except:
    peopleView = previousPeopleView
  return json.loads(peopleView)

run(host='localhost', port=8080)
