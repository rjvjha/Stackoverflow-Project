package com.example.rajeevjha.stackoverflow.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rajeevjha.stackoverflow.R;
import com.example.rajeevjha.stackoverflow.models.Tag;

import java.util.List;

public class SelectedTagAdapter extends RecyclerView.Adapter<SelectedTagAdapter.ViewHolder> {

    private List<String> selectedTags;

    public SelectedTagAdapter(List<String> selectedTags) {
        this.selectedTags = selectedTags;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.selected_tag_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final String tag = selectedTags.get(i);
        holder.title.setText(tag);

        holder.setListener(new ViewHolder.ClearTagInterface() {
            @Override
            public void onClearTag(View caller) {
                selectedTags.remove(tag);
                // remove from selected list
                Tag.removeTag(tag);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {

        if (selectedTags != null) return selectedTags.size();

        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageButton clearButton;
        private ClearTagInterface listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tag_title);
            clearButton = itemView.findViewById(R.id.clear_button);
            clearButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v instanceof ImageButton) listener.onClearTag(v);

        }

        public void setListener(ClearTagInterface listener) {
            this.listener = listener;
        }

        public interface ClearTagInterface {
            void onClearTag(View caller);

        }


    }
}
