import React, {Component} from 'react';
import PropTypes from 'prop-types';
import '../css/SearchForm.css';

class SearchForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      place: '東京タワー',
    };
  }

  handlePlaceChange(place) {
    this.setState({place}); // Placeキーにplaceという値をセット
  }

  handleSubmit(e) {
    e.preventDefault(); // フォーム送信してリロードするのを抑制する
    this.props.onSubmit(this.state.place);
  }

  render() {
    return (
      <form className="search-form" onSubmit={e => this.handleSubmit(e)}>
        <input
          className="place-input"
          type="text"
          size="30"
          value={this.state.place}
          onChange={e => this.handlePlaceChange(e.target.value)}
        />
        <input className="submit-button" type="submit" value="検索" />
      </form>
    )
  }
}

SearchForm.propTypes = {
  onSubmit: PropTypes.func.isRequired,
};

export default SearchForm;