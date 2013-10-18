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
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.domain.EvacuationCenter;
import org.vaadin.mockapp.backend.services.EvacuationCenterService;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.mockapp.ui.views.DateTimeToStringConverter;
import org.vaadin.mockapp.ui.views.ViewDefinition;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = ManageEvacuationCentersView.VIEW_NAME,
        caption = "Manage Centers",
        // TODO add icon
        order = 100,
        cache = true,
        allowedRoles = {MockAppRoles.ROLE_ADMIN})
public class ManageEvacuationCentersView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "evacuationCenters";
    private Label title;
    private Table table;
    private BeanItemContainer<EvacuationCenter> container;
    private TextField filter;
    private Button refresh;
    private Button add;
    private Button edit;
    private Button delete;

    public ManageEvacuationCentersView() {
        init();
    }

    private void init() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        final HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setSpacing(true);
        addComponent(header);

        title = new Label("Manage Evacuation Centers");
        title.setSizeUndefined();
        title.addStyleName(MockAppTheme.LABEL_H1);
        header.addComponent(title);
        header.setExpandRatio(title, 1f);

        filter = new TextField();
        filter.setInputPrompt("Filter by name or address");
        filter.addStyleName(MockAppTheme.TEXTFIELD_FILTER);
        filter.setWidth(25, Unit.EM);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                filter(event.getText());
            }
        });
        header.addComponent(filter);
        header.setComponentAlignment(filter, Alignment.MIDDLE_RIGHT);

        container = new BeanItemContainer<EvacuationCenter>(EvacuationCenter.class);
        container.addNestedContainerBean("address");

        table = new Table();
        table.setSizeFull();
        table.setContainerDataSource(container);
        table.setSelectable(true);
        table.setImmediate(true);
        table.setVisibleColumns("name", "address.street", "address.postalCode", "address.city", "openDate", "closeDate");
        table.setColumnHeaders("Name", "Street", "Postal Code", "City", "Opened", "Closed");
        table.setCellStyleGenerator(new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table source, Object itemId, Object propertyId) {
                if (((EvacuationCenter) itemId).isClosed()) {
                    return "closed";
                } else {
                    return null;
                }
            }
        });
        table.setConverter("openDate", new DateTimeToStringConverter());
        table.setConverter("closeDate", new DateTimeToStringConverter());
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                updateButtonStates();
            }
        });
        addComponent(table);
        setExpandRatio(table, 1f);

        final HorizontalLayout footer = new HorizontalLayout();
        footer.setWidth("100%");
        footer.setSpacing(true);
        addComponent(footer);

        refresh = new Button("Refresh", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                refresh();
            }
        });
        refresh.setDisableOnClick(true);
        refresh.addStyleName(MockAppTheme.BUTTON_DEFAULT_NO_MODIFICATIONS);
        footer.addComponent(refresh);
        footer.setExpandRatio(refresh, 1f);

        add = new Button("Add Center", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                add();
            }
        });
        add.setDisableOnClick(true);
        add.addStyleName(MockAppTheme.BUTTON_DEFAULT_MODIFY);
        footer.addComponent(add);
        footer.setComponentAlignment(add, Alignment.MIDDLE_RIGHT);

        edit = new Button("Edit", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                edit();
            }
        });
        edit.setDisableOnClick(true);
        footer.addComponent(edit);
        footer.setComponentAlignment(edit, Alignment.MIDDLE_RIGHT);

        delete = new Button("Delete", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                delete();
            }
        });
        delete.setDisableOnClick(true);
        footer.addComponent(delete);
        footer.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);

        updateButtonStates();
    }

    private EvacuationCenterService getEvacuationCenterService() {
        return Services.get(EvacuationCenterService.class);
    }

    private void refresh() {
        container.removeAllItems();
        container.addAll(getEvacuationCenterService().findAll());
        refresh.setEnabled(true);
    }

    private void delete() {
        EvacuationCenter selected = (EvacuationCenter) table.getValue();
        if (selected != null) {
            getEvacuationCenterService().delete(selected);
            container.removeItem(selected);
        }
    }

    private void add() {
        getUI().getNavigator().navigateTo(ManageEvacuationCentersFormView.VIEW_NAME);
        add.setEnabled(true);
    }

    private void edit() {
        EvacuationCenter selected = (EvacuationCenter) table.getValue();
        if (selected != null) {
            getUI().getNavigator().navigateTo(ManageEvacuationCentersFormView.VIEW_NAME + "/" + selected.getUuid());
        }
        edit.setEnabled(true);
    }

    private void filter(String filterString) {
        container.removeAllContainerFilters();
        if (!filterString.isEmpty()) {
            for (String singleFilterTerm : filterString.split(" ")) {
                container.addContainerFilter(
                        new Or(
                                new SimpleStringFilter("name", singleFilterTerm, true, false),
                                new SimpleStringFilter("address.street", singleFilterTerm, true, false),
                                new SimpleStringFilter("address.postalCode", singleFilterTerm, true, false),
                                new SimpleStringFilter("address.city", singleFilterTerm, true, false)
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
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        refresh();
    }
}
