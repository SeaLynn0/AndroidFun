package xyz.sealynn.androidfun.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.sealynn.androidfun.R;
import xyz.sealynn.androidfun.module.main.MainActivity;

/**
 * Created by SeaLynn0 on 2018/10/13 1:36
 * <p>
 * Email：sealynndev@gmail.com
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.layout_splash)
    ConstraintLayout layout;

    AlphaAnimation alphaAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        alphaAnimation = new AlphaAnimation(0.3F, 1.0F);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpToMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        layout.startAnimation(alphaAnimation);
    }

    private void jumpToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
