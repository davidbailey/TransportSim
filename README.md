# TransportSim: A transportation simulation. #

[![Build Status](https://travis-ci.org/davidbailey/TransportSim.svg?branch=master)](https://travis-ci.org/davidbailey/TransportSim)
[![Coverage Status](https://coveralls.io/repos/davidbailey/TransportSim/badge.svg?branch=master&service=github)](https://coveralls.io/github/davidbailey/TransportSim?branch=master)

## Architecture ##

1. python/peopleModel   - generates people (origins and destinations)
2. python/routeModel    - generates a route for each person (polylines)
3. scala/simulation     - simulates people (polylines) moving; sends views to kafka 
4. python/view          - view the models and simulations from kafka (openlayers + bottle REST API)

## Documentation ##
Time is always in seconds.
Distances have their own class and can be feet or meters.

## Install ##

```
git clone https://github.com/davidbailey/TransportSim.git

# osrm-backend
brew install boost git cmake libzip libstxxl libxml2 lua51 luajit luabind tbb
git clone https://github.com/Project-OSRM/osrm-backend.git
cd osrm-backend
mkdir -p build
cd build
cmake ..
make

wget http://download.geofabrik.de/north-america/us/california-latest.osm.pbf
./osrm-extract bremen-latest.osm.pbf
./osrm-prepare bremen-latest.osrm
./osrm-routed bremen-latest.osrm


# kafka
brew install kafka
zookeeper-server-start.sh /usr/local/etc/zookeeper/zoo.cfg
kafka-server-start.sh /usr/local/etc/kafka/server.properties
kafka-topics.sh  --zookeeper localhost:2181 --create --topic people --partitions 1 --replication-factor 1
kafka-topics.sh  --zookeeper localhost:2181 --create --topic cars --partitions 1 --replication-factor 1
kafka-topics.sh  --zookeeper localhost:2181 --create --topic bicycles --partitions 1 --replication-factor 1

# TransportSim
cd TransportSim
sbt compile
sbt run
python src/main/python/view.py
Launch a browser and open [http://localhost:8080/](http://localhost:8080/).
```

