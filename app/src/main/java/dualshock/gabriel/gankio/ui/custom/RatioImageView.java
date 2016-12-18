package dualshock.gabriel.gankio.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RatioImageView extends ImageView {

    private float ratio = 0;
    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratio > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
//            int width = getMeasuredWidth();
            if (width > 0) {
                // 由于float2int没有四舍五入机制，所以加0.5让结果更精确
                int height = (int) (width / ratio +0.5f);
                setMeasuredDimension(width, height);
                return;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setRatio(int imageWidth, int imageHeight) {
        if (imageHeight != 0) {
            ratio = (float) imageWidth / imageHeight;
        }
    }
}
