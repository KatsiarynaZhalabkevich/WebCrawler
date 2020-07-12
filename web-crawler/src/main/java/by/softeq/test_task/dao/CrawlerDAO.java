package by.softeq.test_task.dao;

import by.softeq.test_task.entity.Statistics;

import java.util.List;

public interface CrawlerDAO {
    void writeToFile(List<Statistics> statisticsList) throws DAOException;
}
