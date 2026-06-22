package fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.trackattendance.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import database.AppDatabase;
public class DashboardFragment extends Fragment {
    private TextView tvTotalStudents, tvPresentStudents, tvAbsentStudents;
    private AppDatabase db;
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        tvTotalStudents = view.findViewById(R.id.tvTotalStudents);
        tvPresentStudents = view.findViewById(R.id.tvPresentStudents);
        tvAbsentStudents = view.findViewById(R.id.tvAbsentStudents);
        db = AppDatabase.getInstance(requireContext());
        loadDashboard();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (db != null) loadDashboard();
    }
    private void loadDashboard() {
        try {
            String today = new SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
            ).format(new Date());
            int total = db.studentDao().getTotalStudents();
            int present = db.attendanceDao().getPresentCountByDate(today);
            int absent = db.attendanceDao().getAbsentCountByDate(today);
            tvTotalStudents.setText(
                    getString(R.string.total_students, total)
            );
            tvPresentStudents.setText(
                    getString(R.string.present_students, present)
            );
            tvAbsentStudents.setText(
                    getString(R.string.absent_students, absent)
            );
        } catch (Exception e) {
            e.printStackTrace(); // temporary safe debug
        }
    }
}