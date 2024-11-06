package sample;


import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class Controller {
    Image closed = new Image("resources/BankLocker.png");
    Image item1 = new Image("resources/i1.jpeg");
    Image item2 = new Image("resources/i2.jpeg");
    Image item3 = new Image("resources/i3.jpeg");
    Image item4 = new Image("resources/i4.jpeg");
    Image item5 = new Image("resources/i5.jpeg");
    Image item6 = new Image("resources/i6.jpeg");
    Image item7 = new Image("resources/i7.jpeg");
    Image item8 = new Image("resources/i8.jpeg");
    Image item9 = new Image("resources/i9.jpeg");
    Image item10 = new Image("resources/i10.jpeg");
    Image item11 = new Image("resources/i11.jpeg");
    Image item12 = new Image("resources/i12.jpeg");
    Image item13 = new Image("resources/i13.jpeg");
    Image item14 = new Image("resources/i14.jpeg");
    Image item15 = new Image("resources/i15.jpeg");
    Image item16 = new Image("resources/i16.jpeg");
    Image item17 = new Image("resources/i17.jpeg");
    Image item18 = new Image("resources/i18.jpeg");
    Image item19 = new Image("resources/i19.jpeg");
    Image item20 = new Image("resources/i20.jpeg");
    Image pCase = new Image("resources/playerCase.jpeg");

    @FXML
    Label lblTitle, lblPrice, lblMoney, lblRound, lblGoal, lblTPrice;
    @FXML
    GridPane gdpPlayGrid;
    @FXML
    ListView lstItems, lstStats, lstShop;
    @FXML
    TextField txtInput;
    @FXML
    Button btnEnter, btnAccept, btnReject, btnStats;

    private ImageView[][] boardSpotsIMG = new ImageView[4][5];
    private Locker[][] board = new Locker[4][5];
    private Integer[][] openedLockers = new Integer[4][5];
    private ArrayList<Locker> lockers = new ArrayList<>();
    private ArrayList<Integer> values = new ArrayList<>();

    int rowIndex, colIndex, pRow, pCol, bankAccount = 100000, numOpen, numOpenR, numAccept, numAcceptR, numReject, numRejectR, highestOffer, highestOfferR, lowestOffer, lowestOfferR, round, clicks, l1x, l1y, l2x, l2y, guessVal,
            shop1p = 1200, shop2p = 1000, shop3p = 750, shop4p = 500, shop5p = 250, monSpent, monSpentR, typeStat;
    Locker playerCase, lock1, lock2;
    boolean choseCase, clickable = true, roundEnd, twoFold, doubleT, lastLocker, setZero, endGame, negotiate, lastOffer;
    Offer bob = new Offer(values);

    //Sets up game
    @FXML
    public void initialize() {
        setLockers();
        updateList();
        round++;
        lstStats.getItems().clear();
        lstShop.getItems().clear();
        lstItems.getItems().clear();
        lstStats.getItems().add("Choose your locker");
        lstShop.getItems().add("Choose your locker");
        txtInput.setPromptText("Input Locker Here");
        lblGoal.setText("$" + NumberFormat.getInstance(Locale.US).format(shop5p*2.2));
        btnAccept.setVisible(false);
        btnReject.setVisible(false);

        for (int i = 0; i < boardSpotsIMG.length; i++) {
            for (int j = 0; j < boardSpotsIMG[i].length; j++) {
                boardSpotsIMG[i][j] = new ImageView();
                boardSpotsIMG[i][j].setImage(closed);
                boardSpotsIMG[i][j].setFitHeight(175);
                boardSpotsIMG[i][j].setFitWidth(183);
                openedLockers[i][j] = 0;
                gdpPlayGrid.add(boardSpotsIMG[i][j], j, i);
            }
        }

        //Actions for lockers being opened
        EventHandler z = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                boolean alreadyOpen = false;
                rowIndex = GridPane.getRowIndex(((ImageView) t.getSource()));
                colIndex = GridPane.getColumnIndex(((ImageView) t.getSource()));

                if ((board[rowIndex][colIndex] != null && choseCase && clickable)){
                    lstStats.getItems().clear();
                    boardSpotsIMG[rowIndex][colIndex].setImage(board[rowIndex][colIndex].getShowImage());
                    lblTitle.setText("Price Of Locker:");
                    lblPrice.setText("$" + NumberFormat.getInstance(Locale.US).format(board[rowIndex][colIndex].getValue()));

                    if(openedLockers[rowIndex][colIndex]==1){
                        alreadyOpen = true;
                    }

                    for (int i = values.size() - 1; i >= 0; i--) {
                        if (board[rowIndex][colIndex].getValue() == values.get(i)) {
                            values.remove(i);
                        }
                    }
                    if (openedLockers[rowIndex][colIndex] != 1 && openedLockers[rowIndex][colIndex] != 2 && !twoFold) {
                        numOpen++;
                        numOpenR++;
                    }


                    if(twoFold){
                        clicks++;
                        if(clicks == 1){
                            lock1 = board[rowIndex][colIndex];
                            l1x = rowIndex;
                            l1y = colIndex;
                        }
                        else if(clicks == 2){
                            lock2 = board[rowIndex][colIndex];
                            l2x = rowIndex;
                            l2y = colIndex;
                            closeLockers();
                            boardSpotsIMG[l1x][l1y].setImage(lock1.getShowImage());
                            boardSpotsIMG[l2x][l2y].setImage(lock2.getShowImage());
                            lblTitle.setText("Choose a Locker To Put Back");
                        }
                        else if(clicks == 3){
                            if(openedLockers[rowIndex][colIndex]!=0) {
                                openLockers();
                                setZero = true;
                                boardSpotsIMG[rowIndex][colIndex].setImage(closed);
                                numOpen++;
                                clicks = 0;
                                twoFold = false;
                                lblTitle.setText("Accept or Negotiate");
                                lblPrice.setVisible(true);
                            }
                            else{
                                lblTitle.setText("Click an Open Locker");
                                lblPrice.setVisible(false);
                                boardSpotsIMG[rowIndex][colIndex].setImage(closed);
                                setZero = true;
                                values.add(board[rowIndex][colIndex].getValue());
                                clicks = 2;
                            }
                        }
                    }

                    if(doubleT) {
                        if(guessVal == board[rowIndex][colIndex].getValue()){
                            lblTitle.setText("You Guessed Right!");
                            bankAccount+=(guessVal/2);
                            lblMoney.setText("$" + bankAccount);
                            doubleT = false;
                        }
                    }

                    openedLockers[rowIndex][colIndex] = 1;

                    if(setZero){
                        openedLockers[rowIndex][colIndex] = 0;
                        setZero = false;
                    }
                    updateList();
                    gameStats();

                } else if (!choseCase) {
                    lblPrice.setText("Must Choose Your Case First");
                }

                if (allOpened()) {
                    lastLocker = true;
                    playerCase.resetFlipLockerTime();
                    lblTPrice.setText("Actual Cash: \n $" + NumberFormat.getInstance(Locale.US).format((playerCase.getValue())/3));
                    timeEvents();
                }

                if(numOpen % 4 == 0 && numOpen!=0 && !alreadyOpen && negotiate){
                    negotiateOffer();
                }
                else if(numOpen == 18){
                    lastOffer = true;
                    offerSetUp();
                }
                else if (numOpen % 4 == 0 && numOpen!=0 && !alreadyOpen && numOpen>7) {
                    offerSetUp();
                }
            }
        };

        for (int i = 0; i < boardSpotsIMG.length; i++) {
            for (int j = 0; j < boardSpotsIMG[i].length; j++) {
                boardSpotsIMG[i][j].setOnMouseClicked(z);
            }
        }

    }

    //Items to buy from the shop
    @FXML
    public void shop(){
        int itemChosen = lstShop.getSelectionModel().getSelectedIndex();

            if(endGame){
                if(itemChosen == 1){
                    resetGame();
                }
                else if(itemChosen == 2){
                    lstShop.getItems().clear();
                }
            }
            else {
                //Retry
                if (itemChosen == 0 && bankAccount>=shop1p) {
                    bankAccount -= shop1p;
                    monSpent+= shop1p;
                    lblMoney.setText("$" + bankAccount);
                    boardSpotsIMG[rowIndex][colIndex].setImage(closed);
                    openedLockers[rowIndex][colIndex] = 0;
                    numOpen--;
                    values.add(board[rowIndex][colIndex].getValue());
                    shuffle();
                }
                //TwoFold
                else if (itemChosen == 2 && bankAccount>=shop2p) {
                    twoFold = true;
                    monSpent+= shop2p;
                    bankAccount -= shop2p;
                    lblMoney.setText("$" + bankAccount);
                    lblTitle.setText("Choose 2 Cases");
                    lblPrice.setText("");
                }

                //Extra Offer
                else if (itemChosen == 4 && bankAccount>=shop3p) {
                    bankAccount -= shop3p;
                    monSpent+= shop3p;
                    lblMoney.setText("$" + bankAccount);
                    lblPrice.setText("");
                    offerSetUp();
                }

                //Double or Trouble
                else if (itemChosen == 6 && bankAccount>=shop4p) {
                    bankAccount -= shop4p;
                    monSpent+= shop4p;
                    lblMoney.setText("$" + bankAccount);
                    lblPrice.setText("");
                    doubleT = true;
                    doubleTrouble();
                }

                //Negotiate
                else if (itemChosen == 8 && bankAccount>=shop5p) {
                    bankAccount -= shop5p;
                    monSpent+= shop5p;
                    lblMoney.setText("$" + bankAccount);
                    lblTitle.setText("Negotiate Bought");
                    lblPrice.setText("");
                    negotiate = true;
                }
                else{
                    lblPrice.setText("Not enough Money");
                }
            }
    }

    //Sets up some visuals
    public void negotiateOffer(){
        clickable = false;
        txtInput.setVisible(true);
        btnEnter.setVisible(true);
        txtInput.setPromptText("Enter Your Price");
    }

    //Adds lockers on to the board
    public void setLockers() {
        lockers.add(new Locker(item1, 1000000));
        lockers.add(new Locker(item2, 1000));
        lockers.add(new Locker(item3, 200));
        lockers.add(new Locker(item4, 50000));
        lockers.add(new Locker(item5, 500000));
        lockers.add(new Locker(item6, 750000));
        lockers.add(new Locker(item7, 500));
        lockers.add(new Locker(item8, 750));
        lockers.add(new Locker(item9, 5000));
        lockers.add(new Locker(item10, 1));
        lockers.add(new Locker(item11, 50));
        lockers.add(new Locker(item12, 100));
        lockers.add(new Locker(item13, 300));
        lockers.add(new Locker(item14, 400));
        lockers.add(new Locker(item15, 3000));
        lockers.add(new Locker(item16, 100000));
        lockers.add(new Locker(item17, 75));
        lockers.add(new Locker(item18, 7500));
        lockers.add(new Locker(item19, 4000));
        lockers.add(new Locker(item20, 2000));


        for (Locker i : lockers) {
            values.add(i.getValue());
        }

        for (int counter = 0; counter < 10000; counter++) {
            int index = (int) (Math.random() * lockers.size());
            int randPosition = (int) (Math.random() * lockers.size());
            Locker temp = lockers.get(index);
            Locker temp2 = lockers.get(randPosition);
            lockers.set(randPosition, temp);
            lockers.set(index, temp2);
        }

        for (int i = 0; i < 4; i++) {
            for (int z = 0; z < 5; z++) {
                board[i][z] = lockers.get(0);
                lockers.remove(0);
            }
        }
    }

    //Sets up double or trouble power-up
    public void doubleTrouble(){
        lblTitle.setText("What Case Is Next?");
        lblPrice.setText("Input value into text field");
        txtInput.setVisible(true);
        btnEnter.setVisible(true);
        txtInput.setPromptText("Input Value");
    }

    //Updates items listview
    public void updateList() {
        lstItems.getItems().clear();
        Collections.sort(values);
        for (int va : values) {
            lstItems.getItems().add("$" + NumberFormat.getInstance(Locale.US).format(va));
        }
    }

    //Chooses player case and does everything for the enter button
    public void enterButtonActions() {
        if(doubleT) {
            guessVal = Integer.parseInt(txtInput.getText());
            txtInput.clear();
            txtInput.setVisible(false);
            btnEnter.setVisible(false);
            doubleT = false;
        }
        else if(negotiate){
            int negotiatePrice = Integer.parseInt(txtInput.getText());
            txtInput.clear();
            int bobPrice = (bob.getOffer())+2000;
            clickable = true;
            if(negotiatePrice<bobPrice){
                lblTitle.setText("Offer Accepted");
                bob.setNegotiation(negotiatePrice);
                accept();
            }
            else{
                lblTitle.setText("Offer Rejected");
                negotiate = false;
            }
            txtInput.setVisible(false);
            btnEnter.setVisible(false);
        }
        else {
            int Case = Integer.parseInt(txtInput.getText());
            if (Case != 20) {
                pRow = (Case - 1) / 5;
                pCol = (Case - 1) % 5;
                boardSpotsIMG[pRow][pCol].setImage(pCase);
                playerCase = board[pRow][pCol];
                board[pRow][pCol] = null;
                openedLockers[pRow][pCol] = 2;
                txtInput.clear();
                lstStats.getItems().clear();
                lstShop.getItems().clear();
                choseCase = true;
                lblPrice.setText("");
                txtInput.setPromptText("");
                txtInput.setVisible(false);
                btnEnter.setVisible(false);
                shopSetUp();
            } else {
                txtInput.clear();
                txtInput.setPromptText("Choose Again");
            }
        }
    }

    //Sets up the offer
    public void offerSetUp(){
        btnAccept.setVisible(true);
        btnReject.setVisible(true);
        clickable = false;
        if(lastOffer){
            lblTitle.setText("Offer: $" + NumberFormat.getInstance(Locale.US).format(bob.getLastOffer()));
            setHighestOffer(bob.getLastOffer());
            setLowestOffer(bob.getLastOffer());
        }
        else {
            lblTitle.setText("Offer: $" + NumberFormat.getInstance(Locale.US).format(bob.getOffer()));
            setHighestOffer(bob.getOffer());
            setLowestOffer(bob.getOffer());
        }
    }

    //Checks if all the lockers are opened
    public boolean allOpened() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (openedLockers[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //Updates stats listview
    public void gameStats() {
        lstStats.getItems().add("GAME STATS");
        lstStats.getItems().add("");
        lstStats.getItems().add("Cases Opened: " + numOpen);
        lstStats.getItems().add("");
        lstStats.getItems().add("Money Won: $" + NumberFormat.getInstance(Locale.US).format(bankAccount));
        lstStats.getItems().add("Money Spent: $" + NumberFormat.getInstance(Locale.US).format(monSpent));
        lstStats.getItems().add("Highest Offer: $" + NumberFormat.getInstance(Locale.US).format(highestOffer));
        lstStats.getItems().add("");
        lstStats.getItems().add("Lowest Offer: $" + NumberFormat.getInstance(Locale.US).format(lowestOffer));
        lstStats.getItems().add("Acceptances: " + numAccept);
        lstStats.getItems().add("Rejections: " + numReject);
    }

    //Updates stats listview
    public void roundStats(){
        typeStat++;
        lstStats.getItems().clear();
        if(typeStat%2 != 0){
            lstStats.getItems().add("ROUND STATS");
            lstStats.getItems().add("");
            lstStats.getItems().add("Cases Opened: " + numOpenR);
            lstStats.getItems().add("");
            lstStats.getItems().add("Money Spent: $" + NumberFormat.getInstance(Locale.US).format(monSpentR));
            lstStats.getItems().add("Highest Offer: $" + NumberFormat.getInstance(Locale.US).format(highestOfferR));
            lstStats.getItems().add("");
            lstStats.getItems().add("Lowest Offer: $" + NumberFormat.getInstance(Locale.US).format(lowestOfferR));
            lstStats.getItems().add("Acceptances: " + numAcceptR);
            lstStats.getItems().add("Rejections: " + numRejectR);
        }
        else{
            gameStats();
        }
    }

    //Ends the game
    public void gameOver(){
        lblTitle.setText("Game Over");
        lblPrice.setText("Goal Not Met");
        closeLockers();
        lstShop.getItems().clear();
        lstStats.getItems().clear();
        lstItems.getItems().clear();
        lstShop.getItems().add("Do You Want To Play Again?");
        lstShop.getItems().add("YES");
        lstShop.getItems().add("NO");
        endGame = true;
    }

    //Accepts computer offer
    public void accept() {
        if(negotiate){
            bankAccount += bob.getNewOffer();
            negotiate = false;
        }
        else if(lastOffer){
            bankAccount += bob.getLastOffer();
            lastOffer = false;
        }
        else {
            bankAccount += bob.getOffer();
        }
        lblMoney.setText("$" + NumberFormat.getInstance(Locale.US).format(bankAccount));
        btnAccept.setVisible(false);
        btnReject.setVisible(false);
        clickable = false;
        numAccept++;
        numAcceptR++;
        closeLockers();
        boardSpotsIMG[pRow][pCol].setImage(playerCase.getShowImage());
        lblTitle.setText("Price Of Your Locker:");
        lblPrice.setText("$" + NumberFormat.getInstance(Locale.US).format(playerCase.getValue()));
        playerCase.resetFlipLockerTime();
        roundEnd = true;
        timeEvents();
        if(shop5p>bankAccount){
            gameOver();
        }
    }

    //Rejects computer offer
    public void reject() {
        lblMoney.setText("$" + NumberFormat.getInstance(Locale.US).format(bankAccount));
        lblTitle.setText("Mr.Banker");
        btnAccept.setVisible(false);
        btnReject.setVisible(false);
        numReject++;
        numRejectR++;
        clickable = true;
    }

    //Shows the player's locker
    public void showPlayerLocker() {
        if(roundEnd) {
            closeLockers();
            clickable = false;
            boardSpotsIMG[pRow][pCol].setImage(playerCase.getShowImage());
            lblTitle.setText("Price Of Your Locker:");
            lblPrice.setText("$" + NumberFormat.getInstance(Locale.US).format(playerCase.getValue()));
            bankAccount += playerCase.getValue()/3;
            lblMoney.setText("$" + NumberFormat.getInstance(Locale.US).format(bankAccount));
            playerCase.resetFlipLockerTime();
            timeEvents();
        }
    }

    //Closes all lockers
    public void closeLockers() {
        for (ImageView[] imageViews : boardSpotsIMG) {
            for (ImageView imageView : imageViews) {
                imageView.setImage(closed);
            }
        }
    }

    //Closes all lockers except the already open ones
    public void openLockers() {
        for(int i = 0; i< openedLockers.length; i++){
            for(int j = 0; j<openedLockers[0].length; j++){
                if(openedLockers[i][j]==1){
                    boardSpotsIMG[i][j].setImage(board[i][j].getShowImage());
                }
                if(openedLockers[i][j] == 2){
                    boardSpotsIMG[i][j].setImage(pCase);
                }
            }
        }
    }

    //Sets a variable
    public void setHighestOffer(int offer) {
        if (offer > highestOffer) {
            highestOffer = offer;
            highestOfferR = offer;
        }
    }

    //Sets a variable
    public void setLowestOffer(int offer) {
        if (offer < lowestOffer) {
            lowestOffer = offer;
            lowestOfferR = offer;
        }
    }

    //Resets the round to the next
    public void nextRound(){
        choseCase = false;
        clickable = true;
        roundEnd = false;
        negotiate = false;
        lastOffer = false;
        shop1p*=2.2;
        shop2p*=2.2;
        shop3p*=2.2;
        shop4p*=2.2;
        shop5p*=2.2;
        if(shop5p>bankAccount){
            gameOver();
            return;
        }
        numOpenR = 0;
        numRejectR = 0;
        numAcceptR = 0;
        numOpen = 0;
        highestOfferR = 0;
        lowestOfferR = 0;
        values.clear();
        txtInput.setVisible(true);
        btnEnter.setVisible(true);
        lblTitle.setText("Mr.Banker");
        lblTPrice.setText("");
        lblPrice.setText("");
        bob.increaseRoundVal();
        initialize();
        lblRound.setText("" + round);
        lstShop.getItems().clear();
    }

    //Resets the whole game
    public void resetGame(){
        values.clear();
        bankAccount = 1000;
        numOpen = 0;
        numAccept = 0;
        numReject = 0;
        highestOffer = 0;
        lowestOffer = 0;
        numOpenR = 0;
        numAcceptR = 0;
        numRejectR = 0;
        highestOfferR = 0;
        lowestOfferR = 0;
        round = 0;
        clicks = 0;
        shop1p = 1200;
        shop2p = 1000;
        shop3p = 750;
        shop4p = 500;
        shop5p = 250;
        monSpent = 0;
        monSpentR = 0;
        typeStat = 0;
        choseCase = false;
        clickable = true;
        roundEnd = false;
        twoFold = false;
        doubleT = false;
        lastLocker = false;
        setZero = false;
        endGame = false;
        negotiate = false;
        lastOffer = false;
        txtInput.setVisible(true);
        btnEnter.setVisible(true);
        lblTitle.setText("Mr.Banker");
        lblPrice.setText("");
        lblRound.setText("" + round);
        lstShop.getItems().clear();
        lstStats.getItems().clear();
        initialize();
    }

    //Updates shop listview
    public void shopSetUp(){
        lstShop.getItems().add("Retry- $" + NumberFormat.getInstance(Locale.US).format(shop1p));
        lstShop.getItems().add("");
        lstShop.getItems().add("TwoFold - $" + NumberFormat.getInstance(Locale.US).format(shop2p));
        lstShop.getItems().add("");
        lstShop.getItems().add("Extra Offer - $" + NumberFormat.getInstance(Locale.US).format(shop3p));
        lstShop.getItems().add("");
        lstShop.getItems().add("Double or Trouble - $" + NumberFormat.getInstance(Locale.US).format(shop4p));
        lstShop.getItems().add("");
        lstShop.getItems().add("Negotiate - $" + NumberFormat.getInstance(Locale.US).format(shop5p));
    }


    //Closes and opens player case plus last case
    public void timeEvents() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - playerCase.getFlipLockerTime() > 3000000000.0 && lastLocker) {
                    boardSpotsIMG[rowIndex][colIndex].setImage(board[rowIndex][colIndex].getShowImage());
                    playerCase.resetFlipLockerTime();
                    lastLocker = false;
                    roundEnd = true;
                    showPlayerLocker();
                }
                if (now - playerCase.getFlipLockerTime() > 4000000000.0 && roundEnd) {
                    nextRound();
                    playerCase.resetFlipLockerTime();
                    roundEnd = false;
                }
            }
        }.start();
    }

    //Shuffles all the cases on the board
    public void shuffle(){

        for (Locker[] i : board) {
            lockers.addAll(Arrays.asList(i));
        }

        ArrayList<Integer> openArray = new ArrayList<>();

        for (Integer[] i : openedLockers) {
            openArray.addAll(Arrays.asList(i));
        }

        for (int counter = 0; counter < 10000; counter++) {
            int index = (int) (Math.random() * lockers.size());
            int randPosition = (int) (Math.random() * lockers.size());
            Locker temp = lockers.get(index);
            Locker temp2 = lockers.get(randPosition);
            int temp3 = openArray.get(index);
            int temp4 = openArray.get(randPosition);
            lockers.set(randPosition, temp);
            lockers.set(index, temp2);
            openArray.set(randPosition, temp3);
            openArray.set(index, temp4);
        }

        for (int i = 0; i < 4; i++) {
            for (int z = 0; z < 5; z++) {
                board[i][z] = lockers.get(0);
                lockers.remove(0);
                openedLockers[i][z] = openArray.get(0);
                openArray.remove(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (openedLockers[i][j] == 0) {
                    boardSpotsIMG[i][j].setImage(closed);
                }
                else if(openedLockers[i][j] == 1){
                    boardSpotsIMG[i][j].setImage(board[i][j].getShowImage());
                }
                else if(openedLockers[i][j] == 2){
                    boardSpotsIMG[i][j].setImage(pCase);
                }
            }
        }
    }

}

