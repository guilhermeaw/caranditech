package db;

import db.fetchers.*;

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

            public Columns( String alias )
            {
                ID             = alias + "id";
                NAME           = alias + "name";
                LOGIN          = alias + "login";
                PASSWORD       = alias + "password";
                STATE          = alias + "state";
            }

            @Override
            public String toString()
            {
                return  ID        + ", " +
                        NAME      + ", " +
                        LOGIN     + ", " +
                        PASSWORD  + ", " +
                        STATE;
            }
        }
    }

    public static class Occupations
    {
        public String name = "occupations";

        public static final Occupations table = new Occupations( null );
        public final Columns columns;

        public final OccupationFetcher fetcher = new OccupationFetcher();

        public final String select;

        private Occupations( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String NAME;
            public String DESCRIPTION;
            public String STATE;

            public Columns( String alias )
            {
                ID          = alias + "id";
                NAME        = alias + "name";
                DESCRIPTION = alias + "description";
                STATE       = alias + "state";
            }

            @Override
            public String toString()
            {
                return  ID          + ", " +
                        NAME        + ", " +
                        DESCRIPTION + ", " +
                        STATE;
            }
        }
    }

    public static class Wings
    {
        public String name = "wings";

        public static final Wings table = new Wings( null );
        public final Columns columns;

        public final WingFetcher fetcher = new WingFetcher();

        public final String select;

        private Wings( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String NAME;
            public String STATE;

            public Columns( String alias )
            {
                ID          = alias + "id";
                NAME        = alias + "name";
                STATE       = alias + "state";
            }

            @Override
            public String toString()
            {
                return  ID          + ", " +
                        NAME        + ", " +
                        STATE;
            }
        }
    }

    public static class Cells
    {
        public String name = "cells";

        public static final Cells table = new Cells( null );
        public final Columns columns;

        public final CellFetcher fetcher = new CellFetcher();

        public final String select;

        private Cells( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String NAME;
            public String REF_WING;
            public String STATE;

            public Columns( String alias )
            {
                ID          = alias + "id";
                NAME        = alias + "name";
                REF_WING    = alias + "ref_wing";
                STATE       = alias + "state";
            }

            @Override
            public String toString()
            {
                return  ID          + ", " +
                        NAME        + ", " +
                        REF_WING    + ", " +
                        STATE;
            }
        }
    }

    public static class Employees
    {
        public String name = "employees";

        public static final Employees table = new Employees( null );
        public final Columns columns;

        public final EmployeeFetcher fetcher = new EmployeeFetcher();

        public final String select;

        private Employees( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String NAME;
            public String CPF;
            public String PHONE;
            public String REF_OCCUPATION;
            public String REF_WING;
            public String STATE;

            public Columns( String alias )
            {
                ID              = alias + "id";
                NAME            = alias + "name";
                CPF             = alias + "cpf";
                PHONE           = alias + "phone";
                REF_OCCUPATION  = alias + "ref_occupation";
                REF_WING        = alias + "ref_wing";
                STATE           = alias + "state";
            }

            @Override
            public String toString()
            {
                return  ID             + ", " +
                        NAME           + ", " +
                        CPF            + ", " +
                        PHONE          + ", " +
                        REF_OCCUPATION + ", " +
                        REF_WING       + ", " +
                        STATE;
            }
        }
    }

    public static class Occurrences
    {
        public String name = "occurrences";

        public static final Occurrences table = new Occurrences( null );
        public final Columns columns;

        public final OccurrenceFetcher fetcher = new OccurrenceFetcher();

        public final String select;

        private Occurrences( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String TITLE;
            public String DESCRIPTION;
            public String CREATED_DATE;
            public String REF_PRISONER;
            public String REF_USER;
            public String STATE;
            public String REF_OCCURRENCE_TYPE;

            public Columns( String alias )
            {
                ID           = alias + "id";
                TITLE        = alias + "title";
                DESCRIPTION  = alias + "description";
                CREATED_DATE = alias + "created_date";
                REF_PRISONER = alias + "ref_prisoner";
                REF_USER     = alias + "ref_user";
                STATE        = alias + "state";
                REF_OCCURRENCE_TYPE        = alias + "ref_occurrence_type";
            }

            @Override
            public String toString()
            {
                return  ID           + ", " +
                        TITLE        + ", " +
                        DESCRIPTION  + ", " +
                        CREATED_DATE + ", " +
                        REF_PRISONER + ", " +
                        REF_USER   + ", " +
                        STATE   + ", " +
                        REF_OCCURRENCE_TYPE;
            }
        }
    }

    public static class PrisonerTypes
    {
        public String name = "prisoner_types";

        public static final PrisonerTypes table = new PrisonerTypes( null );
        public final Columns columns;

        public final PrisonerTypeFetcher fetcher = new PrisonerTypeFetcher();

        public final String select;

        private PrisonerTypes( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String NAME;
            public String PROFIT;
            public String DESCRIPTION;
            public String STATE;

            public Columns( String alias )
            {
                ID          = alias + "id";
                NAME        = alias + "name";
                PROFIT      = alias + "profit";
                DESCRIPTION = alias + "description";
                STATE       = alias + "state";
            }

            @Override
            public String toString()
            {
                return  ID          + ", " +
                        NAME        + ", " +
                        PROFIT      + ", " +
                        DESCRIPTION + ", " +
                        STATE;
            }
        }
    }

    public static class Prisoners
    {
        public String name = "prisoners";

        public static final Prisoners table = new Prisoners( null );
        public final Columns columns;

        public final PrisonerFetcher fetcher = new PrisonerFetcher();

        public final String select;

        private Prisoners( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String NAME;
            public String ENTER_DATE;
            public String EXIT_DATE;
            public String REF_CELL;
            public String REF_PRISONER_TYPE;
            public String STATE;

            public Columns( String alias )
            {
                ID                = alias + "id";
                NAME              = alias + "name";
                ENTER_DATE        = alias + "enter_date";
                EXIT_DATE         = alias + "exit_date";
                REF_CELL          = alias + "ref_cell";
                REF_PRISONER_TYPE = alias + "ref_prisoner_type";
                STATE             = alias + "state";
            }

            @Override
            public String toString()
            {
                return  ID                + ", " +
                        NAME              + ", " +
                        ENTER_DATE        + ", " +
                        EXIT_DATE         + ", " +
                        REF_CELL          + ", " +
                        REF_PRISONER_TYPE + ", " +
                        STATE;
            }
        }
    }

    public static class Visitors
    {
        public String name = "visitors";

        public static final Visitors table = new Visitors( null );
        public final Columns columns;

        public final VisitorFetcher fetcher = new VisitorFetcher();

        public final String select;

        private Visitors( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String NAME;
            public String CPF;
            public String PHONE;
            public String STATE;

            public Columns( String alias )
            {
                ID    = alias + "id";
                NAME  = alias + "name";
                CPF   = alias + "cpf";
                PHONE = alias + "phone";
                STATE = alias + "state";
            }

            @Override
            public String toString()
            {
                return  ID    + ", " +
                        NAME  + ", " +
                        CPF   + ", " +
                        PHONE + ", " +
                        STATE;
            }
        }
    }

    public static class Visits
    {
        public String name = "visits";

        public static final Visits table = new Visits( null );
        public final Columns columns;

        public final VisitFetcher fetcher = new VisitFetcher();

        public final String select;

        private Visits( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String SCHEDULE_DATE;
            public String REF_PRISONER;
            public String REF_VISITOR;
            public String STATE;

            public Columns( String alias )
            {
                ID            = alias + "id";
                SCHEDULE_DATE = alias + "schedule_date";
                REF_PRISONER  = alias + "ref_prisoner";
                REF_VISITOR   = alias + "ref_visitor";
                STATE         = alias + "state";
            }

            @Override
            public String toString()
            {
                return  ID            + ", " +
                        SCHEDULE_DATE + ", " +
                        REF_PRISONER  + ", " +
                        REF_VISITOR   + ", " +
                        STATE;
            }
        }
    }

    public static class OccurrenceTypes
    {
        public String name = "occurrence_types";

        public static final OccurrenceTypes table = new OccurrenceTypes( null );
        public final Columns columns;

        public final OccurrenceTypeFetcher fetcher = new OccurrenceTypeFetcher();

        public final String select;

        private OccurrenceTypes( String alias )
        {
            this.name = alias != null ? name + " " + alias : name;

            columns = new Columns( alias != null ? alias + "." : "" );

            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String NAME;
            public String DESCRIPTION;
            public String STATE;

            public Columns( String alias )
            {
                ID            = alias + "id";
                NAME = alias + "name";
                DESCRIPTION  = alias + "description";
                STATE         = alias + "state";
            }

            @Override
            public String toString()
            {
                return  ID            + ", " +
                        NAME + ", " +
                        DESCRIPTION   + ", " +
                        STATE;
            }
        }
    }
}
