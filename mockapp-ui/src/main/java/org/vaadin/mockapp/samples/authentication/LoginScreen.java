package org.vaadin.mockapp.samples.authentication;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

import java.io.Serializable;

/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends VerticalLayout {

	private TextField username;
	private PasswordField password;
	private Button login;
	private Button forgotPassword;
	private LoginListener loginListener;
	private AccessControl accessControl;

	public LoginScreen(AccessControl accessControl, LoginListener loginListener) {
		this.loginListener = loginListener;
		this.accessControl = accessControl;
		buildUI();
		username.focus();
	}

	private void buildUI() {
		addStyleName("login-screen");
		setSizeFull();

		FormLayout loginForm = new FormLayout();

		loginForm.addStyleName("login-form");
		loginForm.setSizeUndefined();
		loginForm.addComponent(username = new TextField("Username", "admin"));
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
		login.addStyleName(Reindeer.BUTTON_DEFAULT);

		buttons.addComponent(forgotPassword = new Button("Forgot password?"));
		buttons.setComponentAlignment(forgotPassword, Alignment.MIDDLE_LEFT);
		forgotPassword.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				Notification.show("Hint: Try anything");
			}
		});
		forgotPassword.addStyleName(Reindeer.BUTTON_LINK);

		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
	}

	private void login() {
		if (accessControl.signIn(username.getValue(), password.getValue())) {
			loginListener.loginSuccessful();
		} else {
			Notification.show("Login failed",
					"Please check your username and password and try again.",
					Notification.Type.HUMANIZED_MESSAGE);
			username.focus();
		}
	}

	public interface LoginListener extends Serializable {
		void loginSuccessful();
	}
}
