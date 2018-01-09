package valliappan.busrouteapp.Mvp;

import java.util.ArrayList;

/**
 * Created by valliappan on 6/1/18.
 */

public class ModelList {
    private String id, name, description, accessible, image;
    private ArrayList stops;

    public ModelList(String id, String name, String description, String accessible, String image, ArrayList stops) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accessible = accessible;
        this.image = image;
        this.stops = stops;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAccessible() {
        return accessible;
    }

    public String getImage() {
        return image;
    }

    public ArrayList getStops() {
        return stops;
    }
}

