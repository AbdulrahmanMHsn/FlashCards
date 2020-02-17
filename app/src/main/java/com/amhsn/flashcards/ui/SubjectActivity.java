package com.amhsn.flashcards.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amhsn.flashcards.R;
import com.amhsn.flashcards.adapter.SubjectListAdapter;
import com.amhsn.flashcards.database.entity.SubjectEntity;
import com.amhsn.flashcards.viewmodel.SubjectListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class SubjectActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SubjectActivity.class.getSimpleName();
    //vars
    private SubjectListAdapter adapter;
    private SubjectListViewModel subjectListViewModel;
    private SubjectEntity subjectEntity;
    //widgets
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        Log.d(TAG, "onCreate: Started.");

        // invoking method
        setupToolbar();
        setupView();
        setupRecyclerView();

        // created object from SubjectListViewModel
        subjectListViewModel = ViewModelProviders.of(this).get(SubjectListViewModel.class);
        subjectListViewModel.getAllSubjects().observe(this, new Observer<List<SubjectEntity>>() {
            @Override
            public void onChanged(List<SubjectEntity> subjectEntities) {
                adapter.setList(subjectEntities);
            }
        });

        // Delete subject by swabbing item left and right
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NewApi")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final SubjectEntity subject = adapter.getItem(position);
                subjectListViewModel.delete(subject);
                Toast.makeText(SubjectActivity.this, "Subject: " + Objects.requireNonNull(subject).getTitle() + " deleted.", Toast.LENGTH_SHORT).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    /*
     * init View
     * */
    private void setupView() {
        Log.d(TAG, "setupRecyclerView: initializing 'Widgets'.");
        final FloatingActionButton actionButton = findViewById(R.id.fab);
        actionButton.setOnClickListener(this);
    }

    /*
     * setup RecyclerView
     * */
    private void setupRecyclerView() {
        Log.d(TAG, "setupRecyclerView: initializing 'RecyclerView and adapter list'.");
        recyclerView = findViewById(R.id.container);
        adapter = new SubjectListAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            openDialogAddSubject(view.getContext());
        }
    }

    /*
     * Use to add subject in list
     * */
    @SuppressLint("NewApi")
    private void openDialogAddSubject(Context context) {
        Log.d(TAG, "openDialogAddSubject: it was successfully implemented");
        // declare object from Dialog
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        // set layout in dialog
        dialog.setContentView(R.layout.dialog_add_subject);

        final EditText editText = dialog.findViewById(R.id.dialog_edTxt_subjectName);

        final Button btn_saveSubject = dialog.findViewById(R.id.dialog_btn_save);
        btn_saveSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.length() <= 0) {
                    editText.setBackgroundResource(R.drawable.background_input_empty);
                    return;
                }
                subjectListViewModel.insert(new SubjectEntity(editText.getText().toString()));
                dialog.dismiss();
            }
        });

        final Button btn_close = dialog.findViewById(R.id.dialog_btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();// display dialog
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_delete) {
            subjectListViewModel.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        Log.d(TAG, "setupToolbar: initializing 'Toolbar'.");
        Toolbar toolbar = findViewById(R.id.subject_toolbar);
        setSupportActionBar(toolbar);
    }
}
