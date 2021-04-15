package db;

import db.fetchers.UserFetcher;

public class Schema {
    public static class Users
    {
        public String name = "users";

        public static final Users table = new Users( null );
        public final Columns columns;

        public final UserFetcher fetcher = new UserFetcher();

        public final String select;

        private Users( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String NAME;
            public String LOGIN;
            public String PASSWORD;
            public String STATE;
            public String REF_OCCUPATION;

            public Columns( String alias )
            {
                ID             = alias + "id";
                NAME           = alias + "name";
                LOGIN          = alias + "login";
                PASSWORD       = alias + "password";
                STATE          = alias + "state";
                REF_OCCUPATION = alias + "ref_occupation";
            }

            @Override
            public String toString()
            {
                return  ID        + ", " +
                        NAME      + ", " +
                        LOGIN     + ", " +
                        PASSWORD  + ", " +
                        STATE     + ", " +
                        REF_OCCUPATION;
            }
        }
    }
}
