package by.softeq.test_task.entity;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Entity to collect data from an html page. Implements Serializable, Comparable
 */
public class Statistics implements Serializable, Comparable<Statistics> {
    private static final long serialVersionUID = 1L;
    /**
     * link to follow
     */
    private URL url;
    /**
     * map of terms ( key = term, value = number of matches on a page)
     */
    private Map<String, Integer> terms = new HashMap<>();
    /**
     * total sum of matches
     */
    private int total;

    public Statistics() {
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Map<String, Integer> getTerms() {
        return terms;
    }

    public void setTerms(Map<String, Integer> terms) {
        this.terms = terms;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics statistics = (Statistics) o;
        return getTotal() == statistics.getTotal() &&
                getUrl().equals(statistics.getUrl()) &&
                getTerms().equals(statistics.getTerms());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getTerms(), getTotal());
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "url=" + url +
                ", terms=" + terms +
                ", total=" + total +
                '}';
    }

    @Override
    public int compareTo(Statistics o) {
        return o.getTotal() - this.getTotal();
    }
}
