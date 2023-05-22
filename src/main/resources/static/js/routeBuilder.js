L.mapquest.key = 'swloV0uLzDhExu3uzkkeJ3kawqdm8y50';

let map = L.mapquest.map('map', {
    center: [54.6082, 39.7151],
    layers: L.mapquest.tileLayer('map'),
    zoom: 12
});


function runDirection(locationsJSON) {

    map = L.map('map', {
        layers: MQ.mapLayer(),
        center: [54.6082, 39.7141],
        zoom: 13
    });

    var dir = MQ.routing.directions();

    dir.route({
        locations: [
            locationsJSON
        ]
    });


    CustomRouteLayer = MQ.Routing.RouteLayer.extend({
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


function submitForm() {

    map.remove();

    start = document.getElementById("start").value;
    end = document.getElementById("destination").value;

    runDirection(start, end);

    document.getElementById("form").reset();
}