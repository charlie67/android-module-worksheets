package uk.ac.aber.dcs.cs31620.simplefilewithparcelableexample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Defines a Parcelable for the screen settings. This is so that we
 * can marshal/unmarshall the contents to and from temporary storage.
 * Note that a Parcelable will only live for the lifetime of an app.
 *
 */
public class EditorPreferences implements Parcelable {
	private int textColour;
	private int backgroundColour;
	private int textSize;

	/**
	 * The unmarshaller. This must be written in this form with a CREATOR public static field
	 */
	public static Creator<EditorPreferences> CREATOR =
		new Creator<EditorPreferences>() {

		/**
		 * The unmarshaller method. Is the reverse of writeToParcel
		 * @param source - The Parcel from which we extract our primitive data and from which we create
		 * our EditorPreferences object
		 */
		public EditorPreferences createFromParcel(Parcel source) {
			int tc = source.readInt();
			int bc = source.readInt();
			int ts = source.readInt();
			return new EditorPreferences(tc, bc, ts);
		}

		/**
		 * Create a new array of the Parcelable class, if this is needed
		 */
		public EditorPreferences[] newArray(int size) {
			return new EditorPreferences[size];
		}
	};

	public EditorPreferences(int textColour, int backgroundColour, int textSize) {
		this.textColour = textColour;
		this.backgroundColour = backgroundColour;
		this.textSize = textSize;
	}

	/**
	 * The marshaller. We write the constituent parts of object as primitives. 
	 * @param dest - The Parcel in which the object should be written.
	 * @param flags - 0 or PARCELABLE_WRITE_RETURN_VALUE which means free up the resource
	 * in this method.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(textColour);
		dest.writeInt(backgroundColour);
		dest.writeInt(textSize);
	}

	public int getTextColour() {
		return textColour;
	}
	
	public void setTextColour(int tc) {
		textColour = tc;
	}
	
	public int getBackgroundColour() {
		return backgroundColour;
	}
	
	public void setBackgroundColour(int bc) {
		backgroundColour = bc;
	}
	
	public int getTextSize() {
		return textSize;
	}
	
	public void setTextSize(int ts) {
		textSize = ts;
	}

	/**
	 * Describe the kinds of special objects contained in this Parcelable's marshalled representation.
	 * @return a bitmask indicating the set of special object types marshalled by the Parcelable. 
	 */
	@Override
	public int describeContents() {
		return 0;
	}
}
