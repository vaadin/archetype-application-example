package org.vaadin.mockapp.samples.authentication;

import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

import java.io.Serializable;

/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends CssLayout {

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

		Component loginForm = buildLoginForm();

		CssLayout loginInformation = buildLoginInformation();
		
		VerticalLayout centeringLayout = new VerticalLayout();
		centeringLayout.setStyleName("centering-layout");
		centeringLayout.addComponent(loginForm);
		centeringLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
		
		addComponent(centeringLayout);
		addComponent(loginInformation);
	}

	private Component buildLoginForm() {
		FormLayout loginForm = new FormLayout();

		loginForm.addStyleName("login-form");
		loginForm.setSizeUndefined();
		loginForm.addComponent(username = new TextField("Username", "admin"));
		username.setWidth(15, Unit.EM);
		loginForm.addComponent(password = new PasswordField("Password"));
		password.setWidth(15, Unit.EM);
		CssLayout buttons = new CssLayout();
		buttons.setStyleName("buttons");
		loginForm.addComponent(buttons);

		buttons.addComponent(login = new Button("Login"));
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
		forgotPassword.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				Notification.show("Hint: Try anything");
			}
		});
		forgotPassword.addStyleName(Reindeer.BUTTON_LINK);
		return loginForm;
	}

	private CssLayout buildLoginInformation() {
		CssLayout loginInformation = new CssLayout();
		loginInformation.setStyleName("login-information");
		Label loginInfoText = new Label("<h1>Login Information</h1>"
				+ "The user admin has more privileges than any other user.",
				ContentMode.HTML);
		loginInformation.addComponent(loginInfoText);
		return loginInformation;
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
