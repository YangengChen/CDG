load("classpath:topojson.js");
load("classpath:topojson-server.js");
var geoJSON,
topoJSON;

geoJSON = JSON.parse(geoString);
var geoCollection = {
	    collection : geoJSON
};
topoJSON = topojson.topology(geoCollection);
annotateNeighbors();
JSON.stringify(geoJSON);

function annotateNeighbors() {
	var geometries = topoJSON.objects.collection.geometries;
	var neighbors = topojson.neighbors(geometries); //neighbors[i] = array of neighbors (as indexes in geometries) for geometries[i]
	var currNeighbors;
	var currNeighborIDs;
	for(var i = 0; i < geometries.length; i++) {
		currNeighbors = neighbors[i]; //neighbors of geometries[i]
		currNeighborIDs = [];
		var neighborID;
		for (var j = 0; j < currNeighbors.length; j++) {
			neighborID = geometries[currNeighbors[j]].properties["ID"];
			currNeighborIDs.push(neighborID);
		}
		geoJSON.features[i].properties["neighbors"] = currNeighborIDs;
	}
}
