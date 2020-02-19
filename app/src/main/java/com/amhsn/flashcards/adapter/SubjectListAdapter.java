package com.amhsn.flashcards.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.amhsn.flashcards.R;
import com.amhsn.flashcards.database.entity.SubjectEntity;
import com.amhsn.flashcards.ui.CardActivity;

import java.util.ArrayList;
import java.util.List;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.SubjectViewHolder> {

    private static final String TAG = SubjectListAdapter.class.getSimpleName();
    private List<SubjectEntity> list = new ArrayList<>();
    private Context context;

    public SubjectListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SubjectViewHolder holder, final int position) {
        if (list != null) {
            holder.txtVw_title.setText(list.get(position).getTitle());
            holder.subjectLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: navigating to card subject and pass data");
                    Intent intent = new Intent(context, CardActivity.class);
                    intent.putExtra("SUBJECT_EXTRA_ID", list.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setList(List<SubjectEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Method to get item by position.
     * @param position
     * @return
     */
    @Nullable
    public SubjectEntity getItem(int position) {
        return  list.get(position);
    }


    class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView txtVw_title,txtVw_date;
        LinearLayout subjectLinearLayout;
        SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVw_title = itemView.findViewById(R.id.subject_txtVw_title);
            subjectLinearLayout = itemView.findViewById(R.id.subjectLinearLayout);
        }
    }
}
