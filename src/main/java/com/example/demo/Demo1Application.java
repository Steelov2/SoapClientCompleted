package com.example.demo;

import com.example.demo.client.DocumentClient;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootApplication

public class Demo1Application extends Application {
    @Autowired
    DocumentClient documentClient;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        SpringApplication.run(getClass()).getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("FileChooser");
            FileChooser fil_chooser = new FileChooser();
            Label label = new Label("no files selected");
            Button button = new Button("Show open dialog");
            EventHandler<ActionEvent> event =
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                            File file = fil_chooser.showOpenDialog(stage);
                            if (file != null) {
                                label.setText(file.getAbsolutePath()
                                        + " selected");
                                try {
                                    String sha1;
                                    byte[] bytes = Files.readAllBytes(file.toPath());
//                                    System.out.println(Arrays.toString(bytes));
                                    sha1 = documentClient.getDocument(bytes).getSha();
                                    label.setText("The SHA-1 of the file " + file.getName() + " is \n " + sha1);
                                    System.out.println(sha1);

                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }

                        }
                    };
            button.setOnAction(event);
            VBox vbox = new VBox(30, label, button);
            vbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vbox, 800, 500);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
