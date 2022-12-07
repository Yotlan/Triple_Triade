package fr.playcards.cardgame;

//Import part
import fr.playcards.cardgame.card.Card;
import fr.playcards.cardgame.card.FF8Card;
import fr.playcards.client.Client;
import fr.playcards.client.IClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.*;

public class FF8TripleTriadeController implements CardGameController{
    public String UUID;
    public CardGame game;
    @FXML
    public ImageView Player1_Card1 = new ImageView();
    @FXML
    public ImageView Player1_Card2 = new ImageView();
    @FXML
    public ImageView Player1_Card3 = new ImageView();
    @FXML
    public ImageView Player1_Card4 = new ImageView();
    @FXML
    public ImageView Player1_Card5 = new ImageView();
    @FXML
    public ImageView Player2_Card1 = new ImageView();
    @FXML
    public ImageView Player2_Card2 = new ImageView();
    @FXML
    public ImageView Player2_Card3 = new ImageView();
    @FXML
    public ImageView Player2_Card4 = new ImageView();
    @FXML
    public ImageView Player2_Card5 = new ImageView();
    @FXML
    public ImageView Empty_Card11 = new ImageView();
    @FXML
    public ImageView Empty_Card12 = new ImageView();
    @FXML
    public ImageView Empty_Card13 = new ImageView();
    @FXML
    public ImageView Empty_Card21 = new ImageView();
    @FXML
    public ImageView Empty_Card22 = new ImageView();
    @FXML
    public ImageView Empty_Card23 = new ImageView();
    @FXML
    public ImageView Empty_Card31 = new ImageView();
    @FXML
    public ImageView Empty_Card32 = new ImageView();
    @FXML
    public ImageView Empty_Card33 = new ImageView();
    @FXML
    public ImageView SelectedCard = new ImageView();
    @FXML
    public Card SelectedCardEntity = new FF8Card("N/A",0,0,0,0,"N/A");
    public int SelectedCardIndex=-1;
    @FXML
    public Label Player1_Pseudo = new Label("N/A");
    @FXML
    public Label Player2_Pseudo = new Label("N/A");
    @FXML
    public Label Message = new Label("");
    public IClient client;
    @FXML
    public Circle c11 = new Circle();
    @FXML
    public Circle c12 = new Circle();
    @FXML
    public Circle c13 = new Circle();
    @FXML
    public Circle c21 = new Circle();
    @FXML
    public Circle c22 = new Circle();
    @FXML
    public Circle c23 = new Circle();
    @FXML
    public Circle c31 = new Circle();
    @FXML
    public Circle c32 = new Circle();
    @FXML
    public Circle c33 = new Circle();

    public FF8TripleTriadeController(String UUID) {
        this.UUID = UUID;
    }

