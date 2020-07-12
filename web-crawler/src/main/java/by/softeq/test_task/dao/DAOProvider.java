package by.softeq.test_task.dao;

import by.softeq.test_task.dao.impl.CSVCrawlerDAO;

public class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();
    private final CrawlerDAO crawlerDAO = new CSVCrawlerDAO();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public CrawlerDAO getCrawlerDAO() {
        return crawlerDAO;
    }


}
