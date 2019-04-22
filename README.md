# CircleRateView
 带动画的百分比圆形控件
 
 ![image](https://raw.githubusercontent.com/feyolny/RelatedPictureCollection/master/CircleRateView/Video_2019-04-19_172345.gif)

# How do you use it in your project?
## Step 1. Add the JitPack repository to your build file

      allprojects{
          repositories {
		       maven { url 'https://jitpack.io' }
		        }
	      }
 
## Step 2. Add the dependency
 
    dependencies {
	         implementation 'com.github.feyolny:CircleRateView:v1.0.0'
	     }
 
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
  
