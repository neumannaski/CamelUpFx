package com.example.camelupfx;

import de.ostfalia.prog.s22ws.base.Filemanager;
import de.ostfalia.prog.s22ws.model.*;
import de.ostfalia.prog.s22ws.model.exceptions.UngueltigerZugException;
import de.ostfalia.prog.s22ws.model.felder.Plaettchen;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CamelUpController implements Initializable {

    Parent root;
    public GridPane fieldlayout;
    public ImageView feld5x1;
    public ImageView feld1x1;
    public ImageView feld2x1;
    public ImageView feld3x1;
    public ImageView feld4x1;
    public ImageView feld1x2;
    public ImageView feld5x2;
    public ImageView feld4x2;
    public ImageView feld3x2;
    public ImageView feld2x2;
    public ImageView feld1x3;
    public ImageView feld2x3;
    public ImageView feld3x3;
    public ImageView feld4x3;
    public ImageView feld5x3;
    public ImageView feld1x4;
    public ImageView feld2x4;
    public ImageView feld3x4;
    public ImageView feld4x4;
    public ImageView feld5x4;
    public ImageView feld1x5;
    public ImageView feld5x5;
    public ImageView feld4x5;
    public ImageView feld2x5;
    public ImageView feld3x5;
    public ImageView feld1x6;
    public ImageView feld2x6;
    public ImageView feld3x6;
    public ImageView feld4x6;
    public ImageView feld5x6;
    public ImageView feld1x7;
    public ImageView feld2x7;
    public ImageView feld3x7;
    public ImageView feld4x7;
    public ImageView feld5x7;
    public ImageView feld1x8;
    public ImageView feld2x8;
    public ImageView feld3x8;
    public ImageView feld4x9;
    public ImageView feld5x8;
    public ImageView feld4x8;
    public ImageView feld1x9;
    public ImageView feld2x9;
    public ImageView feld3x9;
    public ImageView feld5x9;
    public ImageView feld1x10;
    public ImageView feld2x10;
    public ImageView feld3x10;
    public ImageView feld4x10;
    public ImageView feld5x10;
    public ImageView feld1x11;
    public ImageView feld2x11;
    public ImageView feld3x11;
    public ImageView feld4x11;
    public ImageView feld5x11;
    public ImageView feld1x12;
    public ImageView feld2x12;
    public ImageView feld4x12;
    public ImageView feld3x12;
    public ImageView feld5x12;
    public ImageView feld1x13;
    public ImageView feld2x13;
    public ImageView feld3x13;
    public ImageView feld4x13;
    public ImageView feld5x13;
    public ImageView feld1x14;
    public ImageView feld3x14;
    public ImageView feld2x14;
    public ImageView feld4x14;
    public ImageView feld5x14;
    public ImageView feld1x15;
    public ImageView feld2x15;
    public ImageView feld3x15;
    public ImageView feld4x15;
    public ImageView feld5x15;
    public ImageView feld1x17;
    public ImageView feld1x16;
    public ImageView feld2x16;
    public ImageView feld3x16;
    public ImageView feld4x16;
    public ImageView feld5x16;
    public ImageView feld2x17;
    public ImageView feld3x17;
    public ImageView feld4x17;
    public ImageView feld5x17;
    public ImageView finish;
    public Label status;
    public Label lblName;
    public Label lblCharakter;
    public ImageView charakterPic;
    public Label lblMoney;
    public ImageView background;
    public TextArea field1;
    public TextArea field2;
    public TextArea field3;
    public TextArea field4;
    public TextArea field5;
    public TextArea field6;
    public TextArea field7;
    public TextArea field8;
    public TextArea field9;
    public TextArea field10;
    public TextArea field11;
    public TextArea field12;
    public TextArea field13;
    public TextArea field14;
    public TextArea field15;
    public TextArea field16;
    public Button btnOase;
    public Button btnMorgana;
    public Button btnWuerfen;
    public Button btnWueste;
    public Button btnEWette;
    public Button btnOllesK;
    public Button btnTollesK;
    public Label lblPyramide;
    ImageView [][] feld;
    TextArea [] tileArray;
    Spieler spieler;
    Boolean setplaetchen=false;
    CamelUp game=HelloApplication.game;
    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        togglePlaetchen();
        try {
            Image backgroundimage=new Image(new FileInputStream(Filemanager.path+"Hintergrund.jpg"));
            background.setImage(backgroundimage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        spieler=game.getCurrentSpieler();
        game.startPosition();

        try {
            Image image= new Image(new FileInputStream(Filemanager.path+"karoschwarzweissflagge.jpg"));
            finish.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView[][] field={
                {feld1x1,feld2x1,feld3x1,feld4x1,feld5x1},
                {feld1x2,feld2x2,feld3x2,feld4x2,feld5x2},
                {feld1x3,feld2x3,feld3x3,feld4x3,feld5x3},
                {feld1x4,feld2x4,feld3x4,feld4x4,feld5x4},
                {feld1x5,feld2x5,feld3x5,feld4x5,feld5x5},
                {feld1x6,feld2x6,feld3x6,feld4x6,feld5x6},
                {feld1x7,feld2x7,feld3x7,feld4x7,feld5x7},
                {feld1x8,feld2x8,feld3x8,feld4x8,feld5x8},
                {feld1x9,feld2x9,feld3x9,feld4x9,feld5x9},
                {feld1x10,feld2x10,feld3x10,feld4x10,feld5x10},
                {feld1x11,feld2x11,feld3x11,feld4x11,feld5x11},
                {feld1x12,feld2x12,feld3x12,feld4x12,feld5x12},
                {feld1x13,feld2x13,feld3x13,feld4x13,feld5x13},
                {feld1x14,feld2x14,feld3x14,feld4x14,feld5x14},
                {feld1x15,feld2x15,feld3x15,feld4x15,feld5x15},
                {feld1x16,feld2x16,feld3x16,feld4x16,feld5x16},
                {feld1x17,feld2x17,feld3x17,feld4x17,feld5x17}
        };
        tileArray= new TextArea[]{
                field1,
                field2,
                field3,
                field4,
                field5,
                field6,
                field7,
                field8,
                field9,
                field10,
                field11,
                field12,
                field13,
                field14,
                field15,
                field16,
        };
        feld=field;
        try{
            showCamels();
        }catch (FileNotFoundException e){
            System.out.println("error");
        }
        updatePlayer();
    }

    public void ondice(){
        status.setText(game.bewegeKamel(spieler.getCharakter()));
        lblPyramide.setText("Die Pyramide enthält noch "+Pyramide.getInstance().anzahlWuerfel()+" Würfel");
        try{
            showCamels();
        }catch (FileNotFoundException e){
            System.out.println("error");
        }
        if(game.isGameEnd()){
            gameEnd();
        }
        updatePlayer();
    }

    private void gameEnd(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Das Spiel ist vorbei");
        alert.setHeaderText(null);
        alert.setContentText(game.spielAuswertung());
        alert.showAndWait();
        openNewGame(new ActionEvent());
    }

    private void updatePlayer(){
        spieler=game.getNextSpieler();
        lblCharakter.setText(spieler.getCharakter().toString());
        lblMoney.setText("Guthaben: "+spieler.getMoney()+" AP");
        lblName.setText("Name: "+spieler.getName());
        charakterPic.setImage(spieler.getCharakter().getImage());

    }

    private void showCamels() throws FileNotFoundException {
        clearField();
        int col=0;
        for (List<IStandable> list:game.toList()) {
            int row=0;
            for (IStandable standable:list) {
                System.out.println(standable.toString());
                feld[col-1][4-row].setImage((standable).getImage());
                row++;
            }
            col++;
        }
        int row=0;
        for (IStandable iStandable:game.getFinishline().toList()) {
            feld[16][4-row].setImage(iStandable.getImage());
            row++;
        }
       System.out.println(game);
    }

    private Node getNode(int col,int row){
        for (Node node:fieldlayout.getChildren()) {
            if(GridPane.getColumnIndex(node)==col&& GridPane.getRowIndex(node)==row) {
                return node;
            }
        }
        return null;
    }

    private void clearField(){
        for (int i=0;i<16;i++){
            for (int j=0;j<5;j++){
                feld[i][j].setImage(null);
            }
        }
    }

    public void clickfield(MouseEvent mouseEvent) {
        if (setplaetchen) {
            Node node = (Node) mouseEvent.getSource();
            String data = (String) node.getUserData();
            int value = Integer.parseInt(data);
            try {
                game.legeWuestenplaettchen(spieler.getCharakter(), value, spieler.getCharakter().getPlaettchen().getPlaettchen());
                status.setText("Wüstenplättchen auf Feld " + (value) + " gelegt");
                setplaetchen=false;
            } catch (UngueltigerZugException e) {
                status.setText("Error! Ungültiges Feld");
                e.printStackTrace();
                return;
            }
            try {
                showCamels();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            toggleButtons();
            updatePlayer();
            System.out.println(value);
        }
    }

    public void selectField(MouseEvent mouseEvent) {
    }

    public void placeDes(ActionEvent actionEvent) {
        togglePlaetchen();
        toggleButtons();
    }

    private String format(Color c) {
        int r = (int) (255 * c.getRed());
        int g = (int) (255 * c.getGreen());
        int b = (int) (255 * c.getBlue());
        return String.format("#%02x%02x%02x", r, g, b);
    }

    private void checkDes(Plaettchen plaettchen){
        spieler.getCharakter().getPlaettchen().setPlaettchen( plaettchen);
    }




    private void togglePlaetchen(){
        btnMorgana.setVisible(!btnMorgana.isVisible());
        btnOase.setVisible(!btnOase.isVisible());
    }

    public void btnOase(ActionEvent actionEvent) {
        setplaetchen=true;
        checkDes(Plaettchen.OASE);
        togglePlaetchen();
    }

    public void btnFata(ActionEvent actionEvent) {
        setplaetchen=true;
        checkDes(Plaettchen.FATAMORGANA);
        togglePlaetchen();
    }

    private void toggleButtons(){
        btnWuerfen.setDisable(!btnWuerfen.isDisabled());
        btnEWette.setDisable(!btnEWette.isDisabled());
        btnTollesK.setDisable(!btnTollesK.isDisabled());
        btnOllesK.setDisable(!btnOllesK.isDisabled());
        btnWueste.setDisable(!btnWueste.isDisabled());
    }

    private void openNewGame(ActionEvent actionEvent)  {
        Stage stage = (Stage) btnWueste.getScene().getWindow();
        stage.close();
    }


}
