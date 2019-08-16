import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { setSortKey } from '../actions/'
import '../css/HotelsClickableTh.css';

const HotelsClickableTh = props => (
  <th
    className="hotel-clickable-th"
    onClick={() => props.setSortKey(props.sortKey)}
  >
    {props.label}{props.isSelected ? '△' : ''}
  </th>
);

HotelsClickableTh.propTypes = {
  label: PropTypes.string.isRequired,
  sortKey: PropTypes.string.isRequired,
  isSelected: PropTypes.bool.isRequired,
  setSortKey: PropTypes.func.isRequired
};


export default connect(
  (state, ownprops) => ({
    isSelected: ownprops.sortKey === state.sortKey,
  }),
  { setSortKey },
)(HotelsClickableTh);
