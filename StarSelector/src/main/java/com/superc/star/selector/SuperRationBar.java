package com.superc.star.selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论专用星星
 * <p>
 * 宽高都不能用wrap_content 必须使用固定值或者match_parent
 * <p>
 * MIXED : 在控件的宽度范围内等分星星
 * <p>
 * SCROLL:根据 星星的宽度和每个星星之间的间距画星星
 */
public class SuperRationBar extends View implements View.OnTouchListener {

    final public static int MIXED = 0;

    final public static int SCROLL = 1;

    //不传默认为 MIXED
    private int mode = MIXED;
    // 需要建立多少星星 不传 默认为5
    private int number = 5;
    // 单个星星的宽度 这里宽度和高度相等  必传
    private int startWidth = 50;
    // 每个星星之间的间距 默认20 (mode == MIXED 用不到)
    private int startPadding = 10;


    //是否已经初始化试图
    private boolean isInit = false;
    //被选中的个数 可以为0.5
    private float selectNumber = 0;
    //选中的样式
    private Bitmap bmSel;
    //未选中的样式
    private Bitmap bmNol;
    //一半为选中的样式
    private Bitmap bmHalf;
    //记录每个星星的位置 用 , 分割
    private List<String> pointList;
    // 画笔
    private Paint mPaint;

    public SuperRationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        setOnTouchListener(this);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SuperRationBar);
        mode = a.getInteger(R.styleable.SuperRationBar_mode, MIXED);
        number = a.getInteger(R.styleable.SuperRationBar_SuperRationBar_number, 5);
        startWidth = (int) a.getDimension(R.styleable.SuperRationBar_SuperRationBar_startWidth, 50);
        startPadding = (int) a.getDimension(R.styleable.SuperRationBar_SuperRationBar_startPadding, 10);
        a.recycle();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!isInit) {
            return;
        }
        {//记录每个星星的位置 用 , 分割
            pointList = new ArrayList<>();
        }
        if (mode == MIXED) {
            //每个星星之间的间距
            int padding = (getWidth() - startWidth * number) / (number - 1);
            //根据每个星星之间的间距画星星
            for (int i = 0; i < number; i++) {
                int left = i == 0 ? 0 : (startWidth * i + padding * i);
                int top = (getHeight() - startWidth) / 2;
                pointList.add(left + "," + top + "," + (left + startWidth) + "," + (top + startWidth));
                int j = i + 1;
                int _i = (int) selectNumber;
                int fullStarNum = _i;
                if (j <= fullStarNum) {
                    canvas.drawBitmap(bmSel, left, top, mPaint);
                } else if (selectNumber > fullStarNum && selectNumber <= fullStarNum + 0.5 && fullStarNum == i && bmHalf != null) {
                    canvas.drawBitmap(bmHalf, left, top, mPaint);
                } else {
                    canvas.drawBitmap(bmNol, left, top, mPaint);
                }
            }
        } else if (mode == SCROLL) {
            int totalWidth = (startWidth + startPadding) * (number - 1) + startWidth;
            //单个星星的宽度
            int itemWidth = totalWidth / number;
            //根据每个星星之间的间距画星星
            for (int i = 0; i < number; i++) {
                int left = i == 0 ? 0 : itemWidth * i;
                int top = (getHeight() - startWidth) / 2;
                pointList.add(left + "," + top + "," + (left + itemWidth) + "," + (top + itemWidth));
                int j = i + 1;
                int _i = (int) selectNumber;
                int fullStarNum = _i;
                if (j <= fullStarNum) {
                    canvas.drawBitmap(bmSel, left, top, mPaint);
                } else if (selectNumber > fullStarNum && selectNumber <= fullStarNum + 0.5 && fullStarNum == i && bmHalf != null) {
                    canvas.drawBitmap(bmHalf, left, top, mPaint);
                } else {
                    canvas.drawBitmap(bmNol, left, top, mPaint);
                }
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        isInit = true;
    }

    /**
     * 设置三种图片样式的id
     *
     * @param selId
     * @param nolId
     */
    public SuperRationBar setImageResIds(int selId, int halfId, int nolId) {
        if (selId != 0) {
            bmSel = BitmapFactory.decodeResource(getResources(), selId);
            bmSel = zoomBitmap(bmSel, startWidth);
        }
        if (halfId != 0) {
            bmHalf = BitmapFactory.decodeResource(getResources(), halfId);
            bmHalf = zoomBitmap(bmHalf, startWidth);
        }
        if (nolId != 0) {
            bmNol = BitmapFactory.decodeResource(getResources(), nolId);
            bmNol = zoomBitmap(bmNol, startWidth);
        }
        return this;
    }

    public SuperRationBar setSelectNumber(float selectNumber) {
        this.selectNumber = selectNumber;
        return this;
    }

    /**
     * 调用这个方法刷新页面
     */
    public void launcher() {
        if (isInit) {
            postInvalidate();
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    postInvalidate();
                }
            });
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (pointList != null) {
                float num = contain((int) event.getX(), (int) event.getY());
                if (num != -1) {
                    selectNumber = num + 1;
                }
                postInvalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断点击的位置是不是在星星上边  并返回星星的下标  错误 返回-1
     *
     * @param x
     * @param y
     * @return
     */
    private float contain(int x, int y) {
        int size = pointList.size();
        for (int i = 0; i < size; i++) {
            String[] pointArray = pointList.get(i).split(",");
            int rl = Integer.parseInt(pointArray[0]);
            int rt = Integer.parseInt(pointArray[1]);
            int rr = Integer.parseInt(pointArray[2]);
            int rb = Integer.parseInt(pointArray[3]);
            if (bmHalf == null) {
                if (x > rl && x < rr) {
                    return i;
                }
            } else {
                if (x > rl && x <= rl + startWidth / 2) {
                    //在范围内 返回下标
                    return i - 0.5f;
                } else if (x > rr - startWidth / 2 && x <= rr) {
                    return i;
                }
            }
        }
        return -1;
    }

    public float getSelectNumber() {
        return selectNumber;
    }


    /**
     * 等比例缩放bitmap图片
     *
     * @param bitmap
     * @param reqWidth
     * @return
     */
    public Bitmap zoomBitmap(Bitmap bitmap, float reqWidth) {
        if (bitmap == null) {
            return null;
        }
        final int width = bitmap.getWidth();
        Matrix matrix = new Matrix();
        float scale = reqWidth / width;
        matrix.setScale(scale, scale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bitmap;
    }

}

