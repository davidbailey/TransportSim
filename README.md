TransportSim: A transportation simulation.

[![Build Status](https://travis-ci.org/davidbailey/TransportSim.svg?branch=master)](https://travis-ci.org/davidbailey/TransportSim)
[![Coverage Status](https://coveralls.io/repos/davidbailey/TransportSim/badge.svg?branch=master&service=github)](https://coveralls.io/github/davidbailey/TransportSim?branch=master)

1. python/peopleModel   - generates people (origins and destinations)
2. python/routeModel    - generates a route for each person (polylines)
3. scala/simulation     - simulates all the people moving
4. http://localhost     - view the models and simulations (openlayers + scalatra REST API)

Scala REPL Use:
cd src/main/scala
scala
:load models.scala

Documentation:
Time is always in seconds.
Distances have their own class and can be feet or meters.
