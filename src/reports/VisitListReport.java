package reports;

import com.itextpdf.text.Document;
import common.ReportToolkit;
import db.managers.PrisonerManager;
import db.managers.VisitorManager;
import formatters.DateFormatter;
import models.Visit;
import models.Prisoner;
import models.Visitor;
import utils.ApplicationUtilities;

import java.util.List;

public class VisitListReport extends ReportToolkit {
    private List<Visit> visits;

    private final String[] header = new String[]
            {
                    "#",
                    "Data de agendamento",
                    "Prisioneiro",
                    "Visitante"
            };

    private final float[] columnWidths = new float[]
            {
                    1.5f,
                    4f,
                    8f,
                    8f
            };

    public VisitListReport( List<Visit> visits )
    {
        super( true );
        this.visits = visits;
    }

    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Listagem de Visitas" );
        setSubtitle(ApplicationUtilities.getInstance().getCompany());

        separator();
        newLine();

        Table table = new Table(columnWidths);
        table.setHeader( header );

        for ( Visit visit : visits )
        {
            Prisoner prisoner = PrisonerManager.getInstance().getById(visit.getPrisonerId());
            Visitor visitor = VisitorManager.getInstance().getById(visit.getVisitorId());

            table.addRow(String.valueOf(visit.getId()),
                    DateFormatter.format(visit.getScheduleDate()),
                    prisoner != null ? prisoner.getName() : "n/d",
                    visitor != null ? visitor.getName() : "n/d");
        }

        document.add( table );
    }
}
