import java.awt.Color;

import javalib.impworld.WorldScene;
import tester.Tester;
import java.util.ArrayList;
import java.util.Arrays;

import javalib.worldimages.*;


class ExamplesCards {
  Card aDUp;
  Card aDDown;
  Card aHUp;
  Card aHDown;
  Card aCUp;
  Card aCDown;
  Card aSUp;
  Card aSDown;
  Card kSUp;
  Card kSDown;
  Card qHUp;
  Card qHDown;
  Card jCUp;
  Card jCDown;
  Card twoCUp;
  Card twoCDown;
  Card fourteenCUp;
  Card fourteenCDown;
  Card tenSDown;
  Card sevenSDown; 
  Board board1;
  Board board2;
  Card fourHDown;
  Board board3;
  Board board4;
  Board board5;
  Board board6;
  Board board7;

  Card blank;
  int cardWidth = 50;
  int cardHeight = 80;
  int spacing = 25;
  // 80 * 13 + 15 * 25 = 1040 + 375 = 1415
  int BG_WIDTH = (13 * cardWidth) + (15 * spacing);
  // 4 * 80 + 7 *25 = 320 + 175 = 495
  int BG_HEIGHT = (4 * cardHeight) + (7 * spacing);


  void initCards() {
    this.board1 = new Board();
    this.board2 = new Board(13, 4, 1);
    this.board3 = new Board(4, 4, 1);
    this.board4 = new Board(2, 4, 1);
    this.board5 = new Board(1, 4, 1);
    this.board6 = new Board(1, 4, 1);
    this.board7 = new Board(1, 4, 1);


    this.aDUp = new Card(1, "♦", false);
    this.aDDown = new Card(1, "♦", true);
    this.aCUp = new Card(1, "♣", false);
    this.aCDown = new Card(1, "♣", true);
    this.aSUp = new Card(1, "♠", false);
    this.aSDown = new Card(1, "♠", true);
    this.aHUp = new Card(1, "♥", false);
    this.aHDown = new Card(1, "♥", true);

    this.kSUp = new Card(13, "♠", false);
    this.kSDown = new Card(13, "♠", true);
    this.qHUp = new Card(12, "♥", false);
    this.qHDown = new Card(12, "♥", true);
    this.jCUp = new Card(11, "♣", false);
    this.jCDown = new Card(11, "♣", true);
    this.twoCUp = new Card(2, "♣", false);
    this.twoCDown = new Card(2, "♣", true);
    this.fourteenCUp = new Card(14, "♣", false);
    this.fourteenCDown = new Card(14, "♣", true);
    this.tenSDown = new Card(10, "♠", true);
    this.sevenSDown = new Card(7, "♠", true);
    this.fourHDown = new Card(4, "♥", true);

    this.blank = new Card(0, "", true);
  }


  void testRankToString(Tester t) {
    initCards();
    t.checkExpect(this.aDUp.rankToString(), "A"); 
    t.checkExpect(this.aDDown.rankToString(), "A"); 
    t.checkExpect(this.kSUp.rankToString(), "K"); 
    t.checkExpect(this.kSDown.rankToString(), "K"); 
    t.checkExpect(this.qHUp.rankToString(), "Q"); 
    t.checkExpect(this.qHDown.rankToString(), "Q"); 
    t.checkExpect(this.jCUp.rankToString(), "J"); 
    t.checkExpect(this.jCDown.rankToString(), "J"); 
    t.checkExpect(this.twoCUp.rankToString(), "2"); 
    t.checkExpect(this.twoCDown.rankToString(), "2"); 

    t.checkException(new RuntimeException("Not a valid rank."), this.fourteenCUp, "rankToString");
    t.checkException(new RuntimeException("Not a valid rank."), this.fourteenCDown, "rankToString");
  }

  void testDrawCard(Tester t) {
    initCards();
    WorldImage cardBG = new RectangleImage(50, 80, "solid", Color.WHITE);
    WorldImage cardOutline = new RectangleImage(50, 80, "outline", Color.BLACK);
    WorldImage card = new OverlayImage(cardOutline, cardBG);
    t.checkExpect(this.aDUp.drawCard(50, 80), 
        new OverlayImage(new TextImage("A♦", Color.RED), card));
    t.checkExpect(this.aDDown.drawCard(50, 80), 
        new FromFileImage("src/pink.png"));
    t.checkExpect(this.twoCUp.drawCard(50, 80), 
        new OverlayImage(new TextImage("2♣", Color.black), card));
    t.checkExpect(this.twoCDown.drawCard(50, 80), 
        new FromFileImage("src/pink.png"));
    t.checkExpect(this.blank.drawCard(50, 80), 
        new FromFileImage("src/blank.png"));
  }

