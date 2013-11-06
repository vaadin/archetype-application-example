package org.vaadin.mockapp.samples.authentication;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

import java.io.Serializable;

/**
 * UI content when the user is not logged in yet.
 *
 * @author petter@vaadin.com
 */
public class LoginScreen extends VerticalLayout {

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

        loginForm.addComponent(new Label("Any username will be accepted as long as the password is 'p'."));

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
        login.addStyleName(Reindeer.BUTTON_DEFAULT);

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
        forgotPassword.addStyleName(Reindeer.BUTTON_LINK);

        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
    }

    /**
     * In a real application, you should delegate to a real authentication service and not
     * do the authentication hard coded!
     */
    private void login() {
        if (password.getValue().equals("p")) {
            CurrentUser.set(username.getValue());
            callback.loginSuccessful();
        } else {
            Notification.show("Login failed", "Please check your username and password and try again.", Notification.Type.HUMANIZED_MESSAGE);
            username.focus();
        }
    }

    private void forgotPassword() {
        Notification.show("This feature is not implemented in this project stub :-)");
    }

    public interface Callback extends Serializable {
        void loginSuccessful();
    }
}
