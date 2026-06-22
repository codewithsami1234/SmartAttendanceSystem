package database;
import androidx.room.*;
import java.util.List;
import Entity.Attendance;
@Dao
public interface AttendanceDao {
    @Insert
    void insert(Attendance attendance);
    @Update
    void update(Attendance attendance);
    @Delete
    void delete(Attendance attendance);
    @Query("SELECT * FROM attendance")
    List<Attendance> getAllAttendance();
    @Query("SELECT * FROM attendance WHERE studentId = :studentId AND date = :date LIMIT 1")
    Attendance getAttendance(int studentId, String date);
    @Query("DELETE FROM attendance WHERE studentId = :studentId")
    void deleteAttendanceByStudentId(int studentId);
    @Query("SELECT COUNT(*) FROM attendance WHERE date = :date AND status = 'PRESENT'")
    int getPresentCountByDate(String date);
    @Query("SELECT COUNT(*) FROM attendance WHERE date = :date AND status = 'ABSENT'")
    int getAbsentCountByDate(String date);
}