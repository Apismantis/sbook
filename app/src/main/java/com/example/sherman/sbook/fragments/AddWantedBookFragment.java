package com.example.sherman.sbook.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.constants.Database;
import com.example.sherman.sbook.models.Book;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kenp on 12/11/2016.
 */
public class AddWantedBookFragment extends android.support.v4.app.Fragment {

    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Database.USERS);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_wanted_book, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final DatabaseReference wantedBookRef = userRef.child("01vKuHSGJTV4Lx9sswhuWc30BC12").child("interests");

        final EditText etBookTitle;
        final EditText etBookAuthor;

        etBookTitle = (EditText) view.findViewById(R.id.etTitle);
        etBookAuthor = (EditText) view.findViewById(R.id.etAuthor);

        Button btnAddWantedBook = (Button) view.findViewById(R.id.btnAddWantedBook);
        btnAddWantedBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etBookTitle.getText().toString();
                String author = etBookAuthor.getText().toString();

                title = title.trim();
                author = author.trim();

                if (title.isEmpty() || author.isEmpty()) {
                    Toast.makeText(getContext(), R.string.invalid_book_info, Toast.LENGTH_SHORT).show();
                    return;
                }

                Book newWantedBook = new Book(title.trim(), author.trim());
                String idKey = wantedBookRef.push().getKey();
                wantedBookRef.child(idKey).setValue(newWantedBook);
            }
        });
    }
}
