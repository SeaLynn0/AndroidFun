package cn.mlynn.androidfun.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import cn.mlynn.androidfun.utils.ToastUtils;

public abstract class BaseFragment<VM extends BaseViewModel, VB extends ViewBinding> extends ExBaseFragment<VB> {

    /**
     * ViewModel
     */
    private VM viewModel;

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = initViewModel();
        initViewModelEvent();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initViewModelEvent() {
        viewModel.getActionLiveData().observe(getViewLifecycleOwner(), baseEventAction -> {
            if (baseEventAction != null) {
                if (baseEventAction.isCurrentAction(BaseEventAction.ACTION.SHOW_LOADING))
                    startLoading();
                else if (baseEventAction.isCurrentAction(BaseEventAction.ACTION.DISMISS_LOADING))
                    dismissLoading();
                else if (baseEventAction.isCurrentAction(BaseEventAction.ACTION.SHOW_TOAST))
                    showToast(baseEventAction.getMessage());
            }
        });
    }

    private void showToast(String message) {
        ToastUtils.shortToast(getContext(), message);
    }

    protected abstract void dismissLoading();

    protected abstract void startLoading();

    protected abstract VM initViewModel();

    public VM getViewModel() {
        return viewModel;
    }
}
