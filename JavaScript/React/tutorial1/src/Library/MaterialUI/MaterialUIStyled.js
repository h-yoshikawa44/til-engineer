import React from 'react';
import { styled  } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';

const CustomButton = styled(Button)({
  backgroundColor: 'red'
});

const MaterialUIStyled = () => {
  return (
    <CustomButton>Test</CustomButton>
  );
}

export default MaterialUIStyled;