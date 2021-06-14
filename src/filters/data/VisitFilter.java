package filters.data;

import common.Identity;
import models.Prisoner;
import models.Visitor;

import java.sql.Date;

public class VisitFilter {
    private Date startScheduleDateRange = null;
    private Date endScheduleDateRange = null;
    private Prisoner prisoner = null;
    private Visitor visitor = null;
    private int state = Identity.STATE_ACTIVE;

    public VisitFilter() {}

    public Date getStartScheduleDateRange() {
        return startScheduleDateRange;
    }

    public void setStartScheduleDateRange(Date startScheduleDateRange) {
        this.startScheduleDateRange = startScheduleDateRange;
    }

    public Date getEndScheduleDateRange() {
        return endScheduleDateRange;
    }

    public void setEndScheduleDateRange(Date endScheduleDateRange) {
        this.endScheduleDateRange = endScheduleDateRange;
    }

    public Prisoner getPrisoner() {
        return prisoner;
    }

    public void setPrisoner(Prisoner prisoner) {
        this.prisoner = prisoner;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
