package ru.mironow;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ru.mironow.business.login.LoginUseCase;
import ru.mironow.ui.loadwindow.LoadWindow;

@SpringBootApplication
public class ItercodesApplication {

	public static void main(String[] args) {
		LoadWindow loadWindow = new LoadWindow();
		loadWindow.setVisible(true);

		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ItercodesApplication.class)
				.headless(false).run(args);
		loadWindow.setVisible(false);
		LoginUseCase loginUseCase = ctx.getBean(LoginUseCase.class);
		loginUseCase.start();
	}

}

