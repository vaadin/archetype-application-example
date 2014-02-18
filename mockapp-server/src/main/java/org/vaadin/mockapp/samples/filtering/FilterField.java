package org.vaadin.mockapp.samples.filtering;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.vaadin.data.util.filter.Compare;
import com.vaadin.event.ShortcutAction;
import org.vaadin.mockapp.samples.ResetButtonForTextField;
import org.vaadin.mockapp.samples.data.State;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ConnectorEventListener;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.util.ReflectTools;

public class FilterField extends CustomField<String> {

    public static class FilterEvent extends Component.Event {
        private final String filter;

        public FilterEvent(Component source, String filter) {
            super(source);
            this.filter = filter;
        }

        public String getFilter() {
            return filter;
        }

        public Container.Filter getContainerFilter() {
            ArrayList<Container.Filter> filters = new ArrayList<Container.Filter>();
            String[] filterComponents = filter.trim().split("\\s");
            for (String component : filterComponents) {
                if (component.contains(":")) {
                    String[] parts = component.split(":");
                    String propertyId = parts[0];
                    if ("category".equals(propertyId)) {
                        filters.add(new HasCategoryFilter(parts[1]));
                    } else if ("state".equals(propertyId)) {
                        filters.add(new Compare.Equal("state", State.valueOf(parts[1])));
                    } else {
                        filters.add(new SimpleStringFilter(propertyId,
                                parts[1], true, false));
                    }
                } else {
                    filters.add(new SimpleStringFilter("productName",
                            component, true, false));
                }
            }
            return new And(
                    filters.toArray(new Container.Filter[filters.size()]));
        }
    }

    public interface FilterListener extends ConnectorEventListener {
        public static String EVENT_ID = "filterEvent";
        public static Method EVENT_METHOD = ReflectTools.findMethod(
                FilterListener.class, "filter", FilterEvent.class);

        public void filter(FilterEvent event);
    }

    private TextField filter;
    private TextField productNameField = new TextField("Product name");
    private TextField categoryField = new TextField("Category");
    private EnumSelect stateSelect = new EnumSelect<State>("State", State.class);
    private Window filterBuilderWindow;

    public FilterField() {
        super();
        setSizeUndefined();
    }

    @Override
    public Class<? extends String> getType() {
        return String.class;
    }

    @Override
    protected Component initContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeUndefined();

        filter = new TextField();
        filter.setInputPrompt("Filter the table");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                fireEvent(new FilterEvent(FilterField.this, event.getText()));
            }
        });

        layout.addComponent(filter);
        layout.addComponent(new Button("...", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (UI.getCurrent().getWindows().contains(filterBuilderWindow)) {
                    UI.getCurrent().removeWindow(filterBuilderWindow);
                } else {
                    clearFilterBuilderFields();
                    if (filterBuilderWindow == null) {
                        filterBuilderWindow = new Window(null,
                                createFilterBuilder());
                        filterBuilderWindow.setClosable(false);
                        filterBuilderWindow.setDraggable(false);
                        filterBuilderWindow.setResizable(false);
                        filterBuilderWindow.setStyleName(Reindeer.WINDOW_LIGHT);
                        filterBuilderWindow.setModal(true);
                    }
                    UI.getCurrent().addWindow(filterBuilderWindow);
                }
            }
        }));

        return layout;
    }

    private void clearFilterBuilderFields() {
        productNameField.setValue("");
        categoryField.setValue("");
        stateSelect.setValue(null);
    }

    private Component createFilterBuilder() {
        VerticalLayout filterBuilderLayout = new VerticalLayout();
        filterBuilderLayout.setMargin(true);
        filterBuilderLayout.addComponent(productNameField);
        filterBuilderLayout.addComponent(categoryField);
        filterBuilderLayout.addComponent(stateSelect);
        Button filterButton = new Button("Filter", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                StringBuffer filterBuf = new StringBuffer();
                if (!productNameField.getValue().isEmpty()) {
                    filterBuf.append("productName:")
                            .append(productNameField.getValue()).append(" ");
                }
                if (!categoryField.getValue().isEmpty()) {
                    filterBuf.append("category:")
                            .append(categoryField.getValue()).append(" ");
                }
                if (stateSelect.getValue() != null) {
                    filterBuf.append("state:").append(stateSelect.getValue())
                            .append(" ");
                }
                String filterStr = filterBuf.toString();
                filter.setValue(filterStr);
                fireEvent(new FilterEvent(FilterField.this, filterStr));
                UI.getCurrent().removeWindow(filterBuilderWindow);
            }
        });
        filterButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        filterBuilderLayout.addComponent(filterButton);
        filterBuilderLayout.setComponentAlignment(filterButton,
                Alignment.BOTTOM_RIGHT);
        return filterBuilderLayout;
    }

    public void addFilterListener(FilterListener listener) {
        addListener(FilterEvent.class, listener, FilterListener.EVENT_METHOD);
    }

    public void removeFilterListener(FilterListener listener) {
        removeListener(FilterEvent.class, listener);
    }
}
