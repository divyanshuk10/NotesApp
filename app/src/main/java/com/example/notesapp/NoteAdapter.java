package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divyanshu Kumar on 2019-09-04.
 * divyanshuk10@gmail.com
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.Noteholder> {
    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public Noteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new Noteholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Noteholder holder, int position) {
        Note currentNote = notes.get(position);
        holder.tv_title.setText(currentNote.getTitle());
        holder.tv_priority.setText(currentNote.getPriority());
        holder.tv_description.setText(currentNote.getDescription());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class Noteholder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_priority;
        private TextView tv_description;

        public Noteholder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_priority = itemView.findViewById(R.id.tv_priority);
            tv_description = itemView.findViewById(R.id.tv_description);
        }
    }
}
