
package com.toto.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import com.toto.backend.BackendApplication;

import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class App extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        try {
            // Configure Spring Boot to NOT start as a web application
            context = new SpringApplicationBuilder(BackendApplication.class)
                    .sources(
                            MainLayoutController.class,
                            MainController.class,
                            DashboardController.class,
                            ChairViewController.class,
                            BedViewController.class
                    )
                    .web(WebApplicationType.NONE) // Important - don't start as web app
                    .headless(false) // Not headless since we're running a UI app
                    .bannerMode(Banner.Mode.OFF) // Optional: disable Spring banner
                    .run(getParameters().getRaw().toArray(new String[0]));
        } catch (Exception e) {
            // Print the actual exception for debugging
            System.err.println("Spring context initialization failed");
            e.printStackTrace();
            // Terminate the JavaFX application
            Platform.exit();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (context == null) {
            // Exit if Spring context failed to initialize
            System.err.println("Application cannot start: Spring context not initialized");
            Platform.exit();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/toto/ui/fxml/MainLayout.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        new JMetro(Style.DARK).setScene(scene); // Apply JMetro theme

        stage.setTitle("Furniture Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        if (context != null) {
            context.close();
        }
    }
}
