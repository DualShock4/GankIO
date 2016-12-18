package dualshock.gabriel.gankio.adapter;

import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import dualshock.gabriel.gankio.R;
import dualshock.gabriel.gankio.bean.Gank;

public class KnowledgeHolder extends BaseHolder{

    private ArrayList<Gank.GankInfo> lists;

    KnowledgeHolder(ArrayList<Gank.GankInfo> lists) {
        this.lists = lists;
    }

    @BindView(R.id.desc)TextView desc;
    @BindView(R.id.author)TextView author;
    @BindView(R.id.time)TextView time;

    @Override
    public void initView(int position) {
        desc.setText(lists.get(position).desc);
        author.setText(lists.get(position).who);
        String[] times = lists.get(position).publishedAt.split("[a-zA-Z]");
        time.setText(times[0]);
    }
}
