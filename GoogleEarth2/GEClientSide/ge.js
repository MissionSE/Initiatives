// TODO: move all global variables into an object
//
// google earth javascript file
// 1) instantiate the google earth plugin and make it visible
// 2) queries database and retrieve kml data.
// 3) pass kml to plugin to be displayed on the globe.

var ge=null; // google earth instance pointer

var default_timeout_value = 1000; // one second

var server_port = '9999';
var url_kml_geo = 'http://localhost:' + server_port + '/example/kml/geo';
var url_kml_trackhistory = 'http://localhost:' + server_port + '/example/kml/trackhistory/2'; // history for track id 2
//var url_kml_trackposition = 'http://localhost:' + server_port + '/example/kml/trackposition';
// Something happened and with /kml in the path trackposition stopped working. Not sure why yet.
var url_kml_trackposition = 'http://localhost:' + server_port + '/example/trackposition';
var url_kml_trackposition_with_labels = 'http://localhost:' + server_port + '/example/trackposition/labels';
var url_find_track = 'http://localhost:' + server_port + '/example/kml/findtrack';
var url_circles = 'http://localhost:' + server_port + '/example/kml/circles'
var url_septarail = 'http://www3.septa.org/hackathon/TrainView/regionalrail.kml';
var url_septabus = 'http://www3.septa.org/transitview/kml/route.kml'; // replace route with bus/trolly number
// external urls
var url_kml_weather = 'http://radar.weather.gov/ridge/kml/animation/N0R/DIX_N0R_loop.kml';
var url_kml_firedata = 'http://activefiremaps.fs.fed.us/data/kml/conus_latest_AFM_bundle.kml';
var url_kml_earthquake = 'http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_month_depth.kml';
var url_kml_airquality = 'http://www.epa.gov/airnow/today/airnow.kml';
var url_kml_weatherstations = 'http://ssc.e-education.psu.edu/get_ssc_in_kml.php?day=0';
var url_kml_parail_active = 'http://www.pasda.psu.edu/pasda/UCI_Metadata/paactiverailroads.xml';
var url_kml_parail_inactive = 'http://www.pasda.psu.edu/pasda/UCI_Metadata/painactiverailroads.xml';
var url_kml_parail_stateroads = 'http://www.pasda.psu.edu/pasda/UCI_Metadata/PaStateRoads2014_02.xml';
var test_url = url_kml_firedata;

function KmlUrls() {

	// local urls
	var server_port = '9999';
	this.geo = 'http://localhost:' + server_port + '/example/kml/geo';
	this.trackhistory = 'http://localhost:' + server_port + '/example/kml/trackhistory/2'; // history for track id 2
	this.trackposition = 'http://localhost:' + server_port + '/example/trackposition'; // kml removed because it stopped working for some reason.
	this.find_track = 'http://localhost:' + server_port + '/example/kml/findtrack';
	this.circles = 'http://localhost:' + server_port + '/example/kml/circles'
	this.gxtrack='http://localhost:' + server_port + '/example/kml/gxtrack';

	// external urls
	this.weather = 'http://radar.weather.gov/ridge/kml/animation/N0R/DIX_N0R_loop.kml';
	this.firedata = 'http://activefiremaps.fs.fed.us/data/kml/conus_latest_AFM_bundle.kml';
	this.earthquake = 'http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_month_depth.kml';
	this.airquality = 'http://www.epa.gov/airnow/today/airnow.kml';
	this.weatherstations = 'http://ssc.e-education.psu.edu/get_ssc_in_kml.php?day=0';
	this.parail_active = 'http://www.pasda.psu.edu/pasda/UCI_Metadata/paactiverailroads.xml';
	this.parail_inactive = 'http://www.pasda.psu.edu/pasda/UCI_Metadata/painactiverailroads.xml';
	this.parail_stateroads = 'http://www.pasda.psu.edu/pasda/UCI_Metadata/PaStateRoads2014_02.xml';
}

