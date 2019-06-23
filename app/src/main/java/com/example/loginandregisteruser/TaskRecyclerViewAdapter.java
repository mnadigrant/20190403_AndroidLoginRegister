package com.example.loginandregisteruser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Tasks> mdata;
    private TaskRecyclerViewAdapter.OnTicketListener monTicketListener;


    public TaskRecyclerViewAdapter(Context mContext, List<Tasks> mdata, TaskRecyclerViewAdapter.OnTicketListener monTicketListener) {
        this.mContext = mContext;
        this.mdata = mdata;
        this.monTicketListener = monTicketListener;
    }

    @NonNull
    @Override
    public TaskRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_item,parent,false);
        TaskRecyclerViewAdapter.MyViewHolder viewHolder = new TaskRecyclerViewAdapter.MyViewHolder(view, monTicketListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.MyViewHolder myViewHolder, int postion) {

        myViewHolder.taskId.setText(mdata.get(postion).getId());
        myViewHolder.taskdesc.setText(mdata.get(postion).getDescription());
        myViewHolder.tasktitle.setText(mdata.get(postion).getTitle());
        myViewHolder.taskdone.setText(mdata.get(postion).getDone());

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView taskId;
        private TextView taskdesc;
        private TextView tasktitle;
        private TextView taskdone;

        TaskRecyclerViewAdapter.OnTicketListener onTicketListener;

        public MyViewHolder(@NonNull View itemView, TaskRecyclerViewAdapter.OnTicketListener onTicketListener) {
            super(itemView);

            taskId = (TextView) itemView.findViewById(R.id.id);
            taskdesc = (TextView) itemView.findViewById(R.id.description);
            tasktitle = (TextView) itemView.findViewById(R.id.title);
            taskdone = (TextView) itemView.findViewById(R.id.done);

            this.onTicketListener = onTicketListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTicketListener.onTicketClick(getAdapterPosition());
        }
    }

    //allows for each train to be selected individually
    public interface OnTicketListener{
        void onTicketClick(int position);
    }
}