  void testCountRanks(Tester t) {
    initCards();
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board5.countMyCards(new CountRanks(0)), 0); // blank card

    this.board5.removeCards(aCDown);
    t.checkExpect(this.board5.countMyCards(new CountRanks(0)), 1); // blank card

    this.board5.removeCards(aHDown);
    t.checkExpect(this.board5.countMyCards(new CountRanks(0)), 2); // blank card
  }


  void testCountSuit(Tester t) {
    initCards();
    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);

    this.board5.removeCards(aCDown);

    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 0);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);

    this.board5.removeCards(aHDown);

    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 0);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 0);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
  }



  void testBoardConstructor(Tester t) {
    initCards();

    t.checkExpect(this.board1.countMyCards(new CountSuit("♦")), 13);
    t.checkExpect(this.board1.countMyCards(new CountSuit("♣")), 13);
    t.checkExpect(this.board1.countMyCards(new CountSuit("♥")), 13);
    t.checkExpect(this.board1.countMyCards(new CountSuit("♠")), 13);
    t.checkExpect(this.board1.countMyCards(new CountRanks(1)), 4);

    t.checkExpect(this.board1.countMyCards(new CountRanks(2)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(3)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(4)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(5)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(6)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(7)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(8)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(9)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(10)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(11)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(12)), 4);
    t.checkExpect(this.board1.countMyCards(new CountRanks(13)), 4);

    t.checkExpect(this.board3.countMyCards(new CountSuit("♦")), 4);
    t.checkExpect(this.board3.countMyCards(new CountSuit("♣")), 4);
    t.checkExpect(this.board3.countMyCards(new CountSuit("♥")), 4);
    t.checkExpect(this.board3.countMyCards(new CountSuit("♠")), 4);
    t.checkExpect(this.board3.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board3.countMyCards(new CountRanks(2)), 4);
    t.checkExpect(this.board3.countMyCards(new CountRanks(3)), 4);
    t.checkExpect(this.board3.countMyCards(new CountRanks(4)), 4);
    t.checkExpect(this.board3.countMyCards(new CountRanks(5)), 0);
    t.checkExpect(this.board3.countMyCards(new CountRanks(11)), 0);

    t.checkExpect(this.board4.countMyCards(new CountSuit("♦")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♣")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♥")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♠")), 2);
    t.checkExpect(this.board4.countMyCards(new CountRanks(1)),4);
    t.checkExpect(this.board4.countMyCards(new CountRanks(2)), 4);
    t.checkExpect(this.board4.countMyCards(new CountRanks(9)), 0);
    t.checkExpect(this.board4.countMyCards(new CountRanks(13)), 0);

    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board5.countMyCards(new CountRanks(2)), 0);
    t.checkExpect(this.board5.countMyCards(new CountRanks(9)), 0);
    t.checkExpect(this.board5.countMyCards(new CountRanks(13)), 0);

  }

  void testShuffle(Tester t) {
    initCards();
    t.checkExpect(this.board2.grid.get(0).get(0), tenSDown); 
    t.checkExpect(this.board2.grid.get(3).get(12), sevenSDown); 
    t.checkExpect(this.board2.grid.size(), 4);
    t.checkExpect(this.board2.countMyCards(new CountSuit("♦")), 13);
    t.checkExpect(this.board2.countMyCards(new CountSuit("♣")), 13);
    t.checkExpect(this.board2.countMyCards(new CountSuit("♥")), 13);
    t.checkExpect(this.board2.countMyCards(new CountSuit("♠")), 13);
    t.checkExpect(this.board2.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(2)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(3)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(4)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(5)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(6)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(7)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(8)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(9)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(10)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(11)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(12)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(13)), 4);
    board2.shuffleDeck();
    t.checkExpect(this.board2.grid.get(0).get(0), fourHDown); 
    t.checkExpect(this.board2.grid.get(3).get(12), kSDown); 
    t.checkExpect(this.board2.grid.size(), 4);
    t.checkExpect(this.board2.countMyCards(new CountSuit("♦")), 13);
    t.checkExpect(this.board2.countMyCards(new CountSuit("♣")), 13);
    t.checkExpect(this.board2.countMyCards(new CountSuit("♥")), 13);
    t.checkExpect(this.board2.countMyCards(new CountSuit("♠")), 13);
    t.checkExpect(this.board2.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(2)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(3)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(4)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(5)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(6)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(7)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(8)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(9)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(10)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(11)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(12)), 4);
    t.checkExpect(this.board2.countMyCards(new CountRanks(13)), 4);
  }



  void testFlipCard(Tester t) {
    initCards();
    t.checkExpect(this.aDUp, aDUp);
    this.aDUp.flipCard();
    t.checkExpect(this.aDUp, aDDown); 
    this.aDUp.flipCard();
    t.checkExpect(this.aDUp, aDUp);

    t.checkExpect(this.blank, blank);
    this.blank.flipCard();
    t.checkExpect(this.blank, blank);
  }

  void testCountFlipped(Tester t) {
    initCards();
    t.checkExpect(this.board1.countMyCards(new CountFlipped()), 0);
    this.board1.onMouseClicked(new Posn(0,0));
    t.checkExpect(this.board1.countMyCards(new CountFlipped()), 0);
    this.board1.onMouseClicked(new Posn(150,80));
    t.checkExpect(this.board1.countMyCards(new CountFlipped()), 1);
    this.board1.onMouseClicked(new Posn(500,500));
    t.checkExpect(this.board1.countMyCards(new CountFlipped()), 1);

  }

  void testSameCard(Tester t) {
    initCards();
    t.checkExpect(this.aCDown.sameCard(aCDown), true); 
    t.checkExpect(this.aCDown.sameCard(aCUp), false); 
    // one is faced down, one is faced up
    t.checkExpect(this.kSDown.sameCard(aCDown), false); // diff suit + rank 
    t.checkExpect(this.aCDown.sameCard(aHDown), false); // diff suit
    t.checkExpect(this.qHUp.sameCard(aHDown), false); 
    // diff rank, one is faced down, one is faced up
    t.checkExpect(this.qHUp.sameCard(aHUp), false); // diff rank
  }

  void testIsMatching(Tester t) {
    initCards();
    t.checkExpect(this.aCDown.isMatching(aSDown), true);
    t.checkExpect(this.aCDown.isMatching(aSUp), true);
    t.checkExpect(this.aHDown.isMatching(aDUp), true);
    t.checkExpect(this.aHUp.isMatching(aDDown), true);
    t.checkExpect(this.aSUp.isMatching(aDDown), false); // spade and heart
    t.checkExpect(this.aSUp.isMatching(kSUp), false); // different rank but same suit
    t.checkExpect(this.aSDown.isMatching(kSUp), false); 
    t.checkExpect(this.twoCUp.isMatching(kSUp), false); // completely different
  }

  void testFlippedCards(Tester t) {
    initCards();
    t.checkExpect(this.board5.flippedCards(), new ArrayList<Card>());
    this.board5.onMouseClicked(new Posn(60,80));
    t.checkExpect(this.board5.flippedCards(), new ArrayList<Card>(Arrays.asList(aHUp)));
    this.board5.onMouseClicked(new Posn(60,80));
    t.checkExpect(this.board5.flippedCards(), new ArrayList<Card>());
    this.board5.onMouseClicked(new Posn(60, 80));
    t.checkExpect(this.board5.flippedCards(), new ArrayList<Card>(Arrays.asList(aHUp)));
    this.board5.onMouseClicked(new Posn(60,180));
    t.checkExpect(this.board5.flippedCards(), new ArrayList<Card>(Arrays.asList(aHUp, aCUp)));
  }

  void testRemoveCards(Tester t) {
    initCards();
    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board5.countMyCards(new CountRanks(0)), 0); // blank card

    this.board5.removeCards(aCDown);

    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 0);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(0)), 1); // blank card

    this.board5.removeCards(aHDown);

    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 0);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 0);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(0)), 2); // blank card
  }



  void testOnMouse(Tester t) {
    initCards();
    t.checkExpect(this.board1.countMyCards(new CountFlipped()), 0);
    this.board1.onMouseClicked(new Posn(60,80));
    t.checkExpect(this.board1.countMyCards(new CountFlipped()), 1);
    this.board1.onMouseClicked(new Posn(150,80));
    t.checkExpect(this.board1.countMyCards(new CountFlipped()), 2);
    this.board1.onMouseClicked(new Posn(20,20));
    t.checkExpect(this.board1.countMyCards(new CountFlipped()), 2);

    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 0);
    t.checkExpect(this.board5.steps, 16);
    //click a card
    this.board5.onMouseClicked(new Posn(60,80));
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 1);
    t.checkExpect(this.board5.steps, 15);
    //click a matching card
    this.board5.onMouseClicked(new Posn(60,240));
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 2);
    t.checkExpect(this.board5.steps, 14);
    this.board5.onTick();
    t.checkExpect(this.board5.steps, 15);
    //click a blank space
    this.board5.onMouseClicked(new Posn(60,240));
    t.checkExpect(this.board5.steps, 15);
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 0);
  }


  void testMakeScene(Tester t) {
    initCards();
    // only making 4 cards in the WorldScene
    WorldImage scoreImage = new TextImage("Score: 2" , 20, Color.black);
    WorldImage stepImage = new TextImage("Steps Left: 16", 20, Color.black);


    // Calculate the elapsed time
    this.board5.calculateElapsedTime();
    long minutes = this.board5.elapsedTime / 100;
    long seconds = this.board5.elapsedTime % 100;
    String newMin = "" + minutes;
    String newSec = "" + seconds;
    if (minutes < 10) {
      newMin = "0" + minutes;
    }

    if (seconds < 10) {
      newSec = "0" + seconds;
    }
    String timeString = "Time: " + newMin + ":" + newSec;

    WorldImage timerImage = new TextImage(timeString, 20, Color.BLACK);
    WorldScene newWorldScene = new WorldScene(BG_WIDTH, BG_HEIGHT);
    WorldImage bgImg = new FromFileImage("src/amongus.png");
    newWorldScene.placeImageXY(bgImg, BG_WIDTH / 2, BG_HEIGHT / 2);

    newWorldScene.placeImageXY(this.aSDown.drawCard(50, 80), 50, 380);
    newWorldScene.placeImageXY(this.aHDown.drawCard(50, 80), 50, 275);
    newWorldScene.placeImageXY(this.aCDown.drawCard(50, 80), 50, 170);
    newWorldScene.placeImageXY(this.aDDown.drawCard(50, 80), 50, 65);
    newWorldScene.placeImageXY(scoreImage, BG_WIDTH / 5, BG_HEIGHT - 40);
    newWorldScene.placeImageXY(stepImage, (BG_WIDTH / 2) + 280, BG_HEIGHT - 40);
    newWorldScene.placeImageXY(timerImage, BG_WIDTH / 2, BG_HEIGHT - 40);

    t.checkExpect(this.board5.makeScene(), newWorldScene);

    initCards();

    // Calculate the elapsed time
    this.board6.calculateElapsedTime();
    long minutes1 = this.board6.elapsedTime / 100;
    long seconds1 = this.board6.elapsedTime % 100;
    String newMin1 = "" + minutes1;
    String newSec1 = "" + seconds1;
    if (minutes1 < 10) {
      newMin1 = "0" + minutes1;
    }

    if (seconds1 < 10) {
      newSec1 = "0" + seconds1;
    }
    String timeString1 = "Time: " + newMin1 + ":" + newSec1;

    //decrease steps by one, change one card to face up
    WorldImage scoreNew = new TextImage("Score: 2" , 20, Color.black);
    WorldImage stepNew = new TextImage("Steps Left: 16", 20, Color.black);
    WorldScene newScene = new WorldScene(BG_WIDTH, BG_HEIGHT);
    WorldImage timerNew = new TextImage(timeString1, 20, Color.BLACK);
    newScene.placeImageXY(bgImg, BG_WIDTH / 2, BG_HEIGHT / 2);
    newScene.placeImageXY(this.aHDown.drawCard(50, 80), 50, 65);
    newScene.placeImageXY(this.aCDown.drawCard(50, 80), 50, 170);
    newScene.placeImageXY(this.aDDown.drawCard(50, 80), 50, 275);
    newScene.placeImageXY(this.aSDown.drawCard(50, 80), 50, 380);
    newScene.placeImageXY(scoreNew, BG_WIDTH / 5, BG_HEIGHT - 40);
    newScene.placeImageXY(stepNew, (BG_WIDTH / 2) + 280, BG_HEIGHT - 40);
    newScene.placeImageXY(timerNew, BG_WIDTH / 2, BG_HEIGHT - 40);

    t.checkExpect(this.board6.makeScene(), newScene);

    initCards();

    this.board7.calculateElapsedTime();
    long minutes10 = this.board7.elapsedTime / 100;
    long seconds10 = this.board7.elapsedTime % 100;
    String newMin10 = "" + minutes10;
    String newSec10 = "" + seconds10;
    if (minutes10 < 10) {
      newMin10 = "0" + minutes10;
    }

    if (seconds10 < 10) {
      newSec10 = "0" + seconds10;
    }
    String timeString10 = "Time: " + newMin10 + ":" + newSec10;


    //decrease score and step by one, replace matching cards with blank cards
    WorldImage score = new TextImage("Score: 2" , 20, Color.black);
    WorldImage step = new TextImage("Steps Left: 16", 20, Color.black);
    WorldScene scene = new WorldScene(BG_WIDTH, BG_HEIGHT);
    WorldImage timer = new TextImage(timeString10, 20, Color.BLACK);
    scene.placeImageXY(bgImg, BG_WIDTH / 2, BG_HEIGHT / 2);
    scene.placeImageXY(this.aHDown.drawCard(50, 80), 50, 65);
    scene.placeImageXY(this.aCDown.drawCard(50, 80), 50, 170);
    scene.placeImageXY(this.aDDown.drawCard(50, 80), 50, 275);
    scene.placeImageXY(this.aSDown.drawCard(50, 80), 50, 380);
    scene.placeImageXY(score, BG_WIDTH / 5, BG_HEIGHT - 40);
    scene.placeImageXY(step, (BG_WIDTH / 2) + 280, BG_HEIGHT - 40);
    scene.placeImageXY(timer, BG_WIDTH / 2, BG_HEIGHT - 40);

    t.checkExpect(this.board7.makeScene(), scene);

  }


  void testOnTick(Tester t) {
    initCards();
    this.board5.onTick();

    // check how many cards is in board5 (4)
    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 0);
    t.checkExpect(this.board5.score, 2);
    t.checkExpect(this.board5.steps, 16);

    // flip one card (heart)
    this.board5.onMouseClicked(new Posn(60,80));
    this.board5.onTick();

    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 1);
    t.checkExpect(this.board5.score, 2);
    t.checkExpect(this.board5.steps, 15);

    // flip another card (diamond)
    this.board5.onMouseClicked(new Posn(60,240));

    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 2);
    t.checkExpect(this.board5.score, 2);
    t.checkExpect(this.board5.steps, 14);

    this.board5.onTick();

    // check how many card is in board5 (2)
    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 0);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 0);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 2);
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 0);
    t.checkExpect(this.board5.score, 1);
    t.checkExpect(this.board5.steps, 15); //get 1 step for a match

    //clicking a blank card
    this.board5.onMouseClicked(new Posn(60,240));
    this.board5.onTick();

    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 0);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 0);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 2);
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 0);
    t.checkExpect(this.board5.score, 1);
    t.checkExpect(this.board5.steps, 15);


    // now we want to click on two cards that are not matching. 
    // they should remain the same
    initCards();
    // check how many cards is in board5 (4)
    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 0);
    t.checkExpect(this.board5.score, 2);
    t.checkExpect(this.board5.steps, 16);

    this.board5.onMouseClicked(new Posn(60,240));   
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 1);
    t.checkExpect(this.board5.steps, 15);

    this.board5.onMouseClicked(new Posn(60,180));
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 2);
    t.checkExpect(this.board5.score, 2);
    t.checkExpect(this.board5.steps, 14);

    this.board5.onTick();

    t.checkExpect(this.board5.countMyCards(new CountSuit("♦")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♣")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♥")), 1);
    t.checkExpect(this.board5.countMyCards(new CountSuit("♠")), 1);
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 4);
    t.checkExpect(this.board5.countMyCards(new CountFlipped()), 0);
    t.checkExpect(this.board5.steps, 14);
  }

  void testOnKeyEvent(Tester t) {
    initCards();

    t.checkExpect(this.board4.countMyCards(new CountSuit("♦")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♣")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♥")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♠")), 2);
    this.board4.onMouseClicked(new Posn(60,240));
    this.board4.onMouseClicked(new Posn(60,80));
    this.board4.onTick();
    t.checkExpect(this.board4.countMyCards(new CountSuit("♦")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♣")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♥")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♠")), 2);
    //pressing a random key
    this.board4.onKeyEvent("w");
    t.checkExpect(this.board4.countMyCards(new CountSuit("♦")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♣")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♥")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♠")), 2);
    //pressing r to reset
    this.board4.onKeyEvent("r");
    t.checkExpect(this.board4.countMyCards(new CountSuit("♦")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♣")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♥")), 2);
    t.checkExpect(this.board4.countMyCards(new CountSuit("♠")), 2);
  }


  void testWorldEnds(Tester t) {
    initCards();
    t.checkExpect(this.board5.worldEnds(), new WorldEnd(false, this.board5.makeScene()));

    //matches all of the cards
    this.board5.onMouseClicked(new Posn(60,80));
    this.board5.onMouseClicked(new Posn(60,240));
    this.board5.onTick();
    this.board5.onMouseClicked(new Posn(60,150));
    this.board5.onMouseClicked(new Posn(60,380));
    this.board5.onTick();

    t.checkExpect(this.board5.worldEnds(), new WorldEnd(true, this.board5.makeWinScene()));
    
    initCards(); 
    t.checkExpect(board5.steps, 16);    
    t.checkExpect(this.board5.worldEnds(), new WorldEnd(false, this.board5.makeScene()));
    
    //clicking the same card 16 times
    for (int i = 0; i < 16; i++) { 
      this.board5.onMouseClicked(new Posn(60,240));   
    }
    
    t.checkExpect(board5.steps, 0);
    t.checkExpect(this.board5.worldEnds(), new WorldEnd(true, this.board5.makeLostScene()));
  }

  void testMakeWinScene(Tester t) {
    initCards();
    WorldScene finalScene = new WorldScene(BG_WIDTH, BG_HEIGHT);
    WorldImage gameOverText = new TextImage("You win! Thank you for playing!", 30, Color.white);
    WorldImage gameOverBG = new FromFileImage("src/amg.png");

    WorldImage gameOverLay = new OverlayImage(gameOverText, gameOverBG);

    finalScene.placeImageXY(gameOverLay, (BG_WIDTH / 2), (BG_HEIGHT / 2)); 
    t.checkExpect(board5.makeWinScene(), finalScene);
    t.checkExpect(board1.makeWinScene(), finalScene);
  }
  
  void testMakeLostScene(Tester t) {
    initCards();
    initCards();
    WorldScene finalScene = new WorldScene(BG_WIDTH, BG_HEIGHT);
    WorldImage gameOverText = new TextImage("You ran out of steps!", 30, Color.white);
    WorldImage gameOverBG = new FromFileImage("src/among.png");

    WorldImage gameOverLay = new OverlayImage(gameOverText, gameOverBG);

    finalScene.placeImageXY(gameOverLay, (BG_WIDTH / 2), (BG_HEIGHT / 2)); 
    t.checkExpect(board5.makeLostScene(), finalScene);
    t.checkExpect(board1.makeLostScene(), finalScene);
  }


  void testCountAllCards(Tester t) {
    initCards();
    Board b = new Board(); 
    t.checkExpect(b.countAllCards(), 52); 
    t.checkExpect(this.board5.countAllCards(), 4);
    t.checkExpect(this.board4.countAllCards(), 8);
    t.checkExpect(this.board3.countAllCards(), 16);
  }

  void testDealCards(Tester t) {
    initCards();
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 4);
    //remove a card from board
    this.board5.removeCards(aCDown);
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 3);
    //deal a new set of cards
    this.board5.dealCards();
    t.checkExpect(this.board5.countMyCards(new CountRanks(1)), 4);
  }

  void testCalculateElapsedTime(Tester t) throws InterruptedException {
    initCards();
    t.checkExpect(this.board5.startTime == System.currentTimeMillis(), true);
    this.board5.calculateElapsedTime();
    t.checkExpect(board5.elapsedTime == 0, true);
    t.checkExpect(board5.elapsedTime, 0L);

    // how to make time pass
    for (int i = 0; i < 420; i++) { 
      this.board5.onTick();   
    }

    // how to make time pass (for testing purposes) 
    Thread.sleep(1);
    t.checkExpect(this.board5.startTime == System.currentTimeMillis(), false);   
  }

  void testGame(Tester t) {
    initCards();

    Board b = new Board();
    double tickRate = 1;
    b.bigBang(BG_WIDTH, BG_HEIGHT, tickRate);
  }
}