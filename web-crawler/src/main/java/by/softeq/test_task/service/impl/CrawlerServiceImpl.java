package by.softeq.test_task.service.impl;

import by.softeq.test_task.dao.DAOException;
import by.softeq.test_task.dao.DAOProvider;
import by.softeq.test_task.dao.impl.CSVCrawlerDAO;
import by.softeq.test_task.entity.Statistics;
import by.softeq.test_task.entity.logic.Calculation;
import by.softeq.test_task.resource_bundle.Parameter;
import by.softeq.test_task.resource_bundle.ResourceManager;
import by.softeq.test_task.service.CrawlerService;
import by.softeq.test_task.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Business Logic layer
 */
public class CrawlerServiceImpl implements CrawlerService {

    private static final Logger logger = LogManager.getLogger(CSVCrawlerDAO.class);

    private final int MAX_DEPTH;
    private final int MAX_VISITED_LINKS;
    private int depth = 1;
    private List<String> terms;
    private final Set<URL> visitedLinks = new HashSet<>();
    private final List<Statistics> statisticsList = new ArrayList<>();

    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final String ERROR_MESSAGE = "Invalid start URL";
    private static final String SEARCH_ERROR = "No criteria for search!";
    private static final String FILE_ERROR = "Can't write data to file!";

    public CrawlerServiceImpl() {
        ResourceManager manager = ResourceManager.getInstance();
        this.MAX_DEPTH = Integer.parseInt(manager.getValue(Parameter.MAX_DEPTH));
        this.MAX_VISITED_LINKS = Integer.parseInt(manager.getValue(Parameter.MAX_LINKS));
    }

    /**
     * Method to follow links, collect data from html pages and send it to the DAO layer
     *
     * @param urls - set of URLs for the WebCrawler to search terms on them
     * @throws ServiceException - custom exception for this layer to hide realization
     */
    public void crawl(Set<URL> urls, List<String> terms) throws ServiceException {

        this.terms = terms;
        if (terms == null) {
            throw new ServiceException(SEARCH_ERROR);
        }
        if (urls != null) {
            collectLinks(urls);
            try {
                daoProvider.getCrawlerDAO().writeToFile(statisticsList);
            } catch (DAOException e) {
                logger.error(e);
                throw new ServiceException(FILE_ERROR);
            }
        } else {
            throw new ServiceException(ERROR_MESSAGE);
        }
    }

    /**
     * Method to parse set of links from an html page. Which contains a condition of max visited links and deepness of diving.
     * All kinds of URL Exceptions and Errors are ignored to get valid links only.
     *
     * @param urls - set URLs from an html page
     */
    private void collectLinks(Set<URL> urls) {

        if (statisticsList.size() < MAX_VISITED_LINKS && depth < MAX_DEPTH) {
            urls.removeAll(visitedLinks);
            if (!urls.isEmpty()) {
                Set<URL> newURLs = new HashSet<>();
                visitedLinks.addAll(urls);
                try {
                    for (URL url : urls) {
                        Document document = Jsoup.connect(url.toString()).get();
                        Elements otherLinks = document.select("a[href]");
                        Statistics statistics = createPageStatistics(url);
                        if (statisticsList.size() < MAX_VISITED_LINKS) {
                            if (statistics != null) {
                                statisticsList.add(statistics);
                            }
                        } else {
                            break;
                        }

                        for (Element newLink : otherLinks) {
                            String urlString = newLink.attr("abs:href");
                            if (!urlString.isEmpty() &&
                                    !urlString.contains("#") &&
                                    !urlString.contains("?") &&
                                    !urlString.startsWith("javascript:")
                            ) {
                                URL newURL = new URL(urlString);
                                newURLs.add(newURL);
                            }
                        }
                    }

                } catch (Exception e) {
                    logger.error(e);
                }
                depth++;
                collectLinks(newURLs);

            }

        }
    }

    /**
     * Method to create terms statistic from an html page
     *
     * @param url - link to collect statistics.
     * @return Statistic object to send to DAO layer;
     * null if link is not valid
     */
    private Statistics createPageStatistics(URL url) {
        try {
            Document document = Jsoup.connect(url.toString()).get();
            Statistics statistics = new Statistics();

            Map<String, Integer> termsMapOnPage = statistics.getTerms();
            statistics.setUrl(url);

            for (String term : terms) {
                Elements termsOnPage = document.select("body");
                for (Element e : termsOnPage) {
                    Pattern pattern = Pattern.compile(term);
                    Matcher matcher = pattern.matcher(e.text());
                    int i = 0;
                    while (matcher.find()) {
                        i++;
                    }
                    termsMapOnPage.put(term, i);
                }
            }
            Calculation calculation = new Calculation();
            calculation.calculateTotalValue(statistics);
            return statistics;

        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @return size of statisticsList to use it in unit tests
     */
    public int getStatisticsListSize() {
        return statisticsList.size();
    }
}
