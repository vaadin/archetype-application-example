package org.vaadin.mockapp.ui.views.admin;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.authentication.Roles;
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.services.DoctorService;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.mockapp.ui.views.ViewDefinition;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = ManageDoctorsView.VIEW_NAME,
        caption = "Manage Doctors",
        // TODO add icon
        order = 100,
        allowedRoles = {Roles.ROLE_ADMIN})
public class ManageDoctorsView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "doctors";
    private Label title;
    private Table table;
    private BeanItemContainer<Doctor> container;
    private TextField filter;
    private Button refresh;
    private Button add;
    private Button edit;
    private Button delete;

    public ManageDoctorsView() {
        init();
    }

    private void init() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        title = new Label("Manage Doctors");
        title.setSizeUndefined();
        title.addStyleName(MockAppTheme.LABEL_H1);

        filter = new TextField();
        filter.setInputPrompt("Filter by name or speciality");
        filter.addStyleName(MockAppTheme.TEXTFIELD_FILTER);
        filter.setWidth(15, Unit.EM);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                filter(event.getText());
            }
        });

        container = new BeanItemContainer<Doctor>(Doctor.class);

        table = new Table();
        table.setSizeFull();
        table.setContainerDataSource(container);
        table.setSelectable(true);
        table.setImmediate(true);
        table.setVisibleColumns("code", "lastName", "firstName", "speciality");
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                updateButtonStates();
            }
        });

        add = new Button("Add Doctor", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                add();
            }
        });
        add.setDisableOnClick(true);
        add.addStyleName(MockAppTheme.BUTTON_DEFAULT_MODIFY);

        edit = new Button("Edit", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                edit();
            }
        });
        edit.setDisableOnClick(true);

        delete = new Button("Delete", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                delete();
            }
        });
        delete.setDisableOnClick(true);

        refresh = new Button("Refresh", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                refresh();
            }
        });
        refresh.setDisableOnClick(true);
        refresh.addStyleName(MockAppTheme.BUTTON_DEFAULT_NO_MODIFICATIONS);


        final HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setSpacing(true);
        addComponent(header);

        header.addComponent(title);
        header.setExpandRatio(title, 1f);

        header.addComponent(filter);
        header.setComponentAlignment(filter, Alignment.MIDDLE_RIGHT);

        addComponent(table);
        setExpandRatio(table, 1f);

        final HorizontalLayout footer = new HorizontalLayout();
        footer.setWidth("100%");
        footer.setSpacing(true);
        addComponent(footer);

        footer.addComponent(refresh);
        footer.setExpandRatio(refresh, 1f);

        footer.addComponent(add);
        footer.setComponentAlignment(add, Alignment.MIDDLE_RIGHT);

        footer.addComponent(edit);
        footer.setComponentAlignment(edit, Alignment.MIDDLE_RIGHT);

        footer.addComponent(delete);
        footer.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);

        updateButtonStates();
    }

    private DoctorService getDoctorService() {
        return Services.get(DoctorService.class);
    }

    private void refresh() {
        container.removeAllItems();
        container.addAll(getDoctorService().findAll(false));
        refresh.setEnabled(true);
    }

    private void delete() {
        Doctor selected = (Doctor) table.getValue();
        if (selected != null) {
            getDoctorService().delete(selected);
            container.removeItem(selected);
        }
    }

    private void add() {
        getUI().getNavigator().navigateTo(ManageDoctorsFormView.VIEW_NAME);
    }

    private void edit() {
        Doctor selected = (Doctor) table.getValue();
        if (selected != null) {
            getUI().getNavigator().navigateTo(ManageDoctorsFormView.VIEW_NAME + "/" + selected.getUuid());
        }
    }

    private void filter(String filterString) {
        container.removeAllContainerFilters();
        if (!filterString.isEmpty()) {
            for (String singleFilterTerm : filterString.split(" ")) {
                container.addContainerFilter(
                        new Or(
                                new SimpleStringFilter("firstName", singleFilterTerm, true, false),
                                new SimpleStringFilter("lastName", singleFilterTerm, true, false),
                                new SimpleStringFilter("code", singleFilterTerm, true, false),
                                new SimpleStringFilter("speciality", singleFilterTerm, true, false)
                        )
                );
            }
        }
    }

    private void updateButtonStates() {
        boolean hasSelection = table.getValue() != null;
        if (edit != null) {
            edit.setEnabled(hasSelection);
        }
        if (delete != null) {
            delete.setEnabled(hasSelection);
        }
        if (hasSelection && ((Doctor) table.getValue()).isDeleted()) {
            delete.setEnabled(false);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        refresh();
    }
}
