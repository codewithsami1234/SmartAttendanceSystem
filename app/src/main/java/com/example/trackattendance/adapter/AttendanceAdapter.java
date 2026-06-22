package com.example.trackattendance.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trackattendance.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Entity.Student;
public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private List<Student> studentList;
    private Map<Integer, String> attendanceMap = new HashMap<>();
    public AttendanceAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.txtName.setText(student.getName());
        holder.txtRoll.setText(student.getRollNo());
        // STEP 1: Remove listener first (VERY IMPORTANT)
        holder.radioGroup.setOnCheckedChangeListener(null);
        // STEP 2: Restore previous selection
        String saved = attendanceMap.get(student.getId());
        if ("PRESENT".equals(saved)) {
            holder.rbPresent.setChecked(true);
        } else if ("ABSENT".equals(saved)) {
            holder.rbAbsent.setChecked(true);
        } else {
            holder.radioGroup.clearCheck();
        }
        // STEP 3: Set listener again
        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbPresent) {
                attendanceMap.put(student.getId(), "PRESENT");
            } else if (checkedId == R.id.rbAbsent) {
                attendanceMap.put(student.getId(), "ABSENT");
            }
        });
    }
    @Override
    public int getItemCount() {
        return studentList.size();
    }
    public Map<Integer, String> getAttendanceMap() {
        return attendanceMap;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRoll;
        RadioGroup radioGroup;
        RadioButton rbPresent, rbAbsent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtRoll = itemView.findViewById(R.id.txtRoll);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            rbPresent = itemView.findViewById(R.id.rbPresent);
            rbAbsent = itemView.findViewById(R.id.rbAbsent);
        }
    }
}