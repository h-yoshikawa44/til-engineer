## lodash

### インストール
`$ yarn add lodash`

### 使い方
元々、`_.js`という名称だった名残で、`_`でインポートする

sortBy  
配列を指定されたキーでソートする

例
```js
import _ from 'lodash';

const sortedHotels = (hotels, sortKey) => _.sortBy(hotels, h => h[sortKey]);
```