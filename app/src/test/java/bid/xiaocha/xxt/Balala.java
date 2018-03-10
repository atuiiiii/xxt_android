package bid.xiaocha.xxt;

import org.junit.Test;

import bid.xiaocha.xxt.iview.ILoginView;
import bid.xiaocha.xxt.presenter.ILoginPresenter;
import bid.xiaocha.xxt.presenter.LoginPresenter;

/**
 * Created by 55039 on 2017/11/5.
 */

public class Balala {
    @Test
    public void testBalala(){
        LoginPresenter presenter = new LoginPresenter(new ILoginView() {
            @Override
            public void showLoading() {

            }

            @Override
            public void showLoginResult(ILoginPresenter.LoginResult result) {

            }

            @Override
            public void dismissLoading() {

            }
        });

//        presenter.loginByPassword();
    }
}
