package com.sreedwish.cybazeapp.dagger;


import com.sreedwish.cybazeapp.Login;
import com.sreedwish.cybazeapp.MainActivity;
import com.sreedwish.cybazeapp.fragments.Frag1Digit;
import com.sreedwish.cybazeapp.rest.ApiClientModule;
import com.sreedwish.cybazeapp.rest.Repository;
import com.sreedwish.cybazeapp.roomDb.RoomDBModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, RoomDBModule.class, ApiClientModule.class})
@Singleton
public interface AppComponent {


    void doInjection(Repository repository);


    void doInjection(Login login);

    void doInjection(MainActivity mainActivity);

    void doInjection(Frag1Digit frag1Digit);
}
