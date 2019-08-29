import React, {Component} from 'react';

import Square from './Square';
import '../css/Board.css';

class Board extends Component {
  renderSquare(i, j) {
    const key = i + 'and'+ j;
    return <Square key={key} value={this.props.squares[i][j]} onClick={() => this.props.onClick(i, j)}/>
  }

  render() {
    return (
      <div>
        {(() => {
          const rows = [];
          for (let i = 0; i < 8; i++) {
            const row = [];
            for (let j = 0; j < 8; j++) {
              row.push(this.renderSquare(i, j))
            }
            rows.push(<div key={i} className="board-row">{row}</div>)
          }
          return rows;
        })()}
      </div>
    )
  }
}

export default Board;