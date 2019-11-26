package uk.ac.aber.dcs.cs31620.faaversion7.ui.cats.addcat;

import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import uk.ac.aber.dcs.cs31620.faaversion7.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion7.model.Gender;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface AddCatContract {

    interface View {
        void openCamera(File saveTo);

        void showMissingFieldsError();

        void showImageError();

        void showCatsList();

    }

    interface UserActionsListener {
        void saveCat(String name, Gender gender, String breed, Date dob, String description);

        void takePicture() throws IOException;

        String createImage(ImageView imageView);

        void imageCaptureFailed();
    }
}
