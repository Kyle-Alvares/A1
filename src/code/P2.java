package code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.text.DecimalFormat;

public class P2 extends Application {

    private Label amountLabel, yearsLabel, interestLabel, futureLabel;
    private TextField amountText, yearsText, interestText, futureAmount;
    private Button calculate;
    private DecimalFormat df;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // initialize decimal format
        df = new DecimalFormat("0.00");

        // layout
        BorderPane pane = new BorderPane();
        pane.setTop(getGridPane());
        pane.setBottom(getHBox());

        // setup
        primaryStage.setScene(new Scene(pane));
        primaryStage.setTitle("Question_2");
        primaryStage.show();
    }

    private GridPane getGridPane() {

        // layout
        GridPane layout = new GridPane();
        layout.setVgap(0);
        layout.setHgap(5);
        layout.setPadding(new Insets(10, 10, 0, 10));

        // label definitions
        amountLabel = new Label("Investment Amount");
        yearsLabel = new Label("Years");
        interestLabel = new Label("Annual Interest Rate");
        futureLabel =  new Label("Future Value");

        // add labels to layout
        layout.add(amountLabel, 0, 0);
        layout.add(yearsLabel, 0, 1);
        layout.add(interestLabel, 0, 2);
        layout.add(futureLabel, 0, 3);

        // text field declarations
        amountText = new TextField();
        amountText.setAlignment(Pos.BASELINE_RIGHT);
        yearsText = new TextField();
        yearsText.setAlignment(Pos.BASELINE_RIGHT);
        interestText = new TextField();
        interestText.setAlignment(Pos.BASELINE_RIGHT);
        futureAmount = new TextField();
        futureAmount.setAlignment(Pos.BASELINE_RIGHT);
        futureAmount.setStyle("-fx-background-color: #F2F2F2;");

        // add textfields to layout
        layout.add(amountText, 1, 0);
        layout.add(yearsText, 1, 1);
        layout.add(interestText, 1, 2);
        layout.add(futureAmount, 1, 3);

        return layout;
    }

    private HBox getHBox() {

        // layout
        HBox layout = new HBox();
        layout.setPadding(new Insets(5, 10, 10, 0));

        // button declaration
        calculate = new Button("Calculate");

        // set button action
        calculate.setOnAction(e -> {
            futureAmount.setText("");
            double future = 0;
            double amount = isDouble(amountText);
            double years = isDouble(yearsText);
            double interest = isDouble(interestText);
            future = futureAmount(amount, years, interest);
            if(amount >= 0 && years >= 0 && interest >= 0)
                futureAmount.setText(""+df.format(future));
        });

        // add button to layout
        layout.getChildren().add(calculate);
        layout.setAlignment(Pos.BOTTOM_RIGHT);

        return layout;
    }

    // calculate future investment
    private double futureAmount(double amount, double years, double interest) {
        return (amount * (Math.pow((1 + interest/1200),(years*12))));
    }

    // verify textfield is of double type
    private double isDouble(TextField field) {
        try {
            double value = Double.parseDouble(field.getText());
            return value;
        } catch(NumberFormatException e) {
            field.setText("Invalid Input");
            return -1;
        }
    }

    public static void main(String[] args) { launch(args); }
}
