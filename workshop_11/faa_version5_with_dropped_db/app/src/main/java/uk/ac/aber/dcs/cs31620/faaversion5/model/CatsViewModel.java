package uk.ac.aber.dcs.cs31620.faaversion5.model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.aber.dcs.cs31620.faaversion5.R;
import uk.ac.aber.dcs.cs31620.faaversion5.datasource.FaaRepository;
import uk.ac.aber.dcs.cs31620.faaversion5.ui.cats.CatsRecyclerWithListAdapter;

/**
 * Cats View Model. Holds UI data for all things cat.
 * @author Chris Loftus
 * @version 2, 1/09/2018.
 */
public class CatsViewModel extends AndroidViewModel{

    private FaaRepository repository;
    private LiveData<List<Cat>> catList;

    private String gender;
    private String breed;
    private String ageConstraint;

    private final String[] ageConstraints;

    private final String anyGender;
    private final String anyBreed;
    private final String anyAge;
    private CatsRecyclerWithListAdapter adapter;

    public CatsViewModel(@NonNull Application application) {
        super(application);
        repository = new FaaRepository(application);

        ageConstraints = application.getResources().getStringArray(R.array.age_range_array);

        anyGender = application.getResources().getString(R.string.any_gender);
        gender = anyGender;
        anyBreed = application.getResources().getString(R.string.any_breed);
        breed = anyBreed;
        anyAge = application.getResources().getString(R.string.any_age);
        ageConstraint = anyAge;

        // We might as well load for everything right now, since if we're
        // here this is the first time and the user won't have selected anything
        catList = repository.getAllCats();
    }

    public LiveData<List<Cat>> getCats(){
        return catList;
    }

    public LiveData<List<Cat>> getCats(String breed, String gender, String ageConstraint) {
        Date startDate = null;
        Date endDate = null;
        boolean changed = false;

        // Did anything change since last time?
        if (!this.breed.equals(breed)){
            this.breed = breed;
            changed = true;
        }
        if (!this.gender.equals(gender)){
            this.gender = gender;
            changed = true;
        }
        if (!this.ageConstraint.equals(ageConstraint)){
            this.ageConstraint = ageConstraint;
            changed = true;
        }
        if (changed) {

            // We load again based on the values in the ivars.
            // We look for values that are defaults: those are the ones that
            // need excluding from the request, and determine which method
            // overload to call.
            if (!breed.equals(anyBreed) && gender.equals(anyGender) && ageConstraint.equals(anyAge)){
                // Just load based on breed value: everything else is default and so omitted
                catList = repository.getCatsByBreed(breed);
            }
            else if (breed.equals(anyBreed) && !gender.equals(anyGender) && ageConstraint.equals(anyAge)){
                // Just load based on gender value: everything else is default and so omitted
                catList = repository.getCatsByGender(gender);
            }
            else if (breed.equals(anyBreed) && gender.equals(anyGender) && !ageConstraint.equals(anyAge)){
                // Just load based on ageConstraint value: everything else is default
                // We need to express this as a time period. If a cat's DOB is between that range then
                // it will be loaded.
                startDate = getStartDate(ageConstraint);
                endDate = getEndDate(ageConstraint);
                catList = repository.getCatsBornBetweenDates(startDate, endDate);
            }
            else if (!breed.equals(anyBreed) && !gender.equals(anyGender) && ageConstraint.equals(anyAge)) {
                // Just load based on breed and gender value: age is default and so omitted
                catList = repository.getCatsByBreedAndGender(breed, gender);
            }
            else if (!breed.equals(anyBreed) && gender.equals(anyGender) && !ageConstraint.equals(anyAge)) {
                startDate = getStartDate(ageConstraint);
                endDate = getEndDate(ageConstraint);
                catList = repository.getCatsByBreedAndBornBetweenDates(breed, startDate, endDate);
            }
            else if (breed.equals(anyBreed) && !gender.equals(anyGender) && !ageConstraint.equals(anyAge)) {
                startDate = getStartDate(ageConstraint);
                endDate = getEndDate(ageConstraint);
                catList = repository.getCatsByGenderAndBornBetweenDates(gender, startDate, endDate);
            }
            else if (!breed.equals(anyBreed) && !gender.equals(anyGender) && !ageConstraint.equals(anyAge)) {
                startDate = getStartDate(ageConstraint);
                endDate = getEndDate(ageConstraint);
                catList = repository.getCatsByBreedAndGenderAndBornBetweenDates(breed, gender, startDate, endDate);
            }
            else {
                // All are defaults

                catList = repository.getAllCats();
            }
        }
        return catList;
    }

    private Date getEndDate(String ageConstraint) {
        Calendar cal = Calendar.getInstance();
        if (ageConstraint.equals(ageConstraints[1])) { // 0 - 1 year
            // End date will be now. Fudge time: we add year here (into the future)
            // to solve the issue of LiveData queries showing up to 1 year cats
            // and a brand new cat is registered that was born on today's date
            // and has a time slightly later than the time used in the query and so
            // the LiveData update does not happen
            cal.add(Calendar.DATE, 365);
        }
        else if (ageConstraint.equals(ageConstraints[2])) { // 1 - 2 years
            // End date is 1 year ago
            cal.add(Calendar.DATE, -364);
        }
        else if (ageConstraint.equals(ageConstraints[3])) { // 2 - 5 years
            // End date is 2 years ago
            cal.add(Calendar.DATE, -(365*2-1));
        } else { // older
            // End date is 5 years ago
            cal.add(Calendar.DATE, -(365*5-1));
        }
        return cal.getTime();
    }

    private Date getStartDate(String ageConstraint) {
        Calendar cal = Calendar.getInstance();
        if (ageConstraint.equals(ageConstraints[1])) { // 0 - 1 year
            // Start date is 1 year ago
            cal.add(Calendar.DATE, -365);
        }
        else if (ageConstraint.equals(ageConstraints[2])) { // 1 - 2 years
            // Start date is 2 year ago
            cal.add(Calendar.DATE, -(365 * 2));
        }
        else if (ageConstraint.equals(ageConstraints[3])) { // 2 - 5 years
            // Start date is 5 years ago
            cal.add(Calendar.DATE, -(365*5));
        } else { // older
            // Start date is > 5 years ago
            // Just use a very large number
            cal.add(Calendar.DATE, -(365*40-1));
        }
        return cal.getTime();
    }

    public CatsRecyclerWithListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CatsRecyclerWithListAdapter adapter){
        this.adapter = adapter;
    }
}
