import React from 'react';
import PropTypes from 'prop-types';
import '../css/HotelsClickableTh.css';

const HotelsClickableTh = ({ label, sortKey, isSelected, onSort }) => (
  <th
    className="hotel-clickable-th"
    onClick={() => onSort(sortKey)}
  >
    {label}{isSelected ? '△' : ''}
  </th>
);

HotelsClickableTh.propTypes = {
  label: PropTypes.string.isRequired,
  sortKey: PropTypes.string.isRequired,
  isSelected: PropTypes.bool.isRequired,
  onSort: PropTypes.func.isRequired
};

export default HotelsClickableTh;
