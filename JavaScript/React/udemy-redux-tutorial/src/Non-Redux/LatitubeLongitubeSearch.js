import React, {Component} from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';
import queryString from 'query-string';

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
      place: this.getPlaceParam() || '東京タワー',
      location: {
        lat: 35.6585805,
        lng: 139.7454329
      },
      sortKey: 'price',
    };
  }

  // ページ描画時に実行される
  componentDidMount() {
    const place = this.getPlaceParam();
    if (place) {
      this.startSearch(place);
    }
  }

  getPlaceParam() {
    const params = queryString.parse(this.props.location.search);
    const place = params.place;
    if (place && place.length > 0) {
      return place;
    }
    return null;
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

  handlePlaceChange(place) {
    this.setState({place}); // Placeキーにplaceという値をセット
  }

  handlePlaceSubmit(e) {
    e.preventDefault();
    this.props.history.push(`?place=${this.state.place}`);
    this.startSearch();
  }

  startSearch() {
    geocode(this.state.place)
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

  handleSortKeyChange(sortKey) {
    this.setState({
      sortKey,
      hotels: sortedHotels(this.state.hotels, sortKey) });
  }

  render() {
    return (
      <div className="app">
        <h1 className="app-title">ホテル検索</h1>
        <SearchForm
          place={this.state.place}
          onPlaceChange={place => this.handlePlaceChange(place)}
          onSubmit={(e) => this.handlePlaceSubmit(e)}
        />
        <div className="result-area">
          <Map location={this.state.location} />
          <div className="result-right">
            <GeocodeResult
              className="geocode-result"
              address={this.state.address}
              location={this.state.location}
            />
            <h2>ホテル検索結果</h2>
            <HotelsTable
              hotels={this.state.hotels}
              sortKey={this.state.sortKey}
              onSort={(sortKey) => this.handleSortKeyChange(sortKey)}
            />
          </div>
        </div>
      </div>
    );
  }
}

LatitubeLongitubeSearch.propTypes = {
  history: PropTypes.shape({ push: PropTypes.func }).isRequired,
  location: PropTypes.shape({ search: PropTypes.string }).isRequired
};

export default LatitubeLongitubeSearch;