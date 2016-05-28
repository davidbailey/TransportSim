# install http://wiki.openstreetmap.org/wiki/Open_Source_Routing_Machine
import pandas
import os
import requests
from tqdm import trange

trips = pandas.read_csv(os.path.expanduser('~/TransportSim/var/trips.csv'))
routes = []

#for i in trange(len(trips) - 1):
for i in trange((len(trips) - 1)/10000):
  #r = requests.get('http://localhost:5000/viaroute?loc=' + str(trips.iloc[i].hY) + ',' + str(trips.iloc[i].hX) + '&loc=' + str(trips.iloc[i].wY) + ',' + str(trips.iloc[i].wX))
  r = requests.get('http://localhost:5000/viaroute?loc=' + str(trips.iloc[10000*i].hY) + ',' + str(trips.iloc[10000*i].hX) + '&loc=' + str(trips.iloc[10000*i].wY) + ',' + str(trips.iloc[10000*i].wX))
  try: routes.append(r.json()['route_geometry'])
  except: print "AppendError"

f = open(os.path.expanduser('~/TransportSim/var/routes.polystrings'), 'w')
for route in routes:
  f.write(route + '\n')

f.close()
