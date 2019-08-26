import React, { Component } from 'react';
import axios from 'axios';

class ApiTutorial extends Component {
  constructor(props) {
    super(props);
    this.state = {
      result: ''
    };
  }

  getApiResult() {
    axios.get('http://localhost:8000/account')
         .then((result) => {
           this.setState({
             result: result.data
           });
          // console.log(result)
         })
         .catch((error) => {
           console.log(error)
         })
  }

  render() {
    return (
      <div>
        <button onClick={() => this.getApiResult()}>API接続</button>
        <p>取得値</p>
        <ul>
          <li>ID：{this.state.result.loginId}</li>
          <li>かな：{this.state.result.kana}</li>
          <li>名前：{this.state.result.name}</li>
        </ul>
      </div>
    )
  }
}

export default ApiTutorial;