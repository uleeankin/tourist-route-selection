function addRoute(locationsJSON) {
    runDirection(locationsJSON);
}

function runDirection(locationsJSON) {

    L.mapquest.key = 'swloV0uLzDhExu3uzkkeJ3kawqdm8y50';

    const dir = L.mapquest.directions();

    dir.route(locationsJSON, createMap);
}

function createMap(err, response) {
    const map = L.mapquest.map('map', {
        center: [0, 0],
        layers: L.mapquest.tileLayer('map'),
        zoom: 12
    });

    L.mapquest.directionsLayer({
        startMarker: {
            icon: 'circle',
            iconOptions: {
                size: 'sm',
                primaryColor: '#1fc715',
                secondaryColor: '#1fc715',
                symbol: 'A'
            },
            title: 'Drag to change location'
        },
        endMarker: {
            icon: 'circle',
            iconOptions: {
                size: 'sm',
                primaryColor: '#e9304f',
                secondaryColor: '#e9304f',
                symbol: 'B'
            },
            title: 'Drag to change location'
        },
        routeRibbon: {
            color: "#2aa6ce",
            opacity: 1.0,
            showTraffic: false
        },
        directionsResponse: response,
        options: {
            routeType: 'pedestrian'
        }
    }).addTo(map);
}