trackhistory_enabled = false;
trackhistory_ko = null;
trackhistory_timeout = 1500; // 1.5 seconds
function toggleTrackHistory() {
	//console.log("toggleTrackHistory called");
	trackhistory_enabled = !trackhistory_enabled;
	updateTrackHistory();

}
function updateTrackHistory() {
	//console.log("updateTrackHistory called");
	if (trackhistory_enabled == false) {	
		//console.log("history updates disabled");
		removeGEChild(trackhistory_ko);
		trackhistory_ko = null;
		return;
	}
	
	//console.log("Fetching TrackHistory from " + url_kml_trackhistory);
	google.earth.fetchKml(ge, url_kml_trackhistory, function(new_ko) {
		if (new_ko) {
			//console.log("Applying TrackHistory to the globe");
			removeGEChild(trackhistory_ko);
			ge.getFeatures().appendChild(new_ko);
			trackhistory_ko = new_ko;
		} else {
			console.log("ERROR: Failed to fetch TrackHistory");
		}
		setTimeout(updateTrackHistory, trackhistory_timeout);
	});

}

function createPlacemark(lat, lng) {
	//console.log('createPlacemark(' + lat + ', ' + lng + ')');

	//console.log('ge: ' + ge );

	var point = ge.createPoint('');
	if (!point) {
		//console.log('could not create point');
		return;
	}
	point.setLatitude(lat);
	point.setLongitude(lng);

	var placemark = ge.createPlacemark('');
	//console.log('4');
	if (placemark) {
		placemark.setGeometry(point);
	} else {
		//console.log('failed to create placemark');
		return;
	}

	//console.log('returning placemark');

	return placemark;
}
function getLatLngValues() {
	try {
		//console.log("getLatLngValues called");
		var lat=document.getElementById("latitude_input").value;
		var lng=document.getElementById("longitude_input").value;
		var alt=document.getElementById("altitude_input").value;
		if (lat.length === 0 || lng.length === 0 || alt.length === 0) {
			alert("Latitude or Longitude missing");
			return;
		}
		//console.log('lat = ' + lat + ' lng = ' + lng + ' alt = ' + alt);

		// Create a placemark and then move focus to there.
		var placemark = createPlacemark(lat,lng);
		//console.log('created placemark for new position');
		lookAtPlacemark(placemark);	
	} catch (e) { 
		alert(e.message);
	}

	return false; // prevents the page from being reload 
}

function loadGxTrack() {
	google.earth.fetchKml(ge, url_gxtrack, function(track_ko) {
		if (track_ko) {
			ge.getFeatures().appendChild(track_ko);
		}else{
			//console.log("Failed to track");
		}
	});
}

var equatorRing = null;
var northRing = null;
var southring = null;
function createRings() {
	var equatorPlacemark = ge.createPlacemark('equatorRing');
	var equatorString = ge.createLineString('');
	equatorPlacemark.setGeometry(equatorString);
	equatorString.setExtrude(true);
	equatorString.setAltitudeMode(ge.ALTITUDE_ABSOLUTE);
	equatorPlacemark.setStyleSelector(ge.createStyle(''));
	equatorPlacemark.getStyleSelector().getLineStyle().setWidth(5);
	equatorPlacemark.getStyleSelector().getLineStyle().getColor().set('9900ffff');

	var northPlacemark = ge.createPlacemark('northRing');
	var northString = ge.createLineString('');
	northPlacemark.setGeometry(northString);
	northString.setExtrude(true);
	northString.setAltitudeMode(ge.ALTITUDE_ABSOLUTE);
	northPlacemark.setStyleSelector(ge.createStyle(''));
	northPlacemark.getStyleSelector().getLineStyle().setWidth(5);
	northPlacemark.getStyleSelector().getLineStyle().getColor().set('9900ff00');

	var southPlacemark = ge.createPlacemark('southRing');
	var southString = ge.createLineString('');
	southPlacemark.setGeometry(southString);
	southString.setExtrude(true);
	southString.setAltitudeMode(ge.ALTITUDE_ABSOLUTE);
	southPlacemark.setStyleSelector(ge.createStyle(''));
	southPlacemark.getStyleSelector().getLineStyle().setWidth(5);
	southPlacemark.getStyleSelector().getLineStyle().getColor().set('990000ff');

	var i;
	for (i=0; i<=180; i++) {
		equatorString.getCoordinates().pushLatLngAlt( 0 ,i, 10000000);
		northString.getCoordinates()  .pushLatLngAlt( 1 ,i, 9000000);
		southString.getCoordinates()  .pushLatLngAlt(-1 ,i, 9000000);
	}
	for (i=-179; i<=0; i++) {
		equatorString.getCoordinates().pushLatLngAlt(0,i, 10000000);
		northString.getCoordinates().pushLatLngAlt(  1,i, 9000000);
		southString.getCoordinates().pushLatLngAlt( -1,i, 9000000);
	}

	ge.getFeatures().appendChild(equatorPlacemark);
	ge.getFeatures().appendChild(northPlacemark);
	ge.getFeatures().appendChild(southPlacemark);

	equatorRing = equatorPlacemark;
	northRing = northPlacemark;
	southRing = southPlacemark;
}

