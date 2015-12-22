# tract files from http://www.census.gov/cgi-bin/geo/shapefiles2010/file-download
# ca_od_2013 files from http://lehd.ces.census.gov/data/lodes/LODES7/
import os
import geopandas
import pandas
from random import uniform
from shapely.geometry import box, Point

tractFile = os.path.expanduser('~/TransportSim/var/tl_2010_06_tabblock10.zip')
tracts = geopandas.GeoDataFrame.from_file('/', vfs = 'zip://' + tractFile)

xmax, xmin, ymax, ymin = (-119.97, -116.80, 34.80, 33.33)
boundingBox = box(xmin, ymin, xmax, ymax)

tractsInBox = []
for name, tract in tracts.iterrows():
  if tract['geometry'].within(boundingBox):
    tractsInBox.append(tract)

tracts = geopandas.GeoDataFrame(tractsInBox)

ca_od_main_2013s = []
for i in range(1,6):
  ca_od_main_i_2013 = pandas.DataFrame.from_csv(os.path.expanduser('~/TransportSim/var/ca_od_2013/ca_od_main_JT0' + str(i) + '_2013.csv'), index_col=False)
  ca_od_main_2013s.append(ca_od_main_i_2013)

ca_od_main_2013 = pandas.concat(ca_od_main_2013s)

fix = lambda x: '0' + unicode(x)

ca_od_main_2013['wGEOID'] = ca_od_main_2013['w_geocode']
ca_od_main_2013['wGEOID'] = ca_od_main_2013['wGEOID'].apply(fix)
ca_od_main_2013['hGEOID'] = ca_od_main_2013['h_geocode']
ca_od_main_2013['hGEOID'] = ca_od_main_2013['hGEOID'].apply(fix)

w_coded = pandas.merge(ca_od_main_2013, tracts, how='inner', left_on='wGEOID', right_on='GEOID10')
wh_coded = pandas.merge(w_coded, tracts, how='inner', left_on='hGEOID', right_on='GEOID10', suffixes=['W','H'])

def randomPointInPolygon(polygon):
  (xmin, ymin, xmax, ymax) = polygon.bounds
  while True:
    x = uniform(xmin,xmax)
    y = uniform(ymin,ymax)
    point = Point(x,y)
    if point.within(polygon):
      return point

trips = []
for name, row in wh_coded.iterrows():
  for i in range(0,row['S000']):
    hPoint = randomPointInPolygon(row.geometryH)
    wPoint = randomPointInPolygon(row.geometryW)
    trips.append([hPoint.y, hPoint.x, wPoint.y, wPoint.x])

pandas.DataFrame(trips, columns = ['hY', 'hX', 'wY', 'wX']).to_csv(os.path.expanduser('~/TransportSim/var/trips.csv'))
