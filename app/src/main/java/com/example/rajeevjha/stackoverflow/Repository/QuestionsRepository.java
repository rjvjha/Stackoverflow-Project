package com.example.rajeevjha.stackoverflow.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.rajeevjha.stackoverflow.data.PreferenceHelper;
import com.example.rajeevjha.stackoverflow.database.AppDatabase;
import com.example.rajeevjha.stackoverflow.database.QuestionDao;
import com.example.rajeevjha.stackoverflow.models.Question;
import com.example.rajeevjha.stackoverflow.models.QuestionList;
import com.example.rajeevjha.stackoverflow.network.GetRemoteDataInterface;
import com.example.rajeevjha.stackoverflow.network.RetrofitClientInstance;

import java.util.List;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class QuestionsRepository {

    private QuestionDao quesDao;
    private String acesssToken;

    //@Inject
    public QuestionsRepository(Application application) {
        quesDao = AppDatabase.getDatabase(application).questionDao();
        acesssToken = PreferenceHelper.getString(PreferenceHelper.PREF_KEY_Token, "");

    }

    // this method will be invoked by QuestionViewModel
    public LiveData<List<Question>> getQuestionList(boolean isNetworkAvailable, String sort, String tag) {

        if (isNetworkAvailable) {
            // fetch from remote
            return getQuestionsFromRemote(sort, tag);

        } else {
            // show user saved questions
            return getSavedQuestions(sort, tag);

        }

    }

    private LiveData<List<Question>> getQuestionsFromRemote(String sort, String tag) {


        final MutableLiveData<List<Question>> data = new MutableLiveData<>();


        GetRemoteDataInterface webservice = RetrofitClientInstance.getRetrofitInstance()
                .create(GetRemoteDataInterface.class);
        Call<QuestionList> call = webservice.getQuestions(
                "desc",
                sort,
                tag,
                "stackoverflow"

        );

        call.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
                data.setValue(response.body().getQuestionList());

            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable t) {
                t.printStackTrace();

            }
        });

        return data;

    }

    private LiveData<List<Question>> getSavedQuestions(String sort, String tag) {

        // Returns a LiveData object directly from the database.
        //return quesDao.getAll();
        return quesDao.getQuestionByTagAndSort(tag, sort);

    }

    public void insertQuestion(Question question) {
        new InsertAsyncTask(quesDao).execute(question);
    }

    // Asynctask for insertion operation
    private static class InsertAsyncTask extends AsyncTask<Question, Void, Void> {
        private QuestionDao questionDao;

        public InsertAsyncTask(QuestionDao dao) {
            this.questionDao = dao;

        }

        @Override
        protected Void doInBackground(Question... questions) {
            if (questions[0] != null) {
                questionDao.insert(questions[0]);
            }
            return null;
        }
    }


}