var rings_enabled=false;
function toggleEarthRing() {
	rings_enabled = !rings_enabled;

	if (!rings_enabled) {
		removeGEChild(equatorRing);
		removeGEChild(northRing);
		removeGEChild(southRing);
	} else {
		if (equatorRing) {
			ge.getFeatures().appendChild(equatorRing);
			ge.getFeatures().appendChild(northRing);
			ge.getFeatures().appendChild(southRing);
		} else {
			createRings();
		}
	}
}

var weather_ko = null;
var weather_enabled = false;
function togglePHLWeather() {
	weather_enabled = !weather_enabled;
	updatePHLWeather();
}
function updatePHLWeather() {
	var url = url_kml_weather;

	if (weather_enabled == false) {
		removeGEChild(weather_ko);
		weather_ko = null;
		return;
	}

	//console.log("Fetching Weather from " + url);
	google.earth.fetchKml(ge, url, function(new_ko) {
		if (new_ko) {
			//console.log("Adding weather to globe.");
			ge.getFeatures().appendChild(new_ko);
			weather_ko = new_ko;
		}else{
			//console.log("Failed to fetch Weather.");
		}
	});
}

var show_fire_maps = false;
var fire_map_ko = null;
function toggleShowFireMaps() {
	show_fire_maps = !show_fire_maps;
	updateActiveFireMaps();
}
function updateActiveFireMaps() {
	if (show_fire_maps == false) {
		removeGEChild(fire_map_ko);
		fire_map_ko = null;
		return;
	}

	var url = url_kml_firedata;
	//console.log("Fetching active fire data from " + url);
	google.earth.fetchKml(ge, url, function(ko) {
		if (ko) {
			removeGEChild(fire_map_ko);
			//console.log("Adding fire data to globe.");
			ge.getFeatures().appendChild(ko);
			fire_map_ko = ko;
		}else{
			//console.log("Failed to fetch Weather.");
		}
	});
}

var show_parail_maps = false;
var parail_map_ko = null;
function togglePARail() {
	show_parail_maps = !show_fire_maps;
	updatePARail();
}
function updatePARail() {
	if (show_parail_maps == false) {
		removeGEChild(parail_map_ko)
		parail_map_ko = null;
		return;
	}

	var url = url_kml_parail_inactive;
	//console.log("Fetching active parail data from " + url);
	google.earth.fetchKml(ge, url, function(ko) {
		if (ko) {
			removeGEChild(parail_map_ko);
			//console.log("Adding parail data to globe.");
			ge.getFeatures().appendChild(ko);
			parail_map_ko = ko;
		}else{
			//console.log("Failed to fetch parail.");
		}
	});
}

