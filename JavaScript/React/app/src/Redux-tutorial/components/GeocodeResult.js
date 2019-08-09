import React from 'react';
import PropTypes from 'prop-types';
import '../css/GeocodeResult.css';

const GeocodeResult = ({address, location}) => ( // propsのaddress、lat、lngを取得
  <ul>
    <li>住所：{address}</li>
    <li>緯度：{location.lat}</li>
    <li>経度：{location.lng}</li>
  </ul>
);

GeocodeResult.propTypes = {
  address: PropTypes.string,
  location: PropTypes.objectOf(PropTypes.number).isRequired,
};

GeocodeResult.defaultProps = {
  address: '',
};

export default GeocodeResult;