    @FXML
    public synchronized void initialize() {
        try{
            List<ImageView> EmptyCard = new ArrayList<>(Arrays.asList(Empty_Card11,Empty_Card12,Empty_Card13,Empty_Card21,Empty_Card22,Empty_Card23,Empty_Card31,Empty_Card32,Empty_Card33));
            for(int i=0; i<EmptyCard.size();i++){
                EmptyCard.get(i).setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
            }
            List<Card> P1Card = this.game.getPlayer1Card();
            List<ImageView> P1CardImage = new ArrayList<>(Arrays.asList(Player1_Card1,Player1_Card2,Player1_Card3,Player1_Card4,Player1_Card5));
            for(int i=0; i<P1Card.size();i++){
                Card card = P1Card.get(i);
                Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+card.getName()+".jpg").toFile().toURI().toString());
                P1CardImage.get(i).setImage(image);
            }
            this.client.getMainServer().initFF8GameClientList(this.game.getUUID(),this.client);
            Player1_Pseudo.setText(this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).get(0).getClientPseudo());
            List<Card> P2Card = this.game.getPlayer2Card();
            List<ImageView> P2CardImage = new ArrayList<>(Arrays.asList(Player2_Card1,Player2_Card2,Player2_Card3,Player2_Card4,Player2_Card5));
            for(int i=0; i<P2Card.size();i++){
                Card card = P2Card.get(i);
                Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+card.getName()+".jpg").toFile().toURI().toString());
                P2CardImage.get(i).setImage(image);
            }
            Platform.runLater(() -> {
                try {
                    if (this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).size() > 1) {
                        Player2_Pseudo.setText(this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).get(1).getClientPseudo());
                    }
                } catch (Exception e) {
                    System.out.println("FF8TripleTriadeController initialize method Error : "+e);
                }
            });


            this.client.getMainServer().initTurn(this.game.getUUID());
        }catch(Exception e){
            System.out.println("FF8TripleTriadeController initialize method Error : "+e);
        }
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                refresh();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void setGame(CardGame game, IClient client) {
        this.game = game;
        this.client = client;
    }

    public synchronized void refresh(){
        try {
            Player1_Pseudo.setText(this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).get(0).getClientPseudo());
            Platform.runLater(() -> {
                try {
                    if (this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).size() > 1) {
                        Player2_Pseudo.setText(this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).get(1).getClientPseudo());
                    }
                } catch (Exception e) {
                    System.out.println("FF8TripleTriadeController refresh method Error : "+e);
                }
            });
            List<Card> P1Card = this.game.getPlayer1Card();
            List<ImageView> P1CardImage = new ArrayList<>(Arrays.asList(Player1_Card1,Player1_Card2,Player1_Card3,Player1_Card4,Player1_Card5));
            for(int i=0; i<P1Card.size();i++){
                Card card = P1Card.get(i);
                if(card.getName().equals("Empty")){
                    Image image = new Image(Paths.get("../Triple_Triade/FF8/img/" + card.getName() + ".jpg").toFile().toURI().toString());
                    P1CardImage.get(i).setImage(image);
                }else {
                    Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + card.getName() + ".jpg").toFile().toURI().toString());
                    P1CardImage.get(i).setImage(image);
                }
            }
            List<Card> P2Card = this.game.getPlayer2Card();
            List<ImageView> P2CardImage = new ArrayList<>(Arrays.asList(Player2_Card1,Player2_Card2,Player2_Card3,Player2_Card4,Player2_Card5));
            for(int i=0; i<P2Card.size();i++){
                Card card = P2Card.get(i);
                if(card.getName().equals("Empty")){
                    Image image = new Image(Paths.get("../Triple_Triade/FF8/img/" + card.getName() + ".jpg").toFile().toURI().toString());
                    P2CardImage.get(i).setImage(image);
                }else {
                    Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + card.getName() + ".jpg").toFile().toURI().toString());
                    P2CardImage.get(i).setImage(image);
                }
            }
            Map<String, String> imageMap = this.client.getFF8CardName(this.UUID);
            Map<String, String> cardOwner = this.client.getFF8CardOwner(this.UUID);
            Platform.runLater(() -> {
                try {
                    boolean isEnd = true;
                    int P1Pawn = 0;
                    int P2Pawn = 0;
                    for (String owner : cardOwner.values()) {
                        if (owner == null) {
                            isEnd = false;
                        } else {
                            if (owner.equals(Player1_Pseudo.getText())) {
                                P1Pawn++;
                            } else {
                                P2Pawn++;
                            }
                        }
                    }
                    if (isEnd) {
                        List<Card> P1EndCard = this.game.getPlayer1Card();
                        List<Card> P2EndCard = this.game.getPlayer2Card();
                        for (Card card : P1EndCard) {
                            if (!(card.getName().equals("Empty"))) {
                                P1Pawn++;
                            }
                        }
                        for (Card card : P2EndCard) {
                            if (!(card.getName().equals("Empty"))) {
                                P2Pawn++;
                            }
                        }
                        if (P1Pawn > P2Pawn) {
                            showAlertWinning(Player1_Pseudo.getText(), Player2_Pseudo.getText(), P1Pawn, P2Pawn);
                        } else if (P2Pawn > P1Pawn) {
                            showAlertWinning(Player2_Pseudo.getText(), Player1_Pseudo.getText(), P2Pawn, P1Pawn);
                        } else {
                            showAlertDraw(Player1_Pseudo.getText(), Player2_Pseudo.getText(), P1Pawn, P2Pawn);
                        }
                    } else {
                        int turn = this.client.getMainServer().getTurn(this.game.getUUID());
                        if (turn == 1) {
                            Message.setText("It's " + Player1_Pseudo.getText() + "'s turn !");
                        } else {
                            Message.setText("It's " + Player2_Pseudo.getText() + "'s turn !");
                        }
                    }
                } catch(Exception e) {
                    System.out.println("FF8TripleTriadeController refresh method Error : " + e);
                }
            });
            if(imageMap.get("11")!=null){
                Empty_Card11.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("11")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("11").equals(Player1_Pseudo.getText())){
                    c11.setFill(Color.BLUE);
                }else{
                    c11.setFill(Color.RED);
                }
            }
            if(imageMap.get("21")!=null){
                Empty_Card21.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("21")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("21").equals(Player1_Pseudo.getText())){
                    c21.setFill(Color.BLUE);
                }else{
                    c21.setFill(Color.RED);
                }
            }
            if(imageMap.get("31")!=null){
                Empty_Card31.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("31")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("31").equals(Player1_Pseudo.getText())){
                    c31.setFill(Color.BLUE);
                }else{
                    c31.setFill(Color.RED);
                }
            }
            if(imageMap.get("12")!=null){
                Empty_Card12.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("12")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("12").equals(Player1_Pseudo.getText())){
                    c12.setFill(Color.BLUE);
                }else{
                    c12.setFill(Color.RED);
                }
            }
            if(imageMap.get("22")!=null){
                Empty_Card22.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("22")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("22").equals(Player1_Pseudo.getText())){
                    c22.setFill(Color.BLUE);
                }else{
                    c22.setFill(Color.RED);
                }
            }
            if(imageMap.get("32")!=null){
                Empty_Card32.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("32")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("32").equals(Player1_Pseudo.getText())){
                    c32.setFill(Color.BLUE);
                }else{
                    c32.setFill(Color.RED);
                }
            }
            if(imageMap.get("13")!=null){
                Empty_Card13.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("13")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("13").equals(Player1_Pseudo.getText())){
                    c13.setFill(Color.BLUE);
                }else{
                    c13.setFill(Color.RED);
                }
            }
            if(imageMap.get("23")!=null){
                Empty_Card23.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("23")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("23").equals(Player1_Pseudo.getText())){
                    c23.setFill(Color.BLUE);
                }else{
                    c23.setFill(Color.RED);
                }
            }
            if(imageMap.get("33")!=null){
                Empty_Card33.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("33")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("33").equals(Player1_Pseudo.getText())){
                    c33.setFill(Color.BLUE);
                }else{
                    c33.setFill(Color.RED);
                }
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController refresh method Error : "+e);
        }
    }

    private void showAlertSelect() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText("You can't select this card because you already play it or it's not your card !");
        alert.showAndWait();
    }

    private void showAlertDisplay() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText("You can't display this card here because someone else display a card here or it's not your turn !");
        alert.showAndWait();
    }

    private void showAlertWinning(String winner, String looser, int nbWinPawn, int nbLoosePawn) {
        Message.setText("Congratulation to "+winner+" ! He's the winner of this game ! He win against "+looser+" with a score of "+Integer.toString(nbWinPawn)+"-"+Integer.toString(nbLoosePawn)+" !");
    }

    private void showAlertDraw(String winner, String looser, int nbWinPawn, int nbLoosePawn) {
        Message.setText("Congratulation to both "+winner+" and "+looser+" ! It's a draw ! The score is "+Integer.toString(nbWinPawn)+"-"+Integer.toString(nbLoosePawn)+" !");
    }

    public void P1SelectC1(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())
                    && !(this.game.getPlayer1Card().get(0).getName().equals("Empty"))
            ){
                this.SelectedCard = Player1_Card1;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(0);
                this.SelectedCardIndex = 0;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC1 method Error : "+e);
        }
    }

    public void P1SelectC2(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())
                    && !(this.game.getPlayer1Card().get(1).getName().equals("Empty"))
            ){
                this.SelectedCard = Player1_Card2;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(1);
                this.SelectedCardIndex = 1;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC2 method Error : "+e);
        }
    }

    public void P1SelectC3(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())
                    && !(this.game.getPlayer1Card().get(2).getName().equals("Empty"))
            ){
                this.SelectedCard = Player1_Card3;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(2);
                this.SelectedCardIndex = 2;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC3 method Error : "+e);
        }
    }

    public void P1SelectC4(){

        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())
                    && !(this.game.getPlayer1Card().get(3).getName().equals("Empty"))
            ){
                this.SelectedCard = Player1_Card4;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(3);
                this.SelectedCardIndex = 3;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC4 method Error : "+e);
        }
    }

    public void P1SelectC5(){
        try {
            if(this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())
                    && !(this.game.getPlayer1Card().get(4).getName().equals("Empty"))
            ){
                this.SelectedCard = Player1_Card5;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(4);
                this.SelectedCardIndex = 4;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC5 method Error : "+e);
        }
    }

    public void P2SelectC1(){
        try {
            if(this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())
                    && !(this.game.getPlayer2Card().get(0).getName().equals("Empty"))
            ){
                this.SelectedCard = Player2_Card1;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(0);
                this.SelectedCardIndex = 0;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC1 method Error : "+e);
        }
    }

    public void P2SelectC2(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())
                    && !(this.game.getPlayer2Card().get(1).getName().equals("Empty"))
            ){
                this.SelectedCard = Player2_Card2;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(1);
                this.SelectedCardIndex = 1;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC2 method Error : "+e);
        }
    }

    public void P2SelectC3(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())
                    && !(this.game.getPlayer2Card().get(2).getName().equals("Empty"))
            ){
                this.SelectedCard = Player2_Card3;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(2);
                this.SelectedCardIndex = 2;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC3 method Error : "+e);
        }
    }

    public void P2SelectC4(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())
                    && !(this.game.getPlayer2Card().get(3).getName().equals("Empty"))
            ){
                this.SelectedCard = Player2_Card4;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(3);
                this.SelectedCardIndex = 3;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC4 method Error : "+e);
        }
    }

    public void P2SelectC5(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())
                    && !(this.game.getPlayer2Card().get(4).getName().equals("Empty"))
            ){
                this.SelectedCard = Player2_Card5;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(4);
                this.SelectedCardIndex = 4;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC5 method Error : "+e);
        }
    }

    public void displayC11(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8CardName(this.UUID).get("11") == null)
                    && (playerID==this.client.getMainServer().getTurn(this.game.getUUID()))
            ){
                this.Empty_Card11.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard11(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }
                this.SelectedCard = null;
                this.flipRule("11");
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC11 method Error : "+e);
        }
    }

    public void displayC21(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID = 2;
            }
            if ((this.client.getFF8CardName(this.UUID).get("21") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){
                this.Empty_Card21.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard21(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }
                this.SelectedCard = null;
                this.flipRule("21");
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC21 method Error : "+e);
        }
    }

    public void displayC31(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8CardName(this.UUID).get("31") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){
                this.Empty_Card31.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard31(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }
                this.SelectedCard = null;
                this.flipRule("31");
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC31 method Error : "+e);
        }
    }

    public void displayC12(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8CardName(this.UUID).get("12") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){
                this.Empty_Card12.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard12(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }
                this.SelectedCard = null;
                this.flipRule("12");
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC12 method Error : "+e);
        }
    }

    public void displayC22(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8CardName(this.UUID).get("22") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){
                this.Empty_Card22.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard22(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }
                this.SelectedCard = null;
                this.flipRule("22");
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC22 method Error : "+e);
        }
    }

    public void displayC32(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if((this.client.getFF8CardName(this.UUID).get("32") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){
                this.Empty_Card32.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard32(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }
                this.SelectedCard = null;
                this.flipRule("32");
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC32 method Error : "+e);
        }
    }

    public void displayC13(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8CardName(this.UUID).get("13") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){
                this.Empty_Card13.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard13(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }
                this.SelectedCard = null;
                this.flipRule("13");
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC13 method Error : "+e);
        }
    }

    public void displayC23(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8CardName(this.UUID).get("23") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){
                this.Empty_Card23.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard23(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }
                this.SelectedCard = null;
                this.flipRule("23");
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC23 method Error : "+e);
        }
    }

    public void displayC33(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8CardName(this.UUID).get("33") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){
                this.Empty_Card33.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard33(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }
                this.SelectedCard = null;
                this.flipRule("33");
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC33 method Error : "+e);
        }
    }

    public synchronized void flipRule(String position) throws RemoteException {
        boolean turnRight=false;
        boolean turnDown=false;
        boolean turnLeft=false;
        boolean turnTop=false;
        int x;
        int y;
        int positionNumeric_x = Integer.parseInt(String.valueOf(position.charAt(0)));
        int positionNumeric_y = Integer.parseInt(String.valueOf(position.charAt(1)));
        //rightable
        if (positionNumeric_y<3){
            turnRight=this.checkRight(positionNumeric_x,positionNumeric_y);
        }
        //leftable
        if (positionNumeric_y>1){
            turnLeft=this.checkLeft(positionNumeric_x,positionNumeric_y);
        }
        //toptable
        if (positionNumeric_x>1){
            turnTop=this.checkTop(positionNumeric_x,positionNumeric_y);
        }
        //downtable
        if (positionNumeric_x<3){
            turnDown=this.checkDown(positionNumeric_x,positionNumeric_y);
        }

        if (turnRight || turnLeft || turnDown || turnTop){
            if (turnRight){
                x = Integer.parseInt(String.valueOf(position.charAt(0)));
                y = Integer.parseInt(String.valueOf(position.charAt(1)))+1;
                flipRule(Integer.toString(x).concat(Integer.toString(y)));
                this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) +Integer.toString((y)),this.client.getClientPseudo());
            }
            if (turnLeft){
                x = Integer.parseInt(String.valueOf(position.charAt(0)) );
                y = Integer.parseInt(String.valueOf(position.charAt(1)))-1;
                flipRule(Integer.toString(x).concat(Integer.toString(y)));
                this.client.getMainServer().setFF8CardOwner(this.game.getUUID(),Integer.toString(x) +Integer.toString((y)),this.client.getClientPseudo());
            }
            if (turnTop){
                x = Integer.parseInt(String.valueOf(position.charAt(0)))-1;
                y = Integer.parseInt(String.valueOf(position.charAt(1)));
                flipRule(Integer.toString(x).concat(Integer.toString(y)));
                this.client.getMainServer().setFF8CardOwner(this.game.getUUID(),Integer.toString(x) +Integer.toString((y)),this.client.getClientPseudo());
            }
            if (turnDown){
                x = Integer.parseInt(String.valueOf(position.charAt(0)))+1;
                y = Integer.parseInt(String.valueOf(position.charAt(1)));
                flipRule(Integer.toString(x).concat(Integer.toString(y)));
                this.client.getMainServer().setFF8CardOwner(this.game.getUUID(),Integer.toString(x) +Integer.toString((y)),this.client.getClientPseudo());
            }
        }

    }

    public boolean checkRight(int positionX,int positionY) throws RemoteException {
        int cardValueRight=this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
        int x = positionX;
        int y = positionY+1;
        String key=Integer.toString(x).concat(Integer.toString(y));
        if (this.client.getFF8CardLeft(this.game.getUUID()).get(key)!=null){
            int cardValueLeft=this.client.getFF8CardLeft(this.game.getUUID()).get(key);
            if (!this.client.getFF8CardOwner(this.game.getUUID()).get(key).equals(this.client.getClientPseudo())){
                if (cardValueRight > cardValueLeft){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean checkTop(int positionX,int positionY) throws RemoteException {
        int cardValueTop=this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
        int x = positionX-1;
        int y = positionY;
        String key=Integer.toString(x).concat(Integer.toString(y));
        if (this.client.getFF8CardDown(this.game.getUUID()).get(key)!=null){
            int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(key);
            if (!this.client.getFF8CardOwner(this.game.getUUID()).get(key).equals(this.client.getClientPseudo())){
                if (cardValueTop > cardValueDown){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean checkLeft(int positionX,int positionY) throws RemoteException {
        int cardValueLeft=this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
        int x = positionX;
        int y = positionY-1;
        String key=Integer.toString(x).concat(Integer.toString(y));
        if (this.client.getFF8CardRight(this.game.getUUID()).get(key)!=null){
            int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(key);
            if (!this.client.getFF8CardOwner(this.game.getUUID()).get(key).equals(this.client.getClientPseudo())){
                if (cardValueLeft > cardValueRight){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean checkDown(int positionX, int positionY) throws RemoteException {
        int cardValueDown=this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
        int x = positionX+1;
        int y = positionY;
        String key = Integer.toString(x).concat(Integer.toString(y));
        if (this.client.getFF8CardUp(this.game.getUUID()).get(key)!=null){
            int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(key);
            if(!this.client.getFF8CardOwner(this.game.getUUID()).get(key).equals(this.client.getClientPseudo())) {
                if (cardValueDown > cardValueTop){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

}
