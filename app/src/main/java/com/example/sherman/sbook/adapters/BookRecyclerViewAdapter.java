package com.example.sherman.sbook.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.activities.BookDetailActivity;
import com.example.sherman.sbook.constants.Constants;
import com.example.sherman.sbook.models.Book;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookViewHolders> {
    private List<Book> books;
    private Context mContext;

    public BookRecyclerViewAdapter(Context context, List<Book> itemList) {
        this.books = itemList;
        this.mContext = context;
    }

    @Override
    public BookViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, null);
        BookViewHolders rcv = new BookViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(BookViewHolders holder, int position) {
        holder.tvTitle.setText(books.get(position).getTitle());
        holder.tvAuthor.setText(books.get(position).getAuthor());
        holder.tvRating.setText(books.get(position).getRating());
        holder.bookId = books.get(position).getId();
        if (!books.get(position).getCoverUrl().equals(""))
            Picasso.with(mContext)
                    .load(books.get(position).getCoverUrl())
                    .into(holder.imvBookCover);
    }

    @Override
    public int getItemCount() {
        return this.books.size();
    }

    public void changeData(List<Book> books) {
        Log.d("TAG", "Change data...");
        this.books.clear();
        this.books.addAll(books);
        //notifyDataSetChanged();
        notifyItemRangeChanged(0, books.size());
    }
}

class BookViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView tvRating;
    TextView tvTitle;
    TextView tvAuthor;
    ImageView imvBookCover;
    String bookId;

    public BookViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.bookId = bookId;

        imvBookCover = (ImageView) itemView.findViewById(R.id.imvBookCover);
        tvTitle = (TextView) itemView.findViewById(R.id.tvBookName);
        tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
        tvRating = (TextView) itemView.findViewById(R.id.tvRating);
    }

    @Override
    public void onClick(View view) {
        Intent bookDetailIntent = new Intent(view.getContext(), BookDetailActivity.class);
        bookDetailIntent.putExtra(Constants.bookId, bookId);
        view.getContext().startActivity(bookDetailIntent);
    }
}
