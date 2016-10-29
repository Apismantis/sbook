package com.example.sherman.sbook.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sherman.sbook.R;
import com.example.sherman.sbook.activities.BookDetailActivity;
import com.example.sherman.sbook.constants.Constants;
import com.example.sherman.sbook.models.Book;

import java.util.List;

import static com.example.sherman.sbook.R.id.rlNotification;
import static com.example.sherman.sbook.R.id.view;

/**
 * Created by kenp on 30/10/2016.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private List<Book> mBooks;

    public NotificationAdapter(Context context, List<Book> books) {
        this.context = context;
        this.mBooks = books;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder holder, int position) {
        final Book book = mBooks.get(position);

        Glide.with(context).load(book.getCoverUrl()).into(holder.ivBookCover);
        holder.tvBookTitle.setText(book.getTitle());
        holder.tvBookAuthor.setText(book.getAuthor());
        holder.tvTime.setText("2 minutes ago");
        holder.bookId = book.getId();
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBookCover;
        TextView tvBookTitle;
        TextView tvBookAuthor;
        TextView tvTime;
        String bookId;

        public ViewHolder(View itemView) {
            super(itemView);

            ivBookCover = (ImageView) itemView.findViewById(R.id.ivBookCover);
            tvBookTitle = (TextView) itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = (TextView) itemView.findViewById(R.id.tvBookAuthor);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BookDetailActivity.class);
                    intent.putExtra(Constants.bookId, bookId);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
