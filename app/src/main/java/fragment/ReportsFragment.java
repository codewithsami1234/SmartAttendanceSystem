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
public class ReportsFragment extends Fragment {
    private TextView tvReport;
    private AppDatabase db;
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        tvReport = view.findViewById(R.id.tvReport);
        db = AppDatabase.getInstance(requireContext());
        generateReport();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        generateReport(); // auto refresh
    }
    private void generateReport() {
        String today = new SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
        ).format(new Date());
        int totalStudents = db.studentDao().getTotalStudents();
        int presentToday = db.attendanceDao().getPresentCountByDate(today);
        int absentToday = totalStudents - presentToday;

        if (absentToday < 0) {
            absentToday = 0; // safety guard
        }
        double percentage = 0;

        if (totalStudents > 0) {
            percentage = (presentToday * 100.0) / totalStudents;
        }
        String report =
                "Attendance Report\n\n" +
                        "Date: " + today + "\n\n" + "Total Students: " + totalStudents + "\n" + "Present: " + presentToday + "\n" + "Absent: " + absentToday + "\n" +
                        "Attendance Percentage: " +
                        String.format(Locale.getDefault(), "%.2f", percentage) + "%";
        tvReport.setText(report);
    }
}