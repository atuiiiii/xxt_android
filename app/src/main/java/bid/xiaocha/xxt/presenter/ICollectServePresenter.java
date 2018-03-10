package bid.xiaocha.xxt.presenter;

/**
 * Created by lenovo-pc on 2018/3/5.
 */

public interface ICollectServePresenter {
    void getCollectListByPage(String userId ,int pageNum);
    void findOneCollect(String userId ,String serveId);
    void addOneCollect(String userId , String serveId);
    void deleteCollect(String userId , String serveId);
}
