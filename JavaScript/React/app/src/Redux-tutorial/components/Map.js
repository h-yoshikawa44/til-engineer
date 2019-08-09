import React from 'react';
import PropTypes from 'prop-types';
import { withScriptjs, withGoogleMap, GoogleMap, Marker } from 'react-google-maps'
import '../css/Map.css';

const InnerMap = withScriptjs(withGoogleMap(({ location, marker }) => (
  <GoogleMap
    defaultZoom={12}
    defaultCenter={location}
    center={location} // マップの中央の座標
  >
    <Marker {...marker} // マーカーを立てる位置(渡す引数には　lat, lng を指定)
    />
  </GoogleMap>
)));

const Map = ({ location }) => (
  <InnerMap
    googleMapURL={process.env.REACT_APP_GOOGLE_MAP_URL}
    loadingElement={<div style={{ height: `100%` }} />}
    containerElement={(<div />)}
    mapElement={(<div className="map" />)}
    location={location}
    marker={{ position: location }}
  />
);

Map.prototypes = {
  location: PropTypes.objectOf(PropTypes.number).isRequired,
};

export default Map;