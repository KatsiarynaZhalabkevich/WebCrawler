package by.softeq.test_task.service;

import by.softeq.test_task.resource_bundle.Parameter;
import by.softeq.test_task.resource_bundle.ResourceManager;
import by.softeq.test_task.service.impl.CrawlerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class CrawlerServiceTest {

    private final Set<URL> startURL = new HashSet<>();
    private final ResourceManager manager = new ResourceManager();
    private List<String> terms = new ArrayList<>();
    private ServiceProvider serviceProvider = ServiceProvider.getInstance();

    @Before
    public void init() {
        terms = Arrays.asList(
                manager.getValue(Parameter.TERM_1),
                manager.getValue(Parameter.TERM_2),
                manager.getValue(Parameter.TERM_3),
                manager.getValue(Parameter.TERM_4));
        try {
            startURL.add(new URL(manager.getValue(Parameter.URL)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void crawlTest() {
        int statisticListSize = Integer.parseInt(manager.getValue(Parameter.MAX_LINKS));
        CrawlerServiceImpl service = new CrawlerServiceImpl();
        try {

            service.crawl(startURL, terms);
        } catch (ServiceException e) {

        }
        Assert.assertTrue(statisticListSize == service.getStatisticsListSize());
    }
}
