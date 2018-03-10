package bid.xiaocha.xxt.presenter;

import bid.xiaocha.xxt.iview.ICollectServeView;
import bid.xiaocha.xxt.iview.IShowServeView;
import bid.xiaocha.xxt.model.CollectResult;
import bid.xiaocha.xxt.model.GetResultByPage;
import bid.xiaocha.xxt.service.NetService;
import bid.xiaocha.xxt.util.JwtUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo-pc on 2018/3/5.
 */

public class CollectServePresenter implements ICollectServePresenter {

    private IShowServeView<CollectResult> view;
    private ICollectServeView collectServeView;

    public CollectServePresenter(IShowServeView<CollectResult> view ,ICollectServeView collectServeView){
        this.view = view;
        this.collectServeView = collectServeView;
    }
    public CollectServePresenter(IShowServeView<CollectResult> view){
        this.view = view;
    }
    public CollectServePresenter(ICollectServeView collectServeView){
        this.collectServeView = collectServeView;
    }
    @Override
    public void getCollectListByPage(String userId, int pageNum) {
        Call<GetResultByPage<CollectResult>> call = NetService.getInstance().getCollectListByPage(JwtUtil.getJwt(), userId, pageNum);
        call.enqueue(new Callback<GetResultByPage<CollectResult>>() {
            @Override
            public void onResponse(Call<GetResultByPage<CollectResult>> call, Response<GetResultByPage<CollectResult>> response) {
                if(response.code()==200) {
                    view.showServeListSuccess(response.body().getDataList(), response.body().isHaveMore());
                }else{
                    onFailure(call,new Throwable(response.code()+""));
                }
            }

            @Override
            public void onFailure(Call<GetResultByPage<CollectResult>> call, Throwable t) {
                t.printStackTrace();
                view.showServeListFail();
            }
        });
    }

    @Override
    public void findOneCollect(String userId, String serveId) {
        Call<Short> call = NetService.getInstance().findOneCollect(JwtUtil.getJwt(), userId, serveId);
        call.enqueue(new Callback<Short>() {
            @Override
            public void onResponse(Call<Short> call, Response<Short> response) {
                if(response.code()==200){
                    collectServeView.isCollectResult(response.body());
                }else {
                    onFailure(call,new Throwable(response.code()+""));
                }
            }

            @Override
            public void onFailure(Call<Short> call, Throwable t) {
                collectServeView.isCollectResult((short)4);//网络错误
                t.printStackTrace();

            }
        });
    }

    @Override
    public void addOneCollect(String userId, String serveId) {
        collectServeView.showLoading();
        Call<Short> call = NetService.getInstance().addOneCollect(JwtUtil.getJwt(), userId, serveId);
        call.enqueue(new Callback<Short>() {
            @Override
            public void onResponse(Call<Short> call, Response<Short> response) {
                if(response.code()==200){
                    collectServeView.dismissLoading();
                    collectServeView.showResult(response.body());
                }else {
                    collectServeView.dismissLoading();
                    onFailure(call,new Throwable(response.code()+""));
                }
            }

            @Override
            public void onFailure(Call<Short> call, Throwable t) {
                collectServeView.showResult((short)4);//网络错误
                t.printStackTrace();

            }
        });
    }

    @Override
    public void deleteCollect(String userId, String serveId) {
        collectServeView.showLoading();
        Call<Short> call = NetService.getInstance().deleteOneCollect(JwtUtil.getJwt(), userId, serveId);
        call.enqueue(new Callback<Short>() {
            @Override
            public void onResponse(Call<Short> call, Response<Short> response) {
                if(response.code()==200){
                    collectServeView.dismissLoading();
                    collectServeView.showResult(response.body());
                }else {
                    collectServeView.dismissLoading();
                    onFailure(call,new Throwable(response.code()+""));
                }
            }

            @Override
            public void onFailure(Call<Short> call, Throwable t) {
                collectServeView.showResult((short)4);//网络错误
                t.printStackTrace();

            }
        });
    }
}
