package code;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class P4 extends  Application {

    // variables
    final int alphabetLength = 26;
    final int A = 'A';
    final int a = 'a';
    final int Z = 'Z';
    final int z = 'z';
    int frequency[];
    Rectangle[] bars;
    TextField input;
    Label filename;
    BorderPane pane;
    Button view = new Button("View");

    @Override
    public void start(Stage primaryStage) throws Exception {

        // store data
        frequency = new int[alphabetLength];

        // layout
        pane = new BorderPane();
        pane.setTop(graph());

        // button action
        view.setOnAction(event -> display());

        // setup stage
        primaryStage.setScene(new Scene(pane,400,250));
        primaryStage.setTitle("Question_4");
        primaryStage.show();
    }

    // get frequency of characters in file
    public void inputData(String data) {
        for(char c : data.toCharArray()){
            if(c >= A && c <= Z)
                frequency[c-A]++;
            if(c >= a && c <= z)
                frequency[c-a]++;
        }
    }

    // reset frequencies of each letter
    public void clearData() {
        for(int i = 0; i < alphabetLength; i++) frequency[i] = 0;
    }

    // rectangles
    public HBox data() {
        HBox layout = new HBox();
        layout.setSpacing(3);
        bars = new Rectangle[alphabetLength];
        int x = 0;
        int y = 150;
        int width = 10;
        int index = 0;
        int scaleFactor = 10;
        for(Rectangle r : bars) {
            r = new Rectangle();
            r.setX(x);
            r.setY(y - frequency[index]);
            r.setWidth(width);
            r.setHeight(frequency[index++] * scaleFactor);
            r.setFill(Color.WHITE);
            r.setStroke(Color.BLACK);
            layout.setAlignment(Pos.BOTTOM_CENTER);
            layout.getChildren().add(r);
        }
        return layout;
    }

    // return input layout
    public HBox input() {
        HBox layout = new HBox();
        filename = new Label("Filename");
        input = new TextField();
        input.setMinWidth(270);
        input.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER)
                    display();
            }
        });
        layout.getChildren().add(filename);
        layout.getChildren().add(input);
        layout.getChildren().add(view);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-border-insets: 19;" + "-fx-border-width: 1;" + "-fx-border-color: black;");
        return layout;
    }

    // return graph layout
    public VBox graph() {
        VBox layout = new VBox();
        layout.getChildren().add(data());
        Label x_axis = new Label();
        String text = "  ";
        for(int i=A; i <= Z; i++) {
            text += (char) i + "  ";
        }
        x_axis.setText(text);
        x_axis.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.BOTTOM_CENTER);
        layout.getChildren().add(x_axis);
        layout.getChildren().add(input());
        return layout;
    }

    // display graph
    public void display() {
        clearData();
        String filename = input.getText();
        File file = new File(filename);
        String line;
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                line = scanner.nextLine();
                inputData(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            input.setText("File Not Found!");
        }
        pane.setTop(graph());
    }

    public static void main(String[] args) { launch(args); }
}
