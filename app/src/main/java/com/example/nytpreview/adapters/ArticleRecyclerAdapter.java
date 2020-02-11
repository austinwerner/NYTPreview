package com.example.nytpreview.adapters;


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

    public ArticleRecyclerAdapter(OnArticleListener mOnArticleListener) {
        this.mArticles = new ArrayList<>();
        this.mOnArticleListener = mOnArticleListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_article_list_item, viewGroup, false);
        return new ArticleViewHolder(view, mOnArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if( mArticles.get(i).getMultimedia().length > 0 ) {
            ((ArticleViewHolder) viewHolder).image.setVisibility(View.VISIBLE);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Glide.with(((ArticleViewHolder) viewHolder).itemView)
                    .setDefaultRequestOptions(options)
                    .load(mArticles.get(i).getMultimedia()[0].getUrl())
                    .into(((ArticleViewHolder) viewHolder).image);
        }
        else {
            ((ArticleViewHolder) viewHolder).image.setVisibility(View.GONE);
        }

        ((ArticleViewHolder)viewHolder).title.setText(mArticles.get(i).getHeadline().getMain());

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date parsed = null;
        try {
            parsed = sdf.parse( mArticles.get(i).getPub_date() );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((ArticleViewHolder)viewHolder).date.setText(new SimpleDateFormat("EEE, MMM d yyyy", Locale.US).format(parsed));
    }

    @Override
    public int getItemCount() {
        if(mArticles != null) {
            return mArticles.size();
        }
        return 0;
    }

    public void setArticles(List<Article> articles){
        mArticles = articles;
        notifyDataSetChanged();
    }

    public String getArticleUrl(int position) {
        return mArticles.get(position).getWeb_url();
    }
}