package bid.xiaocha.xxt.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;

import bid.xiaocha.xxt.BR;
import bid.xiaocha.xxt.R;
import bid.xiaocha.xxt.adater.CommonListAdater;
import bid.xiaocha.xxt.databinding.ActivityCollectServeBinding;
import bid.xiaocha.xxt.databinding.ContentCollectServeBinding;
import bid.xiaocha.xxt.iview.IShowServeView;
import bid.xiaocha.xxt.model.CollectResult;
import bid.xiaocha.xxt.model.UserEntity;
import bid.xiaocha.xxt.presenter.CollectServePresenter;
import bid.xiaocha.xxt.util.App;

import static bid.xiaocha.xxt.util.CommonUtils.showToast;

public class CollectServeActivity extends AppCompatActivity implements IShowServeView<CollectResult> {

    private ActivityCollectServeBinding binding;
    private ContentCollectServeBinding contentBinding;
    private CollectServePresenter presenter;
    private boolean isHaveMore;
    private CommonListAdater<CollectResult> adater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_collect_serve);
        contentBinding = binding.content;
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        initPresenter();
        initView();


    }

    private void initPresenter() {
        presenter = new CollectServePresenter(this);
    }

    private void initView() {
        adater = new CommonListAdater<CollectResult>(new ArrayList<CollectResult>(), BR.collect, getLayoutInflater(), R.layout.list_item_collect, new CommonListAdater.OnItemClick<CollectResult>() {
            @Override
            public void setItemClick(final CollectResult result, View view) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CollectServeActivity.this,ShowOfferServeDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("serveJson",result.getOfferServeEntity().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });
        contentBinding.lvCollectServe.setAdapter(adater);
        contentBinding.lvCollectServe.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isHaveMore){
                    if (firstVisibleItem + visibleItemCount == totalItemCount-5){
                        int nextPageNum = (totalItemCount-1)/ App.NUM_IN_A_PAGE+2;
                        contentBinding.lyFooter.setVisibility(View.VISIBLE);
                        presenter.getCollectListByPage(UserEntity.getCurrentUser().getUserId(),nextPageNum);
                    }
                }else{
                    return;
                }

            }
        });
    }



    @Override
    public void showServeListSuccess(List<CollectResult> ServeList, boolean isHaveMore) {
        if (ServeList == null){
            showToast("列表为空");
        }else {
            this.isHaveMore = isHaveMore;
            adater.addDataList(ServeList);
            contentBinding.lyFooter.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void showServeListFail() {
        showToast("获取数据出错");
        contentBinding.lyFooter.setVisibility(View.INVISIBLE);

    }
}
