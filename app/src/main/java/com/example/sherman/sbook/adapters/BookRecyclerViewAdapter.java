package com.example.sherman.sbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.models.Book;

import java.util.List;

/**
 * Created by Sherman on 10/29/2016.
 */

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BookViewHolders> {
    private List<Book> itemList;
    private Context context;

    public BookRecyclerViewAdapter(Context context, List<Book> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public BookViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, null);
        BookViewHolders rcv = new BookViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(BookViewHolders holder, int position) {
        holder.title.setText(itemList.get(position).getTitle());
        holder.author.setText(itemList.get(position).getAuthor());
        holder.rating.setText(itemList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class BookViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView rating;
        public TextView title;
        public TextView author;

        public BookViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.book_name);
            author = (TextView) itemView.findViewById(R.id.idText);
            rating = (TextView) itemView.findViewById(R.id.rating);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}

