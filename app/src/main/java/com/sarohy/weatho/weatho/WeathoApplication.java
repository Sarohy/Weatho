package com.sarohy.weatho.weatho;

import android.app.Application;

import com.sarohy.weatho.weatho.Dagger.components.DaggerApplicationComponent;
import com.sarohy.weatho.weatho.Dagger.components.ApplicationComponent;
import com.sarohy.weatho.weatho.Dagger.modules.ContextModule;

public class WeathoApplication extends Application {
    public static ApplicationComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }
}
