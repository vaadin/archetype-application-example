package org.vaadin.mockapp.ui.views.admin;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.domain.EvacuationCenter;
import org.vaadin.mockapp.backend.services.EvacuationCenterService;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.mockapp.ui.views.DateTimeField;
import org.vaadin.mockapp.ui.views.ViewDefinition;

import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = ManageEvacuationCentersFormView.VIEW_NAME,
        allowedRoles = {MockAppRoles.ROLE_ADMIN})
public class ManageEvacuationCentersFormView extends VerticalLayout implements View, FieldEvents.TextChangeListener, Property.ValueChangeListener {

    public static final String VIEW_NAME = "evacuationCenter";
    private Label title;
    @PropertyId("name")
    private TextField name;
    @PropertyId("address.street")
    private TextField street;
    @PropertyId("address.postalCode")
    private TextField postalCode;
    @PropertyId("address.city")
    private TextField city;
    @PropertyId("openDate")
    private DateTimeField opened;
    @PropertyId("closeDate")
    private DateTimeField closed;
    private BeanFieldGroup<EvacuationCenter> binder;
    private Button save;
    private Button cancel;

    public ManageEvacuationCentersFormView() {
        init();
    }

    private void init() {
        setMargin(true);
        setSpacing(true);

        title = new Label();
        title.addStyleName(MockAppTheme.LABEL_H1);
        addComponent(title);

        final FormLayout formLayout = new FormLayout();
        addComponent(formLayout);

        name = new TextField("Name");
        name.setRequired(true);
        name.setRequiredError("Please enter a name");
        name.setWidth(20, Unit.EM);
        name.setNullRepresentation("");
        name.addTextChangeListener(this);
        formLayout.addComponent(name);

        street = new TextField("Street");
        street.setWidth(20, Unit.EM);
        street.setNullRepresentation("");
        street.addTextChangeListener(this);
        formLayout.addComponent(street);

        postalCode = new TextField("Postal code");
        postalCode.setWidth(5, Unit.EM);
        postalCode.setNullRepresentation("");
        postalCode.addTextChangeListener(this);
        formLayout.addComponent(postalCode);

        city = new TextField("City");
        city.setWidth(20, Unit.EM);
        city.setNullRepresentation("");
        city.addTextChangeListener(this);
        formLayout.addComponent(city);

        opened = new DateTimeField("Opened");
        opened.setDateFormat("dd.MM.yyyy");
        opened.addValueChangeListener(this);
        formLayout.addComponent(opened);

        closed = new DateTimeField("Closed");
        closed.setDateFormat("dd.MM.yyyy");
        closed.addValueChangeListener(this);
        formLayout.addComponent(closed);

        final HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        formLayout.addComponent(buttons);

        save = new Button("Save Changes", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                save();
            }
        });
        save.setDisableOnClick(true);
        save.addStyleName(MockAppTheme.BUTTON_DEFAULT_MODIFY);
        save.setEnabled(false);
        buttons.addComponent(save);

        cancel = new Button("Go back without saving", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                cancel();
            }
        });
        cancel.setDisableOnClick(true);
        buttons.addComponent(cancel);

        binder = new BeanFieldGroup<EvacuationCenter>(EvacuationCenter.class);
        binder.bindMemberFields(this);
    }

    private EvacuationCenterService getEvacuationCenterService() {
        return Services.get(EvacuationCenterService.class);
    }

    private void save() {
        try {
            binder.commit();
            getEvacuationCenterService().save(binder.getItemDataSource().getBean());
            updateTitle();
            Notification.show("The changes have been saved", Notification.Type.TRAY_NOTIFICATION);
        } catch (FieldGroup.CommitException e) {
            Notification.show("Please check the form value and try again.");
        } catch (Exception e) {
            Notification.show("The data could not be saved. Reported error is: " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void cancel() {
        getUI().getNavigator().navigateTo(ManageEvacuationCentersView.VIEW_NAME);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String uuid = event.getParameters();
        EvacuationCenter evacuationCenter = null;
        try {
            evacuationCenter = getEvacuationCenterService().findByUuid(UUID.fromString(uuid));
        } catch (IllegalArgumentException ex) {
        }

        if (evacuationCenter == null) {
            evacuationCenter = new EvacuationCenter();
        }
        BeanItem<EvacuationCenter> item = new BeanItem<EvacuationCenter>(evacuationCenter);
        item.addNestedProperty("address.street");
        item.addNestedProperty("address.postalCode");
        item.addNestedProperty("address.city");
        binder.setItemDataSource(item);
        updateTitle();
    }

    private void updateTitle() {
        if (binder.getItemDataSource().getBean().getUuid() == null) {
            title.setValue("New Evacuation Center");
        } else {
            title.setValue("Modify Evacuation Center");
        }
    }

    @Override
    public void textChange(FieldEvents.TextChangeEvent event) {
        save.setEnabled(true);
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        save.setEnabled(true);
    }
}
