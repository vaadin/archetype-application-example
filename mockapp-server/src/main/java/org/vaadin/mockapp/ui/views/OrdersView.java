package org.vaadin.mockapp.ui.views;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.joda.time.LocalDate;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.authentication.AuthenticationHolder;
import org.vaadin.mockapp.backend.domain.Order;
import org.vaadin.mockapp.backend.domain.OrderState;
import org.vaadin.mockapp.backend.services.OrderService;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.mockapp.ui.views.components.AddressToStringConverter;
import org.vaadin.mockapp.ui.views.components.DateTimeToStringConverter;
import org.vaadin.mockapp.ui.views.components.H1;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = OrdersView.VIEW_NAME,
        caption = "Orders",
        iconThemeResource = "icons/OrdersView.png",
        order = 100,
        cache = true,
        allowedRoles = {MockAppRoles.ROLE_OBSERVER, MockAppRoles.ROLE_SALESMAN})
public class OrdersView extends VerticalLayout implements View, Property.ValueChangeListener {

    public static final String VIEW_NAME = "orders";
    private Table table;
    private BeanItemContainer<Order> container;
    private DateField from;
    private DateField to;
    private OptionGroup states;
    private Button search;
    private Button add;
    private Button open;

    public OrdersView() {
        init();
    }

    private void init() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        addStyleName("orders-view");

        addComponent(new H1("Orders"));

        final HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setSpacing(true);
        addComponent(header);

        Label lbl;
        header.addComponent(lbl = new Label("Find orders received between"));
        lbl.setSizeUndefined();
        header.setComponentAlignment(lbl, Alignment.MIDDLE_LEFT);

        from = new DateField();
        from.setDateFormat("d.M.yyyy");
        from.setValue(new LocalDate().minusMonths(1).toDate());
        from.isImmediate();
        from.addValueChangeListener(this);
        header.addComponent(from);

        header.addComponent(lbl = new Label("and"));
        lbl.setSizeUndefined();
        header.setComponentAlignment(lbl, Alignment.MIDDLE_LEFT);

        to = new DateField();
        to.setDateFormat("d.M.yyyy");
        to.setValue(new LocalDate().toDate());
        to.setImmediate(true);
        to.addValueChangeListener(this);
        header.addComponent(to);

        header.addComponent(lbl = new Label("in"));
        lbl.setSizeUndefined();
        header.setComponentAlignment(lbl, Alignment.MIDDLE_LEFT);

        states = new OptionGroup();
        states.setMultiSelect(true);
        states.setImmediate(true);
        for (OrderState state : OrderState.values()) {
            states.addItem(state);
        }
        states.setValue(Arrays.asList(OrderState.values()));
        states.addValueChangeListener(this);
        PopupView statesPopupView = new PopupView("any of these states", states);
        header.addComponent(statesPopupView);
        header.setComponentAlignment(statesPopupView, Alignment.MIDDLE_LEFT);

        search = new Button("Search", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                search();
            }
        });
        search.setDisableOnClick(true);
        search.addStyleName(MockAppTheme.BUTTON_DEFAULT_NO_MODIFICATIONS);
        header.addComponent(search);
        header.setExpandRatio(search, 1f);

        if (AuthenticationHolder.getAuthentication().hasRole(MockAppRoles.ROLE_SALESMAN)) {
            add = new Button("New Order", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    add();
                }
            });
            add.setDisableOnClick(true);
            add.addStyleName(MockAppTheme.BUTTON_DEFAULT_MODIFY);
            header.addComponent(add);
            header.setComponentAlignment(add, Alignment.MIDDLE_RIGHT);
        }
        open = new Button("Open Selected", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                open();
            }
        });
        open.setDisableOnClick(true);
        header.addComponent(open);
        header.setComponentAlignment(open, Alignment.MIDDLE_RIGHT);

        container = new BeanItemContainer<Order>(Order.class);

        table = new Table();
        table.setSizeFull();
        table.setContainerDataSource(container);
        table.setSelectable(true);
        table.setImmediate(true);
        table.setVisibleColumns("orderNo", "orderReceived", "orderReceivedVia", "orderReceivedBy", "customerName", "customerAddress", "state", "totalWithoutTax", "totalTax", "total");
        table.setColumnCollapsingAllowed(true);
        table.setColumnCollapsed("orderReceivedVia", true);
        table.setColumnCollapsed("orderReceivedBy", true);
        table.setColumnCollapsed("customerAddress", true);
        table.setColumnCollapsed("total", true);
        table.setColumnHeaders("Order No", "Received on", "Received via", "Received by", "Customer Name", "Billing Address", "Order State", "Total (excluding tax)", "Total Tax", "Total (including tax)");
        table.setColumnAlignment("orderNo", Table.Align.RIGHT);
        table.setColumnAlignment("totalWithoutTax", Table.Align.RIGHT);
        table.setColumnAlignment("totalTax", Table.Align.RIGHT);
        table.setColumnAlignment("total", Table.Align.RIGHT);
        table.setConverter("orderReceived", new DateTimeToStringConverter());
        table.setConverter("customerAddress", new AddressToStringConverter());
        table.setCellStyleGenerator(new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table source, Object itemId, Object propertyId) {
                return ((Order) itemId).getState().toString();
            }
        });
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                updateButtonStates();
            }
        });
        addComponent(table);
        setExpandRatio(table, 1f);

        updateButtonStates();
    }

    private OrderService getOrderService() {
        return Services.get(OrderService.class);
    }

    @SuppressWarnings("unchecked")
    private void search() {
        List<Order> orderList = getOrderService().find(new LocalDate(from.getValue()), new LocalDate(to.getValue()), (Set<OrderState>) states.getValue());
        container.removeAllItems();
        container.addAll(orderList);
        search.setEnabled(true);
    }

    private void add() {
        getUI().getNavigator().navigateTo(OrderView.VIEW_NAME);
        add.setEnabled(true);
    }

    private void open() {
        Order selected = (Order) table.getValue();
        if (selected != null) {
            getUI().getNavigator().navigateTo(OrderView.VIEW_NAME + "/" + selected.getUuid());
        }
        open.setEnabled(true);
    }

    /*private void filter(String filterString) {
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
    }*/

    private void updateButtonStates() {
        boolean hasSelection = table.getValue() != null;
        if (open != null) {
            open.setEnabled(hasSelection);
        }
        boolean canSearch = !((Set) states.getValue()).isEmpty() && from.getValue() != null && to.getValue() != null
                && from.getValue().compareTo(to.getValue()) <= 0;
        search.setEnabled(canSearch);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        updateButtonStates();
    }
}
