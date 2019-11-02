package to.charlie.faa.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import to.charlie.faa.model.util.DateTimeConverter;
import to.charlie.faa.model.util.GenderConverter;

@Entity(tableName = "cats")
@TypeConverters({DateTimeConverter.class, GenderConverter.class})
public class Cat {
    public static final int ONE_YEAR = 12;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private Gender gender;

    private String breed;

    private String description;

    private Date dob;

    @ColumnInfo(name = "admission_date")
    private Date admissionDate;

    @ColumnInfo(name = "main_image_path")
    private String imagePath;

    public Cat(String name, Gender gender, String breed, Date dob, int resourceId, String description) {
        this.name = name;
        this.gender = gender;
        this.breed = breed;
        this.dob = dob;
        this.resourceId = resourceId;
        this.description = description;
    }

    public boolean isKitten() {
        Date now = new Date();
        long daysDiff = DateTimeConverter.getDateDiff(now, dob, TimeUnit.DAYS);
        return daysDiff < 365;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    public String getBreed() {
        return breed;
    }

    public Date getDob() {
        return dob;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setAge(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", breed='" + breed + '\'' +
                ", description='" + description + '\'' +
                ", dob=" + dob +
                ", resourceId='" + resourceId + '\'' +
                '}';
    }
}