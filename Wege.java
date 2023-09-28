

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Collections;
import javafx.scene.control.Label;
import java.util.ArrayList;

/**
 * A class that has the Wege Game coded into it 
 * @author Jiana Kambo
 */
public class Wege extends Application{
  
  /** Indetifies of the first move is played or not */
  private boolean first = true;
  
  /**Number of rows in the grid */
  private static int row; 
  
  /**Number of coloums in the grid */
  private static int coloumn; 
  
  private static int specialCards;
  
  /** stores the created grid plane */
  private GridPane gridPane; 
  
  /**Stores the created flow plane */
  private FlowPane flowPane;
  
  /**Stores the deck card */
  private WegeButton deckCard ; 
  
  /**Stores the array of grid buttons present in the game  */
  private WegeButton[][] gridButtons;
  
  /**Linked list that stores all the cards  */
  private LinkedList<WegeCard> deckCards= new LinkedList<WegeCard>();
  
  /**Label storing land's turn */
  private Label landTurn = new Label("It's Land's turn!");
  
  /** Label storing Water's turn*/
  private Label waterTurn = new Label("It's Water's turn!");
  
  /** boolean identifying when the label needs tp changed / which player's turn it is*/
  private boolean changeLabel;
  
  /**
   * Creates an array of buttons and then stores it on the created grid plane
   * @return the created grid pane 
   */
  public GridPane createGridPane(){
    
    this.gridButtons = new WegeButton [row][coloumn];
    
    /**loop to create buttons and store then in the array of buttons*/
    for(int i = 0; i < row; i++){
      for(int j = 0; j<coloumn; j++){
        WegeButton b = new WegeButton(100,100);
        b.setOnAction(new GridButtonClick());
        gridButtons[i][j] = b;
      } 
    }
    
    this.gridPane = new GridPane();
    
    /**loop to add the buttons on the created grid plane*/
    for(int i = 0; i<row; i++){
      for(int j = 0; j<coloumn; j++){
        this.gridPane.add(gridButtons[j][i], j , i);
      }
    }
    
    return this.gridPane;
  }
  
  /**
   * Creates a flow plane and adds in the grid created as well as labels. 
   * @return the created flow pane 
   */
  public FlowPane createFlowPane(){
    
    this.deckCard = new WegeButton(100,100);
    this.flowPane = new FlowPane();  
    this.flowPane.getChildren().addAll(createGridPane(),deckCard,landTurn,waterTurn);
    
    return this.flowPane;
    
  }
  
  /**
   * Start method initializes all the values and starts the gui
   * @param the stage
   */
  public void start(Stage stage){
    
    this.deckCards = createDeckCards(row*coloumn, specialCards);
    landTurn.setVisible(false);
    waterTurn.setVisible(false);
    Scene scene = new Scene(createFlowPane());
    stage.setScene(scene);
    stage.show();
    deckCard.setOnAction(new DeckButtonClick());
  }
  
  /**
   * Main method which launches the GUI 
   * @param String args
   */
  public static void main(String[] args){
    /** if statements checking what the user input is and accordingly intializing the needed values for the deck cards */
    if(args.length == 2){
      row = Integer.parseInt(args[0]);
      coloumn = Integer.parseInt(args[1]);
      specialCards = -1;
    }
    else if(args.length == 1){
      row = 6;
      coloumn = 6;
      specialCards = Integer.parseInt(args[0]);
    }
    else if(args.length >= 3) {
      row = Integer.parseInt(args[0]);
      coloumn = Integer.parseInt(args[1]);
      specialCards = Integer.parseInt(args[2]);
    }
    else{
      row = 6;
      coloumn = 6;
      specialCards = -1;
    }
    launch(args);
  }
  
