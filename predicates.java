import java.util.function.Predicate;

//how many cards in this board is facing up?
class CountFlipped implements Predicate<Card> {
  
  //is this card facing up?
  public boolean test(Card c) {
    return !c.faceDown;
  }
}

//how many card in this board has the given rank?
class CountRanks implements Predicate<Card> {
  int rank;
  
  CountRanks(int rank) {
    this.rank = rank;
  }

  //does this card have the same rank as the given rank?
  public boolean test(Card c) {
    return c.rank == this.rank ;
  }
}

//how many card in this board has the given suit?
class CountSuit implements Predicate<Card> {
  String suit;
  
  CountSuit(String suit) {
    this.suit = suit;
  }

  //does this card have the same suit as the given suit?
  public boolean test(Card c) {
    return c.suit.equals(this.suit) ;
  }
}