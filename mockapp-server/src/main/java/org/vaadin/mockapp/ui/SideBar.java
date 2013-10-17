package org.vaadin.mockapp.ui;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.jetbrains.annotations.NotNull;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.services.LoginService;
import org.vaadin.mockapp.backend.authentication.AuthenticationHolder;
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

        title = new Label("MockApp");
        title.addStyleName("title");
        root.addComponent(title);

        currentUser = new Label(AuthenticationHolder.getAuthentication().getName());
        currentUser.addStyleName("current-user");
        root.addComponent(currentUser);

        logout = new NativeButton("Logout", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                logout();
            }
        });
        logout.setDisableOnClick(true);
        logout.addStyleName("logout");
        root.addComponent(logout);

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
