package by.softeq.test_task;


import by.softeq.test_task.controller.CrawlerController;
import by.softeq.test_task.resource_bundle.Parameter;
import by.softeq.test_task.resource_bundle.ResourceManager;

import java.util.*;

/**
 * Class to start the application WebCrawler
 *
 * @version 1.0
 */

public class Main {


    public static void main(String[] args) {
        long start;
        long finish;
        ResourceManager manager = ResourceManager.getInstance();
        final String URL = manager.getValue(Parameter.URL);


        List<String> terms = Arrays.asList(
                manager.getValue(Parameter.TERM_1),
                manager.getValue(Parameter.TERM_2),
                manager.getValue(Parameter.TERM_3),
                manager.getValue(Parameter.TERM_4));

        start = System.currentTimeMillis();

        CrawlerController controller = new CrawlerController();
        System.out.println(controller.start(URL, terms));

        finish = System.currentTimeMillis();

        System.out.println("Time " + (finish - start));

    }


}
