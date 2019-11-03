package uk.ac.aber.dcs.cs31620.faaversion5.model.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Simple utility class to support loading of images from assets/images folder.
 * NB: current implementatuon is synchronous
 * @author Chris Loftus
 * @version 1, 26/08/2018.
 */
public class ResourceUtil {
    private static final String ASSET_MISSING_TAG = "ASSET_MISSING";

    public static Bitmap getAssetImageAsBitmap(Context context, String imagePath){
        Bitmap result = null;
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("images/" + imagePath);
            result = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            // If the asset doesn't exist we will just log the fact
            Log.w(ASSET_MISSING_TAG, e.toString());
        }
        return result;
    }
}
