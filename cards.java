import java.awt.Color;

import javalib.worldimages.FromFileImage;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

//represents a card in a deck
class Card {
  int rank; // Numbers 
  String suit; // 4 variations
  boolean faceDown; 

  Card(int rank, String suit, boolean faceDown) {
    this.rank = rank;
    this.suit = suit;
    this.faceDown = faceDown;
  }

  // draws this card onto board
  WorldImage drawCard(int width, int height) {
    if (this.rank == 0 && this.suit.equals("")) { // blank card
      return new FromFileImage("src/blank.png");
    }

    else if (faceDown) { // faceDown card
      return new FromFileImage("src/pink.png");
    }

    else { // faceUp card
      // "♣" "♦" "♥" "♠"
      WorldImage cardBG = new RectangleImage(width, height, "solid", Color.WHITE);
      WorldImage cardOutline = new RectangleImage(width, height, "outline", Color.BLACK);
      WorldImage card = new OverlayImage(cardOutline, cardBG);
      if (this.suit.equals("♠") || this.suit.equals("♣")) {
        WorldImage suitImage = new TextImage(this.rankToString() + this.suit, Color.BLACK); 
        return new OverlayImage(suitImage, card);
      }
      else {
        WorldImage suitImage = new TextImage(this.rankToString() + this.suit, Color.RED);
        return new OverlayImage(suitImage, card);
      }
    }
  }

  // Converts the rank (1, 2...10, 11, 12, 13) to a String. 
  // 1, 11, 12, 13, are converted to A, J, Q, K respectively
  String rankToString() {
    if (this.rank == 11) {
      return "J"; 
    }

    else if (this.rank == 12) {
      return "Q";
    }

    else if (this.rank == 13) {
      return "K";
    }

    else if (this.rank == 1) {
      return "A";
    }

    else if (this.rank > -1 && this.rank < 11) {
      return Integer.toString(this.rank);
    }

    else {
      throw new RuntimeException("Not a valid rank.");
    }
  }

  // flips the card
  // can't flip blank cards
  void flipCard() {
    if (this.rank != 0 && !this.suit.equals("")) {
      this.faceDown = !faceDown;
    }
    else {
      return;
    }
  }

  //is this card and the given card matches (values and color)?
  boolean isMatching(Card other) {
    if (this.suit.equals("♦") || this.suit.equals("♥")) {
      return this.rank == other.rank && (other.suit.equals("♦") || other.suit.equals("♥"));
    }

    else {
      return this.rank == other.rank && (other.suit.equals("♣") || other.suit.equals("♠"));
    }
  }

  // is this the same card as the other card? 
  boolean sameCard(Card other) {
    return (this.rank == other.rank) 
        && (this.suit.equals(other.suit)) 
        && this.faceDown == other.faceDown;
  }

}