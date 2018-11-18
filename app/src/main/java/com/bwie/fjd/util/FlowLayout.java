package com.bwie.fjd.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FlowLayout extends ViewGroup {
    private int horizontalSpacing = 15;//水平的间距
    private int verticalSpacing = 15;//行与行的垂直间距

    //用来存放所有的Line对象
    private ArrayList<Line> lineList = new ArrayList<Line>();
    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public FlowLayout(Context context) {
        super(context);
    }

    public void setHorizontalSpacing(int horizontalSpacing){
        this.horizontalSpacing = horizontalSpacing;
    }
    public void setVerticalSpacing(int verticalSpacing){
        this.verticalSpacing = verticalSpacing;
    }

    /**
     * 遍历所有的子View，进行分行的逻辑操作，相当于定好座位表
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
// LogUtil.e(this, "onMeasure");
//由于onMeasure方法可能会执行多次，所以最好先清空
        lineList.clear();

//1.获取FLowLayout的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
//2.获取实际用于比较的宽度，其实就是除去两边的padding值的宽度
        int noPaddingWidth = width - getPaddingLeft()-getPaddingRight();

//3.遍历所有的子View，跟noPaddingWidth进行比较，
        Line line = new Line();//准备Line对象
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);//获取子VIew对象
//通知view的onMeasuce回调，保证能够获取到宽高的值
            childView.measure(0, 0);

//4.如果当前Line中木有子View，那么则直接将child加入到Line，因为不能有空行
            if(line.getViewList().size()==0){
                line.addLineView(childView);
            }else if (childView.getMeasuredWidth()+line.getLineWidth()+horizontalSpacing>noPaddingWidth) {
//5.说明childView需要放入下一行，
//注意：需要先记录之前的Line，否则会造成之前的Line丢失
                lineList.add(line);

//重新创建新的Line
                line = new Line();
                line.addLineView(childView);//放入新的Line对象中
            }else {
//6.说明child应该放入当前Line中
                line.addLineView(childView);
            }

//7.当for循环结束后，会造成最后的Line对象丢失，所以需要手动保存
            if(i==(getChildCount()-1)){
                lineList.add(line);//保存最后的Line
            }

        }

//for循环结束后，lineList中存放了所有Line对象，而 每个Line对象又记录自己当前行的所有子View;
//计算FlowLayout的高度,
        int height = getPaddingTop()+getPaddingBottom();//首先加上上下的padding值
//再加上所有行的高度
        for (int i = 0; i < lineList.size(); i++) {
            height += lineList.get(i).getLineHeight();
        }
//最后再加上每行直接的垂直间距
        height += (lineList.size()-1)*verticalSpacing;

//设置FlowLayout的宽高,向父VIew申请宽高
        setMeasuredDimension(width, height);
    }

    /**
     * 将所有的line中的子View摆放到指定的位置上
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);//获取line对象

//从第2行开始，每行的top总是比上一行的top多一个行高和垂直间距
            if(i>0){
                paddingTop += lineList.get(i-1).getLineHeight()+verticalSpacing;
            }

            ArrayList<View> viewList = line.getViewList();//获取每行的view集合

//1.计算每行的留白
            int remainSpacing = getLineRemainSpacing(line);
//2.计算每个view对象平均得到的值
            float perSpacing = remainSpacing/viewList.size();
            for (int j = 0; j < viewList.size(); j++) {
                View child = viewList.get(j);
//3.将perSpacing增加到每个子VIew的宽度上面
                int widthSpec = MeasureSpec.makeMeasureSpec((int) (child.getMeasuredWidth()+perSpacing),MeasureSpec.EXACTLY);
                child.measure(widthSpec,0);//高度不用变，所以传0

                if(j==0){
//摆放每行的第1个view
                    child.layout(paddingLeft,paddingTop,paddingLeft+child.getMeasuredWidth()
                            ,paddingTop+child.getMeasuredHeight());
                }else {
                    View preChild = viewList.get(j-1);//获取前一个View
//当前的left等于前一个View的right+水平间距
                    int left = preChild.getRight()+horizontalSpacing;
                    child.layout(left,preChild.getTop(),left+child.getMeasuredWidth(),preChild.getBottom());
                }
            }
        }
    }
    /**
     * 获取指定line对象的留白值
     * @param line
     * @return
     */
    private int getLineRemainSpacing(Line line){
        return getMeasuredWidth()-getPaddingLeft()-getPaddingRight()-line.getLineWidth();
    }

    /**
     * 行对象，用来封装每一行的数据，包括子View，宽度和高度
     * @author Administrator
     *
     */
    class Line{
        private ArrayList<View> viewList = new ArrayList<View>();//用来存放当前行所有的View对象
        private int lineWidth;//行的宽度,表示的是所有子View的宽+水平间距
        private int lineHeight;//行的高度,在摆放子View的时候会用到

        /**
         * 往viewList中添加view对象
         * @param child
         */
        public void addLineView(View child){
            if(!viewList.contains(child)){
                viewList.add(child);

//1.更新lineWidth
                if(viewList.size()==1){
//如果当前只有1个child，那么就是child的宽度
                    lineWidth = child.getMeasuredWidth();
                }else {
//如果是不第1个，则应该在当前宽的基础上+水平间距+child的宽
                    lineWidth += horizontalSpacing + child.getMeasuredWidth();
                }

//2.更新lineHeight
                lineHeight = Math.max(lineHeight, child.getMeasuredHeight());
            }
        }

        /**
         * 获取当前行的所有的子View
         * @return
         */
        public ArrayList<View> getViewList(){
            return viewList;
        }
        /**
         * 获取行宽
         * @return
         */
        public int getLineWidth(){
            return lineWidth;
        }
        /**
         * 获取行高
         * @return
         */
        public int getLineHeight(){
            return lineHeight;
        }
    }

}
