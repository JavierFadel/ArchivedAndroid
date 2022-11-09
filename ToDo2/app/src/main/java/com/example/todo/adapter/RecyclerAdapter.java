package com.example.todo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.model.Note;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Note> noteArrayList;

    // Constructor
    public RecyclerAdapter(List<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
    }

    // Turn xml into object
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_recycler, viewGroup, false);
        return new ViewHolder(view);
    }

    // Bind data to object
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Note note = noteArrayList.get(position);

        holder.itemName.setText(note.getItem());
        holder.itemQuantity.setText(note.getQuantity());
        holder.itemNote.setText(note.getNote());
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity, itemNote;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            itemNote = itemView.findViewById(R.id.item_note);
        }
    }
}
