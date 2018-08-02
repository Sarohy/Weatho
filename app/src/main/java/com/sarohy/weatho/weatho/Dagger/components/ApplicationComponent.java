package com.sarohy.weatho.weatho.Dagger.components;



import com.bumptech.glide.RequestManager;
import com.sarohy.weatho.weatho.Dagger.annotations.ApplicationScope;
import com.sarohy.weatho.weatho.Dagger.modules.ContextModule;
import com.sarohy.weatho.weatho.Dagger.modules.ServiceModule;
import com.sarohy.weatho.weatho.Model.ProjectRepository;
import com.sarohy.weatho.weatho.SharedPreferencesClass;

import dagger.Component;

/**
 * Public interface to our dependency graph
 * Dagger will aggro at @Component annotation and generate an instance(subclass) of this interface
 * and will provide required instances for the annotated methods from our Modules (@Module)
 * (Dagger looks for what he needs to provide, searches in provided Modules on how to do it)
 */
@ApplicationScope
@Component(modules = {ContextModule.class,ServiceModule.class}) // tell which modules to use in order to generate this instance
public interface ApplicationComponent {



    SharedPreferencesClass getSharedPrefs();

    RequestManager getGlide();

    void injectRepo(ProjectRepository repository);

}
