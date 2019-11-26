package uk.ac.aber.dcs.cs31620.faaversion7.database_tests.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.aber.dcs.cs31620.faaversion7.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion7.model.Gender;

import static uk.ac.aber.dcs.cs31620.faaversion7.model.Gender.MALE;

public class TestUtil {
    private final Date veryRecentAdmission;
    private final Calendar calendar;


    public TestUtil() {
        veryRecentAdmission = new Date(); // Today!
        calendar = Calendar.getInstance();
    }

    public Date getVeryRecentAdmission() {
        return veryRecentAdmission;
    }

    public List<Cat> createMaleRecent(int num, int days){
        List<Cat> cats = new ArrayList<>();

        buildCatsList(num, MALE, createDate(days), veryRecentAdmission, cats);

        return cats;
    }

    public Date createDate(int days){
        // We want one fixed calendar date that is set up when all the tests start. So
        // we just make a deep copy of it (with clone) and play with that.
        Calendar cal = (Calendar) calendar.clone();
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    private void buildCatsList(int num, Gender gender, Date dob, Date admission, List<Cat> cats) {
        for(int i=0; i<num; i++) {
            cats.add(new Cat("Tibs",
                    gender,
                    "Moggie",
                    dob,
                    admission,
                    "cat1.png",
                    "Lorem ipsum dolor sit amet, "));
        }
    }
}
