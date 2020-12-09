# StarSelectorSimple
StarSelectorSimple

## 自定义的RationBar
### 使用简单操作方便
#### 在根目录下的build.gradle添加如下代码

```
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```

#### 然后在module下的build.gradle添加如下代码

```
	dependencies {
	        implementation 'com.github.peoplewithnoname:StarSelectorSimple:1.0.0'
	}
```

#### 最重要的来了 在xml布局里面添加

```
    <com.superc.star.selector.SuperRationBar
        android:id="@+id/rationBar"
        android:layout_width="0dp"
        android:layout_height="50dp"
        android:layout_marginTop="10dp"
        android:background="@color/colorAccent"
        app:SuperRationBar_number="6"
        app:SuperRationBar_startPadding="10dp"
        app:SuperRationBar_startWidth="40dp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:mode="fixed" />
```
#### SuperRationBar_number总共显示的星星的数量
#### SuperRationBar_startPadding 表示星星之间的距离
#### SuperRationBar_startWidth表示一个星星的宽度

#### 使用也超级简单 直接上代码

```
        rationBar.setImageResIds(R.mipmap.star_full_1, R.mipmap.star_half_1, R.mipmap.star_empty_1)
                .setSelectNumber(2.4f)
                .launcher();
```
#### setImageResIds 分别设置选中状态的星星；选中一半状态的星星；没有选中状态的星星。这三个参数只有 选中一半状态的星星 为非必填（不要的时候传0就可以了，表示没有这种状态），其余两个为必填。
#### setSelectNumber 设置分数Float类型  比如：2.4表示2个相中状态和一个选中一半的状态
####  launcher 刚才设置的参数生效
#### 另外还有一个方法 用来表示控件是否可以进行选择

```
/**
     * 设置图片是否可以选择
     *
     * @param enable
     * @return
     */
    public SuperRationBar setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

        rationBar.setImageResIds(R.mipmap.star_full_1, R.mipmap.star_half_1, R.mipmap.star_empty_1)
                .setSelectNumber(2.4f)
                .setEnable(true)
                .launcher();

```
效果图看传送门

[传送门](https://editor.csdn.net/md/?articleId=110939248)

