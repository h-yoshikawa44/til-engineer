import React, {Component} from 'react';
import axios from 'axios';
import SearchForm from './components/SearchForm';
import GeocodeResult from './components/GeocodeResult';
import Map from './components/Map';

const GEOCODE_ENDPOINT = 'https://maps.googleapis.com/maps/api/geocode/json';

class LatitubeLongitubeSearch extends Component {
  constructor(props) {
    super(props);
    this.state = {
      location: {
        lat: 35.6585805,
        lng: 139.7454329
      },
    };
  }

  setErrorMessae(message) {
    this.setState({
      address: message,
      location: {
        lat: 0,
        lng: 0
      }
    });
  }

  handlePlaceSubmit(place) {
    axios.get(GEOCODE_ENDPOINT, { params: { address: place, key: process.env.REACT_APP_GOOGLE_MAPS_API_KEY}})
          .then((results) => {
            console.log(results);
            const data = results.data
            const result = data.results[0];
            switch (data.status) {
              case 'OK': {
                this.setState({
                  address: result.formatted_address,
                  location: result.geometry.location,
                });
                break;
              }
              case 'ZERO_RESULTS': {
                this.setErrorMessae('結果が見つかりませんでした');
                break;
              }
              default: {
                this.setErrorMessae('エラーが発生しました');
              }
            }
          })
          .catch((error) => {
            this.setErrorMessae('通信に失敗しました');
          })
  }

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
}

export default LatitubeLongitubeSearch;