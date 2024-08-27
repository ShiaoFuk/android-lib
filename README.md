# 分支
## MyCardView
### 用途
是录音器的竖线图显示，根据接受的音量大小实时更新
### 使用方法
自行创建录音器类，使用官方的录音器类即可，然后计算每个时间片的音量float类型，调用该类的addWaveLine(float length)函数即可自行绘制


## PieChart
### 用途
鉴于官方没有饼状图供使用，自行绘制了一个，继承自View类，绘制一个圆弧状的统计图，目前根据需求只有四个部分，可以自行修改添加。绘制后会自带legend在左侧。
### 使用方法
1. 设置半径
   调用setRadius(float radius)设置圆弧半径控制大小，注意要小于组件的大小，否则会溢出
2. 添加内容
  1. 添加一个内容
     addItem(String key, float value)，key是对应的图例文字说明，value是对应占据图的百分比
  2. 添加多个内容，基本同上
     addItems(List<String> keylist, List<Float> valueList)



## LoadingView
### 用途
鉴于官方的loadingView样式太固定，而且不太好看，自定义了一个圆形的loadingView，继承自View类，仿照网易云听歌识曲的样式，取组件宽高的较小值为最外层直径。
### 使用方法
静态在xml中添加或动态添加都可。


## 联系方式
mashufu@outlook.com
