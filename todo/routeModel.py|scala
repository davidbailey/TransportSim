# install http://wiki.openstreetmap.org/wiki/Open_Source_Routing_Machine
import pandas
import os
import requests

trips = pandas.read_csv(os.path.expanduser('~/Desktop/maps/trips.csv'))
routes = []

for name, trip in trips.iterrows():
  r = requests.get('http://localhost:5000/viaroute?loc=' + trip.hY + ',' + trip.hX + '&loc=' + trip.wY + ',' + trip.wX)
  routes.append(r.json()['route_geometry'])
