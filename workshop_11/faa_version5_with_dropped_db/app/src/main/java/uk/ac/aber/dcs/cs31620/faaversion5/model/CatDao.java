package uk.ac.aber.dcs.cs31620.faaversion5.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import uk.ac.aber.dcs.cs31620.faaversion5.model.util.DateTimeConverter;
import uk.ac.aber.dcs.cs31620.faaversion5.model.util.GenderConverter;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Cat DAO. CRUD operations to support Room Database interactions
 * @author Chris Loftus
 * @version 1, 26/08/2018.
 */
@Dao
@TypeConverters({DateTimeConverter.class, GenderConverter.class})
public interface CatDao {

    @Insert(onConflict = IGNORE)
    void insertSingleCat(Cat cat);

    @Insert
    void insertMultipleCats(Cat[] catsList);

    @Insert
    void insertMultipleCats(List<Cat> catsList);

    @Update(onConflict = REPLACE)
    void updateCat(Cat cat);

    @Delete
    void deleteCat(Cat cat);

    @Query("DELETE FROM cats")
    void deleteAll();

    @Query("SELECT * FROM cats WHERE id = :id")
    Cat fetchCatByCatId(int id);

    // LiveData provides asynchronous calling without additional setup
    @Query("SELECT * FROM cats")
    LiveData<List<Cat>> getAllCats();

    @Query("SELECT * FROM cats WHERE breed = :breed AND gender = :gender AND dob BETWEEN :startDate AND :endDate")
    LiveData<List<Cat>> getCats(String breed, String gender, Date startDate, Date endDate);

    @Query("SELECT * FROM cats WHERE admission_date BETWEEN :startDate AND :endDate")
    LiveData<List<Cat>> getCatsAdmittedBetweenDates(Date startDate, Date endDate);

    // Avoid using synchronous calls from the main thread
    @Query("SELECT * FROM cats WHERE admission_date BETWEEN :startDate AND :endDate")
    List<Cat> getCatsAdmittedBetweenDatesSync(Date startDate, Date endDate);

    @Query("SELECT * FROM cats WHERE breed = :breed")
    LiveData<List<Cat>> getCatsByBreed(String breed);

    @Query("SELECT * FROM cats WHERE gender = :gender")
    LiveData<List<Cat>> getCatsByGender(String gender);

    @Query("SELECT * FROM cats WHERE breed = :breed AND gender = :gender")
    LiveData<List<Cat>> getCatsByBreedAndGender(String breed, String gender);

    @Query("SELECT * FROM cats WHERE dob BETWEEN :startDate AND :endDate")
    LiveData<List<Cat>> getCatsBornBetweenDates(Date startDate, Date endDate);

    @Query("SELECT * FROM cats WHERE breed = :breed AND dob BETWEEN :startDate AND :endDate")
    LiveData<List<Cat>> getCatsByBreedAndBornBetweenDates(String breed, Date startDate, Date endDate);

    @Query("SELECT * FROM cats WHERE gender = :gender AND dob BETWEEN :startDate AND :endDate")
    LiveData<List<Cat>> getCatsByGenderAndBornBetweenDates(String gender, Date startDate, Date endDate);

    @Query("SELECT * FROM cats WHERE breed = :breed AND gender = :gender AND dob BETWEEN :startDate AND :endDate")
    LiveData<List<Cat>> getCatsByBreedAndGenderAndBornBetweenDates(String breed, String gender, Date startDate, Date endDate);
}
