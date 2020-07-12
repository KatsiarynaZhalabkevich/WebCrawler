package by.softeq.test_task.controller;

import by.softeq.test_task.service.ServiceException;
import by.softeq.test_task.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to manage the layers of the application
 */
public class CrawlerController {

    private static final Logger logger = LogManager.getLogger(CrawlerController.class);
    private final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    /**
     * Method to call WebCrawler
     *
     * @param url   -  String value of start URL
     * @param terms - List<String> data for search on html pages
     *
     * @return string result to console
     *
     */

    public String start(String url, List<String> terms) {
        String result = "";

        try {
            serviceProvider.getCrawlerService().crawl(initURL(url), terms);
        } catch (ServiceException e) {
            logger.error(e);
            return e.getMessage();
        }
        return result;
    }

    /**
     * Special method to prepare the initial data for the WebCrawler
     *
     * @param url - String value of the URL
     * @return Set of URLs consist of 1 element to start the WebCrawler
     */
    private Set<URL> initURL(String url) {
        Set<URL> startURL = new HashSet<>();
        try {
            startURL.add(new URL(url));
        } catch (IOException e) {
            logger.error(e);
            return null;
        }
        return startURL;

    }
}
