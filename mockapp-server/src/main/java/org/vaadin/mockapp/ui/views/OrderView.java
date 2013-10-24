package org.vaadin.mockapp.ui.views;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import fi.jasoft.qrcode.QRCode;
import org.vaadin.mockapp.ResetButtonForTextField;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.domain.ContactMethod;
import org.vaadin.mockapp.backend.domain.Order;
import org.vaadin.mockapp.backend.domain.OrderState;
import org.vaadin.mockapp.backend.services.OrderService;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.mockapp.ui.views.components.*;

import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = OrderView.VIEW_NAME,
        allowedRoles = {MockAppRoles.ROLE_SALESMAN, MockAppRoles.ROLE_OBSERVER})
public class OrderView extends VerticalLayout implements View, FieldEvents.TextChangeListener, OrderItemsField.SelectionCallback {

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
    private TextField shippingStreet;
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
    private Button copyFromShippingAddress;
    @PropertyId("state")
    private ComboBox state;
    @PropertyId("items")
    private OrderItemsField items;
    private QRCode qrCode;
    private TextField filterItems;
    private Button save;
    private Button backWithoutSaving;
    private Button addItem;
    private Button editItem;
    private Button deleteItem;
    private BeanFieldGroup<Order> binder;

    public OrderView() {
        init();
    }

    private void init() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        HorizontalLayout group;
        addComponent(group = createGroup());
        group.setWidth("100%");
        group.addComponent(title = new H1("Order Details"));

        group.addComponent(orderNo = new TextField("Order No"));
        group.setComponentAlignment(orderNo, Alignment.MIDDLE_RIGHT);
        group.setExpandRatio(orderNo, 1);
        orderNo.setWidth("60px");
        orderNo.setConverter(new OrderNoConverter());

        group.addComponent(state = new ComboBox("State"));
        group.setComponentAlignment(state, Alignment.MIDDLE_RIGHT);
        state.setWidth("140px");
        state.setTextInputAllowed(false);
        state.setNullSelectionAllowed(false);
        state.setRequired(true);
        for (OrderState s : OrderState.values()) {
            state.addItem(s);
            state.setItemCaption(s, s.getDisplayName());
        }

        addComponent(group = createGroup());

        group.addComponent(orderReceived = new DateTimeField("Order received on"));
        orderReceived.setRequired(true);
        orderReceived.setDateFormat("d.M.yy");

        group.addComponent(orderReceivedVia = new ComboBox("Order received via"));
        orderReceivedVia.setTextInputAllowed(false);
        orderReceivedVia.setNullSelectionAllowed(false);
        orderReceivedVia.setRequired(true);
        for (ContactMethod method : ContactMethod.values()) {
            orderReceivedVia.addItem(method);
            orderReceivedVia.setItemCaption(method, method.getDisplayName());
        }
        orderReceivedVia.setWidth("130px");

        group.addComponent(orderReceivedBy = new TextField("Order received by"));
        orderReceivedBy.setRequired(true);
        orderReceivedBy.setWidth("150px");

        addComponent(new H2("Customer"));
        addComponent(group = createGroup());

        group.addComponent(customerName = new TextField("Customer Name"));
        customerName.setWidth("200px");

        group.addComponent(customerEmail = new TextField("E-mail"));
        customerEmail.setNullRepresentation("");
        customerEmail.addValidator(new EmailValidator("Please enter a valid e-mail address"));
        customerEmail.setWidth("200px");

        group.addComponent(customerPhone = new TextField("Phone"));
        customerPhone.setNullRepresentation("");
        customerPhone.setWidth("110px");


        HorizontalLayout columns = new HorizontalLayout();
        addComponent(columns);
        columns.setSpacing(true);
        {
            VerticalLayout column = new VerticalLayout();
            column.setSpacing(true);
            columns.addComponent(column);

            column.addComponent(new H2("Shipping Address"));

            column.addComponent(shippingStreet = new TextField());
            shippingStreet.setWidth("200px");
            shippingStreet.setNullRepresentation("");

            group = createGroup();
            group.setWidth("200px");
            column.addComponent(group);

            group.addComponent(shippingPostalCode = new TextField());
            shippingPostalCode.setWidth("60px");
            shippingPostalCode.setNullRepresentation("");
            group.addComponent(shippingCity = new TextField());
            group.setExpandRatio(shippingCity, 1);
            shippingCity.setWidth("100%");
            shippingCity.setNullRepresentation("");

        }
        {
            VerticalLayout column = new VerticalLayout();
            column.setSpacing(true);
            columns.addComponent(column);

            column.addComponent(group = createGroup());
            group.addComponent(new H2("Billing Address"));

            group.addComponent(copyFromShippingAddress = new Button("Copy from shipping address"));
            copyFromShippingAddress.addStyleName(MockAppTheme.BUTTON_LINK);

            column.addComponent(billingStreet = new TextField());
            billingStreet.setWidth("200px");
            billingStreet.setNullRepresentation("");

            group = createGroup();
            group.setWidth("200px");
            column.addComponent(group);

            group.addComponent(billingPostalCode = new TextField());
            billingPostalCode.setWidth("60px");
            billingPostalCode.setNullRepresentation("");
            group.addComponent(billingCity = new TextField());
            group.setExpandRatio(billingCity, 1);
            billingCity.setWidth("100%");
            billingCity.setNullRepresentation("");
        }
        {
            qrCode = new QRCode();
            qrCode.setDescription("This does not provide any value to the application, it is just here to demo how to use a third party add-on from the directory.");
            qrCode.setWidth("100px");
            qrCode.setHeight("100px");
            columns.addComponent(qrCode);
            qrCode.setValue("https://www.vaadin.com");
        }

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
        items.setSelectionCallback(this);
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

        binder = new BeanFieldGroup<Order>(Order.class);
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
        Order order = null;
        try {
            UUID uuid = UUID.fromString(event.getParameters());
            order = getOrderService().findByUuid(uuid);
        } catch (IllegalArgumentException ex) {
        }
        if (order == null) {
            order = new Order();
        }
        setOrder(order);
    }

    private void setOrder(Order order) {
        orderNo.setReadOnly(false);
        binder.setItemDataSource(order);
        binder.getItemDataSource().addNestedProperty("customerAddress.street");
        binder.getItemDataSource().addNestedProperty("customerAddress.postalCode");
        binder.getItemDataSource().addNestedProperty("customerAddress.city");
        binder.getItemDataSource().addNestedProperty("billingAddress.street");
        binder.getItemDataSource().addNestedProperty("billingAddress.postalCode");
        binder.getItemDataSource().addNestedProperty("billingAddress.city");
        binder.bindMemberFields(this);
        orderNo.setReadOnly(true);
        updateButtonStates();
    }

    private OrderService getOrderService() {
        return Services.get(OrderService.class);
    }

    private void updateButtonStates() {
        editItem.setEnabled(items.hasSelection());
        deleteItem.setEnabled(items.hasSelection());
    }

    @Override
    public void onSelectionChanged(OrderItemsField sender, boolean hasSelection) {
        updateButtonStates();
    }
}
