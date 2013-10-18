package org.vaadin.mockapp.ui.views;

import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.tokenfield.TokenField;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = EvacueeView.VIEW_NAME,
        allowedRoles = {MockAppRoles.ROLE_ADMIN})
public class EvacueeView extends VerticalLayout implements View, FieldEvents.TextChangeListener {

    public static final String VIEW_NAME = "evacuee";
    private Label title;
    private DateField date;
    private TextField number;
    private TokenField tags;
    private TextField lastName;
    private TextField firstNames;
    private TextField idNumber;
    private TextField evacuatedFromAddress;
    private TextField homeAddress;
    private TextField cellPhone;
    private TextField petsInApartment;
    private TextArea notes;
    private Button save;
    private Button backWithoutSaving;

    public EvacueeView() {
        init();
    }

    private void init() {
        setWidth("600px");
        setMargin(true);
        setSpacing(true);

        title = new Label("Evacuee Information Card");
        title.addStyleName(MockAppTheme.LABEL_H1);
        addComponent(title);

        HorizontalLayout group;

        addComponent(group = createGroup());
        group.addComponent(date = new DateField("Date"));
        group.addComponent(number = new TextField("Number"));
        group.setExpandRatio(number, 1f);

        addComponent(group = createGroup());

        group.addComponent(lastName = new TextField("Family name"));
        lastName.setWidth(100, Unit.PERCENTAGE);
        group.setExpandRatio(lastName, 1f);

        group.addComponent(firstNames = new TextField("Given names"));
        firstNames.setWidth(100, Unit.PERCENTAGE);
        group.setExpandRatio(firstNames, 1f);

        group.addComponent(idNumber = new TextField("ID"));
        idNumber.setWidth(10, Unit.EM);

        addComponent(evacuatedFromAddress = new TextField("Address from which the evacuee was evacuated"));
        evacuatedFromAddress.setInputPrompt("Street address or a description of the location where the evacuee was picked up");
        evacuatedFromAddress.setWidth(100, Unit.PERCENTAGE);

        addComponent(homeAddress = new TextField("Home address"));
        homeAddress.setInputPrompt("Leave empty if the evacuee was evacuated from this address");
        homeAddress.setWidth(100, Unit.PERCENTAGE);

        addComponent(group = createGroup());

        group.addComponent(cellPhone = new TextField("Cell Phone"));
        cellPhone.setWidth(10, Unit.EM);

        group.addComponent(petsInApartment = new TextField("Pets in apartment"));
        petsInApartment.setWidth(100, Unit.PERCENTAGE);
        group.setExpandRatio(petsInApartment, 1);

        addComponent(notes = new TextArea("Special notes (medication, allergies, health problems, etc.)"));
        notes.setWidth(100, Unit.PERCENTAGE);
        notes.setHeight(60, Unit.PIXELS);

        addComponent(tags = new TokenField("Tags"));

        addComponent(group = createGroup());

        group.addComponent(save = new Button("Save Changes"));
        save.addStyleName(MockAppTheme.BUTTON_DEFAULT_MODIFY);
        group.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
        group.setExpandRatio(save, 1f);

        group.addComponent(backWithoutSaving = new Button("Go back without saving"));
        group.setComponentAlignment(backWithoutSaving, Alignment.MIDDLE_RIGHT);
    }

    private HorizontalLayout createGroup() {
        HorizontalLayout group = new HorizontalLayout();
        group.setSpacing(true);
        group.setWidth(100, Unit.PERCENTAGE);
        return group;
    }

    @Override
    public void textChange(FieldEvents.TextChangeEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
