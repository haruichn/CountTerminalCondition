# 軸補正ツール

## 概要
HASCデータに含まれるTerminalPositionとTerminalMountの組み合わせを表示するプログラム

## 使い方
#### IntelliJ IDEAから使う場合
1. このプロジェクトを読み込む
2. dataディレクトリにHASCコーパスのデータを入れる.
3. 実行
4. outputディレクトリが作成され, そこに補正されたデータが出力される.

#### コマンドラインから使う場合
1. CountTerminalCondition.javaを適当なティレクトリにコピー
2. CountTerminalCondition.javaがある階層にcdで移動
3. CountTerminalCondition.javaと同ディレクトリにdataディレクトリを用意.
4. dataディレクトリにHASCコーパスのデータを入れる.
5. 以下のコマンドを実行
6. outputディレクトリが作成され, そこに補正されたデータが出力される.

##### コマンド
```
javac CountTerminalCondition.java
java CountTerminalCondition
```


### データのディレクトリ構成
```
.  
data  
├── 1_stay  
│  ├── person0001  
│  │   ├── HASCXXXXXX-acc.csv  
│  │   ├── HASCXXXXXX-gyro.csv  
│  │   ├── HASCXXXXXX-mag.csv  
│  │   └── ...  
│  ├── person0002  
│  └── ...  
├── 2_walk  
│　├── person0001  
│　│   ├── HASCXXXXXX-acc.csv  
│　│   ├── HASCXXXXXX-gyro.csv  
│　│   ├── HASCXXXXXX-mag.csv  
│　│   └── ...  
│　├── person0002  
│　└── ...  
└── ...
```


### 　
Developed by icchi  
2016/03/25
