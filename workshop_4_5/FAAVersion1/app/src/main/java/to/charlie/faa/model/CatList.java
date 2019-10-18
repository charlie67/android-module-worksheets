package to.charlie.faa.model;

import java.util.Date;

import to.charlie.faa.R;

import static to.charlie.faa.model.Gender.MALE;

public class CatList {
    private Cat[] cats = {
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ..."),
            new Cat("Tibs", MALE, "Short hair", new Date(), R.drawable.cat1, "Lorem ipsum dolor sit amet, ...")
    };

    public Cat[] getCats(){
        return cats;
    }
}
