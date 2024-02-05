package model.comparator;

import model.Publication;

import java.util.Comparator;

public class DateComparator implements Comparator<Publication> {
    @Override
    public int compare(Publication p1, Publication p2) {
        if (p1 == null && p2 == null)
            return 0;
        else if (p1 == null)
            return 1;
        else if (p2 == null)
            return -1;
        int p1Year = p1.getYear();
        int p2Year = p2.getYear();
        return Integer.compare(p1Year, p2Year);
    }
}
