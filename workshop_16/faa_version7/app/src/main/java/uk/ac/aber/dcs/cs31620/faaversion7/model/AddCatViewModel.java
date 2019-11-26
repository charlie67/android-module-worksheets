package uk.ac.aber.dcs.cs31620.faaversion7.model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import uk.ac.aber.dcs.cs31620.faaversion7.datasource.FaaRepository;

/**
 * Add Cat View Model. Interfaces with repository.
 * @author Chris Loftus
 * @version 1, 1/09/2018.
 */
public class AddCatViewModel extends AndroidViewModel{
    private FaaRepository repository;

    public AddCatViewModel(@NonNull Application application) {
        super(application);
        repository = new FaaRepository(application);

    }

    public void insertCat(Cat cat){
        repository.insert(cat);
    }
}
