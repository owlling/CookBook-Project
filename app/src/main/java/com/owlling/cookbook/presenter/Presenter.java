package com.owlling.cookbook.presenter;

import android.content.Context;

import com.owlling.cookbook.model.executer.JobExecutor;
import com.owlling.cookbook.model.executer.RxJavaExecuter;
import com.owlling.cookbook.model.executer.UIThread;
public abstract class Presenter {

    protected Context context;
    protected RxJavaExecuter rxJavaExecuter;

    public Presenter(Context context){
        this.context = context;
        this.rxJavaExecuter = new RxJavaExecuter(JobExecutor.instance(), UIThread.instance());
    }
    public Presenter(){
        this.rxJavaExecuter = new RxJavaExecuter(JobExecutor.instance(), UIThread.instance());
    }

    public abstract void destroy();
}
