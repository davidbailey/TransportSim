# tract files from TIGER # todo switch to 2010 tracts
# ca_od_2013 files from http://lehd.ces.census.gov/data/lodes/LODES7/

import os
import io
import geopandas
import pandas
from shapely.geometry import Polygon

tractDirectory = os.path.expanduser('~/Desktop/maps/TRACT/')
tractFiles = os.listdir(tractDirectory)

tractList = []
for tractFile in tractFiles:
  tractList.append(geopandas.GeoDataFrame.from_file('/', vfs = 'zip://' + tractDirectory + tractFile))

xmax, xmin, ymax, ymin = (-119.97, -116.80, 34.80, 33.33)
boundingBox = Polygon([(xmax, ymax), (xmin, ymax), (xmin, ymin), (xmax, ymin)])

tractsInBox = []
for tracts in tractList:
  for name, tract in tracts.iterrows():
    if tract['geometry'].within(boundingBox):
      tractsInBox.append(tract)

ca_xwalk = pandas.DataFrame.from_csv(os.path.expanduser('~/Desktop/maps/ca_od_2013/ca_xwalk.csv'))

trips = []
for tract in tractsInBox
  trips.append(getOriginAndDestination(tract.NAME))
  
# connect to osm and route ^ (this is route.py|scala)
