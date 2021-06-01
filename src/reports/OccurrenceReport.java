package reports;

import com.itextpdf.text.Document;
import common.ReportToolkit;
import db.managers.PrisonerManager;
import db.managers.UserManager;
import models.Occurrence;
import models.Prisoner;
import models.User;
import utils.ApplicationUtilities;

public class OccurrenceReport extends ReportToolkit {
    private Occurrence source;

    public OccurrenceReport(Occurrence source) {
        super(false);
        this.source = source;
    }

    @Override
    protected void generateDocument(Document document) throws Exception {
        setTitle("Relatório de Ocorrência");
        setSubtitle(ApplicationUtilities.getInstance().getCompany());

        separator();
        newLine();

        Prisoner prisoner = PrisonerManager.getInstance().getById(source.getPrisonerId());
        User author = UserManager.getInstance().getById(source.getAuthorId());

        String description = source.getDescription();

        ReportToolkit.DetailsTable details = new ReportToolkit.DetailsTable(3f, 10f);
        details.addRow("Título", source.getTitle());
        details.addRow("Prisioneiro", prisoner != null ? prisoner.getName() : "n/d");
        details.addRow("Autor", author != null ? author.getName() : "n/d");
        details.addRow("Data", source.getCreatedDate().toString());
        details.addRow("Descrição", description != null ? description.toString() : "n/d");

        document.add(details);
    }
}
