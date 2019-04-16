package xyz.sealynn.androidfun.module.register;

import xyz.sealynn.androidfun.base.BasePresenter;
import xyz.sealynn.androidfun.base.BaseView;

/**
 * Created by SeaLynn0 on 2018/12/25 18:09
 * <p>
 * Email：sealynndev@gmail.com
 */
public interface RegisterContract {

    interface View extends BaseView {
        void freeze();
        void unFreeze();
    }

    interface Presenter extends BasePresenter<View> {
        void register(String u, String p, String rp);
    }
}
