package com.owlling.cookbook.presenter;

import android.content.Context;

import com.owlling.cookbook.IView.ICookSearchResultView;
import com.owlling.cookbook.constants.Constants;
import com.owlling.cookbook.model.entity.cookentity.subscriberEntity.SearchCookMenuSubscriberResultInfo;
import com.owlling.cookbook.model.interfaces.ICookRespository;
import com.owlling.cookbook.model.respository.CookRepository;
import com.owlling.cookbook.utils.ErrorExceptionUtil;

import rx.Subscriber;

public class CookSearchResultPresenter extends Presenter{

    private ICookSearchResultView iCookSearchResultView;
    private ICookRespository iCookRespository;

    private int curPage = 1;
    private int totalPages = 1;
    private String searchKey;

    public CookSearchResultPresenter(Context context, String searchKey, int totalPages, ICookSearchResultView iCookSearchResultView){
        super(context);

        this.searchKey = searchKey;
        this.totalPages = totalPages;

        this.iCookSearchResultView = iCookSearchResultView;
        this.iCookRespository = CookRepository.getInstance();
    }

    public void destroy(){
        if(searchCookMenuByNameSubscriber != null){
            searchCookMenuByNameSubscriber.unsubscribe();
        }
    }

    public void setData(String searchKey, int totalPages){
        this.searchKey = searchKey;
        this.totalPages = totalPages;
    }

    public void loadMore(){
        curPage++;

        if(curPage > totalPages){
            curPage--;
            if(iCookSearchResultView != null)
                iCookSearchResultView.onCookSearchLoadMoreNoData();

            return ;
        }

        rxJavaExecuter.execute(
                iCookRespository.searchCookMenuByName(searchKey, curPage, Constants.Per_Page_Size)
                , searchCookMenuByNameSubscriber = new SearchCookMenuByNameSubscriber()
        );
    }

    private SearchCookMenuByNameSubscriber searchCookMenuByNameSubscriber;
    private class SearchCookMenuByNameSubscriber extends Subscriber<SearchCookMenuSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(searchCookMenuByNameSubscriber != null){
                searchCookMenuByNameSubscriber.unsubscribe();
            }

            if(iCookSearchResultView != null)
                iCookSearchResultView.onCookSearchLoadMoreFaile(ErrorExceptionUtil.getErrorMsg(e));

        }

        @Override
        public void onNext(SearchCookMenuSubscriberResultInfo data){

            if(data == null || data.getResult() == null){
                if(iCookSearchResultView != null)
                    iCookSearchResultView.onCookSearchLoadMoreFaile("找不到相关菜谱");
                this.onCompleted();
                return ;
            }

            totalPages = data.getResult().getTotal();

            if(iCookSearchResultView != null) {
                iCookSearchResultView.onCookSearchLoadMoreSuccess(data.getResult().getList());
            }

            this.onCompleted();
        }
    }
}
