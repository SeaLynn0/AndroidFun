package xyz.sealynn.androidfun.module.main;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import java.util.Objects;

import xyz.sealynn.androidfun.R;
import xyz.sealynn.androidfun.base.BasePresenterImpl;
import xyz.sealynn.androidfun.base.Constants;
import xyz.sealynn.androidfun.model.Result;
import xyz.sealynn.androidfun.net.RetrofitManager;
import xyz.sealynn.androidfun.receiver.NotificationClickReceiver;
import xyz.sealynn.androidfun.utils.DateUtils;
import xyz.sealynn.androidfun.utils.NotificationUtils;

/**
 * Created by SeaLynn0 on 2018/9/5 11:43
 * <p>
 * Email：sealynndev@gmail.com
 */
public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {

    public static final int LOGOUT = 1;

    MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void checkYearProgress() {
//        Logger.d(PreferenceManager.getDefaultSharedPreferences(getView().getContext()).getBoolean("notifications_year_progress",true));
        //  通知开启还是关闭
        if (PreferenceManager.getDefaultSharedPreferences(getView().getContext())
                .getBoolean("notifications_year_progress", true)) {
            //  现在的半分比和存储中的是否相等
            if (!Objects.equals(
                    getView().getContext().getSharedPreferences(Constants.CONFIG_DEFAULT, Context.MODE_PRIVATE)
                            .getString(Constants.YEAR_PROGRESS_PERCENT, "0%")
                    , DateUtils.getPercentsOfTheYearPassed())) {
                //  不相等就通知
                Intent intent = new Intent(getView().getContext(), NotificationClickReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getView().getContext(), 0, intent, 0);
                NotificationUtils notificationUtils = new NotificationUtils(getView().getContext(), "progress_channel"
                        , getView().getContext().getString(R.string.progress_chanel));
                notificationUtils.sendNotificationWithIntent("YearProgress", DateUtils.getYear() + " is "
                        + DateUtils.getPercentsOfTheYearPassed()
                        + " complete!", pendingIntent);
                //  保存新的半分比
                getView().getContext().getSharedPreferences(Constants.CONFIG_DEFAULT, Context.MODE_PRIVATE)
                        .edit().putString(Constants.YEAR_PROGRESS_PERCENT
                        , DateUtils.getPercentsOfTheYearPassed()).apply();
                //  保存通知模式
                getView().getContext().getSharedPreferences(Constants.CONFIG_DEFAULT, Context.MODE_PRIVATE)
                        .edit().putBoolean(Constants.YEAR_PROGRESS_MODE
                        , true).apply();
            }
        }
    }

    @Override
    public void logout() {
        new AlertDialog.Builder(getView().getContext())
                .setIcon(R.drawable.ic_action_logout)
                .setTitle(R.string.logout)
                .setMessage(R.string.confirm)
                .setPositiveButton(R.string.yes, (dialog, which) -> domine(RetrofitManager.getInstance().createReq().logout(), LOGOUT))
                .setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss())
                .show();

    }


    @Override
    public void onResponse(Object t, int what) {
        switch (what) {
            case LOGOUT:
                Result requestBody = Result.cast(t);
                if (requestBody.getErrorCode() == 0)
                    getView().logout();
                break;
        }
    }
}
