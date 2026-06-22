package fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trackattendance.R;
import com.example.trackattendance.adapter.AttendanceAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import Entity.Attendance;
import Entity.Student;
import database.AppDatabase;
public class AttendanceFragment extends Fragment {
    private RecyclerView recyclerView;
    private AppDatabase db;
    private AttendanceAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container,
                false
        );
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );
        Button btnSave = view.findViewById(R.id.btnSaveAttendance);
        db = AppDatabase.getInstance(requireContext());
        loadStudents();
        btnSave.setOnClickListener(v -> saveAttendance());
        return view;
    }
    private void loadStudents() {
        List<Student> list = db.studentDao().getAllStudents();
        adapter = new AttendanceAdapter(list);
        recyclerView.setAdapter(adapter);
    }
    private void saveAttendance() {
        List<Student> list = db.studentDao().getAllStudents();
        Map<Integer, String> attendanceMap = adapter.getAttendanceMap();
        String date = new SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
        ).format(new Date());
        int inserted = 0;
        int updated = 0;
        for (Student student : list) {
            String status = attendanceMap.get(student.getId());
            if (status != null) {
                // CHECK if record already exists
                Attendance existing = db.attendanceDao()
                        .getAttendance(student.getId(), date);
                if (existing == null) {
                    // INSERT NEW RECORD
                    Attendance attendance = new Attendance(
                            student.getId(),
                            1,
                            date,
                            status
                    );
                    db.attendanceDao().insert(attendance);
                    inserted++;
                } else {
                    // UPDATE EXISTING RECORD
                    existing.setStatus(status);
                    db.attendanceDao().update(existing);
                    updated++;
                }
            }
        }
        Toast.makeText(
                requireContext(),
                "Saved: " + inserted + " new, " + updated + " updated",
                Toast.LENGTH_SHORT
        ).show();
    }
}