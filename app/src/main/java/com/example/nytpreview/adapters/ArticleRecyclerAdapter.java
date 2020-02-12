package com.example.nytpreview.adapters;


import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nytpreview.R;
import com.example.nytpreview.models.Article;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Article> mArticles;
    private OnArticleListener mOnArticleListener;
    private Resources mResources;

    public ArticleRecyclerAdapter(OnArticleListener mOnArticleListener) {

        this.mArticles = new ArrayList<>();
        this.mOnArticleListener = mOnArticleListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        mResources = viewGroup.getResources();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_article_list_item, viewGroup, false);
        return new ArticleViewHolder(view, mOnArticleListener);
    }

    @Override
    // Binds a specific view with the view holder
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final ArticleViewHolder holder = (ArticleViewHolder)viewHolder;
        boolean hasImage = mArticles.get(i).getMultimedia().length > 0;
        if (hasImage) {

            // Has an image - show the image and hide large text
            // Use bottom text as the title
            holder.getImage().setVisibility(View.VISIBLE);

            // load images with glide library
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Glide.with((holder).itemView)
                    .setDefaultRequestOptions(options)
                    .load(mArticles.get(i).getMultimedia()[0].getUrl())
                    .into(((ArticleViewHolder) viewHolder).getImage());

            holder.getTopText().setVisibility(View.GONE);
            holder.getBottomText().setText(mArticles.get(i).getHeadline().getMain());
            holder.getBottomText().setTextSize(TypedValue.COMPLEX_UNIT_PX,mResources.getDimension(R.dimen.article_title_text_size));
            holder.getBottomText().setTypeface(null,Typeface.BOLD);
        }
        else {

            // No image - Show big text instead and hide image view
            // Use the bottom text for the article details
            holder.getImage().setVisibility(View.GONE);

            holder.getTopText().setVisibility(View.VISIBLE);
            holder.getTopText().setText(mArticles.get(i).getHeadline().getMain());

            holder.getBottomText().setText(mArticles.get(i).getSnippet());
            holder.getBottomText().setTextSize(TypedValue.COMPLEX_UNIT_PX,mResources.getDimension(R.dimen.article_detail_text_size));
            holder.getBottomText().setTypeface(null,Typeface.ITALIC);
        }

        // Always show the date
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date parsed = null;
        try {
            parsed = sdf.parse(mArticles.get(i).getPub_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((ArticleViewHolder)viewHolder).getDate().setText(new SimpleDateFormat("EEE, MMM d yyyy", Locale.US).format(parsed));
    }

    @Override
    public int getItemCount() {
        if (mArticles != null) {
            return mArticles.size();
        }
        return 0;
    }

    public void setArticles(List<Article> articles) {

        mArticles = articles;
        notifyDataSetChanged();
    }

    public String getArticleUrl(int position) {
        return mArticles.get(position).getWeb_url();
    }
}