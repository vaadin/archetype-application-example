package org.vaadin.mockapp.ui;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import org.vaadin.mockapp.EventBus;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.LoginService;
import org.vaadin.mockapp.ui.theme.MockAppTheme;

/**
 * @author petter@vaadin.com
 */
public class LoginScreen extends CustomComponent {

    private TextField username;
    private PasswordField password;
    private Button login;

    public LoginScreen() {
        init();
    }

    private void init() {
        addStyleName("login-screen");
        setSizeFull();

        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setCompositionRoot(root);

        FormLayout loginForm = new FormLayout();
        loginForm.setSizeUndefined();
        loginForm.addComponent(username = new TextField("Username"));
        loginForm.addComponent(password = new PasswordField("Password"));
        loginForm.addComponent(login = new Button("Login"));
        login.setDisableOnClick(true);
        login.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    login();
                } finally {
                    login.setEnabled(true);
                }
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addStyleName(MockAppTheme.BUTTON_DEFAULT);
        root.addComponent(loginForm);
        root.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
    }

    private void login() {
        if (Services.get(LoginService.class).login(username.getValue(), password.getValue())) {
            Services.get(EventBus.class).publish(new LoginSucceededEvent());
        } else {
            Notification.show("Login failed", "Please check your username and password and try again.", Notification.Type.HUMANIZED_MESSAGE);
            username.focus();
        }
    }

    public static final class LoginSucceededEvent {
    }
}
