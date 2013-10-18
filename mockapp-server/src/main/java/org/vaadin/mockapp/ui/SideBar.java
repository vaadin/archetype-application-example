package org.vaadin.mockapp.ui;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.jetbrains.annotations.NotNull;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.authentication.AuthenticationHolder;
import org.vaadin.mockapp.backend.services.LoginService;
import org.vaadin.mockapp.ui.views.ViewManager;

/**
 * @author petter@vaadin.com
 */
public class SideBar extends CustomComponent {

    private Label title;
    private Label currentUser;
    private NativeButton logout;
    private Panel navigationPanel;

    public SideBar() {
        init();
    }

    private void init() {
        addStyleName("side-bar");
        setSizeFull();

        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setCompositionRoot(root);

        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.setWidth("100%");
        root.addComponent(titleLayout);

        title = new Label("MockApp");
        title.addStyleName("title");
        title.setSizeUndefined();
        titleLayout.addComponent(title);
        titleLayout.setExpandRatio(title, 1f);
        titleLayout.setComponentAlignment(title, Alignment.MIDDLE_LEFT);

        logout = new NativeButton();
        logout.setDescription("Logout");
        logout.setIcon(new ThemeResource("icons/off_20x20.png"));
        logout.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                logout();
            }
        });
        logout.setDisableOnClick(true);
        logout.addStyleName("logout");
        titleLayout.addComponent(logout);
        titleLayout.setComponentAlignment(logout, Alignment.MIDDLE_RIGHT);

        currentUser = new Label(String.format("Logged in as %s", AuthenticationHolder.getAuthentication().getName()));
        currentUser.addStyleName("current-user");
        root.addComponent(currentUser);

        navigationPanel = new Panel();
        navigationPanel.addStyleName("navigation");
        navigationPanel.setSizeFull();
        navigationPanel.setContent(new VerticalLayout());
        root.addComponent(navigationPanel);
        root.setExpandRatio(navigationPanel, 1f);

        addNavigationButtons();
    }

    private void addNavigationButtons() {
        for (ViewManager.ViewDefinitionEntry viewDefinitionEntry : Services.get(ViewManager.class).getViewDefinitionsForMenu()) {
            addNavigationButton(viewDefinitionEntry);
        }
    }

    private void addNavigationButton(@NotNull final ViewManager.ViewDefinitionEntry viewDefinitionEntry) {
        ComponentContainer componentContainer = (ComponentContainer) navigationPanel.getContent();
        NativeButton navigationButton = new NativeButton();
        navigationButton.setCaption(viewDefinitionEntry.getViewCaption());
        navigationButton.setIcon(viewDefinitionEntry.getViewIcon());
        navigationButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(viewDefinitionEntry.getViewName());
            }
        });
        componentContainer.addComponent(navigationButton);
    }

    private void logout() {
        Services.get(LoginService.class).logout();
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }
}
