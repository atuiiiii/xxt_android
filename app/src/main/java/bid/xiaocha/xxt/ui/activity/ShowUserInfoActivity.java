package bid.xiaocha.xxt.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.tu.loadingdialog.LoadingDialog;

import bid.xiaocha.xxt.R;
import bid.xiaocha.xxt.databinding.ActivityShowUserInfoBinding;
import bid.xiaocha.xxt.databinding.ContentShowUserInfoBinding;
import bid.xiaocha.xxt.iview.IShowUserInfoView;
import bid.xiaocha.xxt.model.UserEntity;
import bid.xiaocha.xxt.presenter.IShowUserInfoPresenter;
import bid.xiaocha.xxt.presenter.ShowUserInfoPrenster;
import bid.xiaocha.xxt.util.UITool;

import static bid.xiaocha.xxt.util.CommonUtils.showToast;

public class ShowUserInfoActivity extends AppCompatActivity implements IShowUserInfoView {
    private ActivityShowUserInfoBinding activityBinding;
    private ContentShowUserInfoBinding contentBinding;
    private IShowUserInfoPresenter presenter;
    private boolean isHaveMore = true;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_user_info);
        contentBinding = activityBinding.content;
        setSupportActionBar(activityBinding.toolbar);
        initDatas();
        initView();
    }
    private void initDatas(){
        Bundle bundle = getIntent().getExtras();
        String userId = bundle.getString("userId");
        if (userId == null || userId.equals("")) {
            showToast("信息加载错误");
            presenter = new ShowUserInfoPrenster(this,null);
        }else{
            presenter = new ShowUserInfoPrenster(this,userId);
            presenter.getUserInfo();
        }
    }
    private void initView() {
        loadingDialog = UITool.createLoadingDialog(this);
    }

    @Override
    public void showUserInfo(UserEntity userEntity) {
        if (userEntity == null){
            showToast("信息加载失败");
        }else{

        }
    }

    @Override
    public void showLoading() {
        UITool.showLoadingDialog(loadingDialog);
    }

    @Override
    public void dismissLoading() {
        UITool.dismissLoadingDialog(loadingDialog);
    }
}
