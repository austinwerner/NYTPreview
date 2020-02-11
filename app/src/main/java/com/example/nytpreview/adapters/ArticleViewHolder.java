package com.example.nytpreview.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nytpreview.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, date;
    ImageView image;
    OnArticleListener onArticleListener;

    public ArticleViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
        super(itemView);

        this.onArticleListener = onArticleListener;

        title = itemView.findViewById(R.id.article_title_text);
        date = itemView.findViewById(R.id.article_date);
        image = itemView.findViewById(R.id.article_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onArticleListener.onArticleClick(getAdapterPosition());
    }
}
