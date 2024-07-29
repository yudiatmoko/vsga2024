/* *    Hi, Code Enthusiast! *    https://github.com/yudiatmoko *//* *    Hi, Code Enthusiast! *    https://github.com/yudiatmoko */package com.iyam.myvsgaproject2.ui.detail;import android.content.Context;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ArrayAdapter;import android.widget.TextView;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import com.iyam.myvsgaproject2.model.Note;import java.util.ArrayList;public class NoteAdapter extends ArrayAdapter<Note> {    public NoteAdapter(@NonNull Context context) {        super(context, 0, new ArrayList<>());    }    @NonNull    @Override    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {        View itemView = convertView;        if (itemView == null){            itemView = LayoutInflater.from(getContext()).inflate(                    android.R.layout.simple_list_item_2, parent, false            );        }        Note noteItem = getItem(position);        TextView tvNoteTitle = itemView.findViewById(android.R.id.text1);        tvNoteTitle.setText(noteItem.getNoteTitle());        TextView tvTimeStamp = itemView.findViewById(android.R.id.text2);        tvTimeStamp.setText(noteItem.getTimeStamp());        return itemView;    }}