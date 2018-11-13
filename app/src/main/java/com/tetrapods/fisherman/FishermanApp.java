package com.tetrapods.fisherman;

import android.app.Application;

import com.tetrapods.fisherman.data.fishRecord.DaoMaster;
import com.tetrapods.fisherman.data.fishRecord.DaoSession;
import com.tetrapods.fisherman.data.fishRecord.FishRecordDao;
import com.tetrapods.fisherman.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * We create a custom {@link Application} class that extends  {@link DaggerApplication}.
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as {@link DaggerApplication} will do that for us.
 */
public class FishermanApp extends DaggerApplication {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "fish.db");
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static FishRecordDao FishRecordDao(){
        return daoSession.getFishRecordDao();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}
