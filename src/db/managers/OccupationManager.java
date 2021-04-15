package db.managers;

import db.transactions.OccupationManagerTransactions;
import db.transactions.UserManagerTransactions;
import models.Occupation;

import java.util.List;

public class OccupationManager implements DefaultManager<Occupation> {
    private static OccupationManager instance;
    private static OccupationManagerTransactions transactions;

    public static OccupationManager getInstance() {
        if (instance == null) {
            instance = new OccupationManager();
        }

        return instance;
    }

    private OccupationManager() {
        transactions = new OccupationManagerTransactions();
    }

    @Override
    public void create(Occupation value) throws Exception {

    }

    @Override
    public void update(Occupation value) throws Exception {

    }

    @Override
    public void delete(Occupation value) throws Exception {

    }

    @Override
    public Occupation getById(int id) throws Exception {
        return null;
    }

    @Override
    public List<Occupation> getAll() throws Exception {
        return null;
    }
}
