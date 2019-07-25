import React from 'react';
import PropTypes from 'prop-types';

const GeocodeResult = ({address, lat, lng}) => ( // propsのaddress、lat、lngを取得
  <ul>
    <li>住所：{address}</li>
    <li>緯度：{lat}</li>
    <li>経度：{lng}</li>
  </ul>
);

GeocodeResult.propTypes = {
  address: PropTypes.string,
  lat: PropTypes.number,
  lng: PropTypes.number,
};

GeocodeResult.defaultProps = {
  address: '',
  lat: 0,
  lng: 0,
};

export default GeocodeResult;