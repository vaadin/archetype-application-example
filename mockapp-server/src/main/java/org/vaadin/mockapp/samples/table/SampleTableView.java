package org.vaadin.mockapp.samples.table;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.vaadin.mockapp.samples.ResetButtonForTextField;
import org.vaadin.mockapp.samples.data.MockData;
import org.vaadin.mockapp.samples.data.SampleMaster;
import org.vaadin.mockapp.samples.form.SampleFormView;

/**
 * @author petter@vaadin.com
 */
public class SampleTableView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "sampleTableView";
    private BeanItemContainer<SampleMaster> container;
    private Table table;
    private TextField filter;
    private Button goToFormView;

    public SampleTableView() {
        setSpacing(true);
        setMargin(true);
        setSizeFull();

        container = new BeanItemContainer<SampleMaster>(SampleMaster.class);
        container.addNestedContainerProperty("embeddedProperty.enumProperty");

        filter = new TextField();
        filter.setInputPrompt("Filter the table");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                filter(event.getText());
            }
        });
        addComponent(filter);
        setComponentAlignment(filter, Alignment.TOP_RIGHT);

        table = new Table();
        table.setSizeFull();
        table.setContainerDataSource(container);
        table.setVisibleColumns("uuid", "stringProperty", "integerProperty", "bigDecimalProperty", "booleanProperty", "embeddedProperty.enumProperty");
        table.setColumnHeaders("UUID", "String", "Integer", "BigDecimal", "Boolean", "Enum");
        table.setColumnCollapsingAllowed(true);
        table.setColumnCollapsed("integerProperty", true);
        table.setColumnCollapsed("bigDecimalProperty", true);
        table.setSelectable(true);
        table.setImmediate(true);
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                updateButtonState();
            }
        });
        addComponent(table);
        setExpandRatio(table, 1);

        goToFormView = new Button("Open Form View", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                goToFormView();
            }
        });
        addComponent(goToFormView);
        setComponentAlignment(goToFormView, Alignment.BOTTOM_RIGHT);

        updateButtonState();
    }

    private void refresh() {
        SampleMaster oldSelection = (SampleMaster) table.getValue();
        container.removeAllItems();
        container.addAll(MockData.getInstance().getMasterRecords());
        table.setValue(oldSelection);
    }

    private void filter(String filterString) {
        container.removeAllContainerFilters();
        if (filterString.length() > 0) {
            String[] filterWords = filterString.split(" ");
            Container.Filter[] filters = new Container.Filter[filterWords.length];
            for (int i = 0; i < filterWords.length; ++i) {
                String word = filterWords[i];
                filters[i] = new Or(
                        new SimpleStringFilter("stringProperty", word, false, false),
                        new SimpleStringFilter("integerProperty", word, true, true),
                        new SimpleStringFilter("embeddedProperty.enumProperty", word, true, false)
                );
            }
            container.addContainerFilter(new And(filters));
        }
    }

    private void goToFormView() {
        SampleMaster selection = (SampleMaster) table.getValue();
        if (selection != null) {
            getUI().getNavigator().navigateTo(SampleFormView.VIEW_NAME + "/" + selection.getUuid());
        }
    }

    private void updateButtonState() {
        SampleMaster selection = (SampleMaster) table.getValue();
        goToFormView.setEnabled(selection != null);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        refresh();
    }
}
