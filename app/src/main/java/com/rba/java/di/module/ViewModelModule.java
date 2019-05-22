package com.rba.java.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.rba.java.di.util.ViewModelKey;
import com.rba.java.ui.main.RepositoryViewmodel;
import com.rba.java.util.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RepositoryViewmodel.class)
    abstract ViewModel bindRepositoryViewModel(RepositoryViewmodel repositoryViewmodel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}