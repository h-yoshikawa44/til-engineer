import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles({
  button: {
    backgroundColor: 'green'
  },
  msg: {
    color: 'blue',
    '& span': {
      color: 'red',
      '&:hover': {
        color: 'black'
      }
    }
  },
  button2: props => ({
    backgroundColor: props.backgroundColor
  })
});

const MaterialUIHooks = () => {
  const props = { backgroundColor: 'gray'};
  const classes = useStyles(props);
  return (
    <div>
      <p>MaterialUIHooks</p>
      <Button variant="contained" className={classes.button}>
        Test
      </Button>
      <Typography className={classes.msg}>これは<span>テスト</span>です</Typography>
      <Button variant="contained" className={classes.button2}>
        Test Prop
      </Button>
    </div>
  );
}

export default MaterialUIHooks;