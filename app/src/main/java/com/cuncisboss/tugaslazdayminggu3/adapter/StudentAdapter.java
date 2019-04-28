package com.cuncisboss.tugaslazdayminggu3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cuncisboss.tugaslazdayminggu3.MainActivity;
import com.cuncisboss.tugaslazdayminggu3.R;
import com.cuncisboss.tugaslazdayminggu3.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {

    private List<Student> studentList;
    private Context context;
    private ClickMenuListener clickMenuListener;

    public StudentAdapter(List<Student> studentList, Context context, ClickMenuListener clickMenuListener) {
        this.studentList = studentList;
        this.context = context;
        this.clickMenuListener = clickMenuListener;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentHolder holder, final int position) {
        holder.tvName.setText(studentList.get(position).getName());
        holder.tvCity.setText(studentList.get(position).getCity());
        holder.tvDate.setText(studentList.get(position).getDate());
        holder.btnSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMenuListener.onClickMenu(holder.btnSelection, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCity, tvDate;
        ImageButton btnSelection;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvDate = itemView.findViewById(R.id.tv_date);
            btnSelection = itemView.findViewById(R.id.btn_selection);
        }
    }

    public interface ClickMenuListener {
        void onClickMenu(ImageButton btnSelection, int position);
    }

}




















