package by.softeq.test_task.entity.logic;

import by.softeq.test_task.entity.Statistics;

public class Calculation {

    public void calculateTotalValue(Statistics statistics){
        int count = 0;
        for (String s: statistics.getTerms().keySet()) {
            count+= statistics.getTerms().get(s);
        }

        statistics.setTotal(count);

    }
}
