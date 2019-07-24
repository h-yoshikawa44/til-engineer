import React, {Component} from 'react';
import SearchForm from './components/SearchForm';
import GeocodeResult from './components/GeocodeResult';

class LatitubeLongitubeSearch extends Component {
  constructor(props) {
    super(props);
    this.state = {

    };
  }

  handlePlaceSubmit(place) {
    console.log(place);
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