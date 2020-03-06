package code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.Random;

public class P3 extends Application {

    private Random rand;
    private int radius = 75;
    private int center = 150;
    private final int numDots = 3;
    private double[][] points;
    private Line lines[];
    private double sideLengths[];
    private int[] angleValues;
    private Text angles[];

    @Override
    public void start(Stage primaryStage) throws Exception {

        // layout
        Pane pane = new Pane();
        rand = new Random();

        // shape
        Circle outer = new Circle(radius);
        outer.setCenterX(center);
        outer.setCenterY(center);
        outer.setStroke(Color.BLACK);
        outer.setFill(Color.WHITE);

        // generate points
        points = new double[2][numDots];
        for(int i=0; i < points[0].length; i++) {
            points[0][i] = initX();
            points[1][i] = initY(points[0][i]);
        }

        // lines for initial setting
        sideLengths = new double[numDots];
        lines = new Line[numDots];
        for(int i=0; i < numDots; i++) lines[i] = new Line();
        updateLines();

        // display initial angles
        angleValues = new int[numDots];
        angles = new Text[numDots];
        for(int i=0; i < numDots; i++) {
            angles[i] = new Text();
            angles[i].setFont(new Font(10));
        }
        updateAngles();

        // handle mouse events
        EventHandler cursor[] = new EventHandler[numDots];

        // generate dots and lines
        int dotRadius = 5;
        Circle[] dots = new Circle[numDots];
        for(int k=0; k < numDots; k++) {
            dots[k] = new Circle(dotRadius);
            dots[k].setCenterX(points[0][k]);
            dots[k].setCenterY(points[1][k]);
            dots[k].setStroke(Color.BLACK);
            dots[k].setFill(Color.RED);

            final int m = k;
            // handle method for each dot
            cursor[m] = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    double dotX, dotY; // x and y coordinates
                    double cursorX = event.getX();
                    double cursorY = event.getY();
                    // check if the cursor is within the bounds of the circle
                    if(cursorX >= (center - radius) && cursorX <= (center + radius)) {
                        dotX = cursorX;
                        if(cursorY < center)
                            dotY = generateY(dotX, true);
                        else
                            dotY = generateY(dotX, false);

                        points[0][m] = dotX;
                        points[1][m] = dotY;
                        dots[m].setCenterX(dotX);
                        dots[m].setCenterY(dotY);

                        // update values
                        updateLines();
                        updateAngles();

                    }
                }
            };
            dots[m].addEventHandler(MouseEvent.MOUSE_DRAGGED, cursor[m]);
        }

        // add nodes to pane
        pane.getChildren().add(outer);
        for(int i=0; i < numDots; i++) {
            pane.getChildren().add(lines[i]);
            pane.getChildren().add(dots[i]);
            pane.getChildren().add(angles[i]);
        }

        // setup
        primaryStage.setScene(new Scene(pane,300,300));
        primaryStage.setTitle("Question_3");
        primaryStage.show();

    }

    // generates initial x value within bounds
    private int initX() {
        return rand.nextInt(radius * 2) + (center - radius);
    }

    // uses x value to create y-value in circle
    private double initY(double x) {
        double y = Math.sqrt(Math.pow(radius,2) - Math.pow(x-center, 2)) + 150;
        if(rand.nextInt() % 2 == 0) return y;
        double offset = y - 150;
        return 150 - offset;
    }

    // gets new y based on x and +/- cursor placement
    private double generateY(double x, boolean above) {
        double y = Math.sqrt(Math.pow(radius,2) - Math.pow(x-center, 2)) + 150;
        if(!above) return y;
        double offset = y - 150;
        return 150 - offset;
    }

    // updates lines
    private void updateLines() {

        // line a
        lines[0].setStartX(points[0][0]);
        lines[0].setStartY(points[1][0]);
        lines[0].setEndX(points[0][1]);
        lines[0].setEndY(points[1][1]);

        // line b
        lines[1].setStartX(points[0][0]);
        lines[1].setStartY(points[1][0]);
        lines[1].setEndX(points[0][2]);
        lines[1].setEndY(points[1][2]);

        // line c
        lines[2].setStartX(points[0][1]);
        lines[2].setStartY(points[1][1]);
        lines[2].setEndX(points[0][2]);
        lines[2].setEndY(points[1][2]);

        updateLineLengths();
    }

    // updates line lengths
    private void updateLineLengths() {
        for(int i=0; i<sideLengths.length; i++)
            sideLengths[i] = lineLength(lines[i]);
    }

    // calculates length of line
    private double lineLength(Line line) {
        return Math.sqrt(Math.pow(line.getEndX() - line.getStartX(),2) +
                Math.pow(line.getEndY() - line.getStartY(),2));
    }

    // updates angle text placement
    private void updateAngles() {
        for(int i=0; i < numDots; i++) {

            updateAngleValues();
            angles[i].setText(angleValues[i]+"");

            if(points[0][i] < center)
                angles[i].setX(points[0][i] + 10);
            else if(points[0][i] == center)
                angles[i].setX(points[0][i]);
            else
                angles[i].setX(points[0][i] - 10);

            if(points[1][i] < center)
                angles[i].setY(points[1][i] + 10);
            else if(points[1][i] == center)
                angles[i].setY(points[1][i]);
            else
                angles[i].setY(points[1][i] - 10);
        }
    }

    // refresh angle values
    private void updateAngleValues() {
        double a = sideLengths[0];
        double b = sideLengths[1];
        double c = sideLengths[2];
        double temp;
        temp = Math.toDegrees(Math.acos(((a*a) - (b*b) - (c*c)) / (-2 * b * c)));
        angleValues[2] = (int) (Math.round(temp));
        temp = Math.toDegrees(Math.acos(((b*b) - (a*a) - (c*c)) / (-2 * a * c)));
        angleValues[1] = (int) (Math.round(temp));
        angleValues[0] = 180 - angleValues[2] - angleValues[1];
    }
}
