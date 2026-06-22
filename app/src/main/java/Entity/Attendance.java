package Entity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(
        tableName = "attendance",
        foreignKeys = @ForeignKey(
                entity = Student.class,
                parentColumns = "id",
                childColumns = "studentId",
                onDelete = ForeignKey.CASCADE   // ⭐ IMPORTANT FIX
        ),
        indices = {@Index(value = {"studentId", "date"}, unique = true)}
)
public class Attendance {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int studentId;
    private int subjectId;
    private String date;
    private String status;
    public Attendance(int studentId, int subjectId, String date, String status) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.date = date;
        this.status = status;
    }
    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getSubjectId() { return subjectId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public void setId(int id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }
}