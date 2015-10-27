# install http://wiki.openstreetmap.org/wiki/Open_Source_Routing_Machine
import pandas
import os
import requests

trips = pandas.read_csv(os.path.expanduser('~/Desktop/maps/trips.csv'))
routes = []

for name, trip in trips.iterrows():
  r = requests.get('http://localhost:5000/viaroute?loc=' + str(trip.hY) + ',' + str(trip.hX) + '&loc=' + str(trip.wY) + ',' + str(trip.wX))
  routes.append(r.json()['route_geometry'])
