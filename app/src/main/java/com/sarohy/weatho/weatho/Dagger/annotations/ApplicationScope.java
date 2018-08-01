package com.sarohy.weatho.weatho.Dagger.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


@Scope // ctrl+q on that annotation ~~~
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationScope {
    // Could be replaced by Singleton
}
