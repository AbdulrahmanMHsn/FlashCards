package com.amhsn.flashcards.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.amhsn.flashcards.R;
import com.amhsn.flashcards.database.entity.CardEntity;

import java.util.ArrayList;
import java.util.List;

public class CardListAdapter extends PagedListAdapter<CardEntity, CardListAdapter.CardViewHolder> {

    private List<CardEntity> list = new ArrayList<>();

    public CardListAdapter(Context context) {
        super(CardEntity.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.front_textView.setText(list.get(position).getFrontSide());
        holder.back_textView.setText(list.get(position).getBackSide());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CardEntity> cardEntity) {
        this.list = cardEntity;
        notifyDataSetChanged();
    }

    public CardEntity getItem(int position) {
        return list.get(position);
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        TextView front_textView, back_textView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            front_textView = itemView.findViewById(R.id.card_front_textView);
            back_textView = itemView.findViewById(R.id.card_back_textView);
        }
    }
}
