package com.example.rajeevjha.stackoverflow.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rajeevjha.stackoverflow.R;
import com.example.rajeevjha.stackoverflow.models.Tag;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {

    private List<Tag> tagList;
    private Context context;
    private SelectedTagAdapter adapter;


    public TagAdapter(Context context, List<Tag> tagList, SelectedTagAdapter adapter) {
        this.tagList = tagList;
        this.context = context;
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.tag_item_row, viewGroup, false);

        return new TagViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int i) {
        final Tag currentTag = tagList.get(i);
        // set tag title
        holder.title.setText(currentTag.getName());

        // add Tag click listener
        holder.setmListener(new TagViewHolder.TagClickInterface() {
            @Override
            public void onTagItemClick(View caller) {
                // add tag to userSelectedTags
                if (!Tag.userSelectedTags.contains(currentTag.getName()))
                    currentTag.addTag();

                adapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    static class TagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TagClickInterface listener;
        private TextView title;
        private LinearLayout container;


        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tag_title);
            container = itemView.findViewById(R.id.item_container);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v instanceof LinearLayout) {
                listener.onTagItemClick(v);
            }
        }

        public void setmListener(TagClickInterface tagClickInterface) {
            listener = tagClickInterface;
        }

        public interface TagClickInterface {
            void onTagItemClick(View caller);
        }
    }
}
