package code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

public class P1 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // layout
        HBox layout = new HBox();

        // variables used to store images
        final int numCards = 3;
        Image[] cards = new Image[numCards];
        ImageView[] images = new ImageView[numCards];
        String file;

        // random setup
        Random rand = new Random();
        int randomValue;

        // generate random cards
        for(int i=0; i<3; i++) {
            randomValue = rand.nextInt(54);     // generate card number (0-53)
            file = "/Cards/"+(randomValue+1)+".png";    // create file path
            cards[i] = new Image(file);                 // create image
            images[i] = new ImageView(cards[i]);        // create image view
            layout.getChildren().add(images[i]);        // add imageview to layout
        }

        // setup display
        primaryStage.setScene(new Scene(layout));
        primaryStage.setTitle("Question_1");
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
