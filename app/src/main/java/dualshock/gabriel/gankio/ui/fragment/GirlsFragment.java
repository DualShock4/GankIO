package dualshock.gabriel.gankio.ui.fragment;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import butterknife.BindView;
import dualshock.gabriel.gankio.R;
import dualshock.gabriel.gankio.adapter.GirlsAdapter;
import dualshock.gabriel.gankio.api.Api;
import dualshock.gabriel.gankio.bean.Gank;
import dualshock.gabriel.gankio.bean.GirlsImageInfo;
import dualshock.gabriel.gankio.util.ConstantValue;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;


public class GirlsFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.text_view)
    TextView textView;

    private static final int columnCount = 2;
    private static final int count = 30;
    private String type;
    private int page = 0;
    private GirlsAdapter mAdapter;
    private static final String TAG = "GirlsFragment";
    private boolean isRequesting = false;
    private ArrayList<Gank.GankInfo> mList;
    private StaggeredGridLayoutManager layoutManager;
    private int[] columnArray;
    private ArrayList<GirlsImageInfo> mImages;
    private AsyncTask<Void, Void, Void> mAsyncTask;

    @Override
    protected int getLayout() {
        return R.layout.fragment_girls;
    }

    @Override
    protected void initView() {

        type = getArguments().getString(ConstantValue.TYPE);
        layoutManager = new StaggeredGridLayoutManager(columnCount,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        columnArray = new int[columnCount];

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                layoutManager.findLastCompletelyVisibleItemPositions(columnArray);
                int maxElem = columnArray[0] > columnArray[1] ? columnArray[0] : columnArray[1];
                if (!isRequesting&& maxElem + 1 == mAdapter.getItemCount()) {
                    requestData();
                    textView.setVisibility(GONE);
                }
            }
        });
        requestData();

        textView.setOnClickListener(this);
    }




    private void requestData() {
        page++;
        isRequesting = true;
        progressBar.setVisibility(View.VISIBLE);
        Api.getApi(type, count, page).enqueue(new Callback<Gank>() {

            @Override
            public void onResponse(Call<Gank> call, Response<Gank> response) {
                mList = response.body().results;
                loadImage();
            }

            @Override
            public void onFailure(Call<Gank> call, Throwable t) {
                page--;
                isRequesting = false;
                progressBar.setVisibility(GONE);
                startAnimation();
            }

        });
    }

    private void startAnimation() {
        textView.setVisibility(View.VISIBLE);
        final int height = textView.getHeight();
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(textView, "show", 1.0f, 0.0f)
                .setDuration(500);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                textView.setAlpha(1-value);
                textView.setTranslationY(height * value);
            }
        });
    }

    private void loadImage() {
        mImages = new ArrayList<>();
        mAsyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                for (Gank.GankInfo gankInfo : mList) {
                    mImages.add(setImageList(gankInfo.url));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                isRequesting = false;
                progressBar.setVisibility(GONE);
                super.onPostExecute(aVoid);
                if (mAdapter == null) {
                    mAdapter = new GirlsAdapter(GirlsFragment.this, mImages, getActivity());
                    recyclerView.setAdapter(mAdapter);
                } else {
                    ArrayList<GirlsImageInfo> data = mAdapter.getData();
                    int from = data.size();
                    int size = mImages.size();
                    data.addAll(mImages);
                    mAdapter.notifyItemRangeChanged(from, size);
                }
            }
        }.execute();
    }

    private GirlsImageInfo setImageList(String url) {
        GirlsImageInfo girlsImageInfo = new GirlsImageInfo(url);
        try {
            Bitmap bitmap = Glide.with(getActivity())
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap != null) {
                girlsImageInfo.setWidth(bitmap.getWidth());
                girlsImageInfo.setHeight(bitmap.getHeight());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return girlsImageInfo;
    }

    public static GirlsFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(ConstantValue.TYPE, type);
        GirlsFragment fragment = new GirlsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: ");
        if (resultCode == ConstantValue.GIRLS_FRAGMENT_REQUEST) {
            recyclerView.scrollToPosition(data.getIntExtra(ConstantValue.POSITION, 0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mAsyncTask!=null)mAsyncTask.cancel(true);
    }


    @Override
    public void onClick(View v) {
        textView.setVisibility(GONE);
        progressBar.setVisibility(View.VISIBLE);
        requestData();
    }
}
