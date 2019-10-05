import React from 'react';
import { StyleSheet, View, Text } from 'react-native';

class BodyText extends React.Component {
  render() {
    return (
      <View>
        <Text style={styles.text}>
          {this.props.children}
        </Text>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  text: {
    color: '#000',
    backgroundColor: '#eee'
  }
})

export default BodyText;