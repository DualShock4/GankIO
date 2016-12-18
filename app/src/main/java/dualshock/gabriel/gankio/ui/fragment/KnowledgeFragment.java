package dualshock.gabriel.gankio.ui.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import butterknife.BindView;
import dualshock.gabriel.gankio.R;
import dualshock.gabriel.gankio.adapter.KnowledgeAdapter;
import dualshock.gabriel.gankio.api.Api;
import dualshock.gabriel.gankio.api.GankApi;
import dualshock.gabriel.gankio.bean.Gank;
import dualshock.gabriel.gankio.ui.activity.WebActivity;
import dualshock.gabriel.gankio.util.ConstantValue;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;


public class KnowledgeFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.home_fragment_lv)
    ListView listView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.text_view)
    TextView textView;
    private static final int count = 10;
    private String type;
    private KnowledgeAdapter knowledgeAdapter;
    private static final String TAG = "KnowledgeFragment";
    private ArrayList<Gank.GankInfo> results;
    private int page = 0;
    private boolean isRequesting = false;

    @Override
    protected int getLayout() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initView() {
        type = getArguments().getString(ConstantValue.TYPE);
        requestData();
        listView.setOnItemClickListener(new MyItemClickListener());
        listView.setOnScrollListener(new MyScrollListener());
        textView.setOnClickListener(this);
    }

    private void requestData() {
        page++;
        progressBar.setVisibility(View.VISIBLE);
        isRequesting = true;
        Api.getApi(type,count,page).enqueue(new Callback<Gank>() {
            @Override
            public void onResponse(Call<Gank> call, Response<Gank> response) {
                isRequesting = false;
                progressBar.setVisibility(View.GONE);
                if (knowledgeAdapter == null) {
                    results = response.body().results;
                    knowledgeAdapter = new KnowledgeAdapter(getContext(), response.body());
                    listView.setAdapter(knowledgeAdapter);
                } else {
                    Gank gank = knowledgeAdapter.getData();
                    gank.results.addAll(response.body().results);
                    knowledgeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Gank> call, Throwable t) {
                startAnimation();
                Log.i(TAG, "onFailure: SomeThingWrong");
                page--;
                isRequesting = false;
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public static KnowledgeFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(ConstantValue.TYPE, type);
        KnowledgeFragment fragment = new KnowledgeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class MyItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(ConstantValue.URL, results.get(position).url);
            intent.putExtra(ConstantValue.TITLE, type);
            startActivity(intent);
        }
    }

    private class MyScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (knowledgeAdapter != null) {
                if (view.getLastVisiblePosition() == knowledgeAdapter.getCount() - 1&&!isRequesting) {
                    requestData();
                    textView.setVisibility(GONE);
                }
            }
        }
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

    @Override
    public void onClick(View v) {
        textView.setVisibility(GONE);
        progressBar.setVisibility(View.VISIBLE);
        requestData();
    }
}
