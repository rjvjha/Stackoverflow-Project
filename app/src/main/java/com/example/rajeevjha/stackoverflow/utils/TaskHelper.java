package com.example.rajeevjha.stackoverflow.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.util.ArraySet;
import android.widget.Toast;

import com.example.rajeevjha.stackoverflow.Repository.QuestionsRepository;
import com.example.rajeevjha.stackoverflow.application.MyApplication;
import com.example.rajeevjha.stackoverflow.data.PreferenceHelper;
import com.example.rajeevjha.stackoverflow.database.AppDatabase;
import com.example.rajeevjha.stackoverflow.database.QuestionDao;
import com.example.rajeevjha.stackoverflow.models.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TaskHelper {

    public static void openCustomChromeTab(Context context, String url) {
        CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
        intent.launchUrl(context, Uri.parse(url));
    }

    public static void shareQuestionIntent(Context context, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        String message = "Checkout this question at Stack Overflow \n" + url;
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        shareIntent.setType("text/plain");
        context.startActivity(shareIntent);
    }

    public static List<String> convertSelectedTagsSetToList() {
        Set<String> tagsSet = PreferenceHelper.getTagsStringSet(new ArraySet<String>());
        List<String> tagsSetList = new ArrayList<>();
        tagsSetList.addAll(tagsSet);
        return tagsSetList;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public static void saveQuestion(Context context, Question question) {
        QuestionsRepository repo = new QuestionsRepository(MyApplication.myApplication);
        repo.insertQuestion(question);
        Toast.makeText(context, "Question Saved!", Toast.LENGTH_SHORT).show();

    }

    public static void deleteAllQuestion(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        new DeleteAsyncTask(database.questionDao()).execute();

    }

    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private QuestionDao dao;

        public DeleteAsyncTask(QuestionDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.deleteAll();

            return null;
        }
    }


}
