package gui.panels.resultsPanels;

import gui.model.ShowGUI;

import java.io.IOException;

public class ShowResultsPanel extends ResultsPanel<ShowGUI> {
    public ShowResultsPanel() {
        super();
    }

    @Override
    public void addItem(ShowGUI show) throws IOException {
        if (show == null)
            throw new NullPointerException();
        setItemName("Спектакль №" + ++itemsCount);
        setHead(show.headField());
        setImage(show.imagePath());
        show.ageRestrictionField().setEditable(false);
        add(show.ageRestrictionField());
        show.durationField().setEditable(false);
        add(show.durationField());
        show.datesField().setEditable(false);
        add(show.datesField());
    }
}