  /**
   * Method that using loops and user initialization creates cards
   * @param the size of cards needed
   * @param the number of special cards needed 
   * @return the deck of cards that will be used in the game
   */
  public LinkedList<WegeCard> createDeckCards(int size, int sc) {
    
    ArrayList deckCards = new ArrayList<WegeCard>(size + 1 + (6*specialCards));
    
    /** loops indetifying what the user input is and accodingly creating the deck*/
    if(sc < 0){
      for(int i=0 ;i < (int)((size/3.0)+0.5); i++){
        deckCards.add(new WegeCard(WegeCard.CardType.WATER,false, false));
        deckCards.add(new WegeCard(WegeCard.CardType.LAND,false, false));
      }
      for(int i = 0; i<3; i++){
        deckCards.add(new WegeCard(WegeCard.CardType.WATER,false, true));
        deckCards.add(new WegeCard(WegeCard.CardType.LAND,false, true));
        deckCards.add(new WegeCard(WegeCard.CardType.COSSACK,false, false));
        deckCards.add(new WegeCard(WegeCard.CardType.BRIDGE,false, false));
      }
      for(int i = 0; i<2; i++){
        deckCards.add(new WegeCard(WegeCard.CardType.WATER,true, false));
        deckCards.add(new WegeCard(WegeCard.CardType.LAND,true, false));
      }
    }
    else{
      for(int i=0 ;i < (int)(((size/3.0)+0.5)); i++){
        deckCards.add(new WegeCard(WegeCard.CardType.WATER,false, false));
        deckCards.add(new WegeCard(WegeCard.CardType.LAND,false, false));
      }
      for(int i = 0; i<sc; i++){
        deckCards.add(new WegeCard(WegeCard.CardType.WATER,false, true));
        deckCards.add(new WegeCard(WegeCard.CardType.LAND,false, true));
        deckCards.add(new WegeCard(WegeCard.CardType.COSSACK,false, false));
        deckCards.add(new WegeCard(WegeCard.CardType.BRIDGE,false, false));
        deckCards.add(new WegeCard(WegeCard.CardType.WATER,true, false));
        deckCards.add(new WegeCard(WegeCard.CardType.LAND,true, false));
      }
    }
    
    /** shuffling the deck and then adding it back to the deck cards field*/
    Collections.shuffle(deckCards);
    for(Object element: deckCards){
      this.deckCards.addToFront((WegeCard)element);
    }                                               
    return this.deckCards;
  }
  
  /** 
   * Class defining what a deck button click should do 
   * @author Jiana Kambo
   */
  private class DeckButtonClick implements EventHandler<ActionEvent>{
    
    /**
     * method defining what actions to carry out if the deck button is clicked
     * @param the action event of the deck button being clicked
     */ 
    public void handle(ActionEvent e ){
      
      WegeButton b = (WegeButton) e.getSource();
      
      /** if staements checkign to see if this is the first grid button click and executing the needed actions */
      if(b.getCard() == null && Wege.this.first == true){
        b.setCard(deckCards.removeFromFront());
        landTurn.setVisible(false);
        waterTurn.setVisible(true);
      }
      /** if staements checkign to see if this is there is a card on the deck and if not what to do */
      if(b.getCard() == null){
        b.setCard(deckCards.removeFromFront());
        landTurn.setVisible(changeLabel);
        waterTurn.setVisible(!changeLabel);
      }
      /** checks to see if a card is present and if so what to do */
      else
        b.rotate();
    }
  }
  
  /**Grid button class defining what should be done if a grid button is clicked */
  private class GridButtonClick implements EventHandler<ActionEvent>{
    
    /* stores the index of the row where the button is clicked */
    private int rowIndex;
    
    /*Stores the coloumn index of the button clicked*/
    private int coloumnIndex; 
    
    /*stores the coloumn index of the adjacent button which has a card*/
    private int coloumnAdIndex; 
    
    /*stores the row index of the adjacent button which has a card*/
    private int rowAdIndex;
    
    /*stores if the button clicked has another button on the left with a card */
    private boolean hasLeft = false;
    
    /*stores if the button clicked has another button on the top with a card */
    private boolean hasTop = false;
    
    /*stores if the button clicked has another button on the bottom with a card */
    private boolean hasBottom = false;
    
