package by.softeq.test_task.service;

import by.softeq.test_task.service.impl.CrawlerServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private ServiceProvider() {
    }

    private CrawlerService crawlerService = new CrawlerServiceImpl();

    public static ServiceProvider getInstance() {
        return instance;
    }

    public CrawlerService getCrawlerService() {
        return crawlerService;
    }

}
