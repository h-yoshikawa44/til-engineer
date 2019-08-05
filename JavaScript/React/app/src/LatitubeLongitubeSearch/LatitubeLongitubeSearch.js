import React, {Component} from 'react';
import _ from 'lodash';
import './css/LatitubeLongitubeSearch.css';
import SearchForm from './components/SearchForm';
import GeocodeResult from './components/GeocodeResult';
import Map from './components/Map';
import { geocode } from './domain/Geocoder';
import HotelsTable from './components/HotelsTable';
import { searchHotelByLocation } from './domain/HotelRepository';

const sortedHotels = (hotels, sortKey) => _.sortBy(hotels, h => h[sortKey]);

class LatitubeLongitubeSearch extends Component {
  constructor(props) {
    super(props);
    this.state = {
      location: {
        lat: 35.6585805,
        lng: 139.7454329
      },
      sortKey: 'price',
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
    geocode(place)
      .then(({ status, address, location }) => {
        switch (status) {
          case 'OK': {
            this.setState({ address, location });
            return searchHotelByLocation(location);
          }
          case 'ZERO_RESULTS': {
            this.setErrorMessae('結果が見つかりませんでした');
            break;
          }
          default: {
            this.setErrorMessae('エラーが発生しました');
          }
        }
        return [];
      })
      .then((hotels) => {
        this.setState({ hotels: sortedHotels(hotels, this.state.sortKey) });
      })
      .catch((error) => {
        console.log(error);
        this.setErrorMessae('通信に失敗しました');
      })
  }

  render() {
    return (
      <div className="app">
        <h1 className="app-title">ホテル検索</h1>
        <SearchForm onSubmit={place => this.handlePlaceSubmit(place)}/>
        <div className="result-area">
          <Map location={this.state.location} />
          <div className="result-right">
            <GeocodeResult
              className="geocode-result"
              address={this.state.address}
              location={this.state.location}
            />
            <h2>ホテル検索結果</h2>
            <HotelsTable hotels={this.state.hotels} />
          </div>
        </div>
      </div>
    );
  }
}

export default LatitubeLongitubeSearch;