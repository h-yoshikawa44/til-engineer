import React from 'react';
import Konva from 'konva';
import { Stage, Layer, Star, Text } from 'react-konva';
// import useImage from 'use-image';

const Component = () => {
  const handleDragStart = e => {
    e.target.setAttrs({
      shadowOffset: {
        x: 15,
        y: 15
      },
      scaleX: 1.1,
      scaleY: 1.1
    });
  };
  const handleDragEnd = e => {
    e.target.to({
      duration: 0.5,
      easing: Konva.Easings.ElasticEaseOut,
      scaleX: 1,
      scaleY: 1,
      shadowOffsetX: 5,
      shadowOffsetY: 5
    });
  };

  return (
    <React.Fragment>
      <Stage width={window.innerWidth} height={window.innerHeight}>
        <Layer width={720}>
          <Text text="Try to drag a star" />
          {[...Array(10)].map((_, i) => (
            <Star
              key={i}
              x={Math.random() * 500}
              y={Math.random() * window.innerHeight}
              numPoints={5}
              innerRadius={20}
              outerRadius={40}
              fill="#89b7"
              opacity={0.8}
              draggable
              rotation={Math.random() * 180}
              shadowColor="black"
              shadowBlur={10}
              shadowOpacity={0.6}
              onDragStart={handleDragStart}
              onDragEnd={handleDragEnd}
            />
          ))}
        </Layer>
      </Stage>
    </React.Fragment>
    );
  }


export default Component;
