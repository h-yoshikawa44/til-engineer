import React, {Component} from 'react';

class PlayerData extends Component {
  render() {
    return (
      <div>
        <h2>{this.props.color}プレイヤー</h2>
        <p>{this.props.point}</p>
      </div>
    )
  }
}

export default PlayerData;