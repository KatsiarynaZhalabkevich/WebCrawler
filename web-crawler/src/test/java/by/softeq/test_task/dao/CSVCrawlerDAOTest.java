package by.softeq.test_task.dao;

import by.softeq.test_task.dao.impl.CSVCrawlerDAO;
import by.softeq.test_task.entity.Statistics;
import by.softeq.test_task.entity.logic.Calculation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class CSVCrawlerDAOTest {
    private List<Statistics> testStatisticsList = new ArrayList<>();

    @Before
    public void init() {
        int n = 10;
        Random random = new Random();
        Calculation calc = new Calculation();
        for (int i = 0; i < n; i++) {
            Map<String, Integer> testTerms = new HashMap<>();
            testTerms.put("hello", random.nextInt(30) + 1);
            testTerms.put("food", random.nextInt(25) + 1);
            testTerms.put("bye", random.nextInt(15) + 1);

            Statistics st = new Statistics();
            try {
                st.setUrl(new URL("https://en.wikipedia.org/wiki/Elon_Musk/" + i));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            st.setTerms(testTerms);
            calc.calculateTotalValue(st);
            testStatisticsList.add(st);

        }
    }

    @Test
    public void writeToFileTest() {
        int count=0;
        int expected=10;
        CrawlerDAO dao = new CSVCrawlerDAO();
        try {
            dao.writeToFile(testStatisticsList);
            FileReader reader = new FileReader("test.csv");
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()){
                br.readLine();
                count++;
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }catch (IOException e){

        }
        Assert.assertTrue(count==expected);


    }


}
