package com.example.nytpreview.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nytpreview.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTopText;
    private TextView mBottomText;
    private TextView mDate;
    private ImageView mImage;
    private OnArticleListener mOnArticleListener;

    public ArticleViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
        super(itemView);

        this.mOnArticleListener = onArticleListener;

        mTopText = itemView.findViewById(R.id.top_card_text);
        mBottomText = itemView.findViewById(R.id.bottom_card_text);
        mDate = itemView.findViewById(R.id.article_date);
        mImage = itemView.findViewById(R.id.article_image);

        itemView.setOnClickListener(this);
    }

    public TextView getTopText() {
        return mTopText;
    }

    public TextView getBottomText() {
        return mBottomText;
    }

    public TextView getDate() {
        return mDate;
    }

    public ImageView getImage() {
        return mImage;
    }

    @Override
    public void onClick(View v) {
        mOnArticleListener.onArticleClick(getAdapterPosition());
    }
}
