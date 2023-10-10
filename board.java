import java.util.ArrayList;
import java.util.Arrays;

import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

import java.util.function.Predicate;
import java.util.Random;

// represents a board game
class Board extends World { 
  // arraylist is a row  
  // arraylist of arraylist = full board
  ArrayList<ArrayList<Card>> grid;
  Random rand;
  int cardWidth = 50;
  int cardHeight = 80;
  int spacing = 25; //spacing between two cards
  int endCol = 13; //end range of board's column
  int endRow = 4; //end range of board's row
  int score = 0;
  int steps = 0; 
  int bgWidth = (13 * cardWidth) + (15 * spacing);
  int bgHeight = (4 * cardHeight) + (7 * spacing);
  long startTime;
  long elapsedTime;

  //constructor
  Board() {
    this.rand = new Random();
    this.dealCards();
  }

  Board(int endCol, int endRow) {
    this.rand = new Random();
    this.endCol = endCol;
    this.endRow = endRow;
    this.dealCards();
  }

  Board(int endCol, int endRow, int seed) {
    this.rand = new Random(seed);
    this.endCol = endCol;
    this.endRow = endRow;
    this.dealCards();
  }


  // deals the cards on the board
  void dealCards() {
    this.grid = new ArrayList<>();

    ArrayList<String> suits = new ArrayList<String>(Arrays.asList("♦", "♣", "♥", "♠"));
    for (int row = 0; row < this.endRow; row++) {
      ArrayList<Card> rowList = new ArrayList<>();
      for (int col = 0; col < this.endCol; col++) {
        Card card = new Card(col + 1, suits.get(row), true);
        rowList.add(card);
      }

      this.grid.add(rowList);

    }
    this.startTime = System.currentTimeMillis();
    this.score = this.countAllCards() / 2;
    this.steps = this.countAllCards() * 4;
    this.shuffleDeck();
  }

  // changes the time for every min and second
  void calculateElapsedTime() {
    long currentTime = System.currentTimeMillis();
    long elapsedTimeMillis = currentTime - this.startTime;

    // Calculate minutes and seconds
    long minutes = (elapsedTimeMillis / 1000) / 60;
    long seconds = (elapsedTimeMillis / 1000) % 60;

    // Convert to int and store in instance variables
    this.elapsedTime = minutes;
    this.elapsedTime = (this.elapsedTime * 100) + seconds;
  }

  //counts how many card in this board meets the given predicate
  int countMyCards(Predicate<Card> pred) {
    int count = 0;
    for (ArrayList<Card> rowList : this.grid) {
      for (Card card : rowList) {
        if (pred.test(card)) {
          count++;
        }
      }     
    }
    return count;
  }

  //moves the cards on this board around 
  void shuffleDeck() {
    ArrayList<Card> deck = new ArrayList<>();

    for (ArrayList<Card> rowList : this.grid) {
      for (Card card : rowList) {
        deck.add(card);
      }
    }

    for (ArrayList<Card> rowList : this.grid) {
      for (int i = rowList.size() - 1; i >= 0; i--) {
        int randomIndex = rand.nextInt(deck.size());
        Card card = deck.remove(randomIndex);
        rowList.set(i, card);
      }
    }
  }

  // renders the scene of the current world
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(bgWidth, bgHeight);
    WorldImage bgImg = new FromFileImage("src/amongus.png");
    WorldImage scoreImage = new TextImage("Score: " +  this.score, 20, Color.black);
    WorldImage stepImage = new TextImage("Steps Left: " +  this.steps, 20, Color.black);
    
    // Calculate the elapsed time
    calculateElapsedTime();
    long minutes = this.elapsedTime / 100;
    long seconds = this.elapsedTime % 100;
    String newMin = "" + minutes;
    String newSec = "" + seconds;
    
    //adds 0 at the front
    if (minutes < 10) {
      newMin = "0" + minutes;
    }
    if (seconds < 10) {
      newSec = "0" + seconds;
    }
    
    String timeString = "Time: " + newMin + ":" + newSec;
    WorldImage timerText = new TextImage(timeString, 20, Color.black);
    scene.placeImageXY(bgImg, bgWidth / 2, bgHeight / 2);

