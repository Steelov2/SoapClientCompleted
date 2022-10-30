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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import com.example.consumingwebservice.wsdl.StoreDocumentResponse;

@SpringBootApplication

public class Demo1Application extends Application implements Runnable {
    @Autowired
    DocumentClient documentClient;
//    @Autowired
//    DocumentClient documentClient;
    byte[] message;

//    @Bean
//    CommandLineRunner lookup(DocumentClient documentClient) throws IOException {
//
////        File path = new File(absolutePath);
//        return args -> {
////            byte[] array = method(path);
//            StoreDocumentResponse response = documentClient.getDocument(message);
//            System.out.println(Arrays.toString(message));
//            System.err.println(response.getSha());
//        };
//    }
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        SpringApplication.run(getClass()).getAutowireCapableBeanFactory().autowireBean(this);
    }
    @Override
    public void start(Stage stage)
    {
        try {
            // set title for the stage
            stage.setTitle("FileChooser");
            // create a File chooser
            FileChooser fil_chooser = new FileChooser();
            // create a Label
            Label label = new Label("no files selected");
            // create a Button
            Button button = new Button("Show open dialog");
            // create an Event Handler
            EventHandler<ActionEvent> event =
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e)
                        {
                            // get the file selected
                            File file = fil_chooser.showOpenDialog(stage);
                            if (file != null) {
                                label.setText(file.getAbsolutePath()
                                        + " selected");
                                try {
                                    String sha1;
                                    byte[] bytes = Files.readAllBytes(file.toPath());
                                    message = Files.readAllBytes(file.toPath());
//                                    System.out.println(Arrays.toString(bytes));
                                    sha1=documentClient.getDocument(bytes).getSha();
                                    label.setText("The SHA-1 of the file " + file.getName() + " is \n " + sha1);
                                    System.out.println(sha1);

                                }   catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }

                        }
                    };
            button.setOnAction(event);
            // create a VBox
            VBox vbox = new VBox(30, label, button);
            // set Alignment
            vbox.setAlignment(Pos.CENTER);
            // create a scene
            Scene scene = new Scene(vbox, 800, 500);
            // set the scene
            stage.setScene(scene);
            stage.show();

        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static byte[] method(File file)
            throws IOException {
        // Creating an object of FileInputStream to
        // read from a file
        FileInputStream fl = new FileInputStream(file);
        // Now creating byte array of same length as file
        byte[] arr = new byte[(int) file.length()];
        // Reading file content to byte array
        // using standard read() method
        fl.read(arr);
        // lastly closing an instance of file input stream
        // to avoid memory leakage
        fl.close();
        // Returning above byte array
        return arr;
    }

    @Override
    public void run() {
        System.out.println("Now the thread is running ...");
    }
}