var airquality_enabled = false;
var airquality_ko = null;
function toggleAirQuality() {
	//console.log("airquality");
	airquality_enabled = !airquality_enabled;
	updateAirQualityMaps();
}
function updateAirQualityMaps() {
	if (airquality_enabled == false) {
		removeGEChild(airquality_ko);
		airquality_ko = null;
		return;
	}

	var url = url_kml_airquality;
	//console.log("Fetching active airquality data from " + url);
	google.earth.fetchKml(ge, url, function(ko) {
		if (ko) {
			removeGEChild(airquality_ko);
			//console.log("Adding airquality data to globe.");
			ge.getFeatures().appendChild(ko);
			airquality_ko = ko;
		}else{
			//console.log("Failed to fetch airquality.");
		}
	});
}

var weatherstations_enabled = false;
var weatherstations_ko = null;
function toggleWeatherStations() {
	weatherstations_enabled = !weatherstations_enabled;
	updateWeatherStations();
}
function updateWeatherStations() {
	if (weatherstations_enabled == false) {
		removeGEChild(weatherstations_ko);
		weatherstations_ko = null;
		return;
	}

	var url = url_kml_weatherstations;
	//console.log("Fetching active weatherstations data from " + url);
	google.earth.fetchKml(ge, url, function(ko) {
		if (ko) {
			removeGEChild(weatherstations_ko);
			//console.log("Adding weatherstations data to globe.");
			ge.getFeatures().appendChild(ko);
			weatherstations_ko = ko;
		}else{
			//console.log("Failed to fetch weatherstations.");
		}
	});
}


var show_earthquakes = false;
var earthquakes_ko = null;
function toggleShowEaethQuakes() {
	show_earthquakes = !show_earthquakes;
	updateEarthquakes();
}
function updateEarthquakes() {
	if (show_earthquakes == false) {
		removeGEChild(earthquakes_ko);
		earthquakes_ko = null;
		return;
	}

	var url = url_kml_earthquake;
	//console.log("Fetching earthquake data from " + url);
	google.earth.fetchKml(ge, url, function(ko) {
		if (ko) {
			removeGEChild(earthquakes_ko);
			//console.log("Adding fire data to globe.");
			ge.getFeatures().appendChild(ko);
			earthquakes_ko = ko;
		}else{
			//console.log("Failed to fetch Weather.");
		}
	});
}

var cities_enabled = false;
var cities_ko = null
function toggleCities() {
	cities_enabled = !cities_enabled;
	updateCities();
}
function updateCities() {
	if (cities_enabled == false) {
		removeGEChild(cities_ko);
		cities_ko = null;
		return;
	}
	//console.log("Fetching GeoPoints from" + url_kml_geo);
	google.earth.fetchKml(ge, url_kml_geo, function(geo_ko) {
		if (geo_ko) {
			removeGEChild(cities_ko);
			//console.log("Applying GeoPoints to the globe.");
			ge.getFeatures().appendChild(geo_ko);
			cities_ko = geo_ko;
		} else {
			//console.log("Failed to fetch GeoPoints");
		}
	});

}

