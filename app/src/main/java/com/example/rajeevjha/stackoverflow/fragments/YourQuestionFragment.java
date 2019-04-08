package com.example.rajeevjha.stackoverflow.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rajeevjha.stackoverflow.R;
import com.example.rajeevjha.stackoverflow.adapters.QuestionAdapter;
import com.example.rajeevjha.stackoverflow.models.Question;
import com.example.rajeevjha.stackoverflow.models.QuestionViewModel;
import com.example.rajeevjha.stackoverflow.utils.TaskHelper;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourQuestionFragment extends Fragment {

    private static final String LOG_TAG = YourQuestionFragment.class.getSimpleName();
    private static final String SORT_ORDER = "activity";
    private static String sTag;
    private QuestionViewModel viewModel;
    private QuestionAdapter adapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mEmptyTextView;


    public YourQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        mRecyclerView = view.findViewById(R.id.questions_recyclerView);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mEmptyTextView = view.findViewById(R.id.empty_list_feedback);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // set default tag
        sTag = TaskHelper.convertSelectedTagsSetToList().get(0);

        // attach viewModel
        viewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        viewModel.init(TaskHelper.isNetworkConnected(mContext),
                SORT_ORDER,
                sTag
        );


        viewModel.getQuestionList().observe(this, questions -> {

            //finally add data
            mProgressBar.setVisibility(View.GONE);
            addData(viewModel.getQuestionList().getValue());

        });
    }

    // update data in UI
    private void addData(List<Question> data) {
        // set current sort and sTag values in data
        // empty data list

        if (data == null || data.size() == 0) {
            mEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            mEmptyTextView.setVisibility(View.GONE);
            setSortAndTag(data);
            adapter = new QuestionAdapter(mContext, data);
            adapter.setOfflineData(!TaskHelper.isNetworkConnected(mContext));
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(lm);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }


    }

    public void refreshQuestionList(String tag) {

        // refresh only when value of tag changes
        if (!TextUtils.equals(sTag, tag)) {
            sTag = tag;
            viewModel.init(TaskHelper.isNetworkConnected(mContext),
                    SORT_ORDER,
                    sTag

            );

            // clear existing data in the adapter
            if (adapter != null) {
                adapter.clearData();
                mProgressBar.setVisibility(View.VISIBLE);

            }

            viewModel.getQuestionList().observe(this, questions -> {

                //finally add data
                mProgressBar.setVisibility(View.GONE);
                addData(viewModel.getQuestionList().getValue());

            });
        }


    }

    private void setSortAndTag(List<Question> data) {
        for (Question q : data) {
            q.setTag(sTag);
            q.setSortCategory(SORT_ORDER);
        }

    }

}
