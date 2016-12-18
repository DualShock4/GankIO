package dualshock.gabriel.gankio.ui.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import dualshock.gabriel.gankio.R;
import dualshock.gabriel.gankio.bean.GirlsImageInfo;
import dualshock.gabriel.gankio.util.ConstantValue;

public class GalleryActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ArrayList<GirlsImageInfo> mList;
    private int mPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mList = (ArrayList<GirlsImageInfo>) intent.getSerializableExtra(ConstantValue.PICTURE);
        int position =  intent.getIntExtra(ConstantValue.POSITION, 0);
        viewPager.setAdapter(new GalleryAdapter());
        mPosition = position;
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class GalleryAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(getApplicationContext(), R.layout.item_gallery_activity, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            Glide.with(getApplicationContext())
                    .load(mList.get(position).getUrl())
                    .asBitmap()
                    .error(R.drawable.laugh)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    @Override
    public void onBackPressed() {
        setResult(ConstantValue.GIRLS_FRAGMENT_REQUEST,new Intent().putExtra(ConstantValue.POSITION, mPosition));
        super.onBackPressed();
    }
}
