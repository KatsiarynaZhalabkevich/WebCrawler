package by.softeq.test_task.dao.impl;

import by.softeq.test_task.dao.CrawlerDAO;
import by.softeq.test_task.dao.DAOException;
import by.softeq.test_task.entity.Statistics;
import by.softeq.test_task.resource_bundle.Parameter;
import by.softeq.test_task.resource_bundle.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.Map.Entry;

/**
 * The layer which works with data: write it to csv-file or console
 */
public class CSVCrawlerDAO implements CrawlerDAO {

    private static final Logger logger = LogManager.getLogger(CSVCrawlerDAO.class);

    private final int NUMBER_OF_RESULTS_TO_SHOW;
    private final String RESULT_FILE_NAME;
    private final String RESULT_TO_SHOW_FILE_NAME;
    private final String SEPARATOR;

    public CSVCrawlerDAO() {
        ResourceManager manager = ResourceManager.getInstance();
        this.NUMBER_OF_RESULTS_TO_SHOW = Integer.parseInt(manager.getValue(Parameter.MAX_FILE_RES));
        this.RESULT_FILE_NAME = manager.getValue(Parameter.FILE_NAME).concat(manager.getValue(Parameter.FILE_POSTFIX));
        this.RESULT_TO_SHOW_FILE_NAME = manager.getValue(Parameter.FILE_NAME)
                                                           .concat(manager.getValue(Parameter.MAX_FILE_RES)
                                                           .concat(manager.getValue(Parameter.FILE_POSTFIX)));
        this.SEPARATOR=manager.getValue(Parameter.SEPARATOR);
    }

    /**
     * Method which writes data to a file, sorts it, prints to the console and saves a part of data into another file
     * @param statisticsList - list of Statistic objects from Service layer
     * @throws DAOException - custom exception to hide realization
     */
    @Override
    public void writeToFile(List<Statistics> statisticsList) throws DAOException {

        writeToCsvFile(statisticsList, RESULT_FILE_NAME);

        Collections.sort(statisticsList);
        printDataToConsole(statisticsList);
        List<Statistics> mainListResult = statisticsList.subList(0, NUMBER_OF_RESULTS_TO_SHOW);
        writeToCsvFile(mainListResult, RESULT_TO_SHOW_FILE_NAME);


    }

    /**
     * Method to write data in a csv file
     *
     * @param statisticsList - Statistic data list to write
     * @param fileName      - name of the csv file
     * @throws DAOException - custom exception to hide realization of the layer
     */
    private void writeToCsvFile(List<Statistics> statisticsList, String fileName) throws DAOException {

        try {
            FileWriter writer = new FileWriter(fileName);
            for (Statistics statistics : statisticsList) {
                writer.append(statistics.getUrl().toString());
                writer.append(SEPARATOR);
                for (Entry entry : statistics.getTerms().entrySet()) {
                    writer.append(String.valueOf(entry.getValue()));
                    writer.append(SEPARATOR);
                }
                writer.append(String.valueOf(statistics.getTotal()));
                writer.append(System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    /**
     * Method to print data into console
     *
     * @param statisticsList - Statistic data list to be printed
     */
    private void printDataToConsole(List<Statistics> statisticsList) {

        for (int i = 0; i < NUMBER_OF_RESULTS_TO_SHOW; i++) {

            Statistics s = statisticsList.get(i);
            System.out.println(s.getUrl());
            for (Entry entry : s.getTerms().entrySet()) {
                System.out.println(entry.getKey() + " ---- " + entry.getValue());
            }
            System.out.println("Total ----" + s.getTotal());

        }
    }


}
