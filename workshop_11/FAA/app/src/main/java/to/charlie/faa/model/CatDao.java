package to.charlie.faa.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import to.charlie.faa.model.util.DateTimeConverter;
import to.charlie.faa.model.util.GenderConverter;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters({DateTimeConverter.class, GenderConverter.class})
public interface CatDao
{
	@Insert(onConflict = REPLACE)
	void insertSingleCat(Cat cat);

	@Insert
	void insertMultipleCats(Cat[] cats);

	@Insert
	void insertMultipleCats(List<Cat> cats);

	@Update(onConflict = REPLACE)
	void updateCat(Cat cat);

	@Delete
	void deleteCat(Cat cat);

	@Query("DELETE FROM cats")
	void deleteAll();

	@Query("SELECT * FROM cats WHERE id = :id")
	Cat fetchCatByCatId(int id);

	@Query("SELECT * FROM cats WHERE breed = :breed AND gender = :gender AND dob BETWEEN :startDate AND :endDate")
	LiveData<List<Cat>> getCats(String breed, String gender, Date startDate, Date endDate);

	@Query("SELECT * FROM cats WHERE admission_date BETWEEN :startDate AND :endDate")
	LiveData<List<Cat>> getCatsAdmittedBetweenDates(Date startDate, Date endDate);

	// Avoid using synchronous calls from the main thread
	@Query("SELECT * FROM cats WHERE admission_date BETWEEN :startDate AND :endDate")
	List<Cat> getCatsAdmittedBetweenDatesSync(Date startDate, Date endDate);

	@Query("SELECT * FROM cats")
	LiveData<List<Cat>> getAllCats();

	@Query("SELECT * FROM cats WHERE breed = :breed")
	LiveData<List<Cat>> getCatsByBreed(String breed);

	@Query("SELECT * FROM cats WHERE gender = :gender")
	LiveData<List<Cat>> getCatsByGender(String gender);

	@Query("SELECT * FROM cats WHERE breed = :breed AND gender = :gender")
	LiveData<List<Cat>> getCatsByBreedAndGender(String breed, String gender);

	@Query("SELECT * FROM cats WHERE dob BETWEEN :startDate AND :endDate")
	LiveData<List<Cat>> getCatsBornBetweenDates(Date startDate, Date endDate);

	@Query("SELECT * FROM cats WHERE breed = :breed AND dob BETWEEN :startDate AND:endDate")
	LiveData<List<Cat>> getCatsByBreedAndBornBetweenDates(String breed, Date startDate, Date endDate);

	@Query("SELECT * FROM cats WHERE gender = :gender AND dob BETWEEN :startDate AND:endDate")
	LiveData<List<Cat>> getCatsByGenderAndBornBetweenDates(String gender, Date startDate, Date endDate);

	@Query("SELECT * FROM cats WHERE breed = :breed AND gender = :gender AND dob BETWEEN:startDate AND:endDate")
	LiveData<List<Cat>> getCatsByBreedAndGenderAndBornBetweenDates(String breed, String gender, Date startDate, Date endDate);
}
