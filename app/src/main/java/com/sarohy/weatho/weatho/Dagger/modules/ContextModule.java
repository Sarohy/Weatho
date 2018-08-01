package com.sarohy.weatho.weatho.Dagger.modules;

import android.content.Context;

import com.sarohy.weatho.weatho.Dagger.annotations.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context){
        this.context=context;
    }

    @Provides
    public Context context(){
        return context;
    }
}
