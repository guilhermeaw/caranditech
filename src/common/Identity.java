package common;

public class Identity {
    public static int STATE_DELETED = 0;
    public static int STATE_ACTIVE = 1;

    private int id;
    private int state = STATE_ACTIVE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
