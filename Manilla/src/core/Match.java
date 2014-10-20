package core;

import exception.InvalidCardException;
import player.Player;
import player.Team;

/**
 * Main class of the system.
 * As for now, the run() method simulates a single round.
 * @author Daan
 */
public class Match {
	
	private Player p1;
	private Player p2;
	private Player p3;
	private Player p4;
	
	public Match(Player p1, Player p2, Player p3, Player p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}

	public void run() throws InvalidCardException {
		
		System.out.println("========== PLAYERS ==========");
		System.out.println(p1 + " : " + p1.identify());
		System.out.println(p2 + " : " + p2.identify());
		System.out.println(p3 + " : " + p3.identify());
		System.out.println(p4 + " : " + p4.identify());
		
		Team t1 = new Team(p1,p2);
		Team t2 = new Team(p3,p4);
		
		System.out.println("========== TEAMS ==========");
		System.out.println("Team 1: " + p1 + ", " + p2);
		System.out.println("Team 2: " + p3 + ", " + p4);
		
		Deck deck = new Deck();
		deck.shuffle();
		Round r1 = new Round(t1,t2,p1,deck);
		
		// score calculation
		r1.run();
		int roundScoreTeam1 = 0;
		int roundScoreTeam2 = 0;
		if ( t1.getPoolScore() > 30 ) {
			roundScoreTeam1  = (t1.getPoolScore() - 30)*r1.getMultiplier();
		} else {
			roundScoreTeam2 = (t2.getPoolScore() - 30)*r1.getMultiplier();
		}
		t1.increaseTotalScore(roundScoreTeam1);
		t2.increaseTotalScore(roundScoreTeam2);
		t1.getPlayer1().notifyOfRoundScore(roundScoreTeam1, roundScoreTeam2, t1.getTotalScore(), t2.getTotalScore() );
		t1.getPlayer2().notifyOfRoundScore(roundScoreTeam1, roundScoreTeam2, t1.getTotalScore(), t2.getTotalScore() );
		t2.getPlayer1().notifyOfRoundScore(roundScoreTeam2, roundScoreTeam1, t2.getTotalScore(), t1.getTotalScore() );
		t2.getPlayer2().notifyOfRoundScore(roundScoreTeam2, roundScoreTeam1, t2.getTotalScore(), t1.getTotalScore() );
		
		// recalculate the deck
		deck = new Deck(t1.getPool(),t2.getPool());
		t1.getPool().reset();
		t2.getPool().reset();
		
		System.out.println("========== SCORES ==========");
		System.out.println("Team 1: " + t1.getTotalScore());
		System.out.println("Team 2: " + t2.getTotalScore());
	}

}