package com.example.rajeevjha.stackoverflow.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.rajeevjha.stackoverflow.Repository.QuestionsRepository;

import java.util.List;


// custom AndroidViewModel Class
public class QuestionViewModel extends AndroidViewModel {

    private LiveData<List<Question>> questionList;
    private QuestionsRepository questionsRepo;


    public QuestionViewModel(@NonNull Application application) {
        super(application);
        questionsRepo = new QuestionsRepository(application);
    }

    public void init(boolean isNetworkAvailable, String sort, String tag) {
        questionList = questionsRepo.getQuestionList(isNetworkAvailable,
                sort,
                tag);
    }

    public LiveData<List<Question>> getQuestionList() {
        return this.questionList;
    }


}
