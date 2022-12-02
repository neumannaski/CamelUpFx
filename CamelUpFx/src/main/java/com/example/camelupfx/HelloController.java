package com.example.camelupfx;

import de.ostfalia.prog.s22ws.base.Charakter;
import de.ostfalia.prog.s22ws.model.CamelUp;
import de.ostfalia.prog.s22ws.model.Spieler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class HelloController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public ComboBox<String> charakterList;
    @FXML
    public TextField playerName;
    @FXML
    public Button AddPlayer;
    @FXML
    public ListView<String> playerOverview;
    public Button gameStart;
    @FXML
    private Label welcomeText;
    CamelUp game=HelloApplication.game;


    ObservableList<String> list= FXCollections.observableArrayList();


    @FXML
    protected void onHelloButtonClick() {
        if(charakterList.getSelectionModel().getSelectedItem()!=null&&!playerName.getText().equals("")){
            String charakter = charakterList.getSelectionModel().getSelectedItem();
            String name=playerName.getText();
            game.spielerlistAdd(new Spieler(name, Charakter.valueOf(charakter)));
            game.getCharakterlist().remove(Charakter.valueOf(charakter));
            updateCharakterlist();
            playerName.clear();
            welcomeText.setText("Spieler hinzugefügt: "+ name+" "+charakter);
            setPlayerOverview();

            if(game.getSpielerlist().size()>=2){
                gameStart.setDisable(false);
            }
        }else {
            welcomeText.setText("Error Spieler nicht hinzugefügt");
        }
    }

    @FXML
    protected void onGameStart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CamelUp.fxml")));
        stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        //stage.setFullScreen(true);
        stage.setTitle("CamelUp");
        stage.show();
    }

    private void setPlayerOverview(){
        ObservableList<String> listPlayer= FXCollections.observableArrayList();
        for (Spieler s:game.getSpielerlist()) {
            listPlayer.add(s.getName()+" "+s.getCharakter());
        }
        playerOverview.setItems(listPlayer);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCharakterlist();
        gameStart.setDisable(true);

    }

    private void updateCharakterlist(){
        list.clear();
        for (Charakter s: HelloApplication.game.getCharakterlist()) {
            list.add(s.toString());
        }
        charakterList.setItems(list);
    }

    private void updateCurrentSpieler(){

    }
}