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
      var stopStyleFunctionCreator = function() {
	return function(feature, resolution) {
	  var style =  new ol.style.Style({ 
	    image: new ol.style.Circle({ radius: 5, fill: new ol.style.Fill({ color: 'rgba(255, 0, 0, 1)' }) }) 
	  }) 
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
      var carsSource = new ol.source.Vector();
      fetch('api/getCars').then(function(response) {
	return response.json();
      }).then(function(json) {
	var format = new ol.format.GeoJSON();
	var feature = format.readFeature(json, {featureProjection: 'EPSG:3857'});
	carsSource.addFeature(feature);
      });
      var vectorLayer = new ol.layer.Vector({
	source: carsSource,
	style: stopStyleFunctionCreator()
      });
      map.addLayer(vectorLayer);
      }
    </script>
  </body>
</html>
