package uk.ac.aber.dcs.cs31620.faaversion5.model.util;

import androidx.room.TypeConverter;

import uk.ac.aber.dcs.cs31620.faaversion5.model.Gender;
/**
 * Utility class to convert SQLite text-based gender to string and vice versa
 * @author Chris Loftus
 * @version 1, 26/08/2018.
 */
public class GenderConverter {

    @TypeConverter
    public static String toString(Gender gender) {
        String result = gender == null ? null : gender.toString();
        return result;
    }

    @TypeConverter
    public static Gender toGender(String gender) {
        Gender result = gender == null ? null : Gender.valueOf(gender);
        return result;
    }
}


