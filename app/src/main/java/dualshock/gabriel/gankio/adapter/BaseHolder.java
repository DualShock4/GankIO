package dualshock.gabriel.gankio.adapter;

import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseHolder {


    public void findView(View convertView) {
        ButterKnife.bind(this, convertView);
    }

    public abstract void initView(int position);


}
