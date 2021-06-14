package reports;

import com.itextpdf.text.Document;
import common.ReportToolkit;
import db.managers.CellManager;
import db.managers.PrisonerTypeManager;
import formatters.DateFormatter;
import models.Cell;
import models.Prisoner;
import models.PrisonerType;
import utils.ApplicationUtilities;

import java.sql.Date;

public class PrisonerReport extends ReportToolkit
{
    private Prisoner source;

    public PrisonerReport( Prisoner source )
    {
        super( false );
        this.source = source;
    }

    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Prisioneiro" );
        setSubtitle(ApplicationUtilities.getInstance().getCompany());

        separator();
        newLine();

        Cell cell = CellManager.getInstance().getById(source.getCellId());
        PrisonerType prisonerType = PrisonerTypeManager.getInstance().getById(source.getPrisonerTypeId());

        Date exitDate = source.getExitDate();

        DetailsTable details = new DetailsTable( 3f, 10f );
        details.addRow( "Nome", source.getName() );
        details.addRow( "Cela", cell != null ? cell.getName() : "n/d" );
        details.addRow( "Tipo", prisonerType != null ? prisonerType.getName() : "n/d" );
        details.addRow( "Data de Entrada", DateFormatter.format(source.getEnterDate()));
        details.addRow( "Data de Saída", exitDate != null ? DateFormatter.format(exitDate) : "n/d" );

        document.add( details );
    }
}
