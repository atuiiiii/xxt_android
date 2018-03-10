package bid.xiaocha.xxt.iview;

/**
 * Created by lenovo-pc on 2018/3/5.
 */

public interface ICollectServeView {
    void showLoading();
    void dismissLoading();
    void isCollectResult(short isResult);
    void showResult(short result);
}
