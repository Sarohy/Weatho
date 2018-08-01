package com.sarohy.weatho.weatho.Dagger.modules;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.sarohy.weatho.weatho.API.APIClient;
import com.sarohy.weatho.weatho.API.APIInterface;
import com.sarohy.weatho.weatho.Database.AppDatabase;
import com.sarohy.weatho.weatho.Dagger.annotations.ApplicationScope;
import com.sarohy.weatho.weatho.SharedPreferencesClass;

import dagger.Module;
import dagger.Provides;
// also specify which Modules it depends on (if any)
@Module
public class ServiceModule {

    @Provides // Tell Dagger that this method provides dependencies (will also try to supplement required parameters from other modules)
    @ApplicationScope
    public AppDatabase getAppDatabase(Context context){
        return AppDatabase.getAppDatabase(context);
    }


    @Provides
    @ApplicationScope
    public APIInterface retrofit(){
        return APIClient.getClient().create(APIInterface.class);
    }

    @Provides
    @ApplicationScope
    public SharedPreferencesClass getSharedPrefs(Context context){
        return new SharedPreferencesClass(context);
    }
    @Provides
    @ApplicationScope
    public RequestManager getGlide(Context context){
        return Glide.with(context);
    }
}
