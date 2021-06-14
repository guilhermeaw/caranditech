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

public class VisitReport extends ReportToolkit {
    private Visit source;

    public VisitReport(Visit source) {
        super(false);
        this.source = source;
    }

    @Override
    protected void generateDocument(Document document) throws Exception {
        setTitle("Relatório de Visita");
        setSubtitle(ApplicationUtilities.getInstance().getCompany());

        separator();
        newLine();

        Prisoner prisoner = PrisonerManager.getInstance().getById(source.getPrisonerId());
        Visitor visitor = VisitorManager.getInstance().getById(source.getVisitorId());

        ReportToolkit.DetailsTable details = new ReportToolkit.DetailsTable(3f, 10f);
        details.addRow("Data do agendamento", DateFormatter.format(source.getScheduleDate()));
        details.addRow("Prisioneiro", prisoner != null ? prisoner.getName() : "n/d");
        details.addRow("Visitante", visitor != null ? visitor.getName() : "n/d");

        document.add(details);
    }
}
