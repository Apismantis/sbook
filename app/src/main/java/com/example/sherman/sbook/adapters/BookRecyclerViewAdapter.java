package com.example.sherman.sbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sherman.sbook.BookItem;
import com.example.sherman.sbook.activities.BookViewHolders;
import com.example.sherman.sbook.R;

import java.util.List;

/**
 * Created by Sherman on 10/29/2016.
 */

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookViewHolders> {
    private List<BookItem> itemList;
    private Context context;

    public BookRecyclerViewAdapter(Context context, List<BookItem> itemList) {
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
        holder.countryName.setText(itemList.get(position).getName());
       // holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
