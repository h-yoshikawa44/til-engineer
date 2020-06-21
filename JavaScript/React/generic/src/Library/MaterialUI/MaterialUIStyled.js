import React from 'react';
import { styled  } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';

const CustomButton = styled(Button)({
  backgroundColor: 'red'
});

const MaterialUIStyled = () => {
  return (
    <div>
      <p>MaterialUIStyled</p>
      <CustomButton>Test</CustomButton>
    </div>
  );
}

export default MaterialUIStyled;