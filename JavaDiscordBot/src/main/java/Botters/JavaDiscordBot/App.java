package Botters.JavaDiscordBot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import com.fasterxml.jackson.databind.deser.ValueInstantiator.Gettable;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.RequestFuture;


public class App extends ListenerAdapter {
	
	private boolean stop;
	private static JDA bot;
	private final static String botID = "420395938272641034";
	
	// tic-tac-toe variables
	private String[][] ttt;
	private static boolean tic = false;
	private static int whosTurn = 1;
	private static int numTurn = 0;
	private static Message ticMsg;
	
	private final String KAPPA = "420687983365193729";
	private final String N1 = "1⃣";
	private final String N2 = "2⃣";
	private final String N3 = "3⃣";
	private final String N4 = "4⃣";
	private final String N5 = "5⃣";
	private final String N6 = "6⃣";
	private final String N7 = "7⃣";
	private final String N8 = "8⃣";
	private final String N9 = "9⃣";
	
	
    public static void main( String[] args ) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException {
    	bot = new JDABuilder(AccountType.BOT).setToken("").buildBlocking();
    	bot.addEventListener(new App());
//    	begForDonations(1800000, bot); // 30 minutes
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
// 		brianStuff(e);
    	stop = false;
    	checkCurses(e);
    	if(stop) return;
    	checkSlap(e);
    	checkHelp(e);
    	checkRoll(e);
    	checkHardstuck(e);
		checkGreets(e);
		briantic(e);
		checkTicTacToe(e);
		addReactionsTic(e);
    }
    
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
    	ticMove(e);
    }
    
    private void brianStuff(MessageReceivedEvent e)
    {
    	if(e.getAuthor().isBot());
    	if(e.getMessage().getContentDisplay().equals("test")) {
    		e.getChannel().sendMessage("hello").queue();
    	}
    	
    	if(e.getMessage().getContentDisplay().equals("test1")) {
    		e.getChannel().sendMessage("test2 " + e.getAuthor().getAsMention()).queue();
    	}
    	if(getMessage(e).startsWith("!") && getMessage(e).indexOf("roll") == 1) {
    		String Rollss = getMessage(e).substring(6);
    		int i = Integer.parseInt(Rollss);
    		sendMessage(e, "You rolled a " + (int)(Math.random()*i+1));
    	}
    }

    private void briantic(MessageReceivedEvent e) {
    	if(e.getAuthor().isBot())return;
    	String[] hand = {"rock","scissors", "paper"};
    	String hands = "I choose ";
    	String selfhand = "You chose ";
    	if(getMessage(e).startsWith("!") && getMessage(e).indexOf("rps") == 1){
    		hands +=  hand[(int)(Math.random()*hand.length)];
    		sendMessage(e, selfhand + getMessage(e).substring(5) + " and " + hands);	 
    	}
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //START OF TIC-TAC-TOE CODE
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addReactionsTic(MessageReceivedEvent e) {
		if(!e.getAuthor().isBot())return;
		if(getMessage(e).startsWith(":")) {
			e.getMessage().addReaction(N1).queue();
			e.getMessage().addReaction(N2).queue();
			e.getMessage().addReaction(N3).queue();
			e.getMessage().addReaction(N4).queue();
			e.getMessage().addReaction(N5).queue();
			e.getMessage().addReaction(N6).queue();
			e.getMessage().addReaction(N7).queue();
			e.getMessage().addReaction(N8).queue();
			e.getMessage().addReaction(N9).queue();
			tic = true;
			ticMsg = e.getMessage();
		}
	}


	private void ticMove(MessageReactionAddEvent e) {
		if(!tic) return;
		if(e.getUser().getId().equals(botID)) return;
		int pick = getPick(e);
		if(pick < 0 || pick > 8) return;
		int r = pick/3;
		int c = pick%3;
		switch(whosTurn) {
			case 1: // x's turn
				if(ttt[r][c] == ":white_medium_small_square:") {
					ttt[r][c] = ":heavy_multiplication_x:";
					whosTurn = 2;
				}
			case 2: // o's turn
				if(ttt[r][c] == ":white_medium_small_square:") {
					ttt[r][c] = ":white_circle:";
					whosTurn = 1;
				}
		}
		String s = "";
		for(int row = 0; row < ttt.length; row ++) { // loop to add emotes to the string to be printed
			for(int col = 0; col < ttt[0].length; col ++) {
				s += ttt[row][col];
			}
			s += "\n";
		}
		numTurn ++;
		ticMsg.editMessage(s).queue();
		String gameOverMsg = ticGameOver(e);
		if(!gameOverMsg.isEmpty()) e.getChannel().sendMessage(gameOverMsg).queue();
	}

	private String ticGameOver(MessageReactionAddEvent e) {
		System.out.println(numTurn);
		String s = "";
		if(xWin()) {
			s += "\nX wins!";
		}else if(oWin()) {
			s += "\nO wins!";
		}else if(numTurn == 9) s += "\nThis game ended in a draw!";
		if(!s.isEmpty()) {
			tic = false;
			whosTurn = 1;
			numTurn = 0;
		}
		return s;
	}
	

	private boolean oWin() {
		String o = ":white_circle:";
		return false;
	}

	private boolean xWin() {
		String x = ":heavy_multiplication_x:";
		return false;
	}

	private int getPick(MessageReactionAddEvent e) {
		String emoteReacted = e.getReactionEmote().getName();
		if(emoteReacted.equals(N1)) return 0;
		else if(emoteReacted.equals(N2)) return 1;
		else if(emoteReacted.equals(N3)) return 2;
		else if(emoteReacted.equals(N4)) return 3;
		else if(emoteReacted.equals(N5)) return 4;
		else if(emoteReacted.equals(N6)) return 5;
		else if(emoteReacted.equals(N7)) return 6;
		else if(emoteReacted.equals(N8)) return 7;
		else if(emoteReacted.equals(N9)) return 8;
		return -1;
	}

			
		
		
	

	private void checkTicTacToe(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) return;
		if(!e.getMessage().getContentDisplay().startsWith("`tic")) return;
		String s = "";
		if(!tic) { // create a new game is tic is false
			ttt = new String[3][3];
			for(int row = 0; row < ttt.length; row ++) { // loop to create new board game
				for(int col = 0; col < ttt[0].length; col ++) {
					ttt[row][col] = ":white_medium_small_square:";
					s += ttt[row][col];
				}
				s += "\n";
			}
			sendMessage(e, s);
		} else { // proceed if there is already a game
			if(e.getMessage().getContentDisplay().equals("`tic end")) { // statement for if they want to end the games
				sendMessage(e, "This game of tic-tac-toe has ended.");
				tic = false; // turn to false to say that there is no tic-tac-tie running
				whosTurn = 1; // set turn back to starting with x
				return;
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//END OF TIC-TAC-TOE CODE
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void checkSlap(MessageReceivedEvent e) {
        if(e.getAuthor().isBot()) return;
        String slaps = getMention(e) + " slaps ";
        String[] itemList = {" with a fish.", " with their dick.", " with a stick.", " with their ass.", " with RP.", " all the way back to China.", " with a splash of water.",
        		" with a deck of cards."};
        List<Member> members = e.getMessage().getMentionedMembers();
        if(e.getMessage().getContentDisplay().startsWith("`") && e.getMessage().getContentDisplay().indexOf("slap") == 1) {
        	if(members.size() == 1) {
                slaps += members.get(0).getAsMention() + itemList[(int)(Math.random()*itemList.length)];
                sendMessage(e, slaps);
                return;
        	}
            for(int i = 0; i < members.size(); i ++) {
                    slaps += i == members.size()-1 ? " and " + members.get(i).getAsMention() + itemList[(int)(Math.random()*itemList.length)] : members.get(i).getAsMention() + ", ";
            }
            sendMessage(e, slaps);
        }
    }
	
	private void checkHelp(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) return;
		if(e.getMessage().getContentDisplay().startsWith("`") && e.getMessage().getContentDisplay().indexOf("help") == 1) {
			sendMessage(e, getMention(e) + "``` " + "Here is a list of my commands:\n" +
					"`help - prompts for this window \n" + 
					"`roll - rolls a 6 sided dice \n" +
					"`hardstuck - someone who is hardstuck in league \n" +
					"`slap @user @user1 - slap someone with something \n" +
					"```");
		}
	}
	
	private void checkHardstuck(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) return;
		if(e.getMessage().getContentDisplay().startsWith("`") && e.getMessage().getContentDisplay().indexOf("hardstuck") == 1) {
			sendMessage(e, bot.getUserById("152954300933472256").getAsMention() + " is hardstuck " + bot.getEmoteById(KAPPA).getAsMention());
		}
	}
	
	
	private void checkRoll(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) return;
		if(e.getMessage().getContentDisplay().startsWith("`") && e.getMessage().getContentDisplay().indexOf("roll") == 1) {
			int roll = (int)(Math.random()*6+1);
			switch(roll) {
				case 0: 
					sendMessage(e, getMention(e) + " rolled a :zero:");
					break;
				case 1:
					sendMessage(e, getMention(e) + " rolled a :one:");
					break;
				case 2:
					sendMessage(e, getMention(e) + " rolled a :two:");
					break;
				case 3:
					sendMessage(e, getMention(e) + " rolled a :three:");
					break;
				case 4:
					sendMessage(e, getMention(e) + " rolled a :four:");
					break;
				case 5:
					sendMessage(e, getMention(e) + " rolled a :five:");
					break;
				case 6:
					sendMessage(e, getMention(e) + " rolled a :six:");
					break;
			}
		}
	}
	
	private static void begForDonations(int ms, final JDA bot) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {

		            @Override
		            public void run() {
		            	bot.getTextChannelById("152954629993398272").sendMessage("If you like this bot, please consider donating RP to the creators, " 
		            	+ bot.getUserById("152957206025863168").getAsMention() + " and " + bot.getUserById("152954180984635392").getAsMention() + ".").queue();
		            }
		        }, 100, ms); // ms = milleseconds until the next message, can alter this in the main function
	}

    public void checkCurses(MessageReceivedEvent e) {
    	String[] swears = {"asshole", "bastard", "bitch", "crap", "cunt", "fuck", "nigga","nigger","shit","son of a bitch","faggot"};
    	if(e.getAuthor().isBot()) return;
    	for(int i = 0; i < swears.length; i ++) { // check for all possible greetings
			if(getMessage(e).toLowerCase().indexOf(swears[i]) > -1) {
				sendMessage(e, getMention(e) + ", watch your mouth!");
				stop = true;
				break;
			}
    				
    	}
    }
    
	public void checkGreets(MessageReceivedEvent e) {
		String[] greetings = {"Hello ", "Hi ", "Greeting ", "Konichiwa ", "Howdy ", "Ni hao ", "Hola ", "Sup ", "Yo ", "Anyoung haseyo "};
    	if(e.getAuthor().isBot()) return;
    	for(int i = 0; i < greetings.length; i ++) { // check for all possible greetings
			if(getMessage(e).toLowerCase().indexOf(greetings[i].toLowerCase()) == 0) {
				sendMessage(e, greetings[(int) (Math.random()*greetings.length)] + " " + getMention(e) + "!");
				break;
			}
    				
    	}
    }
	
	public String getMention(MessageReceivedEvent e) {
		return e.getAuthor().getAsMention();
	}
	public String getMessage(MessageReceivedEvent e) {
        return e.getMessage().getContentDisplay();
    }

	public void sendMessage(MessageReceivedEvent e, String s) {
        e.getChannel().sendMessage(s).queue();
    }
	
}
