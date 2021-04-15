package db.managers;

import db.transactions.OccupationManagerTransactions;
import db.transactions.WingManagerTransactions;
import models.Wing;

import java.util.List;

public class WingManager implements DefaultManager<Wing> {
    private static WingManager instance;
    private static WingManagerTransactions transactions;

    public static WingManager getInstance() {
        if (instance == null) {
            instance = new WingManager();
        }

        return instance;
    }

    private WingManager() {
        transactions = new WingManagerTransactions();
    }

    @Override
    public void create(Wing value) throws Exception {

    }

    @Override
    public void update(Wing value) throws Exception {

    }

    @Override
    public void delete(Wing value) throws Exception {

    }

    @Override
    public Wing getById(int id) throws Exception {
        return null;
    }

    @Override
    public List<Wing> getAll() throws Exception {
        return null;
    }
}
