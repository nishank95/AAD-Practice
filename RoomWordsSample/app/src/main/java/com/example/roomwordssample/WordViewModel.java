package com.example.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

// **Warning:
//
//    Never pass context into ViewModel instances.
//    Do not store Activity, Fragment, or View instances or their Context in the ViewModel.
public class WordViewModel extends AndroidViewModel {
    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }
    // Add a "getter" method that gets all the words. This completely hides the implementation from the UI.
    LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    // Create a wrapper insert() method that calls the Repository's insert() method. In this way, the        implementation of insert() is completely hidden from the UI.
    public void insert(Word word){
        mRepository.insert(word);
    }
}