    /*stores if the button clicked has another button on the right with a card */
    private boolean hasRight = false;
    
    
    /** 
     * Hnadle method excuting what to do if a button is clicked
     * @param action event of the grid button being clicked 
     */
    public void handle(ActionEvent e ){
      WegeButton b = (WegeButton) e.getSource();
      
      this.indexOf(e);
      this.isAdjacent();
      
      /** if statement checking to see if this is the first card being played and excuting what to do*/
      if(Wege.this.first){
        b.setCard(deckCard.getCard());
        Wege.this.deckCard.setCard(null);
        Wege.this.first = false;
        changeLabel = !changeLabel;
      }
      
      /** if statement checking to see if this is not first card being played and excuting (according to game rule) what to do if button clicked is empty*/
      else if ((deckCard.getCard() != null) && isValid(Wege.this.gridButtons[rowAdIndex][coloumnAdIndex] , e)) {
        
        /** Checking if the deck card is a bridge card and seeing if it can be replayed, if a cosssak card and facing gnome is not present */
        if ((deckCard.getCard().getCardType() == WegeCard.CardType.BRIDGE) && (b.getCard() != null) && ((b.getCard().getCardType() != WegeCard.CardType.COSSACK) /*&& (isFacingGnome(e)==false)*/)) {
          WegeCard replace = b.getCard();
          b.setCard(deckCard.getCard());
          Wege.this.deckCard.setCard(replace);
          changeLabel = !changeLabel;
          landTurn.setVisible(changeLabel);
          waterTurn.setVisible(!changeLabel);
        }
        
        /** if statement checking to see if this is not first card being played and excuting (according to game rule) what to do if button clicked is empty*/
        else if(b.getCard() == null){
          b.setCard(deckCard.getCard());    
          Wege.this.deckCard.setCard(null);
          changeLabel = !changeLabel;
        }
      }
    }
    
    
    /* 
     * Method to find and store the index of the button clicked 
     * @param the action event of the button clicked
     */
    public void indexOf(ActionEvent e){
      
      WegeButton b = (WegeButton) e.getSource();
      
      /* for loops going through the array to see which button is clicked */
      for(int i = 0; i < row; i++){
        for(int j = 0; j<coloumn; j++){
          if (Wege.this.gridButtons[i][j] == b){
            this.rowIndex = i;
            this.coloumnIndex = j;
          }
        }
      }
    } 
    
    /* 
     * Method to find and store the index of the adjacent button which has a card(if present), and returning if said card is present 
     * @return true or false depeding on if an adjacent is present
     */
    public boolean isAdjacent() {
      
      /** checking if the top button has a card and if so storing its index*/
      if ((rowIndex != 0) && ( (gridButtons[rowIndex-1][coloumnIndex]).getCard() != null )){
        hasTop = true; 
        this.rowAdIndex = this.rowIndex-1;
        this.coloumnAdIndex = this.coloumnIndex;
      }
      
      /** checking if the bottom button has a card and if so storing its index*/
      if ((rowIndex != Wege.this.row-1) && ( (gridButtons[rowIndex+1][coloumnIndex]).getCard() != null )){
        hasBottom = true; 
        this.rowAdIndex = this.rowIndex+1;
        this.coloumnAdIndex = this.coloumnIndex;
      }
      
      /** checking if the left button has a card and if so storing its index*/
      if ((coloumnIndex !=  0) && ( (gridButtons[rowIndex][coloumnIndex-1]).getCard() != null )){
        hasLeft = true; 
        this.rowAdIndex = this.rowIndex;
        this.coloumnAdIndex = this.coloumnIndex-1;
      }
      
      /** checking if the right button has a card and if so storing its index*/
      if ((coloumnIndex != Wege.this.coloumn-1)&& ((gridButtons[rowIndex][coloumnIndex+1]).getCard() != null )){
        hasRight = true; 
        this.rowAdIndex =  this.rowIndex;
        this.coloumnAdIndex = this.coloumnIndex+1;
      }
      
      /** checks if a card is on either end*/
      return (hasLeft || hasRight || hasTop || hasBottom);
    }
    
    /*
     * Checks if the where the button is clicked a card can be placed or not. 
     * @param the adjacent card 
     * @param the action event of button clicked 
     * @return if true/false if the move is valid or not
     */ 
    public boolean isValid( WegeButton cardAdjacent,ActionEvent e ){
      
      WegeButton cardPlace = (WegeButton) e.getSource();
      
      if (((this.isAdjacent() == true) && (cardAdjacent.getCard().isWater(Pos.TOP_LEFT) != deckCard.getCard().isWater(Pos.TOP_LEFT))) && ((cardPlace.getCard() == null || deckCard.getCard().getCardType() == WegeCard.CardType.BRIDGE))){
        return true;
      }
 
      else 
        return false;
      
    }
    
    /*
     * another method/way of checking if the move is valid, conditioned to check if a facing gnome is present
     * @param the row of the grid buttons 
     * @param the coloumn of the grid buttons 
     * @return true/false if ti exists or not 
     */ 
    public boolean exists(int row, int coloumn){
      return (Wege.this.gridButtons.length > row && row > -1) && (Wege.this.gridButtons[row].length > coloumn && coloumn > -1) && (Wege.this.gridButtons[row][coloumn].getCard() == null);
    }
    