// TrackPosition
// TODO: select track id to be followed.
var trackposition_ko = null;
var trackposition_enabled = false;
var trackposition_timeout = 1000;
function toggleTrackPosition () {
	//console.log("toggleTrackPosition called");
	trackposition_enabled = !trackposition_enabled;
	if (trackposition_enabled) {
		updateTrackPosition();
	}
}
function updateTrackPosition() {
	//console.log("updateTrackPosition called");
	if (trackposition_enabled == false) {	
		removeGEChild(trackposition_ko);
		trackposition_ko = null;
		return;
	}

	var url = url_kml_trackposition;
	//console.log("Fetching TrackPosition from " + url_kml_trackposition);
	google.earth.fetchKml(ge, url, trackposition_kml_handler);
}
var trackposition_follow = false;
function toggleFollowPosition() {
	trackposition_follow = !trackposition_follow;
}
function trackposition_kml_handler (new_ko)
{
	if (new_ko == null) {
		//console.log("no trackposition data");
		return;
	}
	// If labels are enabled modify the KML
	if (track_labels_enabled) {
		// Get a list of "trackLabelStyle*" elements from the new kml object
		// set the scale value to 1.
		//
		var list = []; 
		console.log('***');
		var trackLabelStyleArray = getAllKmlElement('id', 'trackLabelStyle', new_ko, list);
		console.log('***trackLabelStyleArray.length=' + trackLabelStyleArray.length + '***');
		//var labelStyle = findKMLNodeByType('KmlLabelStyle', new_ko);
	}
	var placemark = null;
	if (trackposition_follow) {
		placemark = findKMLNodeByType('KmlPlacemark', new_ko);
	}

	// if we have a track in the cro update it with the new position.
	if (selected_track_id) {
		console.log('there is a track in the cro');
		var selected_track = getKmlElement('id', selected_track_id, new_ko);
		if (selected_track) {
			console.log('found cro track in new kml');
			try {
			document.getElementById('cro').value = selected_track.getDescription();
			console.log('cro description: ' + selected_track.getDescription());
			} catch (e) {
				console.log(e.message);
			}
		} else {
			console.log('did not find cro track in new kml');
		}
	} else {
		console.log('no selected track');
	}


	console.log("Applying TrackPosition to the globe");
	removeGEChild(trackposition_ko);
	ge.getFeatures().appendChild(new_ko);
	trackposition_ko = new_ko;

	// Look at the position after we place the tracks
	if (placemark) lookAtPlacemark(placemark);

	setTimeout(updateTrackPosition, trackposition_timeout);	
}

/*
 * A better way to do find and follow is to have the trackposition call back handle it.
 * Either, the server sends two documents, one with all the tracks and a second containing
 * just the location of the track to be followed, or the app looks for the track that it
 * wants in the list of placemarks.
 */
function findTrack (trackid) {
	if (isUndef(trackid)) trackid=2;

	// We should receive a KmlDocument containing a KmlPlacemark
	// Take the lat long from the placemark and create a lookat from it.

	var url = url_find_track + '/' + trackid;
	//console.log('findtrack url: '+ url);
	google.earth.fetchKml(ge, url, function (lookat_track_ko) {
			try {
				//console.log('fetchkml find track returned');
				if (lookat_track_ko) {
					var placemark = findKMLNodeByType('KmlPlacemark', lookat_track_ko);
					lookAtPlacemark(placemark);
					return;
				}
			} catch (e) { 
				console.log(e.message);
			}
	});
}

var followtrack_enabled = false;
var followtrack_timeout = 1000;
function toggleFollowTrack() {
	followtrack_enabled = !followtrack_enabled;
	if (followtrack_enabled) followTrack();
}
function followTrack(trackid) {
	if (isUndef(trackid)) trackid=2;
	if (followtrack_enabled) {
		findTrack(trackid);
		// TODO: this would be nicer if we used a long poll and the server
		// responded when the track position changed.
		// TODO: how do you set a timeout with arguments, other then using globals?
		setTimeout(followTrack, followtrack_timeout);
	}
}

var circles_ko = null;
var circles_enabled = false;
function toggleCircles() {
	circles_enabled = !circles_enabled;
	//console.log('toggleCircles called, enabled = ' + circles_enabled);
	updateCircles();
}
function updateCircles () {
	if (circles_enabled == false) {
		removeGEChild(circles_ko);
		circles_ko = null;
		return;
	}
	var url = url_circles + '/1';
	google.earth.fetchKml(ge, url, function (ko) {
		try {
			if (ko) {
				removeGEChild(circles_ko);
				ge.getFeatures().appendChild(ko);
				//console.log('applying circles to globe');
				circles_ko = ko;
			} else { 
				//console.log('Could not fetch circles kml from ' + url);
			}
		} catch (e) {
			console.log(e.message);
		}
	});
}

// Utility functions
function removeGEChild (child) {
	if (child) {
		try {
			ge.getFeatures().removeChild(child);
		} catch (e) {
			console.log(e.message);
		}
	}
}

