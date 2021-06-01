package reports;

import com.itextpdf.text.Document;
import common.ReportToolkit;
import db.managers.CellManager;
import db.managers.PrisonerTypeManager;
import models.Cell;
import models.Prisoner;
import models.PrisonerType;
import utils.ApplicationUtilities;

import java.sql.Date;
import java.util.List;

public class PrisonersListReport extends ReportToolkit
{
    private List<Prisoner> prisoners;

    private final String[] header = new String[]
    {
            "#",
            "Nome",
            "Cela",
            "Tipo",
            "Data de Entrada",
            "Data de Saída"
    };

    private final float[] columnWidths = new float[]
    {
            1.5f,
            8f,
            8f,
            8f,
            8f,
            8f
    };

    public PrisonersListReport( List<Prisoner> prisoners )
    {
        super( true );
        this.prisoners = prisoners;
    }

    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Listagem de Prisioneiros" );
        setSubtitle(ApplicationUtilities.getInstance().getCompany());

        separator();
        newLine();

        Table table = new Table(columnWidths);
        table.setHeader( header );

        for ( Prisoner prisoner : prisoners )
        {
            Cell cell = CellManager.getInstance().getById(prisoner.getCellId());
            PrisonerType prisonerType = PrisonerTypeManager.getInstance().getById(prisoner.getPrisonerTypeId());

            Date exitDate = prisoner.getExitDate();

            table.addRow(String.valueOf(prisoner.getId()),
                    prisoner.getName(),
                    cell != null ? cell.getName() : "n/d",
                    prisonerType != null ? prisonerType.getName() : "n/d",
                    prisoner.getEnterDate().toString(),
                    exitDate != null ? exitDate.toString() : "n/d");
        }

        document.add( table );
    }
}
