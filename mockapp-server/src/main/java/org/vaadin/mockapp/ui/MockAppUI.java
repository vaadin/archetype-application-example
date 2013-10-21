package org.vaadin.mockapp.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vaadin.mockapp.EventBus;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.authentication.Authentication;
import org.vaadin.mockapp.backend.authentication.AuthenticationHolder;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.mockapp.ui.views.*;

import javax.servlet.annotation.WebServlet;

/**
 * @author petter@vaadin.com
 */
@Theme(MockAppTheme.THEME_NAME)
@Widgetset("org.vaadin.mockapp.ui.widgetset.MockAppWidgetset")
public class MockAppUI extends UI implements EventBus.Subscriber {

    static {
        /**
         * This makes it possible to access both the EventBus and the ViewManager via the Services class.
         */
        Services.register(new Services.ServiceProvider<EventBus>() {
            @Nullable
            @Override
            public EventBus get() {
                MockAppUI ui = MockAppUI.getCurrent();
                return ui == null ? null : ui.eventBus;
            }
        }, EventBus.class);
        Services.register(new Services.ServiceProvider<ViewManager>() {
            @Nullable
            @Override
            public ViewManager get() {
                MockAppUI ui = MockAppUI.getCurrent();
                return ui == null ? null : ui.viewManager;
            }
        }, ViewManager.class);
    }

    private final ViewManager viewManager = new ViewManager() {
        {
            /*
                Add your views here. Remember to annotate the view classes with @ViewDefinition!
             */
            addView(HomeView.class);
            addView(OrderView.class);
            addView(OrdersView.class);
        }
    };
    private final EventBus eventBus = new EventBus();

    /**
     * @return
     */
    @Nullable
    public static MockAppUI getCurrent() {
        return (MockAppUI) UI.getCurrent();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        eventBus.subscribe(this);
        getPage().setTitle("Mock App UI");
        if (isLoggedIn()) {
            showMainScreen();
        } else {
            showLoginScreen();
        }
    }

    private boolean isLoggedIn() {
        return AuthenticationHolder.getAuthentication() != Authentication.ANONYMOUS;
    }

    private void showMainScreen() {
        MainScreen mainScreen = new MainScreen();

        Navigator navigator = new Navigator(this, mainScreen);
        navigator.setErrorView(ErrorView.class);
        navigator.addProvider(viewManager);

        setContent(mainScreen);
    }

    private void showLoginScreen() {
        assert getContent() == null : "UI already contains content";
        setContent(new LoginScreen());
    }

    @Override
    public void onEventReceived(@NotNull Object event) {
        if (event instanceof LoginScreen.LoginSucceededEvent) {
            showMainScreen();
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MockAppUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MockAppUI.class, productionMode = false)
    public static class MockAppUIServlet extends VaadinServlet {
    }
}
