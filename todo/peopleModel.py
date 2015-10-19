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

tracts = geopandas.GeoDataFrame(tractsInBox)

ca_od_main_2013s = []
for i in range(1,6):
  ca_od_main_i_2013 = pandas.DataFrame.from_csv(os.path.expanduser('~/Desktop/maps/ca_od_2013/ca_od_main_JT0' + str(i) + '_2013.csv'), index_col=False)
  ca_od_main_2013s.append(ca_od_main_i_2013)

ca_od_main_2013 = pandas.concat(ca_od_main_2013s)

first11 = lambda x: '0' + unicode(x)[0:10]

ca_od_main_2013['wGEOID'] = ca_od_main_2013['w_geocode']
ca_od_main_2013['wGEOID'] = ca_od_main_2013['wGEOID'].apply(first11)
ca_od_main_2013['hGEOID'] = ca_od_main_2013['h_geocode']
ca_od_main_2013['hGEOID'] = ca_od_main_2013['hGEOID'].apply(first11)

w_coded = pandas.merge(ca_od_main_2013, tracts, how='inner', left_on='wGEOID', right_on='GEOID')
wh_coded = pandas.merge(w_coded, tracts, how='inner', left_on='hGEOID', right_on='GEOID', suffixes=['W','H'])

trips = []
for name, row in wh_coded.iterrows():
  for i in range(0,row['S000'])
    trips.append((Hgeometry,Wgeometry))
