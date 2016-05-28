# TransportSim: A transportation simulation. #

[![Build Status](https://travis-ci.org/davidbailey/TransportSim.svg?branch=master)](https://travis-ci.org/davidbailey/TransportSim)
[![Coverage Status](https://coveralls.io/repos/davidbailey/TransportSim/badge.svg?branch=master&service=github)](https://coveralls.io/github/davidbailey/TransportSim?branch=master)

![TransportSim](https://raw.githubusercontent.com/davidbailey/TransportSim/master/TransportSim.gif "TransportSim")

## Architecture ##

1. python/peopleModel   - generates people (origins and destinations)
2. python/routeModel    - generates a route for each person (polylines)
3. python/simulation     - simulates people (polylines) moving; sends views to redis
4. python/view          - view the models and simulations from redis (openlayers + bottle REST API)

## Documentation ##
Time is always in seconds.
Distances have their own class and can be feet or meters.

## Install ##

```
git clone https://github.com/davidbailey/TransportSim.git

pip install pandas geopandas shapely requests bottle redis

# osrm-backend
brew install boost git cmake libzip libstxxl libxml2 lua51 luajit luabind tbb
git clone https://github.com/Project-OSRM/osrm-backend.git
cd osrm-backend
mkdir -p build
cd build
cmake ..
make

wget http://download.geofabrik.de/north-america/us/california-latest.osm.pbf
./osrm-extract california-latest.osm.pbf
./osrm-prepare california-latest.osrm
./osrm-routed california-latest.osrm

# download osm
cd var
wget https://s3.amazonaws.com/metro-extracts.mapzen.com/los-angeles_california.osm.bz2
bunzip2 los-angeles_california.osm.bz2

# redis 
brew install redis 
redis-server

# TransportSim
cd TransportSim
python src/view.py
python src/simulation.py
Launch a browser and open http://localhost:8080/
```
