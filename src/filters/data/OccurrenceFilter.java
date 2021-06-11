package filters.data;

import models.OccurrenceType;
import models.Prisoner;
import models.User;

import java.sql.Date;

public class OccurrenceFilter {
    private OccurrenceType occurrenceType = null;
    private Prisoner prisoner = null;
    private User author = null;
    private String title = "";
    private Date startDateRange = null;
    private Date endDateRange = null;
    private int state = 1;

    public OccurrenceFilter() {}

    public OccurrenceType getOccurrenceType() {
        return occurrenceType;
    }

    public void setOccurrenceType(OccurrenceType occurrenceType) {
        this.occurrenceType = occurrenceType;
    }

    public Prisoner getPrisoner() {
        return prisoner;
    }

    public void setPrisoner(Prisoner prisoner) {
        this.prisoner = prisoner;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDateRange() {
        return startDateRange;
    }

    public void setStartDateRange(Date startDateRange) {
        this.startDateRange = startDateRange;
    }

    public Date getEndDateRange() {
        return endDateRange;
    }

    public void setEndDateRange(Date endDateRange) {
        this.endDateRange = endDateRange;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
