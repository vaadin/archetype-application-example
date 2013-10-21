package org.vaadin.mockapp.ui.views;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.domain.OrderItem;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.mockapp.ui.views.components.DateTimeField;
import org.vaadin.mockapp.ui.views.components.H1;
import org.vaadin.mockapp.ui.views.components.H2;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = OrderView.VIEW_NAME,
        allowedRoles = {MockAppRoles.ROLE_SALESMAN, MockAppRoles.ROLE_OBSERVER})
public class OrderView extends VerticalLayout implements View, FieldEvents.TextChangeListener {

    public static final String VIEW_NAME = "order";
    private H1 title;
    private TextField orderNo;
    private DateTimeField orderReceived;
    private TextField orderReceivedBy;
    private ComboBox orderReceivedVia;
    private TextField customerName;
    private TextField shippingAddress;
    private TextField shippingPostalCode;
    private TextField shippingCity;
    private TextField customerEmail;
    private TextField customerPhone;
    private TextField billingStreet;
    private TextField billingPostalCode;
    private TextField billingCity;
    private CheckBox billingAddressSameAsCustomer;
    private ComboBox state;
    private BeanItemContainer<OrderItem> itemsContainer;
    private Table itemsTable;
    private Button save;
    private Button backWithoutSaving;

    public OrderView() {
        init();
    }

    private void init() {
        setWidth("600px");
        setMargin(true);
        setSpacing(true);

        addComponent(title = new H1("Order"));

        HorizontalLayout group;

        addComponent(new H2("Order Reception"));
        addComponent(group = createGroup());
        group.addComponent(orderReceived = new DateTimeField("Date & time"));
        group.addComponent(orderReceivedVia = new ComboBox("Method"));
        group.addComponent(orderReceivedBy = new TextField("Recipient"));

        addComponent(new H2("Customer Details"));
        addComponent(customerName = new TextField("Customer Name"));

        addComponent(group = createGroup());
        group.setCaption("Shipping Address");
        group.addComponent(shippingAddress = new TextField());
        group.addComponent(shippingPostalCode = new TextField());
        group.addComponent(shippingCity = new TextField());

        addComponent(group = createGroup());
        group.addComponent(customerEmail = new TextField("E-mail"));
        group.addComponent(customerPhone = new TextField("Phone"));

        addComponent(new H2("Billing Details"));
        addComponent(billingAddressSameAsCustomer = new CheckBox("Billing address same as shipping address"));

        addComponent(group = createGroup());
        group.setCaption("Billing Address");
        group.addComponent(billingStreet = new TextField());
        group.addComponent(billingPostalCode = new TextField());
        group.addComponent(billingCity = new TextField());

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
