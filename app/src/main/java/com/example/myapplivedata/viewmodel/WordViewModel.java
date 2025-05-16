package com.example.myapplivedata.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplivedata.data.Word;
import com.example.myapplivedata.repository.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private final WordRepository mRepository;
    private final LiveData<List<Word>> mAllWords;

    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    public void update(Word word) {
        mRepository.update(word);
    }

    public void delete(Word word) {
        mRepository.delete(word);
    }

}

