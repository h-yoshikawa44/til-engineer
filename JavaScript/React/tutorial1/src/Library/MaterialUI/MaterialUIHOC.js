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
    <div>
      <p>MaterialUIHOC</p>
      <Button variant="contained" className={classes.button}>
        Test
      </Button>
    </div>
  );
}

export default withStyles(styles)(MaterialUIHOC);