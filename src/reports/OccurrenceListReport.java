package reports;

import com.itextpdf.text.Document;
import common.ReportToolkit;
import db.managers.PrisonerManager;
import db.managers.UserManager;
import formatters.DateFormatter;
import models.Occurrence;
import models.Prisoner;
import models.User;
import utils.ApplicationUtilities;

import java.util.List;

public class OccurrenceListReport extends ReportToolkit
{
    private List<Occurrence> occurrences;

    private final String[] header = new String[]
    {
            "#",
            "Título",
            "Prisioneiro",
            "Autor",
            "Data",
            "Descrição"
    };

    private final float[] columnWidths = new float[]
    {
            1.5f,
            4f,
            8f,
            8f,
            8f,
            15f
    };

    public OccurrenceListReport( List<Occurrence> occurrences )
    {
        super( true );
        this.occurrences = occurrences;
    }

    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Listagem de Ocorrências" );
        setSubtitle(ApplicationUtilities.getInstance().getCompany());

        separator();
        newLine();

        Table table = new Table(columnWidths);
        table.setHeader( header );

        for ( Occurrence occurrence : occurrences )
        {
            Prisoner prisoner = PrisonerManager.getInstance().getById(occurrence.getPrisonerId());
            User author = UserManager.getInstance().getById(occurrence.getAuthorId());

            String description = occurrence.getDescription();

            table.addRow(String.valueOf(occurrence.getId()),
                    occurrence.getTitle(),
                    prisoner != null ? prisoner.getName() : "n/d",
                    author != null ? author.getName() : "n/d",
                    DateFormatter.format(occurrence.getCreatedDate()),
                    description != null ? description : "n/d");
        }

        document.add( table );
    }
}
