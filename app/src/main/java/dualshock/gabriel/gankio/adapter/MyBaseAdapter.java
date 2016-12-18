package dualshock.gabriel.gankio.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


abstract class MyBaseAdapter<T> extends BaseAdapter{

    public final T data;
    private Context context;

    MyBaseAdapter(Context context,T data) {
        this.data = data;
        this.context =  context;
    }

    @Override
    public T getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, getLayoutId(), null);
            holder = getHolder();
            holder.findView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        holder.initView(position);
        return convertView;
    }

    public T getData() {
        return data;
    }

    abstract int getLayoutId();


    abstract BaseHolder getHolder();


}
