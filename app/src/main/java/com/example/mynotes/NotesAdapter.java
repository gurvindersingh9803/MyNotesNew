package com.example.mynotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    @NonNull
    List<model> notes;
    private List<model> notesFull;

    public Context mCtx;
    LayoutInflater layoutInflater;
    RelativeLayout relativeLayout;
    Activity activity;
    private ArrayList<model> mNotes;


    NotesAdapter(Context mCtx,List<model> notes) {

        this.notes = notes;
        layoutInflater = LayoutInflater.from(mCtx);
        this.mCtx = mCtx;
        this.activity = activity;
        notesFull = new ArrayList<>(notes);


    }

    public NotesAdapter(ArrayList<model> note) {
        mNotes = note;
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        TextView textView1;
        TextView textView2;

        LinearLayout linearLayout;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.titleOfNote);
            textView1 = itemView.findViewById(R.id.descOfNote);
            textView2 = itemView.findViewById(R.id.date);


            // desc = itemView.findViewById(R.id.textView2);

            linearLayout = itemView.findViewById(R.id.layout);

        }
    }

    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleitem,parent,false);
        NotesAdapter.NotesViewHolder noteViewHolder = new NotesAdapter.NotesViewHolder(view);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotesViewHolder holder, final int position) {


        Log.d(TAG, "onBindViewHolder() position: " + position);

        //holder.desc.setText(notes.get(position).desc);

//        holder.textView.setText(notes.get(position).id);




        holder.textView.setText(String.valueOf(notes.get(position).getTitle()));
        holder.textView1.setText(String.valueOf(notes.get(position).getDesc()));
        try {
            holder.textView2.setText(String.valueOf(notes.get(position).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //holder.textView.setText(carNames.get(position).price);
        //holder.textView.setText(carNames.get(position).color);
        //Log.e("ghfrdshbrvdhh", String.valueOf(current));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notifyDataSetChanged();
                notifyItemRemoved(position);


                String title =  notes.get(position).getTitle();
                String desc =  notes.get(position).getDesc();
                int id =  notes.get(position).getId();
                String categoryName =  notes.get(position).getcName();
                String uri =  notes.get(position).getUri();

                int cpos =  notes.get(position).getCpos();


                Context context = view.getContext();


                Intent intent = new Intent(context,Update.class);
                intent.putExtra("id",id);
                intent.putExtra("category",categoryName);
                intent.putExtra("cpos",cpos);
                intent.putExtra("uri",uri);


                intent.putExtra("title",title);
                intent.putExtra("desc", desc);
                Log.e("hhhhhhhhhhhhhhhhhhhhhh", uri);




                context.startActivity(intent);
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(mCtx);
                builder1.setMessage("Do you want to delete the note?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                int ids =  notes.get(position).getId();
                                Databasehelper databaseHelper = new Databasehelper(mCtx);
                                int delete = databaseHelper.deleteData(ids);

                                notes.remove(position);
                                databaseHelper.deleteData(position);
                                notifyItemRemoved(position);

                                if(delete != 0){
                                    notifyDataSetChanged();

                                }







                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                return false;
            }
        });

    }


    @Override
    public int getItemCount() {

        return notes.size();
    }

    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<model> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(notesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (model item : notesFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern) || item.getDesc().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notes.clear();
            notes.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}



