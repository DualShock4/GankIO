package dualshock.gabriel.gankio.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import dualshock.gabriel.gankio.R;
import dualshock.gabriel.gankio.ui.custom.RatioImageView;

public class AboutActivity extends BaseActivity{

    @BindView(R.id.image_view)
    RatioImageView ratioImageView;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_about;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Drawable drawable = ratioImageView.getDrawable();
        ratioImageView.setRatio(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    @Override
    public void startActivity(Intent intent) {
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfos.size() > 0) {
            super.startActivity(intent);
        } else {
            Toast.makeText(this, "未安装邮箱app", Toast.LENGTH_SHORT).show();
        }
    }
}
