package fragment;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trackattendance.R;
import com.example.trackattendance.adapter.StudentAdapter;
import java.util.List;
import database.AppDatabase;
import Entity.Student;
public class StudentsFragment extends Fragment {
    RecyclerView recyclerView;
    Button btnAdd;
    AppDatabase db;
    StudentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        btnAdd = view.findViewById(R.id.btnAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db = AppDatabase.getInstance(requireContext());
        loadStudents();
        btnAdd.setOnClickListener(v -> showAddDialog());
        return view;
    }
    private void loadStudents() {
        List<Student> list = db.studentDao().getAllStudents();
        adapter = new StudentAdapter(list, db);
        recyclerView.setAdapter(adapter);
    }
    private void showAddDialog() {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_student, null);
        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtRoll = dialogView.findViewById(R.id.edtRoll);
        new AlertDialog.Builder(getContext())
                .setTitle("Add Student")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    Student student = new Student(
                            edtName.getText().toString(),
                            edtRoll.getText().toString(),
                            1
                    );
                    db.studentDao().insert(student);
                    loadStudents();
                })
                .setNegativeButton("Cancel", null).show();
    }
}