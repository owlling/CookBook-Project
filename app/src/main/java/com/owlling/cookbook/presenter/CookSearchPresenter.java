package com.owlling.cookbook.presenter;

import android.content.Context;

import com.owlling.cookbook.IView.ICookSearchView;
import com.owlling.cookbook.constants.Constants;
import com.owlling.cookbook.model.entity.cookentity.subscriberEntity.SearchCookMenuSubscriberResultInfo;
import com.owlling.cookbook.model.interfaces.ICookRespository;
import com.owlling.cookbook.model.manager.CookSearchHistoryManager;
import com.owlling.cookbook.model.respository.CookRepository;
import com.owlling.cookbook.utils.ErrorExceptionUtil;

import rx.Observable;
import rx.Subscriber;

public class CookSearchPresenter extends Presenter{

    private ICookSearchView iCookSearchView;
    private ICookRespository iCookRespository;

    public CookSearchPresenter(Context context, ICookSearchView iCookSearchView){
        super(context);

        this.iCookSearchView = iCookSearchView;
        this.iCookRespository = CookRepository.getInstance();
    }

    @Override
    public void destroy(){
        if(searchCookMenuByNameSubscriber != null){
            searchCookMenuByNameSubscriber.unsubscribe();
        }
    }

    public void search(String name){
        rxJavaExecuter.execute(
                iCookRespository.searchCookMenuByName(name, 1, Constants.Per_Page_Size)
                , searchCookMenuByNameSubscriber = new SearchCookMenuByNameSubscriber()
        );
    }

    public void saveHistory(){

        rxJavaExecuter.execute(
                Observable.create(
                        new Observable.OnSubscribe<Void>() {
                            @Override
                            public void call(Subscriber<? super Void> subscriber) {
                                CookSearchHistoryManager.getInstance().save();
                            }
                        }
                )
                , new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                }
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
            if(iCookSearchView != null)
                iCookSearchView.onCookSearchFaile(ErrorExceptionUtil.getErrorMsg(e));

        }

        @Override
        public void onNext(SearchCookMenuSubscriberResultInfo data){

            if(data == null || data.getResult() == null){
                if(iCookSearchView != null)
                    iCookSearchView.onCookSearchFaile("找不到相关菜谱");

                this.onCompleted();
                return ;
            }

            int totalPages = data.getResult().getTotal();

            if(iCookSearchView != null) {
                if(data.getResult().getList().size() < 1){
                    iCookSearchView.onCookSearchEmpty();
                }
                else {
                    iCookSearchView.onCookSearchSuccess(data.getResult().getList(), totalPages);
                }
            }

            this.onCompleted();
        }
    }
}
