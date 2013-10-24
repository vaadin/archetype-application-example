package org.vaadin.mockapp.ui.views;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.vaadin.mockapp.ResetButtonForTextField;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.domain.ContactMethod;
import org.vaadin.mockapp.backend.domain.OrderItem;
import org.vaadin.mockapp.backend.domain.OrderState;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.mockapp.ui.views.components.DateTimeField;
import org.vaadin.mockapp.ui.views.components.H1;
import org.vaadin.mockapp.ui.views.components.H2;

import java.util.List;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = OrderView.VIEW_NAME,
        allowedRoles = {MockAppRoles.ROLE_SALESMAN, MockAppRoles.ROLE_OBSERVER})
public class OrderView extends VerticalLayout implements View, FieldEvents.TextChangeListener {

    public static final String VIEW_NAME = "order";
    private H1 title;
    @PropertyId("orderNo")
    private TextField orderNo;
    @PropertyId("orderReceived")
    private DateTimeField orderReceived;
    @PropertyId("orderReceivedBy")
    private TextField orderReceivedBy;
    @PropertyId("orderReceivedVia")
    private ComboBox orderReceivedVia;
    @PropertyId("customerName")
    private TextField customerName;
    @PropertyId("customerAddress.street")
    private TextField shippingAddress;
    @PropertyId("customerAddress.postalCode")
    private TextField shippingPostalCode;
    @PropertyId("customerAddress.city")
    private TextField shippingCity;
    @PropertyId("customerEmail")
    private TextField customerEmail;
    @PropertyId("customerPhone")
    private TextField customerPhone;
    @PropertyId("billingAddress.street")
    private TextField billingStreet;
    @PropertyId("billingAddress.postalCode")
    private TextField billingPostalCode;
    @PropertyId("billingAddress.city")
    private TextField billingCity;
    private CheckBox billingAddressSameAsCustomer;
    @PropertyId("state")
    private ComboBox state;
    @PropertyId("items")
    private OrderItemsField items;
    private TextField filterItems;
    private Button save;
    private Button backWithoutSaving;
    private Button addItem;
    private Button editItem;
    private Button deleteItem;

    public OrderView() {
        init();
    }

    private void init() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        addComponent(title = new H1("Order"));

        HorizontalLayout group;
        addComponent(group = createGroup());
        group.addComponent(orderNo = new TextField("Order No"));
        group.addComponent(state = new ComboBox("Order State"));
        state.setTextInputAllowed(false);
        state.setNullSelectionAllowed(false);
        state.setRequired(true);
        for (OrderState s : OrderState.values()) {
            state.addItem(s);
            state.setItemCaption(s, s.getDisplayName());
        }

        addComponent(new H2("Order Reception"));
        addComponent(orderReceived = new DateTimeField("Date & time"));
        orderReceived.setRequired(true);

        addComponent(group = createGroup());
        group.addComponent(orderReceivedVia = new ComboBox("Method"));
        orderReceivedVia.setTextInputAllowed(false);
        orderReceivedVia.setNullSelectionAllowed(false);
        orderReceivedVia.setRequired(true);
        for (ContactMethod method : ContactMethod.values()) {
            orderReceivedVia.addItem(method);
            orderReceivedVia.setItemCaption(method, method.getDisplayName());
        }
        orderReceivedVia.setWidth(8, Unit.EM);

        group.addComponent(orderReceivedBy = new TextField("Employee who received the order"));
        orderReceivedBy.setRequired(true);
        orderReceivedBy.setWidth(20, Unit.EM);

        addComponent(new H2("Customer Details"));
        addComponent(customerName = new TextField("Customer Name"));
        customerName.setWidth(20, Unit.EM);

        addComponent(group = createGroup());
        group.setCaption("Shipping Address");
        group.addComponent(shippingAddress = new TextField());
        shippingAddress.setWidth(20, Unit.EM);
        group.addComponent(shippingPostalCode = new TextField());
        shippingPostalCode.setWidth(5, Unit.EM);
        group.addComponent(shippingCity = new TextField());
        shippingCity.setWidth(15, Unit.EM);

        addComponent(group = createGroup());
        group.addComponent(customerEmail = new TextField("E-mail"));
        customerEmail.addValidator(new EmailValidator("Please enter a valid e-mail address"));
        customerEmail.setWidth(20, Unit.EM);
        group.addComponent(customerPhone = new TextField("Phone"));
        customerPhone.setWidth(10, Unit.EM);

        addComponent(new H2("Billing Details"));
        addComponent(billingAddressSameAsCustomer = new CheckBox("Billing address same as shipping address"));

        addComponent(group = createGroup());
        group.setCaption("Billing Address");
        group.addComponent(billingStreet = new TextField());
        billingStreet.setWidth(20, Unit.EM);
        group.addComponent(billingPostalCode = new TextField());
        billingPostalCode.setWidth(5, Unit.EM);
        group.addComponent(billingCity = new TextField());
        billingCity.setWidth(15, Unit.EM);


        addComponent(group = createGroup());
        group.setWidth("100%");
        group.addComponent(new H2("Ordered Items"));
        group.addComponent(filterItems = new TextField());
        filterItems.setInputPrompt("Filter items");
        filterItems.addStyleName(MockAppTheme.TEXTFIELD_FILTER);
        filterItems.setWidth(15, Unit.EM);
        ResetButtonForTextField.extend(filterItems);
        group.setComponentAlignment(filterItems, Alignment.MIDDLE_RIGHT);

        addComponent(items = new OrderItemsField());
        items.setSizeFull();
        setExpandRatio(items, 1);

        addComponent(group = createGroup());
        group.setWidth("100%");

        group.addComponent(addItem = new Button("Add Item..."));
        group.addComponent(editItem = new Button("Edit Item..."));
        group.addComponent(deleteItem = new Button("Delete Item"));

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

    public static class OrderItemsField extends CustomField<List> {

        private BeanItemContainer<OrderItem> itemsContainer;
        private Table itemsTable;

        public OrderItemsField() {
            itemsContainer = new BeanItemContainer<OrderItem>(OrderItem.class);
            itemsTable = new Table();
            itemsTable.setContainerDataSource(itemsContainer);
            itemsTable.setSelectable(true);
            itemsTable.setImmediate(true);
            itemsTable.setVisibleColumns("description", "qty", "unit", "unitPrice", "taxPercentage", "totalTax", "totalWithoutTax");
            itemsTable.setColumnHeaders("Description", "Qty", "Unit", "Unit Price", "Tax-%", "Total Tax", "Total (excluding tax)");
            itemsTable.setColumnAlignment("qty", Table.Align.RIGHT);
            itemsTable.setColumnAlignment("unitPrice", Table.Align.RIGHT);
            itemsTable.setColumnAlignment("taxPercentage", Table.Align.RIGHT);
            itemsTable.setColumnAlignment("totalTax", Table.Align.RIGHT);
            itemsTable.setColumnAlignment("totalWithoutTax", Table.Align.RIGHT);
        }

        public void filter(String filterString) {

        }

        @Override
        @SuppressWarnings("unchecked")
        protected void setInternalValue(List newValue) {
            super.setInternalValue(newValue);
            itemsContainer.addAll(newValue);
        }

        @Override
        protected Component initContent() {
            return itemsTable;
        }

        @Override
        public Class<? extends List> getType() {
            return List.class;
        }

        public void addItem() {

        }

        public void editSelected() {

        }

        public void deleteSelected() {

        }
    }
}
