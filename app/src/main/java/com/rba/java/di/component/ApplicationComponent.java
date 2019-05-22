package com.rba.java.di.component;

import android.app.Application;

import com.rba.java.app.App;
import com.rba.java.di.module.ActivityModule;
import com.rba.java.di.module.ApplicationModule;
import com.rba.java.di.module.ContextModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ContextModule.class, ApplicationModule.class, AndroidSupportInjectionModule.class, ActivityModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(App application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
