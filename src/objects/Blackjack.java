package objects;

import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {
	private ArrayList<Player> players = new ArrayList<>();
	private Player dealer = new LocalPlayer();
	private Deck deck = new Deck();

	public  static void main(String[] args) {
		new Blackjack().run();

	}

	public Blackjack() {
	}

    public void run() {
		for(int i = 1; i < 5; i++){
			players.add(new Player("Player" + i));
		}
		boolean playing = true;
	    Scanner scan1 = new Scanner(System.in);
		while(playing){
			playOneRound();
			System.out.println("Play Again? (Y or N)");
			String response = scan1.next();
			if(!response.equalsIgnoreCase("y")){
				playing = false;
			}
			if(deck.size() <= 13){
				deck.reshuffle();
			}
		}
	    scan1.close();
    }

     void playOneRound(){
		System.out.println("Starting Cards:");
		dealCards();
		printCardsHidden();
		runHands();
		runDealerHand();
		System.out.println("Final Cards:");
		printCards();
		for(Player p: players){
			p.clearHand();
		}
	    dealer.clearHand();
    }


    private void runHands(){
		for(Player p : players){
			while(p.hand().score() < 17){
				p.hand().add(deck.deal());
			}
		}
    }

    private void runDealerHand(){
    	while(dealer.hand().score() < 17){
			dealer.hand().add(deck.deal());
	    }
    }

	private void dealCards(){
		for(int i = 0; i < 2; i ++){
			for(Player p: players) {
				p.deal(deck.deal());
			}
			dealer.deal(deck.deal());
		}
	}

	private void printCards(){
		System.out.println("Dealer Cards: " + dealer.hand().score() + " points, " + dealer.hand().toString());
		for(Player p: players){
			System.out.println(p.name() + ": " + p.hand().score() + " points, " + p.hand().toString() + " - " + printResult(p.hand().score(), dealer.hand().score()));
		}
	}

    private void printCardsHidden(){
		System.out.println("Dealer Cards: " + dealer.hand().toStringDealer());
		for(Player p: players){
			System.out.println(p.name() + ": " + p.hand().score() + " points, " + p.hand().toString());
		}
    }

	private String printResult(int playerScore, int dealerScore){
	    if (playerScore == dealerScore || (playerScore > 21 && dealerScore > 21)) {
		    return ("Push");
	    } else if (playerScore > 21 || (dealerScore > playerScore && dealerScore <= 21)) {
		    return ("Dealer Wins");
	    } else {
		    return ("Player Wins!");
	    }
	}

}
