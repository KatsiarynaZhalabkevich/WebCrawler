package by.softeq.test_task.service;

import java.net.URL;
import java.util.List;
import java.util.Set;


public interface CrawlerService {
    void crawl(Set<URL> urls, List<String> terms) throws ServiceException;

}
