package bid.xiaocha.xxt.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDialog;

import bid.xiaocha.xxt.R;
import bid.xiaocha.xxt.databinding.ContentLoginRegisterBinding;
import bid.xiaocha.xxt.iview.ILoginView;
import bid.xiaocha.xxt.iview.IVerificationCodeView;

import bid.xiaocha.xxt.presenter.ILoginPresenter;
import bid.xiaocha.xxt.presenter.IVerificationPresenter;
import bid.xiaocha.xxt.presenter.LoginPresenter;
import bid.xiaocha.xxt.presenter.VerificationPresenter;
import bid.xiaocha.xxt.util.UITool;

import static bid.xiaocha.xxt.util.CommonUtils.showToast;

public class LoginRegisterActivity extends AppCompatActivity implements ILoginView,IVerificationCodeView,View.OnClickListener{
    private ILoginPresenter loginPresenter;
    private IVerificationPresenter verificationPresenter;

    private ContentLoginRegisterBinding binding;
    private android.os.Handler uiHandler = new Handler();
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.content_login_register, (ViewGroup) this.findViewById(R.id.login_register_content), true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initPresenter();
        initView();
    }

    private  void initPresenter(){
        loginPresenter = new LoginPresenter(this);
        verificationPresenter = new VerificationPresenter(this);
    }
    private void initView() {
        getSupportActionBar().setTitle("登陆");
        binding.btnLogin.setOnClickListener(this);
        binding.btnGetVerificationCode.setOnClickListener(this);
        loadingDialog = UITool.createLoadingDialog(this);
    }

    @Override
    public void showLoading() {
       UITool.showLoadingDialog(loadingDialog);
    }

    @Override
    public void showLoginResult(final ILoginPresenter.LoginResult result) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                if (result == ILoginPresenter.LoginResult.SUCCESS){
                    showToast("登陆成功");
                    Intent intent = new Intent(LoginRegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }else if (result == ILoginPresenter.LoginResult.FAILED_CHECK_OUT_ERROR){
                    showToast("验证码错误，请重试");
                }else if (result == ILoginPresenter.LoginResult.FAILED_NETWORK_ERROR){
                    showToast("网络错误");
                }else if (result == ILoginPresenter.LoginResult.FAILED_UNKNOWN_ERROR){
                    showToast("服务器故障");
                }
            }
        });

    }

    @Override
    public void dismissLoading() {
        UITool.dismissLoadingDialog(loadingDialog);
    }

    @Override
    public void onRequestVerification() {

    }

    @Override
    public void requestVerificationSucceeded() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast("短信已发送到你的手机，请注意查收");
            }
        });
    }

    @Override
    public void requestVerificationFailed() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast("短信发送失败，请重试");
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login){
            String userId = binding.edtUserId.getText().toString().trim();
            String verificationCode = binding.edtVerificationCode.getText().toString().trim();
            if ("".equals(userId)||"".equals(verificationCode)){
                Toast.makeText(this, "请输入手机号或验证码", Toast.LENGTH_SHORT).show();
                return;
            }
            loginPresenter.loginByVerificationCode(userId, verificationCode);
        }else if(v.getId() == R.id.btn_get_verification_code){
            String userId = binding.edtUserId.getText().toString().trim();
            if ("".equals(userId)){
                Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }else {
                verificationPresenter.requestVerificationCode(userId);
            }
        }
    }
}
