package com.owlling.cookbook.presenter;

import android.content.Context;
import android.util.Log;

import com.owlling.cookbook.IView.ISplashView;
import com.owlling.cookbook.model.entity.cookentity.CategoryChildInfo1;
import com.owlling.cookbook.model.entity.cookentity.subscriberEntity.CategorySubscriberResultInfo;
import com.owlling.cookbook.model.interfaces.ICookRespository;
import com.owlling.cookbook.model.manager.CookCategoryManager;
import com.owlling.cookbook.model.manager.CustomCategoryManager;
import com.owlling.cookbook.model.respository.CookRepository;

import java.util.ArrayList;

import rx.Subscriber;
public class SplashPresenter extends Presenter{

    private ICookRespository iCookRespository;
    private ISplashView iSplashView;

    public SplashPresenter(Context context, ISplashView iSplashView){
        super(context);

        this.iCookRespository = CookRepository.getInstance();
        this.iSplashView = iSplashView;
    }

    public void destroy(){
        if(getCategoryQuerySubscriber != null){
            getCategoryQuerySubscriber.unsubscribe();
        }
    }

    public void initData(){
        //execute异步执行getC*拿数据
        rxJavaExecuter.execute(iCookRespository.getCategoryQuery(), getCategoryQuerySubscriber = new GetCategoryQuerySubscriber());

    }
    //根据拿到的数据情况回调以下某个函数(都会启动下一个页面'主页面)
    private GetCategoryQuerySubscriber getCategoryQuerySubscriber;
    private class GetCategoryQuerySubscriber extends Subscriber<CategorySubscriberResultInfo> {
        //
        @Override
        public void onCompleted(){

        }
        //出现异常；文档或图像加载过程中发生错误时被触发
        @Override
        public void onError(Throwable e){
            if(getCategoryQuerySubscriber != null){
                getCategoryQuerySubscriber.unsubscribe();
            }

            CustomCategoryManager.getInstance().initData(null);

            if(iSplashView != null)
                iSplashView.onSplashInitData();

        }
        //
        @Override
        public void onNext(CategorySubscriberResultInfo data){
            Log.i("onNext", "(" + data.getResult().getCategoryInfo().toString());

            ArrayList<CategoryChildInfo1> datas = CookCategoryManager.removeBang(data.getResult().getChilds());

            CookCategoryManager.getInstance().initDatas(datas);
            CustomCategoryManager.getInstance().initData(datas);

            if(iSplashView != null)
                iSplashView.onSplashInitData();

            this.onCompleted();
        }
    }
}
