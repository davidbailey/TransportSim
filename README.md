TransportSim: A transportation simulation.

[![Build Status](https://travis-ci.org/davidbailey/TransportSim.svg?branch=master)](https://travis-ci.org/davidbailey/TransportSim)
[![Coverage Status](https://coveralls.io/repos/davidbailey/TransportSim/badge.svg?branch=master&service=github)](https://coveralls.io/github/davidbailey/TransportSim?branch=master)

1. python/peopleModel   - generates people (origins and destinations)
2. python/routeModel    - generates a route for each person (polylines)
3. scala/simulation     - simulates all the people moving; sends views to queue as producer 
4. http://localhost     - view the models and simulations from queue (openlayers + scalatra REST API)

Documentation:
Time is always in seconds.
Distances have their own class and can be feet or meters.

# TransportSim #

## Build & Run ##

```sh
$ cd TransportSim
$ ./sbt
> container:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.
