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
import com.example.sherman.sbook.models.Category;
import com.example.sherman.sbook.models.Notification;

import java.util.List;

/**
 * Created by Sherman on 10/29/2016.
 */

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.NotificationViewHolders> {
    private List<Notification> itemList;
    private Context context;

    public NotificationRecyclerViewAdapter(Context context, List<Notification> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public NotificationViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, null);
        NotificationViewHolders rcv = new NotificationViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolders holder, int position) {
        holder.content.setText(itemList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class NotificationViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView content;

        public NotificationViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            content = (TextView) itemView.findViewById(R.id.content);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}

