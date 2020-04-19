import React from 'react';
import { withStyles  } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';

const styles = {
  button: {
    backgroundColor: 'orange'
  }
};

const MaterialUIHOC = props => {
  const { classes } = props;
  return (
    <Button variant="contained" className={classes.button}>
      Test
    </Button>
  );
}

export default withStyles(styles)(MaterialUIHOC);