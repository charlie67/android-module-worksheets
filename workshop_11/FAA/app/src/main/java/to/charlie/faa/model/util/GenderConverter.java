package to.charlie.faa.model.util;

import androidx.room.TypeConverter;

import to.charlie.faa.model.Gender;

public class GenderConverter
{
	@TypeConverter
	public static String toString(Gender gender) {
		return gender == null ? null : gender.toString();
	}

	@TypeConverter
	public static Gender toGender(String gender) {
		return gender == null ? null : Gender.valueOf(gender);
	}
}
