package com.example.rajeevjha.stackoverflow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rajeevjha.stackoverflow.adapters.SelectedTagAdapter;
import com.example.rajeevjha.stackoverflow.adapters.TagAdapter;
import com.example.rajeevjha.stackoverflow.data.PreferenceHelper;
import com.example.rajeevjha.stackoverflow.models.Tag;
import com.example.rajeevjha.stackoverflow.models.TagList;
import com.example.rajeevjha.stackoverflow.network.GetRemoteDataInterface;
import com.example.rajeevjha.stackoverflow.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInterestActivity extends AppCompatActivity {

    private static final String LOG_TAG = UserInterestActivity.class.getSimpleName();
    private TagAdapter mAdapter;
    private RecyclerView mTagRecyclerView;
    private RecyclerView mSelectedTagRecyclerView;
    private ProgressDialog mProgressDialog;
    private SelectedTagAdapter selectedTagAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interest);

        String accessToken = PreferenceHelper.getString(PreferenceHelper.PREF_KEY_Token, "");

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading....");
        mProgressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetRemoteDataInterface service = RetrofitClientInstance.
                getRetrofitInstance().
                create(GetRemoteDataInterface.class);
        Call<TagList> call = service.getAllTags(
                "desc",
                "popular",
                "stackoverflow"
        );


        System.out.println(call.request().url());

        // making an async network call
        call.enqueue(new Callback<TagList>() {
            @Override
            public void onResponse(Call<TagList> call, Response<TagList> response) {

                mProgressDialog.dismiss();
                addTagData(response.body().getTagList());

            }


            @Override
            public void onFailure(Call<TagList> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(UserInterestActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, t.getMessage());

            }
        });

    }

    // helper method to setup recycler view
    private void addTagData(List<Tag> dataList) {
        setupSelectedTagData();
        mTagRecyclerView = findViewById(R.id.vertical_recyclerView);
        mAdapter = new TagAdapter(this, dataList, selectedTagAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mTagRecyclerView.setLayoutManager(layoutManager);
        mTagRecyclerView.setAdapter(mAdapter);

    }

    private void setupSelectedTagData() {
        mSelectedTagRecyclerView = findViewById(R.id.horizontal_RecyclerView);
        selectedTagAdapter = new SelectedTagAdapter(Tag.userSelectedTags);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, true);
        mSelectedTagRecyclerView.setLayoutManager(layoutManager);
        mSelectedTagRecyclerView.setAdapter(selectedTagAdapter);
        submitButtonBehaviour();

    }

    // helper method for submit button behaviour
    private void submitButtonBehaviour() {
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Tag.userSelectedTags.size() != 0) {
                    // save the user chosen tags in sharedPreferences
                    PreferenceHelper.putStringSet(PreferenceHelper.PREF_KEY_TAGS,
                            convertSelectedTagListToSet());
                    // launch QuestionListActivity
                    Intent intent = new Intent(UserInterestActivity.this,
                            QuestionListActivity.class);
                    startActivity(intent);
                    UserInterestActivity.this.finish();


                } else {
                    Toast.makeText(UserInterestActivity.this,
                            "No tags selected!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private Set<String> convertSelectedTagListToSet() {
        List<String> list = Tag.userSelectedTags;
        Set<String> set = new ArraySet<>();
        set.addAll(list);
        return set;
    }

    private List<String> convertSelectedTagsSetToList() {
        Set<String> tagsSet = PreferenceHelper.getTagsStringSet(new ArraySet<String>());
        List<String> tagsSetList = new ArrayList<>();
        tagsSetList.addAll(tagsSet);
        return tagsSetList;
    }


}
