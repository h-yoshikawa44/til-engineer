import React, {Component} from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import './css/LatitubeLongitubeSearch.css';
import SearchForm from './containers/SearchForm';
import GeocodeResult from './components/GeocodeResult';
import Map from './components/Map';
import HotelsTable from './components/HotelsTable';
import { startSearch } from './actions/';

class ReduxTutorial extends Component {
  componentDidMount() {
    this.props.dispatch(startSearch());
  }

  render() {
    return (
      <div className="app">
        <h1 className="app-title">ホテル検索</h1>
        <SearchForm　history={this.props.history}/>
        <div className="result-area">
          <Map location={this.props.geocodeResult.location} />
          <div className="result-right">
            <GeocodeResult
              className="geocode-result"
              address={this.props.geocodeResult.address}
              location={this.props.geocodeResult.location}
            />
            <h2>ホテル検索結果</h2>
            <HotelsTable />
          </div>
        </div>
      </div>
    );
  }
}

ReduxTutorial.propTypes = {
  history: PropTypes.shape({ push: PropTypes.func }).isRequired,
  location: PropTypes.shape({ search: PropTypes.string }).isRequired,
  geocodeResult: PropTypes.shape({
    address: PropTypes.string.isRequired,
    location: PropTypes.shape({
      lat: PropTypes.number.isRequired,
      lng: PropTypes.number.isRequired
    }),
  }).isRequired,
  dispatch: PropTypes.func.isRequired
};


const mapStateToProps = state => ({
  geocodeResult: state.geocodeResult
})

export default connect(mapStateToProps)(ReduxTutorial);