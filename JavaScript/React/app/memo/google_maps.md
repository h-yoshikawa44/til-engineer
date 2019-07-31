## Google Maps Pratform

参考：[Google Maps の APIキー を取得する](https://nendeb.com/276)

Google Mapsに関するAPIを利用するためには、APIキーが必要になる
そのため、GCPに登録して作成しておく

キーの参照元制限をつけると、そのAPIキーは使用できないことがあるようなので、
使用できるAPIの種類を制限したりで代用することもできる(IPアドレス制限だと問題なし？)

[Geocoding API](https://developers.google.com/maps/documentation/geocoding/intro)
[Maps Javascript API](https://developers.google.com/maps/documentation/javascript/tutorial)


### react-google-maps
インストール  
`$ yarn add react-google-maps`


マップコンポーネントの例  
検索した場所の座標に動く
```js
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
```

呼び出し側の例
```js
  render() {
    return (
      <div>
        <h1>緯度経度検索</h1>
        <SearchForm onSubmit={place => this.handlePlaceSubmit(place)}/>
        <GeocodeResult
          address={this.state.address}
          location={this.state.location}
        />
        <Map location={this.state.location} />
      </div>
    );
  }
```