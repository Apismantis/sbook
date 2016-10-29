package com.example.sherman.sbook.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sherman.sbook.R;

/**
 * Created by Sherman on 10/29/2016.
 */
public class BookViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView rating;
    public TextView title;
    public TextView author;

    public BookViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        title = (TextView) itemView.findViewById(R.id.book_name);
        author = (TextView) itemView.findViewById(R.id.author);
        rating = (TextView) itemView.findViewById(R.id.rating);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
