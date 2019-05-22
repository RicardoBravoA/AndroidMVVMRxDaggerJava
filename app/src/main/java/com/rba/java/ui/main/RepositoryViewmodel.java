package com.rba.java.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rba.java.data.api.repository.Repository;
import com.rba.java.data.entity.RepositoryEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RepositoryViewmodel extends ViewModel {

    private final Repository repository;
    private CompositeDisposable disposable;

    private final MutableLiveData<List<RepositoryEntity>> repositoryList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public RepositoryViewmodel(Repository repository) {
        this.repository = repository;
        disposable = new CompositeDisposable();
        fetchRepos();
    }

    LiveData<List<RepositoryEntity>> getRepositoryList() {
        return repositoryList;
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    private void fetchRepos() {
        loading.setValue(true);
        disposable.add(repository.getRepositoryList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<RepositoryEntity>>() {
                    @Override
                    public void onSuccess(List<RepositoryEntity> value) {
                        repoLoadError.setValue(false);
                        repositoryList.setValue(value);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }

}
