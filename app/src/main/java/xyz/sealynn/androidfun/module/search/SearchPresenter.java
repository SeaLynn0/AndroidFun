package xyz.sealynn.androidfun.module.search;

import xyz.sealynn.androidfun.base.BasePresenterImpl;

/**
 * Created by SeaLynn0 on 2018/12/26 13:14
 * <p>
 * Email：sealynndev@gmail.com
 */
public class SearchPresenter extends BasePresenterImpl implements SearchContract.Presenter {

    private final SearchContract.View mView;
    public SearchPresenter(SearchContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
