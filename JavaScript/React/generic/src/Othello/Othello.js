import React, {Component} from 'react';

import PlayerData from './components/PlayerData';
import Board from './components/Board';

class Othello extends Component {
  constructor(props) {
    super(props);
    this.state = {
      // 配列をいったんJSON化して、それを再度オブジェクト化することによって、全ての配列が別オブジェクトになる
      squares: JSON.parse(JSON.stringify(Array(8).fill(Array(8).fill(null)))),
      player: [
        {color: '白', point: 2, mark: '○'},
        {color: '黒', point: 2, mark: '●'}
      ],
      isNext: true
    }
  }

  handleClick(i, j) {
    const squares = this.state.squares.slice();
    console.log(this.state.squares);
    squares[i][j] = this.state.isNext ? this.state.player[0].mark : this.state.player[1].mark;
    console.log(squares);
    this.setState({
      squares: squares,
      isNext: !this.state.isNext
    })
  }

  render() {
    return (
      <div>
        <PlayerData color={this.state.player[0].color} point={this.state.player[0].point}/>
        <div>
          <p>{this.state.isNext ? this.state.player[0].color : this.state.player[1].color}プレイヤーの番です</p>
          <Board squares={this.state.squares} onClick={(i, j) => this.handleClick(i, j)}/>
        </div>
        <PlayerData color={this.state.player[1].color} point={this.state.player[1].point}/>
      </div>
    );
  }
}

export default Othello;