package Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String rollNo;
    private int semesterId;

    public Student(String name, String rollNo, int semesterId) {
        this.name = name;
        this.rollNo = rollNo;
        this.semesterId = semesterId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRollNo() { return rollNo; }
    public int getSemesterId() { return semesterId; }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    public void setSemesterId(int semesterId) { this.semesterId = semesterId; }
}