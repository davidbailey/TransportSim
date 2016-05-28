<!doctype html>
<html lang="en">
  <head>
    <title>TransportSim</title>
    <script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=fetch"></script>
  </head>
  <body>
    <h1>Welcome to TransportSim.</h1>
    <input type="submit" value="Start Simulation" id="submit" onclick="startsimulation()"></input>
    <script type="text/javascript">
      var startsimulation = function() {
        document.getElementById("submit").style.display = 'none';
        fetch('/startsimulation');
        setInterval(refresh,500);
      }
      var refresh = function() {
	fetch('api/getPeople').then(function(response) {
          return response.json();
          }).then(function(json) {
	  if (json.people.length) {
	    window.open("/simulation","_self");
	  }
	});
      }
    </script>
  </body>
</html>
