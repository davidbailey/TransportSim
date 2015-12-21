from os.path import expanduser
from bottle import route, run, template
from kafka import KafkaConsumer
import json

carsConsumer = KafkaConsumer('cars', group_id='group_id', bootstrap_servers=['localhost:9092'])
previousCarView = '{}'

@route('/')
def index():
  f = open(expanduser('~/TransportSim/python/index.tpl'), 'r')
  return f.read()
  f.close()

@route('/api/getCars')
def getCars():
  global previousCarView
  try:
    carView = list(carsConsumer.fetch_messages())[-1].value
    previousCarView = carView
  except:
    carView = previousCarView
  print carView
  return json.loads(carView)

run(host='localhost', port=8080)
