package com.example.trackattendance.adapter;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trackattendance.R;
import java.util.List;
import Entity.Student;
import database.AppDatabase;
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> list;
    private AppDatabase db;
    public StudentAdapter(List<Student> list, AppDatabase db) {
        this.list = list;
        this.db = db;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = list.get(position);
        holder.txtName.setText(student.getName());
        holder.txtRoll.setText(student.getRollNo());
        // =========================
        // DELETE STUDENT (FIXED)
        // =========================
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete this student?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        int pos = holder.getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            Student studentToDelete = list.get(pos);
                            // ⚠️ CASCADE in DB will auto-delete attendance
                            db.studentDao().delete(studentToDelete);
                            list.remove(pos);
                            notifyItemRemoved(pos);
                            notifyItemRangeChanged(pos, list.size());
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
        // =========================
        // EDIT STUDENT
        // =========================
        holder.btnEdit.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;
            Student currentStudent = list.get(pos);
            View dialogView = LayoutInflater.from(v.getContext())
                    .inflate(R.layout.dialog_add_student, null);
            EditText edtName = dialogView.findViewById(R.id.edtName);
            EditText edtRoll = dialogView.findViewById(R.id.edtRoll);
            edtName.setText(currentStudent.getName());
            edtRoll.setText(currentStudent.getRollNo());
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Edit Student")
                    .setView(dialogView)
                    .setPositiveButton("Update", (dialog, which) -> {
                        currentStudent.setName(
                                edtName.getText().toString().trim()
                        );
                        currentStudent.setRollNo(
                                edtRoll.getText().toString().trim()
                        );
                        db.studentDao().update(currentStudent);
                        int updatedPos = holder.getAdapterPosition();
                        if (updatedPos != RecyclerView.NO_POSITION) {
                            notifyItemChanged(updatedPos);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public void updateList(List<Student> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRoll;
        Button btnEdit, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtRoll = itemView.findViewById(R.id.txtRoll);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}