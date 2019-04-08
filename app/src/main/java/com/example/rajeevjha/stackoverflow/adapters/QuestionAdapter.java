package com.example.rajeevjha.stackoverflow.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rajeevjha.stackoverflow.R;
import com.example.rajeevjha.stackoverflow.models.Question;
import com.example.rajeevjha.stackoverflow.utils.TaskHelper;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private Context context;
    private List<Question> questionList;
    private boolean offlineData;
    private ArrayList<String> savedQuestionsLabel = new ArrayList<>();


    public QuestionAdapter(Context context, List<Question> data) {
        this.context = context;
        this.questionList = data;
        offlineData = false;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.question_item_row, viewGroup, false);

        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int i) {

        Question question = questionList.get(i);
        // set title
        holder.title.setText(getFormattedTitle(question.getTitle()));

        if (offlineData) {
            // hide the save button in savedQuestions
            holder.saveButton.setVisibility(View.INVISIBLE);
        } else {
            if (savedQuestionsLabel.size() != 0 && i < savedQuestionsLabel.size()) {
                // if question is already saved
                Log.d("QuestionAdapter", savedQuestionsLabel.toString());
                if (TextUtils.equals(question.getTitle(), savedQuestionsLabel.get(i))) {
                    holder.saveButton.setText("Saved");
                }
            }
        }


        //implement click Interface
        holder.setClickInterface(new QuestionViewHolder.ClickInterface() {

            @Override
            public void questionClick(View caller) {
                TaskHelper.openCustomChromeTab(context, question.getLink());

            }

            @Override
            public void shareButtonClick(View caller) {
                TaskHelper.shareQuestionIntent(context, question.getLink());

            }

            @Override
            public void saveButtonClick(View caller) {
                if (!TextUtils.equals(holder.saveButton.getText(), "Saved")) {
                    TaskHelper.saveQuestion(context, question);
                    holder.saveButton.setText("Saved");
                    savedQuestionsLabel.add(question.getTitle());

                }


            }
        });


    }

    public void setOfflineData(boolean offlineData) {
        this.offlineData = offlineData;
    }

    @Override
    public int getItemCount() {

        if (questionList != null) return questionList.size();

        else return 0;
    }

    // helper method to clear the data in adapter
    public void clearData() {

        if (questionList != null && questionList.size() > 0) {
            questionList.clear();
            notifyDataSetChanged();
            savedQuestionsLabel.clear();
        }
    }


    // private helper method to format html entities present in String
    private String getFormattedTitle(String string) {
        return Html.fromHtml(string).toString();
    }


    static class QuestionViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private TextView title;
        private Button shareButton;
        private Button saveButton;
        private RelativeLayout container;
        private ClickInterface clickInterface;


        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_question);
            shareButton = itemView.findViewById(R.id.share_button);
            saveButton = itemView.findViewById(R.id.save_button);
            container = itemView.findViewById(R.id.question_container);
            // attach listeners
            shareButton.setOnClickListener(this);
            saveButton.setOnClickListener(this);
            container.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v instanceof Button) {

                if (v.getId() == R.id.save_button)
                    clickInterface.saveButtonClick(v);
                else
                    clickInterface.shareButtonClick(v);
            }

            if (v instanceof RelativeLayout) {
                clickInterface.questionClick(v);
            }


        }

        void setClickInterface(ClickInterface clickInterface) {
            this.clickInterface = clickInterface;

        }

        interface ClickInterface {

            void questionClick(View caller);

            void shareButtonClick(View caller);

            void saveButtonClick(View caller);


        }


    }
}
