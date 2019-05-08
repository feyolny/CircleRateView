# CircleRateView
 带动画的百分比圆形控件
 
 ![image](https://raw.githubusercontent.com/feyolny/RelatedPictureCollection/master/CircleRateView/Video_2019-05-08_174957.gif)

# How do you use it in your project?
## Step 1. Add the JitPack repository to your build file

      allprojects{
          repositories {
		       maven { url 'https://jitpack.io' }
		        }
	      }
 
## Step 2. Add the dependency
 
    dependencies {
	         implementation 'com.github.feyolny:CircleRateView:v1.0.2'
	     }
 
 ## Step 3. Use it in your project
 
    <com.feyolny.view.CircleRateView
        android:id="@+id/circleRateView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        app:duration="2000"
        app:expressColor="#BBBBBB"
        app:expressSize="18dp"
        app:expressText="错误率"
        app:innerCircleColor="#4755E8"
        app:innerCircleStrokeWidth="1dp"
        app:outCircleColor="#dbdbdb"
        app:outCircleStrokeWidth="1dp"
        app:percentColor="#BBBBBB"
        app:percentSize="18dp"
        app:rateColor="#4755E8" />
 
 # Related API
 
  参数 | 描述 
  -|-
  innerCircleColor|内圆的颜色
  innerCircleStrokeWidth|内圆的线宽
  outCircleColor|外圆的颜色
  outCircleStrokeWidth|外圆的线宽
  rateText|比率(示例图中为 45)
  expressText|表达含义(示例图中为 错误率)
  rateColor|比率的颜色
  expressColor|表达含义的颜色
  percentColor|百分比的颜色
  rateSize|比率的文字大小
  expressSize|表达含义的文字大小
  percentSize|百分比的大小
  duration|动画的时长(单位ms)
  