function findKMLNodeByType (type, node) {
	if (node == null) {
		console.log('top node is null');
		return null;
	}
	console.log('node type = ' + node.getType());
	if (type == node.getType()) {
		return node;
	}
	if (false == node.getFeatures().hasChildNodes()) {
		console.log("node has no children");
		return null;
	}
	var subNodes = node.getFeatures().getChildNodes();
	if (subNodes == null) {
		console.log("could not get children of node");
		return null;
	}

	var length = subNodes.getLength();
	console.log('node has ' + length + ' children');
	for (i = 0; i<length; i++) {
		var subNode = subNodes.item(i);
		var nodeType = subNode.getType();
		if (nodeType == type) {
		       console.log('found matching node type');
	       	       return subNode;
		}
		switch (nodeType) {
			case 'KmlFolder':
			case 'KmlDocument':
				var deepNode = findKMLNodeByType(type, subNode);
				if (deepNode) {
				       console.log('recursive call found matching node type');
			       	       return deepNode;
				}
		}
	}

	return null;
}
// type is the type of value we're comparing against: 'type' or 'id'
// value is the value of the type: value == getType() 
function getKmlElement (type, value, node, count) {
	count = typeof count !== 'undefined' ? count : 0;
	///console.log('[' + count + ']getKmlElement(' + type + ',' + value + ',' + node + ')');
	try {
		if (node == null) {
			console.log('top node is null');
			return null;
		}
		//console.log('node type = ' + node.getType());
		//console.log('node id = ' + node.getId());
		var r = false;
		var nodeType = node.getType();
		var nodeId = node.getId();
		if (type == 'type') {
			r = (value == nodeType)
		} else if (type == 'id') {
			r = (value == nodeId);
		}
		// Return the node if it matches
		if (r) return node;

		// It the node has children recursively search them.
		// Not all nodes have getFeatures.
		if (!node.getFeatures || !node.getFeatures().hasChildNodes) {
			return null;
		}
		if (false == node.getFeatures().hasChildNodes()) {
			//console.log("node has no children");
			return null;
		}

		// TODO: this could be combined with the if above to save a call to hasChildNodes
		var subNodes = node.getFeatures().getChildNodes();
		if (subNodes == null) {
			console.log("could not get children of node");
			return null;
		}

		var length = subNodes.getLength();
		//console.log('node has ' + length + ' children');
		for (i = 0; i<length; i++) {
			var subNode = subNodes.item(i);
			var matchingNode = getKmlElement(type, value, subNode, count+1);
			if (matchingNode)
				// Return the match if found, otherwise continue to the next subnode.
				return matchingNode;
		}
	} catch (e) {
		console.log(e.message);
	}
	return null;
}

// TODO: this is not working. It's not finding any matches for id=trackLabelStyle*
// returns an array of matching elements
// list is an initially empty array passed in as an argument.
// matching is done on substrings, not complete matches.
function getAllKmlElement(type, value, node, list, count) {

	count = typeof count !== 'undefined' ? count : 0;
	///console.log('[' + count + ']getKmlElement(' + type + ',' + value + ',' + node + ')');
	try {
		if (node == null) {
			console.log('top node is null');
			return list;
		}
		//console.log('node type = ' + node.getType());
		//console.log('node id = ' + node.getId());
		var r = false;
		var nodeType = node.getType();
		var nodeId = node.getId();
		if (type == 'type') {
			r = (value == nodeType)
		} else if (type == 'id') {
			//r = (value == nodeId);
			r = (nodeId.indexOf(value) != -1);
		}
		// If it matches added it to the list.
		if (r) { 
			console.log('Found matching node');
			list.push(node);
		}

		// It the node has children recursively search them.
		// Not all nodes have getFeatures.
		if (!node.getFeatures || !node.getFeatures().hasChildNodes) {
			return list;
		}
		if (false == node.getFeatures().hasChildNodes()) {
			//console.log("node has no children");
			return list;
		}

		// TODO: this could be combined with the if above to save a call to hasChildNodes
		var subNodes = node.getFeatures().getChildNodes();
		if (subNodes == null) {
			console.log("could not get children of node");
			return list;
		}

		var length = subNodes.getLength();
		//console.log('node has ' + length + ' children');
		for (i = 0; i<length; i++) {
			var subNode = subNodes.item(i);
			var matchingNode = getKmlElement(type, value, subNode, list, count+1);
			if (matchingNode) {
				console.log('Found matching subNode');
				list.push(matchingNode);
			}
		}
	} catch (e) {
		console.log(e.message);
	}
	return list;
}

