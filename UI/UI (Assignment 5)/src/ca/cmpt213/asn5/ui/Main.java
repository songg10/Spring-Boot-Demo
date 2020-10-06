package ca.cmpt213.asn5.ui;

import ca.cmpt213.asn5.receiver.Tokimon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The Main UI for user experience, interacting with the user.
 */

public class Main extends Application {
    private List<Tokimon> tokimons = new ArrayList<>();
    private Label idLabel = new Label("ID: ");
    private Label nameLabel = new Label("Name: ");
    private Label weightLabel = new Label("Weight: ");
    private Label heightLabel = new Label("Height: ");
    private Label abilityLabel = new Label("Ability: ");
    private Label strengthLabel = new Label("Strength: ");
    private Label colorLabel = new Label("Color: ");
    Button returnHome = new Button("Return to the main menu");

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label blankLabel1 = new Label("");
        Label crossLine = new Label("-------------POST---------------");
        Label weightValueLabel = new Label("0.00");
        Label heightValueLabel = new Label("0.00");
        Label strengthValueLabel = new Label("0.00");

        idLabel.setPrefWidth(100);
        nameLabel.setPrefWidth(100);
        weightLabel.setPrefWidth(100);
        heightLabel.setPrefWidth(100);
        abilityLabel.setPrefWidth(100);
        strengthLabel.setPrefWidth(100);
        colorLabel.setPrefWidth(100);

        idLabel.setPadding(new Insets(0, 0, 0, 20));
        nameLabel.setPadding(new Insets(0, 0, 0, 20));
        weightLabel.setPadding(new Insets(0, 0, 0, 20));
        heightLabel.setPadding(new Insets(0, 0, 0, 20));
        abilityLabel.setPadding(new Insets(0, 0, 0, 20));
        strengthLabel.setPadding(new Insets(0, 0, 0, 20));
        colorLabel.setPadding(new Insets(0, 0, 0, 20));

        weightValueLabel.setPadding(new Insets(0, 0, 0, 10));
        heightValueLabel.setPadding(new Insets(0, 0, 0, 10));
        strengthValueLabel.setPadding(new Insets(0, 0, 0, 10));

        TextField idField = new TextField();
        TextField nameField = new TextField();
        Slider weightSlider = new Slider(0, 96, 48);
        weightSlider.setShowTickMarks(true);
        weightSlider.setShowTickLabels(true);
        Slider heightSlider = new Slider(0, 2, 1);
        heightSlider.setShowTickMarks(true);
        heightSlider.setShowTickLabels(true);
        ComboBox<String> abilityBox = new ComboBox<>();
        abilityBox.getItems().addAll("Fire","Water","Fly","Electric","Ice","Ground","Steel","Rock","Paper","Scissor");
        Slider strengthSlider = new Slider(0, 100, 50);
        strengthSlider.setShowTickMarks(true);
        strengthSlider.setShowTickLabels(true);
        strengthSlider.setSnapToTicks(true);
        TextField colorField = new TextField();

        weightSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number before, Number after) {
                double weightValue = weightSlider.getValue();
                weightValueLabel.setText(String.format("%.2f", weightValue));
            }
        });

        heightSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number before, Number after) {
                double heightValue = heightSlider.getValue();
                heightValueLabel.setText(String.format("%.2f", heightValue));
            }
        });

        strengthSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number before, Number after) {
                double strengthValue = strengthSlider.getValue();
                strengthValueLabel.setText(String.format("%.2f", strengthValue));
            }
        });

        Button getAll = new Button("See all Tokimon");
        Button getById = new Button("See Tokimon by ID");
        Button addToList = new Button("Add Tokimon to List");
        Button deleteByID = new Button("Delete Tokimon by ID");

        GridPane gridpane = new GridPane();
        gridpane.setVgap(5);
        gridpane.setPadding(new Insets(10, 10, 10, 10));
        gridpane.add(idLabel, 0, 0);
        gridpane.add(idField, 1, 0);

        gridpane.add(blankLabel1, 1, 1);

        gridpane.add(getById, 1, 2);
        gridpane.add(deleteByID, 1, 3);
        gridpane.add(getAll, 1, 4);

        gridpane.add(crossLine, 1, 5);

        gridpane.add(nameLabel, 0, 6);
        gridpane.add(nameField, 1, 6);
        gridpane.add(weightLabel, 0, 7);
        gridpane.add(weightSlider, 1, 7);
        gridpane.add(weightValueLabel, 2, 7);
        gridpane.add(heightLabel, 0, 8);
        gridpane.add(heightSlider, 1, 8);
        gridpane.add(heightValueLabel, 2, 8);
        gridpane.add(abilityLabel, 0, 9);
        gridpane.add(abilityBox, 1, 9);
        gridpane.add(strengthLabel, 0, 10);
        gridpane.add(strengthSlider, 1, 10);
        gridpane.add(strengthValueLabel, 2, 10);
        gridpane.add(colorLabel, 0, 11);
        gridpane.add(colorField, 1, 11);

        gridpane.add(addToList, 1, 12);

        Scene mainScene = new Scene(gridpane, 350, 450);
        returnHome.setOnAction(e->primaryStage.setScene(mainScene));

        getAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    URL url = new URL("http://localhost:8080/api/tokimon/all");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    System.out.println(connection.getResponseCode());
                    if (connection.getResponseCode() == 200) {
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()));
                        String output;
                        while ((output = br.readLine()) != null) {
                            System.out.println(output);
                            parsingJson(output);
                        }
                        if (tokimons.size() > 0) {
                            drawingTokimon(primaryStage, mainScene);
                        }
                    }
                    else{
                        notFound(primaryStage);
                    }
                    connection.disconnect();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        getById.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    String id = idField.getText();
                    URL url = new URL("http://localhost:8080/api/tokimon/" + id);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    System.out.println(connection.getResponseCode());
                    if (connection.getResponseCode() == 200){
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()));
                        String output;
                        while((output = br.readLine()) != null) {
                            System.out.println(output);
                            Tokimon get_tokimon = new Gson().fromJson(output, Tokimon.class);
                            if (get_tokimon != null && get_tokimon.getId() == Long.parseLong(id)){
                                printInfo(get_tokimon, primaryStage);
                                break;
                            }
                            else{
                                notFound(primaryStage);
                                break;
                            }
                        }
                    }
                    else{
                        notFound(primaryStage);
                    }
                    connection.disconnect();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        addToList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    URL url = new URL("http://localhost:8080/api/tokimon/add");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");

                    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                    String name = nameField.getText();
                    double weight = Double.parseDouble(String.format("%.2f", weightSlider.getValue()));
                    double height = Double.parseDouble(String.format("%.2f", heightSlider.getValue()));
                    String ability = abilityBox.getValue();
                    double strength = Double.parseDouble(String.format("%.2f", strengthSlider.getValue()));
                    String color = colorField.getText();
                    wr.write("{\"name\":" + "\"" + name + "\"" +",\"ability\":" + "\"" + ability + "\"" + ",\"strength\":" + strength + ",\"weight\":" + weight + ",\"height\":" + height +",\"color\":"+ "\"" + color + "\"" + "}");
                    wr.flush();
                    wr.close();

                    connection.connect();
                    System.out.println(connection.getResponseCode());
                    if (connection.getResponseCode() == 201) {
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()));
                        String output;
                        while((output = br.readLine()) != null) {
                            System.out.println(output);
                            Tokimon new_tokimon = new Gson().fromJson(output,Tokimon.class);
                            if (new_tokimon != null) {
                                printInfo(new_tokimon, primaryStage);
                                break;
                            }
                            else{
                                notFound(primaryStage);
                                break;
                            }
                        }
                    }
                    else{
                        notFound(primaryStage);
                    }
                    connection.disconnect();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        deleteByID.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    String id = idField.getText();
                    URL url = new URL("http://localhost:8080/api/tokimon/" + id);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("DELETE");

                    connection.connect();
                    System.out.println(connection.getResponseCode());
                    if (connection.getResponseCode() == 204){
                        deletedTokimon(primaryStage, id);
                    }
                    else{
                        notFound(primaryStage);
                    }
                    connection.disconnect();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Tokimon Controller");
        primaryStage.show();
    }

    private void parsingJson(String jsonString){
        try {
            Gson gson = new Gson();
            tokimons = gson.fromJson(jsonString, new TypeToken<List<Tokimon>>(){}.getType());
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void drawingTokimon(Stage mainStage, Scene mainScene){
        int count = 0;
        for (int i = 0; i < tokimons.size(); i++){
            count++;
        }
        Rectangle[] tokimonRectangles = new Rectangle[count];
        Label[] labels = new Label[count];
        HBox hbox = new HBox();
        VBox tokiBox;
        for (int i = 0; i < count; i++){
            final int j = i;
            tokimonRectangles[i] = new Rectangle(0, 0, tokimons.get(i).getHeight() + 100, tokimons.get(i).getWeight() + 100);
            Color c = Color.web(tokimons.get(i).getColor());
            tokimonRectangles[i].setFill(c);
            tokimonRectangles[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                   printInfo(tokimons.get(j), mainStage);

                }
            });
            labels[i] = new Label(tokimons.get(i).getName());
            tokiBox = new VBox(tokimonRectangles[i], labels[i]);
            tokiBox.setAlignment(Pos.CENTER);
            tokiBox.setSpacing(10);
            hbox.getChildren().add(tokiBox);
        }
        hbox.setSpacing(15);
        VBox vbox = new VBox(hbox, returnHome);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        Scene subScene = new Scene(vbox);
        mainStage.setScene(subScene);
        mainStage.show();
    }

    private void notFound(Stage mainStage){
        Text text = new Text("NOT FOUND: THE TOKIMON LIST IS EITHER EMPTY OR THE TOKIMON WITH THE ENTERED ID DOESN'T EXIST");
        text.setFont(new Font("Arial", 20));
        ImageView img = new ImageView(new Image("file:image\\mute.png"));
        img.setFitHeight(500);
        img.setFitWidth(500);
        VBox notfound = new VBox(img, text, returnHome);
        notfound.setSpacing(10);
        notfound.setAlignment(Pos.CENTER);
        notfound.setPadding(new Insets(0,30,30,30));
        mainStage.setScene(new Scene(notfound));
        mainStage.show();
    }

    private void printInfo(Tokimon tokimon, Stage mainStage){
        GridPane tokiInfo = new GridPane();
        tokiInfo.add(nameLabel, 0, 0);
        tokiInfo.add(weightLabel, 0, 1);
        tokiInfo.add(heightLabel, 0, 2);
        tokiInfo.add(abilityLabel, 0, 3);
        tokiInfo.add(strengthLabel, 0, 4);
        tokiInfo.add(colorLabel, 0, 5);
        tokiInfo.add(idLabel,0,6);
        tokiInfo.add(new Label(tokimon.getName()), 1, 0);
        tokiInfo.add(new Label(Double.toString(tokimon.getWeight())), 1, 1);
        tokiInfo.add(new Label(Double.toString(tokimon.getHeight())), 1, 2);
        tokiInfo.add(new Label(tokimon.getAbility()), 1, 3);
        tokiInfo.add(new Label(Double.toString(tokimon.getStrength())), 1, 4);
        tokiInfo.add(new Label(tokimon.getColor()), 1, 5);
        tokiInfo.add(new Label(Long.toString(tokimon.getId())),1,6);
        tokiInfo.setHgap(30);
        tokiInfo.setVgap(30);
        tokiInfo.setPadding(new Insets(30,30,30,30));
        VBox verBox = new VBox(tokiInfo, returnHome);
        verBox.setSpacing(10);
        verBox.setAlignment(Pos.CENTER);
        mainStage.setScene(new Scene(verBox));
        mainStage.show();
    }

    private void deletedTokimon(Stage mainStage, String id){
        Text text = new Text("THE TOKIMON WITH THE ID " + id + " WAS DELETED");
        text.setFont(new Font("Arial", 50));
        ImageView img = new ImageView(new Image("file:image\\aku.jpg"));
        img.setFitHeight(500);
        img.setFitWidth(500);
        VBox deleted = new VBox(img, text, returnHome);
        deleted.setSpacing(10);
        deleted.setAlignment(Pos.CENTER);
        deleted.setPadding(new Insets(0,30,30,30));
        mainStage.setScene(new Scene(deleted));
        mainStage.show();
    }
}