    /* 
     * checks if a facing gnome is present or not 
     * @param action event of the button clicked 
     * @return true/false if a facing gnome is present or not 
     */ 
    public boolean isFacingGnome(ActionEvent e ){
      
      WegeButton b = (WegeButton)e.getSource();
      Pos loc = b.getCard().getGnomePosition();
      
      if(b.getCard().hasGnome()){
        
        /** If a gnome is present and its location is top right, checking if adjcaent cards have gnomes and their positions. */
        if(loc == Pos.TOP_RIGHT){
          return (((this.exists(this.rowIndex - 1, this.coloumnIndex)) && (Wege.this.gridButtons[this.rowIndex - 1][this.coloumnIndex].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex - 1][this.coloumnIndex].getCard().getGnomePosition() == Pos.BOTTOM_RIGHT)) ||
                  ((this.exists(this.rowIndex, this.coloumnIndex +1 )) && (Wege.this.gridButtons[this.rowIndex][this.coloumnIndex +1 ].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex][this.coloumnIndex+1].getCard().getGnomePosition() == Pos.TOP_LEFT)) ||
                  ((this.exists(this.rowIndex - 1, this.coloumnIndex +1 )) && (Wege.this.gridButtons[this.rowIndex- 1][this.coloumnIndex +1 ].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex- 1][this.coloumnIndex+1].getCard().getGnomePosition() == Pos.BOTTOM_LEFT)));
        }
        /** If a gnome is present and its location is bottom right, checking if adjcaent cards have gnomes and their positions. */
        if(loc == Pos.BOTTOM_RIGHT){
          return (((this.exists(this.rowIndex + 1, this.coloumnIndex)) && (Wege.this.gridButtons[this.rowIndex + 1][this.coloumnIndex].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex + 1][this.coloumnIndex].getCard().getGnomePosition() == Pos.TOP_RIGHT)) ||
                  ((this.exists(this.rowIndex, this.coloumnIndex +1 )) && (Wege.this.gridButtons[this.rowIndex][this.coloumnIndex +1 ].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex][this.coloumnIndex+1].getCard().getGnomePosition() == Pos.BOTTOM_LEFT)) ||
                  ((this.exists(this.rowIndex + 1, this.coloumnIndex +1 )) && (Wege.this.gridButtons[this.rowIndex + 1][this.coloumnIndex +1 ].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex + 1][this.coloumnIndex+1].getCard().getGnomePosition() == Pos.TOP_RIGHT)));
        }
        
        /** If a gnome is present and its location is bottom left, checking if adjcaent cards have gnomes and their positions. */
        if(loc == Pos.BOTTOM_LEFT){
          return (((this.exists(this.rowIndex + 1, this.coloumnIndex)) && (Wege.this.gridButtons[this.rowIndex + 1][this.coloumnIndex].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex + 1][this.coloumnIndex].getCard().getGnomePosition() == Pos.TOP_LEFT)) ||
                  ((this.exists(this.rowIndex, this.coloumnIndex -1 )) && (Wege.this.gridButtons[this.rowIndex][this.coloumnIndex -1 ].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex][this.coloumnIndex-1].getCard().getGnomePosition() == Pos.BOTTOM_RIGHT)) ||
                  ((this.exists(this.rowIndex + 1, this.coloumnIndex -1 )) && (Wege.this.gridButtons[this.rowIndex + 1][this.coloumnIndex -1 ].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex + 1][this.coloumnIndex-1].getCard().getGnomePosition() == Pos.TOP_RIGHT)));
          
        }
        
        /** If a gnome is present and its location is top left, checking if adjcaent cards have gnomes and their positions. */
        else{
          return (((this.exists(this.rowIndex - 1, this.coloumnIndex)) && (Wege.this.gridButtons[this.rowIndex - 1][this.coloumnIndex].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex - 1][this.coloumnIndex].getCard().getGnomePosition() == Pos.BOTTOM_RIGHT)) ||
                  ((this.exists(this.rowIndex, this.coloumnIndex + 1 )) && (Wege.this.gridButtons[this.rowIndex][this.coloumnIndex + 1 ].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex][this.coloumnIndex+1].getCard().getGnomePosition() == Pos.TOP_LEFT)) ||
                  ((this.exists(this.rowIndex - 1, this.coloumnIndex +1 )) && (Wege.this.gridButtons[this.rowIndex - 1][this.coloumnIndex +1 ].getCard().hasGnome()) && (Wege.this.gridButtons[this.rowIndex - 1][this.coloumnIndex+1].getCard().getGnomePosition() == Pos.BOTTOM_LEFT)));
        }
      }
      else{
        return false ; 
      }
    }
    
  }
}





