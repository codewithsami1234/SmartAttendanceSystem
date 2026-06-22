package database;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import Entity.Attendance;
import Entity.Student;
@Database(
        entities = {Student.class, Attendance.class},
        version = 7,   // ⭐ MUST INCREASE
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract StudentDao studentDao();
    public abstract AttendanceDao attendanceDao();
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "attendance_db"
                    )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}