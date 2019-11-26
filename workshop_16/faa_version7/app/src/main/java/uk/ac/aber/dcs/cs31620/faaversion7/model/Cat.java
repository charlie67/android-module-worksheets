package uk.ac.aber.dcs.cs31620.faaversion7.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import uk.ac.aber.dcs.cs31620.faaversion7.model.util.DateTimeConverter;
import uk.ac.aber.dcs.cs31620.faaversion7.model.util.GenderConverter;

/**
 * Defines a Cat
 * @author Chris Loftus
 * @version 1 12/06/2018.
 */

@Entity(tableName = "cats")
@TypeConverters({DateTimeConverter.class, GenderConverter.class})
public class Cat {

    public static final int ONE_YEAR = 12;

    @NonNull
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

    public Cat(String name, Gender gender, String breed, Date dob, Date admissionDate, String imagePath, String description){
        this.name = name;
        this.gender = gender;
        this.breed = breed;
        this.dob = dob;
        this.admissionDate = admissionDate;
        this.imagePath = imagePath;
        this.description = description;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public boolean isKitten(){
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

    public Date getAdmissionDate(){
        return admissionDate;
    }

    public String getImagePath(){
        return imagePath;
    }

    public void setAge(Date dob) {
        this.dob = dob;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", breed='" + breed + '\'' +
                ", description='" + description + '\'' +
                ", dob=" + dob +
                ", admission date=" + admissionDate +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    /**
     * We don't compare id since it is database generated and we may
     * want to check for equality before we insert in the database
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(name, cat.name) &&
                gender == cat.gender &&
                Objects.equals(breed, cat.breed) &&
                Objects.equals(dob, cat.dob);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, gender, breed, dob);
    }
}
