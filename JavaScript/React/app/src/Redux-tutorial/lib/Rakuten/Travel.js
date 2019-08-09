import axios from 'axios';

const SIMPLE_HOTEL_SEARCH_ENDPOINT = process.env.REACT_APP_RAKUTEN_URL_BASE + process.env.REACT_APP_SIMPLE_HOTEL_SEARCH_URL;
export default {
  simpleHotelSearch: params =>
  axios.get(SIMPLE_HOTEL_SEARCH_ENDPOINT, { params })
};