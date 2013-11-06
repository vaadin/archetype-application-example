package org.vaadin.mockapp.authentication;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import org.vaadin.mockapp.theme.MockAppTheme;

import java.io.Serializable;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author petter@vaadin.com
 */
public class LoginScreen extends VerticalLayout {

    private static final ServiceLoader<LoginService> loginService = ServiceLoader.load(LoginService.class);
    private TextField username;
    private PasswordField password;
    private Button login;
    private Button forgotPassword;
    private Callback callback;

    public LoginScreen(Callback callback) {
        this.callback = callback;
        addStyleName("login-screen");
        setSizeFull();

        FormLayout loginForm = new FormLayout();
        loginForm.addStyleName("login-form");
        loginForm.setSizeUndefined();
        loginForm.addComponent(username = new TextField("Username"));
        username.setWidth(15, Unit.EM);
        username.focus();
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
        login.addStyleName(MockAppTheme.BUTTON_DEFAULT);

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

        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
    }

    private void login() {
        if (getLoginService().login(username.getValue(), password.getValue())) {
            callback.loginSuccessful();
        } else {
            Notification.show("Login failed", "Please check your username and password and try again.", Notification.Type.HUMANIZED_MESSAGE);
            username.focus();
        }
    }

    private LoginService getLoginService() {
        Iterator<LoginService> loginServiceIterator = loginService.iterator();
        if (loginServiceIterator.hasNext()) {
            return loginServiceIterator.next();
        } else {
            throw new IllegalStateException("No LoginService implementation found");
        }
    }

    private void forgotPassword() {
        Notification.show("This feature is not implemented in this project stub :-)");
    }

    public interface Callback extends Serializable {
        void loginSuccessful();
    }
}
