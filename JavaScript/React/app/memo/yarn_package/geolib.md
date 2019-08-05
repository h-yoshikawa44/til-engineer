## geolib
二つの緯度経度から距離を算出するライブラリ

### インストール
`$ yarn add geolib`

### 使い方
```js
import { getDistance } from 'geolib';

        // ※一部抜粋
        const distance = getDistance(
          { latitude: location.lat, longitude: location.lng }, // start地点の緯度経度
          { latitude: basicInfo.latitude, longitude: basicInfo.longitude }, // end地点の緯度経度
        );
```