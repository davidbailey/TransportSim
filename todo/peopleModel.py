import os
import io
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



#Residential/Commercial Densities -> Sources + Destinations (People)
#http://onthemap.ces.census.gov/cgi-bin/mobile_report.py
