package by.softeq.test_task.resource_bundle;

import java.util.ResourceBundle;

public class ResourceManager {

    private final static ResourceManager instance = new ResourceManager();

    private final ResourceBundle bundle = ResourceBundle.getBundle("prop");

    public static ResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
