package com.sreedwish.cybazeapp.dagger;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sreedwish.cybazeapp.rest.Repository;
import com.sreedwish.cybazeapp.roomDb.DbRepository;
import com.sreedwish.cybazeapp.view_models.AuthentiCationVM;
import com.sreedwish.cybazeapp.view_models.DataViewModel;

import javax.inject.Inject;


public class ViewModelFactory implements ViewModelProvider.Factory {

    public Repository repository;
    public DbRepository dbRepository;


    private AuthentiCationVM authentiCationVM;
    private DataViewModel dataViewModel;

    @Inject
    public ViewModelFactory(Repository repository,DbRepository dbRepository) {
        this.repository = repository;
        this.dbRepository = dbRepository;
        initViewModels();

    }

    private void initViewModels(){

        authentiCationVM = new AuthentiCationVM(repository);

        dataViewModel = new DataViewModel(repository);

    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(AuthentiCationVM.class)){
            return (T) authentiCationVM;
        }

        if (modelClass.isAssignableFrom(DataViewModel.class)){
            return (T) dataViewModel;
        }

        throw new IllegalArgumentException("Unknown class name");
    }
}
