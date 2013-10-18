package org.vaadin.mockapp.ui;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import org.vaadin.mockapp.EventBus;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.services.LoginService;
import org.vaadin.mockapp.ui.theme.MockAppTheme;

/**
 * @author petter@vaadin.com
 */
public class LoginScreen extends CustomComponent {

    private TextField username;
    private PasswordField password;
    private Button login;
    private Button forgotPassword;

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
        loginForm.addStyleName("login-form");
        loginForm.setSizeUndefined();
        loginForm.addComponent(username = new TextField("Username"));
        username.setWidth(15, Unit.EM);
        loginForm.addComponent(password = new PasswordField("Password"));
        password.setWidth(15, Unit.EM);

        HorizontalLayout buttons = new HorizontalLayout();
        loginForm.addComponent(buttons);
        buttons.setSpacing(true);

        buttons.addComponent(login = new Button("Login"));
        buttons.setComponentAlignment(login, Alignment.MIDDLE_LEFT);
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
        login.addStyleName(MockAppTheme.BUTTON_DEFAULT_NO_MODIFICATIONS);

        buttons.addComponent(forgotPassword = new Button("Forgot password?"));
        buttons.setComponentAlignment(forgotPassword, Alignment.MIDDLE_LEFT);
        forgotPassword.setDisableOnClick(true);
        forgotPassword.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    forgotPassword();
                } finally {
                    forgotPassword.setEnabled(true);
                }
            }
        });
        forgotPassword.addStyleName(MockAppTheme.BUTTON_LINK);

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

    private void forgotPassword() {
        Notification.show("This feature is not implemented in this project stub :-)");
    }

    public static final class LoginSucceededEvent {
    }
}
