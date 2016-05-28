<!doctype html>
<html lang="en">
  <head>
    <style>
      .map {
        height: 100%;
        width: 100%;
      }
    </style>
    <script src="http://openlayers.org/en/v3.12.1/build/ol-debug.js" type="text/javascript"></script>
    <script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=fetch"></script>
    <title>TransportSim</title>
  </head>
  <body>
    <div id="map" class="map"></div>
    <script type="text/javascript">
      var peopleStyleFunctionCreator = function() {
	return function(feature, resolution) {
            if (feature.getProperties()['arrived'] == "true") {
	      var style =  new ol.style.Style({ 
	        image: new ol.style.Circle({ radius: 5, fill: new ol.style.Fill({ color: 'rgba(0, 0, 0, 1)' }) }) 
	      }) 
            }
            else if (feature.getProperties()['crashed'] == "true") {
	      var style =  new ol.style.Style({ 
	        image: new ol.style.Circle({ radius: 5, fill: new ol.style.Fill({ color: 'rgba(255, 0, 0, 1)' }) }) 
	      }) 
            }
            else {
              switch(feature.getProperties()['vehicle']) {
                case "Person":
	          var style =  new ol.style.Style({ 
	          image: new ol.style.Circle({ radius: 5, fill: new ol.style.Fill({ color: 'rgba(255, 255, 255, 1)' }) }) 
	          }) 
                  break;
                case "Car":
	          var style =  new ol.style.Style({ 
	          image: new ol.style.Circle({ radius: 5, fill: new ol.style.Fill({ color: 'rgba(0, 0, 255, 1)' }) }) 
	          }) 
                  break;
                case "Bicycle":
	          var style =  new ol.style.Style({ 
	          image: new ol.style.Circle({ radius: 5, fill: new ol.style.Fill({ color: 'rgba(0, 255, 0, 1)' }) }) 
	          }) 
                  break;
              }
            }
	return [style];
	}
      }
      var tileLayer = new ol.layer.Tile({
        source: new ol.source.OSM()
      });
      var map = new ol.Map({
        target: 'map',
        layers: [tileLayer],
        view: new ol.View({
          center: ol.proj.transform([-118.385, 34.065], 'EPSG:4326', 'EPSG:3857'),
          zoom: 9
        })
      });
      var peopleSource = new ol.source.Vector();
      var format = new ol.format.GeoJSON();
      fetch('api/getPeople').then(function(response) {
	return response.json();
      }).then(function(json) {
        for (var i = 0; i < json.people.length; i++) {
	      var feature = format.readFeature(json.people[i], {featureProjection: 'EPSG:3857'});
	      peopleSource.addFeature(feature);
        }
      });
      var peopleLayer = new ol.layer.Vector({
	    source: peopleSource,
	    style: peopleStyleFunctionCreator()
      });
      map.addLayer(peopleLayer);
      var refreshPeople = function() {
      fetch('api/getPeople').then(function(response) {
	return response.json();
      }).then(function(json) {
        for (var i = 0; i < json.people.length; i++) {
	      var feature = format.readFeature(json.people[i], {featureProjection: 'EPSG:3857'});
              peopleSource.getFeatureById(feature.getId()).setGeometry(feature.getGeometry())
        }
      });
      }
      setInterval(refreshPeople,500);
    </script>
  </body>
</html>
