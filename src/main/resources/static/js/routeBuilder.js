L.mapquest.key = 'swloV0uLzDhExu3uzkkeJ3kawqdm8y50';

let map = L.mapquest.map('map', {
    center: [54.6082, 39.7151],
    layers: L.mapquest.tileLayer('map'),
    zoom: 12
});


function runDirection(locationsJSON, centerLongitude, centerLatitude) {

    map = L.map('map', {
        center: [centerLatitude, centerLongitude],
        layers: L.mapquest.tileLayer('map'),
        zoom: 14
    });

    const dir = L.route.directions();

    dir.route({
        locations: [
            locationsJSON
        ]
    });


    let CustomRouteLayer = L.route.layer.extend({
        createStartMarker: (location) => {
            var custom_icon;
            var marker;

            custom_icon = L.icon({
                iconUrl: 'https://assets.mapquestapi.com/icon/v2/marker.png',
                iconSize: [20, 29],
                iconAnchor: [10, 29],
                popupAnchor: [0, -29]
            });

            marker = L.marker(location.latLng, {icon: custom_icon}).addTo(map);

            return marker;
        }
    });

    map.addLayer(new CustomRouteLayer({
        directions: dir,
        fitBounds: true
    }));
}


function addRoute(locationsJSON, centerLongitude, centerLatitude) {
    map.remove();
    runDirection(locationsJSON, centerLongitude, centerLatitude);
}