function traverseKml(node, level, id) {

	if (typeof(level) == 'undefined') level = 0; // default argument

	if (false == node.getFeatures().hasChildNodes()) {
		console.log("node has no children");
		return;
	}
	else { // (node.getFeatures().hasChildNodes()) {
		var subNodes = node.getFeatures().getChildNodes();
		if (subNodes == null) {
			console.log(" could not get children of node");
			return;
		}		

		var length = subNodes.getLength();
		for (var i = 0; i<length; i++) {
			var eachSubNode = subNodes.item(i);
			var nodeType = eachSubNode.getType();
			var nodeName = eachSubNode.getName();
			var nodeId = eachSubNode.getId();
			console.log('[' + level.toString() + '] type: ' + nodeType + ' name: ' + nodeName + ' id: ' + nodeId.toString());
			switch (nodeType) {
				case 'KmlFolder':
				case 'KmlDocument':
				case 'KmlPlacemark':
					traverseKml(eachSubNode, level+1);
			}
			if (nodeId == id) {
				console.log('removing ' + nodeId.toString());
				subNode.remove();
			}
		}
	}
}

function setRange (range) {
	var lookat = ge.getView().copyAsLookAt(ge.ALTITUDE_RELATIVE_TO_GROUND);
	lookat.setRange(range);
	ge.getView().setAbstractView(lookat);
	return true;
}

function lookAtPlacemark (placemark) {

	// Traverse the kml document looking for the lookat element.
	if (!placemark) { console.log("Placemark not found"); return;}
	var point = placemark.getGeometry();
	var lat = point.getLatitude();
	var lng = point.getLongitude();
	var alt = point.getAltitude();
	//console.log('lat/lng: '+ lat +'/' + lng);
	var la=ge.createLookAt('');
	//la.set(lat,lng,alt,altmode,heading,tilt,range)
	la.setLatitude(lat);
	la.setLongitude(lng);

	// Preserve the current camera range.
	la.setRange(ge.getView().copyAsLookAt(ge.ALTITUDE_RELATIVE_TO_GROUND).getRange());
	//la.setAltitudeMode(ge.ALTITUDE_ABSOLUTE); // relative to sea level
	// This doesn't take into consideration the altitude mode from the placemark.
	//la.setAltitude(alt + default_view_altitude); // meters?
	ge.getView().setAbstractView(la);
}

function isUndef(v) {
	return typeof(v) == 'undefined' ? true : false;
}

function createCORSRequest(method, url){
	var xhr = new XMLHttpRequest();
	// Special code for IE is needed, this will work on chrome
	xhr.open(method, url, true); 
	return xhr;
}

function makeCORSRequest() {
	var xhr = createCORSRequest('GET', url_kml_geo)
		xhr.onload = function() {
			var text = xhr.responseText;
			alert('response:' + text);
		};

	xhr.onerror = function() {
		alert('CORS request error');
	};

	xhr.send();
}

var buildings_enabled = false;
function toggleBuildings() {
	buildings_enabled = !buildings_enabled;
	ge.getLayerRoot().enableLayerById(ge.LAYER_BUILDINGS, buildings_enabled);
}

var roads_enabled = false;
function toggleRoads() {
	roads_enabled = !roads_enabled;
	ge.getLayerRoot().enableLayerById(ge.LAYER_ROADS, roads_enabled);
}


var terrain_enabled = false;
function toggleTerrain() {
	terrain_enabled = !terrain_enabled;
	ge.getLayerRoot().enableLayerById(ge.LAYER_TERRAIN, terrain_enabled);
}

var trees_enabled = false;
function toggleTrees() {
	trees_enabled = !trees_enabled;
	ge.getLayerRoot().enableLayerById(ge.LAYER_TREES, trees_enabled);
}

