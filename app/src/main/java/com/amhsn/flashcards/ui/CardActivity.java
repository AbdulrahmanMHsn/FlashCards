package com.amhsn.flashcards.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amhsn.flashcards.R;
import com.amhsn.flashcards.adapter.CardListAdapter;
import com.amhsn.flashcards.database.entity.CardEntity;
import com.amhsn.flashcards.database.entity.SubjectEntity;
import com.amhsn.flashcards.viewmodel.CardListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;
import java.util.Random;

public class CardActivity extends AppCompatActivity {

    private static final String TAG = CardActivity.class.getSimpleName();
    //vars
    private CardListAdapter adapter;
    private CardListViewModel cardListViewModel;
    private CardEntity cardEntity;
    private int mCardParentId;
    //widgets
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Log.d(TAG, "onCreate: Started.");

        setupToolbar();
        setupView();
        setupRecyclerView();

        //get id subject
        mCardParentId = getIntent().getIntExtra("SUBJECT_EXTRA_ID", -1);

        // Preparing our factory
        CardListViewModel.Factory factory = new CardListViewModel.Factory(getApplication(), mCardParentId);
        cardListViewModel = ViewModelProviders.of(this,factory).get(CardListViewModel.class);
        cardListViewModel.getCardsByParentId().observe(this, new Observer<PagedList<CardEntity>>() {
            @Override
            public void onChanged(PagedList<CardEntity> cardEntities) {
                adapter.setList(cardEntities);
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
                final CardEntity card = adapter.getItem(position);
                cardListViewModel.delete(card);
                Toast.makeText(CardActivity.this, "Subject: " + Objects.requireNonNull(card).getId() + " deleted.", Toast.LENGTH_SHORT).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }


    private void setupView() {
        final FloatingActionButton actionButton = findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: adding new card");
                Random random = new Random();
                int n = random.nextInt(1000) + 1;
                cardListViewModel.insert(new CardEntity("This is front side. It should be a Word, or a Question. #" + n, "This is back side. It should be a Definition, or an Answer. #" + n, mCardParentId));

            }
        });
    }

    /*
     * setup recyclerView
     * */
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.container);
        adapter = new CardListAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_delete) {
            cardListViewModel.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * setup Toolbar
     * */
    private void setupToolbar() {
        Log.d(TAG, "setupToolbar: initializing 'Toolbar'.");
        Toolbar toolbar = findViewById(R.id.subject_toolbar);
        setSupportActionBar(toolbar);

        ImageView img_back = findViewById(R.id.card_img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating back to Subject Activity");
                startActivity(new Intent(CardActivity.this, SubjectActivity.class));
            }
        });
    }
}
