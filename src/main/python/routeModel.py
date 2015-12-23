# install http://wiki.openstreetmap.org/wiki/Open_Source_Routing_Machine
import pandas
import os
import requests

trips = pandas.read_csv(os.path.expanduser('~/TransportSim/var/trips.csv'))
routes = []

for name, trip in trips.iterrows():
  r = requests.get('http://localhost:5000/viaroute?loc=' + str(trip.hY) + ',' + str(trip.hX) + '&loc=' + str(trip.wY) + ',' + str(trip.wX))
  try: routes.append(r.json()['route_geometry'])
  except: print "KeyError"

f = open(os.path.expanduser('~/TransportSim/var/routes.polystrings'), 'w')
for route in routes:
  f.write(route + '\n')

f.close()