var track_labels_enabled = false;
function toggleTrackLabels() {
	track_labels_enabled = true;

	// Get the current track kml and change the label style item
	// then reapply the kml, so the change happens instantly
	// rather than waiting for the next track update.
}

var fullscreen = false;
function changeScreenSize() {
	// TODO: during initialization set the size of map3d_sized to the initial size of map3d
	//console.log("changeScreenSize called");
	var container_name = fullscreen == true ? "map3d_fullscreen" : "map3d_sized";
	var size_container = document.getElementById(container_name);
	var content_container = document.getElementById('map3d');

	if (fullscreen) {
		content_container.style.left = 0;
		content_container.style.top = 0;
	} else {
		content_container.style.left = size_container.style.left;
		content_container.style.top = size_container.style.top;
	}
	content_container.style.width = size_container.offsetWidth + 'px';
	content_container.style.height = size_container.offsetHeight + 'px';
}

function handleResize() {
	//console.log("handleResize called");
	//console.log('window.innerWidth=' + window.innerWidth + ' screen.width=' + screen.width);
	//console.log('window.innerHeight=' + window.innerHeight + ' screen.height=' + screen.height);
	if (window.innerWidth == screen.width && window.innerHeight == screen.height) {
		// TODO: Chrome needs some margin of error on the height comparison most of the time.
		// screen.height can be less then innerHeight but still be in fullscreen mode.
		fullscreen = true;
	} else {
		fullscreen = false;
	}
	changeScreenSize();
}

var selected_track_id = null;
function registerForGlobeEvents() {
	google.earth.addEventListener(ge.getWindow(), 'mouseup', function(event) {
		var target = event.getTarget();
		//console.log('You clicked on a ' + target.getType());
		//event.preventDefault();
		if (target.getType() == 'KmlPlacemark') {
			selected_track_id = target.getId();  // record it for later.
			console.log('SELECTED TRACK ID: ' + selected_track_id);
			var description = target.getDescription();
			var name = target.getName();
			// TODO: show description info in text box
			//console.log("cro: " + description);
			document.getElementById('cro').value = description;
		}
	});
}

function registerForPageEvents() {

	//document.getElementById('coord_submit').onclick = getLatLngValues;

	document.getElementById('buildings').onclick = toggleBuildings;
	document.getElementById('roads').onclick = toggleRoads;
	document.getElementById('terrain').onclick = toggleTerrain;
	document.getElementById('trees').onclick = toggleTrees;

	document.getElementById('cities').onclick = toggleCities;
	document.getElementById('trackposition').onclick = toggleTrackPosition;
	document.getElementById('followtrackposition').onclick = toggleFollowPosition;
	document.getElementById('trackhistory').onclick = toggleTrackHistory;
	document.getElementById('circles').onclick = toggleCircles;
	document.getElementById('activefiremaps').onclick = toggleShowFireMaps;
	document.getElementById('earthquakes').onclick = toggleShowEaethQuakes;
	document.getElementById('weather').onclick = togglePHLWeather;
	document.getElementById('airquality').onclick = toggleAirQuality;
	document.getElementById('weatherstations').onclick = toggleWeatherStations;
	document.getElementById('showtracklabels').onclick = toggleTrackLabels;
	document.getElementById('showearthring').onclick = toggleEarthRing;

	//document.getElementById('pagebody').onresize = handleResize;
	// TODO: initialize other buttons this way.

}

function init() {
	console.log("ge.js init function called");
	registerForPageEvents();
	google.earth.createInstance('map3d', initCB, failureCB);
}

function initCB(instance) {
	console.log("initCB called");

	ge = instance;
	ge.getWindow().setVisibility(true);
	ge.getOptions().setStatusBarVisibility(true);
	ge.getNavigationControl().setVisibility(ge.VISIBILITY_SHOW);

	registerForGlobeEvents();
	//loadGxTrack();

}

function failureCB(errorCode) {
	console.log("create google earth instance failed");
}
google.setOnLoadCallback(init);

console.log("loading google earth plugin...");
google.load("earth", "1");

