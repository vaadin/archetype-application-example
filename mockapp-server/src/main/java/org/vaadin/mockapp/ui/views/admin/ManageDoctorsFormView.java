package org.vaadin.mockapp.ui.views.admin;

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
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.services.DoctorService;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.mockapp.ui.views.ViewDefinition;

import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = ManageDoctorsFormView.VIEW_NAME,
        allowedRoles = {MockAppRoles.ROLE_ADMIN})
public class ManageDoctorsFormView extends VerticalLayout implements View, FieldEvents.TextChangeListener {

    public static final String VIEW_NAME = "doctor";
    private Label title;
    @PropertyId("code")
    private TextField code;
    @PropertyId("firstName")
    private TextField firstName;
    @PropertyId("lastName")
    private TextField lastName;
    @PropertyId("speciality")
    private TextField speciality;
    private BeanFieldGroup<Doctor> binder;
    private Button save;
    private Button cancel;
    private UniqueDoctorCodeValidator codeValidator;

    public ManageDoctorsFormView() {
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

        code = new TextField("Code");
        code.setRequired(true);
        code.setRequiredError("Please enter a code");
        code.setWidth(5, Unit.EM);
        code.setNullRepresentation("");
        code.addTextChangeListener(this);
        codeValidator = new UniqueDoctorCodeValidator();
        code.addValidator(codeValidator);
        formLayout.addComponent(code);

        lastName = new TextField("Last name");
        lastName.setWidth(20, Unit.EM);
        lastName.setNullRepresentation("");
        lastName.addTextChangeListener(this);
        formLayout.addComponent(lastName);

        firstName = new TextField("First name");
        firstName.setWidth(20, Unit.EM);
        firstName.setNullRepresentation("");
        firstName.addTextChangeListener(this);
        formLayout.addComponent(firstName);

        speciality = new TextField("Speciality");
        speciality.setWidth(20, Unit.EM);
        speciality.setNullRepresentation("");
        speciality.addTextChangeListener(this);
        formLayout.addComponent(speciality);

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

        binder = new BeanFieldGroup<Doctor>(Doctor.class);
        binder.bindMemberFields(this);
    }

    private DoctorService getDoctorService() {
        return Services.get(DoctorService.class);
    }

    private void save() {
        try {
            binder.commit();
            getDoctorService().save(binder.getItemDataSource().getBean());
            updateTitle();
            Notification.show("The changes have been saved", Notification.Type.TRAY_NOTIFICATION);
        } catch (FieldGroup.CommitException e) {
            Notification.show("Please check the form value and try again.");
        } catch (Exception e) {
            Notification.show("The data could not be saved. Reported error is: " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void cancel() {
        getUI().getNavigator().navigateTo(ManageDoctorsView.VIEW_NAME);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String uuid = event.getParameters();
        Doctor doctor = null;
        try {
            doctor = getDoctorService().findByUuid(UUID.fromString(uuid));
        } catch (IllegalArgumentException ex) {
        }

        if (doctor == null) {
            doctor = new Doctor();
        }
        binder.setItemDataSource(new BeanItem<Doctor>(doctor));
        codeValidator.setExistingUuid(doctor.getUuid());
        updateTitle();
    }

    private void updateTitle() {
        if (binder.getItemDataSource().getBean().getUuid() == null) {
            title.setValue("New Doctor");
        } else {
            title.setValue("Modify Doctor");
        }
    }

    @Override
    public void textChange(FieldEvents.TextChangeEvent event) {
        save.setEnabled(true);
    }
}
