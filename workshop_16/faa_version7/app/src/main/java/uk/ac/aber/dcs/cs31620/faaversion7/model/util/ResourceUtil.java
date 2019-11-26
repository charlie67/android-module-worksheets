package uk.ac.aber.dcs.cs31620.faaversion7.model.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.ac.aber.dcs.cs31620.faaversion7.R;

/**
 * Simple utility class to support loading of images from assets/images folder.
 * NB: current implementatuon is synchronous
 *
 * @author Chris Loftus
 * @version 1, 26/08/2018.
 */
public class ResourceUtil {
    private static final String ASSET_MISSING_TAG = "ASSET_MISSING";

    /**
     * Returns a scaled Bitmap for the requested image, or a default image if the requested one
     * cannot be found. Scaling is based on ImageView width and height. If these are zero
     * then a default scaling is applied. See dimens.xml resource file.
     * The fetching of the image is done asynchronously.
     *
     * @param context   The calling context
     * @param imagePath The pathname to the image either under the assets folder or
     *                  in the private external storage of the app. If empty or null,
     *                  then a default image is returned.
     * @param imageView The image to update asynchronously
     * @return The bitmap, or null if couldn't find the file.
     */
    public static void loadImageBitmap(Context context, String imagePath, ImageView imageView) {
        new LoadImageAsync(context, imagePath, imageView).execute();
    }

    private static class LoadImageAsync extends AsyncTask<Void[], Void, Bitmap> {

        private final ImageView imageView;
        private final Context context;
        private final String imagePath;
        private int width;
        private int height;

        private LoadImageAsync(Context context, String imagePath, ImageView imageView) {
            this.context = context;
            this.imageView = imageView;
            this.imagePath = imagePath;
            this.width = imageView.getWidth();
            this.height = imageView.getHeight();
        }

        /**
         * Runs asynchronously
         * @param args Don't have any real args
         * @return
         */
        @Override
        protected Bitmap doInBackground(Void[]... args) {
            Bitmap bitmap = null;
            bitmap = getBitmap(context, imagePath, width, height);
            return bitmap;
        }

        /**
         * We can now add the bitmap to our imageView in the UI thread
         *
         * @param bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * Creates a unique file path for an image using the private external directory. You can access
     * this location via Android Studio's Device File Explorer tool and
     * navigate to storage/self/primary/Android/data/package-path/files/Pictures
     *
     * @param context The application context
     * @return The image file. This is a new empty file ready to receive a picture image when
     * the camera app is fired up.
     * @throws IOException If the file cannot be created
     */
    public static File createImageFile(Context context) throws IOException {
        // Code obtained and adapted from: https://developer.android.com/training/camera/photobasics
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    private static Bitmap getBitmap(Context context, String imagePath, int width, int height) {
        Bitmap result = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        // When set to true the call to decodeFile or decodeStream will just
        // get the information about the bitmap without loading it. We need to find
        // out about the image size so that we can scale it.
        //
        bmOptions.inJustDecodeBounds = true;

        // Use a default image if we're not given one
        if (imagePath == null || imagePath.length() == 0) {
            imagePath = context.getResources().getString(R.string.default_image_path);
        }

        // The imagePath might be an absolute path. If so and the file
        // exists then we can load directly.
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            // Call twice. First time to fill the options based on the image
            BitmapFactory.decodeFile(imagePath, bmOptions);
            // Now update the options to scale the image
            updateOptions(context, bmOptions, width, height);
            // Now really load the image
            result = BitmapFactory.decodeFile(imagePath, bmOptions);
        } else {
            // Assume it's an asset
            AssetManager assetManager = context.getAssets();
            try {
                InputStream is = assetManager.open("images/" + imagePath);
                // We need may need to explicitly rewind the stream on some devices
                // before we can read it for real. Set a mark point at the
                // start of the file.
                is.mark(0);
                // Fill the options based on the image
                BitmapFactory.decodeStream(is, null, bmOptions);
                // Now update the options to scale the image
                updateOptions(context, bmOptions, width, height);
                // If the device supports the marking idea, reset (rewind)
                // the file handle to the start of the file
                if (is.markSupported()) {
                    is.reset();
                }
                // Now really load the file
                result = BitmapFactory.decodeStream(is, null, bmOptions);
            } catch (IOException e) {
                // If the asset doesn't exist we will just log the fact
                Log.w(ASSET_MISSING_TAG, e.toString());
            }
        }
        return result;
    }

    private static void updateOptions(Context context, BitmapFactory.Options bmOptions, int width, int height) {
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image. Watch for divide by zero
        // by providing a default scaling size defined in the dimens resource file.
        int scaleFactor = 1;
        if (width == 0)
            width = (int) context.getResources().getDimension(R.dimen.default_image_width);
        if (height == 0)
            height = (int) context.getResources().getDimension(R.dimen.default_image_height);

        // The scalefactor will reduce the size of the image
        scaleFactor = Math.min(photoW / width, photoH / height);

        // Set to fals so that the next time we try to load the bitmap, it really will be read
        // into memory
        bmOptions.inJustDecodeBounds = false;
        // Set the scalefactor to use when loading the bitmap
        bmOptions.inSampleSize = scaleFactor;
        // See the documentation!
        bmOptions.inPurgeable = true;
    }

}
