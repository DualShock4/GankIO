package dualshock.gabriel.gankio.adapter;


import android.content.Context;


import dualshock.gabriel.gankio.R;
import dualshock.gabriel.gankio.bean.Gank;

public class KnowledgeAdapter extends MyBaseAdapter<Gank>{
    public KnowledgeAdapter(Context context, Gank data) {
        super(context, data);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_knowledge_fragment;
    }

    @Override
    BaseHolder getHolder() {
        return new KnowledgeHolder(data.results);
    }


    @Override
    public int getCount() {
        return data.results.size();
    }
}
