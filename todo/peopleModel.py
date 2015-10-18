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

# ca_xwalk = pandas.DataFrame.from_csv(os.path.expanduser('~/Desktop/maps/ca_od_2013/ca_xwalk.csv'), index_col=False)

ca_od_main_2013s = []
for i in range(1,6):
  ca_od_main_i_2013 = pandas.DataFrame.from_csv(os.path.expanduser('~/Desktop/maps/ca_od_2013/ca_od_main_JT0' + i + '_2013.csv'), index_col=False)
  ca_od_main_2013s.append(ca_od_main_i_2013)
  
ca_od_main_2013 = pandas.concat(ca_od_main_2013s)
# w_coded = pandas.merge(ca_od_main_2013, ca_xwalk, how='inner', left_on='w_geocode', right_on='tabblk2010')
# wh_coded = pandas.merge(w_coded, ca_xwalk, how='inner', left_on='h_geocode', right_on='tabblk2010')

trips = []
for name, row in wh_coded.iterrows():
  for i in range(0,row['S000'])
    trips.append((oGeometry,dGeometry))
  
# connect to osm and route ^ (this is route.py|scala)