    //in every row
    for (int row = 0; row < this.endRow; row++) {
      //in every column
      for (int col = 0; col < this.endCol; col++) {
        Card card = this.grid.get(row).get(col);
        //draw a card at this column in this row
        WorldImage cardImage = card.drawCard(cardWidth, cardHeight);
        //calculating position of the current card on BG
        int cardX = col * (cardWidth + spacing) + (cardWidth / 2) + spacing;
        int cardY = row * (cardHeight + spacing) + (cardHeight / 2) + spacing;
        //place the rendered card at this column in this row on this BG
        scene.placeImageXY(cardImage, cardX, cardY);
      }
    }
    scene.placeImageXY(scoreImage, bgWidth / 5, bgHeight - 40);
    scene.placeImageXY(stepImage, (bgWidth / 2) + 280, bgHeight - 40);
    scene.placeImageXY(timerText, bgWidth / 2, bgHeight - 40);
    return scene;
  }

  // goes through the board and return cards that are facing up 
  ArrayList<Card> flippedCards() {
    ArrayList<Card> list = new ArrayList<Card>();
    for (ArrayList<Card> rowList : this.grid) {
      for (Card card : rowList) {
        // if it is faceUp, then add card to the list
        if (!card.faceDown) {
          list.add(card);
        }
      }
    }
    return list;
  }   


  // removes the given cards in the current list: turn them into blank cards
  void removeCards(Card other) {
    for (int row = 0; row < this.grid.size(); row++) {
      ArrayList<Card> rowList = this.grid.get(row);
      for (int col = 0; col < rowList.size(); col++) {
        Card card = rowList.get(col);
        if (card.sameCard(other)) {
          rowList.set(col, new Card(0, "", true));
        }
      }
    }
  }

  // Case 1: less than 2 cards are faceUp, on click -> faceUp 
  // Case 2: when two cards are faceUp AND they match, remain matching
  // Case 3: when two cards don't match, flips both of them down
  public void onTick() {

    //if there are 2 flipped cards in this board
    if (this.countMyCards(new CountFlipped()) >= 2) {
      ArrayList<Card> flippedList = this.flippedCards();
      Card first = flippedList.get(0);
      Card second = flippedList.get(1);

      //if the two flipped cards are matching
      if (first.isMatching(second)) {
        // we "take them out" - remove them from board
        this.removeCards(first);
        this.removeCards(second);
        this.score -= 1; 
        //get one extra step for every match
        this.steps += 1;
      }
      //if they are not matching
      else {
        first.flipCard();
        second.flipCard();
      }
    }
  }

  // when the mouse is clicked (on a card), flip the cards and steps is decreased by 1
  public void onMouseClicked(Posn pos) {

    for (int row = 0; row < this.endRow; row++) {
      for (int col = 0; col < this.endCol; col++) {
        int cardX = col * (cardWidth + spacing) + cardWidth / 2 + spacing;
        int cardY = row * (cardHeight + spacing) + cardHeight / 2 + spacing;

        //if the mouse is somewhere in the middle of a card, flip that card
        if (pos.x >= cardX - cardWidth / 2 && pos.x <= cardX + cardWidth / 2 
            && pos.y >= cardY - cardHeight / 2 && pos.y <= cardY + cardHeight / 2) {
          Card card = this.grid.get(row).get(col);
          //if there are less than 2 flipped cards, flip the card
          if (this.countMyCards(new CountFlipped()) < 2) {
            card.flipCard();
          }
          //if the card that gets flipped is not a blank card, decrease step
          if (!card.suit.equals("")) {
            this.steps -= 1;
          }
        }
      }
    }
  }

  // restarts the game 
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      Board newBoard = new Board(this.endCol, this.endRow);
      this.grid = newBoard.grid;
    }
  }

  // The end of the game if the WorldEnd is true, else it continues. 
  public WorldEnd worldEnds() {
    if (this.score <= 0) {
      return new WorldEnd(true, this.makeWinScene()); // similar to makeScene
    } 

    else if (this.steps <= 0) {
      return new WorldEnd(true, this.makeLostScene());
    }
    else {
      return new WorldEnd(false, this.makeScene());

    }
  }

  // final scene of the game (when the player matches all of the cards)
  public WorldScene makeWinScene() {
    WorldScene finalScene = new WorldScene(bgWidth, bgHeight);
    WorldImage gameOverText = new TextImage("You win! Thank you for playing!", 30, Color.white);
    WorldImage gameOverBG = new FromFileImage("src/amg.png");

    WorldImage gameOverLay = new OverlayImage(gameOverText, gameOverBG);
    finalScene.placeImageXY(gameOverLay, (bgWidth / 2), (bgHeight / 2)); 
    return finalScene;
  }

  // final scene of the game (when the player ran out of steps)
  public WorldScene makeLostScene() {
    WorldScene finalScene = new WorldScene(bgWidth, bgHeight);
    WorldImage youLostText = new TextImage("You ran out of steps!", 30, Color.white);
    WorldImage gameOverBG = new FromFileImage("src/among.png");

    WorldImage gameOverLay = new OverlayImage(youLostText, gameOverBG);
    finalScene.placeImageXY(gameOverLay, (bgWidth / 2), (bgHeight / 2)); 
    return finalScene;

  }

  // counts all the cards on the board
  public int countAllCards() {
    int dNum = this.countMyCards(new CountSuit("♦"));
    int cNum = this.countMyCards(new CountSuit("♣"));
    int hNum = this.countMyCards(new CountSuit("♥"));
    int sNum = this.countMyCards(new CountSuit("♠"));
    return dNum + cNum + hNum + sNum;
  }
}
