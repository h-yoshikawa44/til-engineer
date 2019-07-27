import React, {Component} from 'react';
import axios from 'axios';
import SearchForm from './components/SearchForm';
import GeocodeResult from './components/GeocodeResult';

const GEOCODE_ENDPOINT = 'https://maps.googleapis.com/maps/api/geocode/json';

class LatitubeLongitubeSearch extends Component {
  constructor(props) {
    super(props);
    this.state = {

    };
  }

  handlePlaceSubmit(place) {
    axios.get(GEOCODE_ENDPOINT, { params: { address: place, key: process.env.REACT_APP_GOOGLE_MAPS_API_KEY}})
          .then((results) => {
            console.log(results);
            const data = results.data
            const result = data.results[0];
            switch (results.status) {
              case 'OK': {
                const location = result.geometry.location;
                this.setState({
                  address: result.formatted_address,
                  lat: location.lat,
                  lng: location.lng
                });
                break;
              }
              case 'ZERO_RESULTS': {
                this.setState({
                  address: '結果が見つかりませんでした',
                  lat: 0,
                  lng: 0
                });
                break;
              }
              default: {
                this.setState({
                  address: 'エラーが発生しました',
                  lat: 0,
                  lng: 0
                });
              }
            }
          });
  }

  render() {
    return (
      <div>
        <h1>緯度経度検索</h1>
        <SearchForm onSubmit={place => this.handlePlaceSubmit(place)}/>
        <GeocodeResult
          address={this.state.address}
          lat={this.state.lat}
          lng={this.state.lng}
        />
      </div>
    );
  }
}

export default LatitubeLongitubeSearch;