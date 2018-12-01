package cn.cloudartisan.crius.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by kenqu on 2015/12/10.
 */
public class GridIcons  extends GridView {
        public GridIcons(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public GridIcons(Context context) {
            super(context);
        }

        public GridIcons(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }



}
