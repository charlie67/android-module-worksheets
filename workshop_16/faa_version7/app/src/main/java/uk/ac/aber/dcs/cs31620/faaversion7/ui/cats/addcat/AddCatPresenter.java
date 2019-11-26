package uk.ac.aber.dcs.cs31620.faaversion7.ui.cats.addcat;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import uk.ac.aber.dcs.cs31620.faaversion7.model.AddCatViewModel;
import uk.ac.aber.dcs.cs31620.faaversion7.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion7.model.Gender;
import uk.ac.aber.dcs.cs31620.faaversion7.model.util.ResourceUtil;

public class AddCatPresenter implements AddCatContract.UserActionsListener {


    private final AddCatViewModel addCatViewModel;
    private final AddCatContract.View addCatView;
    private File photoFile;

    public AddCatPresenter(AddCatViewModel addCatViewModel,
                           AddCatContract.View addCatView){

        this.addCatViewModel = addCatViewModel;
        this.addCatView = addCatView;
    }

    @Override
    public void saveCat(String name, Gender gender, String breed, Date dob, String description) {
        if (dob == null || name == null || name.length() == 0) {
            addCatView.showMissingFieldsError();
        } else {
            String imagePath = "";
            if (photoFile != null && photoFile.exists()) {
                imagePath = photoFile.getAbsolutePath();
            }
            Cat newCat = new Cat(
                    name,
                    gender,
                    breed,
                    dob,
                    new Date(),
                    imagePath,
                    description);
            addCatViewModel.insertCat(newCat);
            addCatView.showCatsList();
        }
    }

    @Override
    public void takePicture() throws IOException {
        // Create the File where the photo should go. We save this for
        // when we get called back to tell us the image is available
        photoFile = ResourceUtil.createImageFile((Context)addCatView);
        addCatView.openCamera(photoFile);
    }

    @Override
    public String createImage(ImageView imageView) {
        String imagePath = "";
        if (photoFile != null && photoFile.exists()){
            imagePath = photoFile.getAbsolutePath();
        }
        ResourceUtil.loadImageBitmap((Context)addCatView, imagePath, imageView);

        return imagePath;
    }

    @Override
    public void imageCaptureFailed() {
        photoFile.delete();
        addCatView.showImageError();
    }
}
