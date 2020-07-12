package by.softeq.test_task.controller;

import by.softeq.test_task.resource_bundle.Parameter;
import by.softeq.test_task.resource_bundle.ResourceManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrawlerControllerTest {
    private String url;
    private List<String> terms = new ArrayList<>();
    private final ResourceManager manager = new ResourceManager();
    private static final String URL_ERROR = "Invalid start URL";
    private static final String SEARCH_ERROR = "No criteria for search!";


    @Before
    public void init() {
        terms = Arrays.asList(
                manager.getValue(Parameter.TERM_1),
                manager.getValue(Parameter.TERM_2),
                manager.getValue(Parameter.TERM_3),
                manager.getValue(Parameter.TERM_4));
    }

    @Test
    public void startInvalidURLTest() {
        String output;
        url = "index.html";
        CrawlerController controller = new CrawlerController();
        output = controller.start(url, terms);
        Assert.assertTrue(URL_ERROR.equals(output));

    }

    @Test
    public void startInvalidTermsTest(){
        terms=null;
        String output;
        url = manager.getValue(Parameter.URL);
        CrawlerController controller = new CrawlerController();
        output = controller.start(url, terms);
        Assert.assertTrue(SEARCH_ERROR.equals(output));
    }
}
