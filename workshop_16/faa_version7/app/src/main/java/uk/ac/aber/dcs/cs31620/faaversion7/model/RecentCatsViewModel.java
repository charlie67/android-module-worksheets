package uk.ac.aber.dcs.cs31620.faaversion7.model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.aber.dcs.cs31620.faaversion7.R;
import uk.ac.aber.dcs.cs31620.faaversion7.datasource.FaaRepository;

/**
 * Recent Cats View Model. Holds UI data for recent cats.
 * @author Chris Loftus
 * @version 1, 1/09/2018.
 */
public class RecentCatsViewModel extends AndroidViewModel{
    private FaaRepository repository;
    private LiveData<List<Cat>> recentCats;
    private final int numDaysRecent;

    public RecentCatsViewModel(@NonNull Application application) {
        super(application);
        repository = new FaaRepository(application);

        // Have to kick the repository into action. For this version doesn't
        // appear to be necessary. Hum.
        // repository.getRecentCatsSync(new Date(), new Date());

        numDaysRecent = application.getResources().getInteger(R.integer.num_days_recent);

        // We should also load recent cats
        loadRecentCats();
    }

    private void loadRecentCats() {
        // We actually make the present the future. This is a fudge to
        // make sure the LiveData query remains relevant to the admission
        // of new cats after the query has been made. If we don't do this
        // the LiveData will not emit onChange requests to its Observers.
        // Bug: we should force re-query when the real current date
        // changes to a new day, otherwise the recent cats period for
        // the LiveData query will stretch!
        Calendar present = Calendar.getInstance();
        present.add(Calendar.DATE, 365);
        Date endDate = present.getTime();

        Calendar past = Calendar.getInstance();
        past.add(Calendar.DATE, -numDaysRecent);
        recentCats = repository.getRecentCats(past.getTime(), endDate);
    }

    public LiveData<List<Cat>> getRecentCats() {
        return recentCats;
    }

    public List<Cat> getRecentCatsSync(){
        Date endDate = Calendar.getInstance().getTime();

        Calendar past = Calendar.getInstance();
        past.add(Calendar.DATE, -numDaysRecent);
        return repository.getRecentCatsSync(past.getTime(), endDate);
    }
}
