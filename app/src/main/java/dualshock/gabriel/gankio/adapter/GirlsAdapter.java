package dualshock.gabriel.gankio.adapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dualshock.gabriel.gankio.R;
import dualshock.gabriel.gankio.bean.GirlsImageInfo;
import dualshock.gabriel.gankio.ui.activity.GalleryActivity;
import dualshock.gabriel.gankio.ui.custom.RatioImageView;
import dualshock.gabriel.gankio.ui.fragment.GirlsFragment;
import dualshock.gabriel.gankio.util.ConstantValue;

public class GirlsAdapter extends RecyclerView.Adapter<GirlsAdapter.GirlsHolder>{

    private final ArrayList<GirlsImageInfo> images;
    private Activity mActivity;
    private Fragment fragment;

    public GirlsAdapter(GirlsFragment girlsFragment, ArrayList<GirlsImageInfo> images, FragmentActivity activity) {
        fragment = girlsFragment;
        this.images = images;
        mActivity = activity;
    }

    @Override
    public GirlsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GirlsHolder(LayoutInflater.from(mActivity)
                .inflate(R.layout.item_girls_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final GirlsHolder holder, final int position) {
        GirlsImageInfo imageInfo = images.get(position);
        Glide.with(mActivity)
                .load(imageInfo.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .error(R.drawable.laugh)
                .into(holder.ratioImageView);
        holder.ratioImageView.setRatio(imageInfo.getWidth(), imageInfo.getHeight());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public ArrayList<GirlsImageInfo> getData() {
        return images;
    }

    class GirlsHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ratio_iv)
        RatioImageView ratioImageView;

        GirlsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v, getAdapterPosition());
                }
            });
        }
    }

    private void onItemClick(View v, int adapterPosition) {
        Intent intent = new Intent(mActivity, GalleryActivity.class);
        intent.putExtra(ConstantValue.PICTURE, images);
        intent.putExtra(ConstantValue.POSITION, adapterPosition);
        fragment.startActivityForResult(intent,ConstantValue.GIRLS_FRAGMENT_REQUEST);
    }


}
