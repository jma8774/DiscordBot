package Botters.JavaDiscordBot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class App extends ListenerAdapter {
	
	private boolean stop;
	private final String KAPPA = "420687983365193729";
	private static JDA bot;
	private String[][] ttt;
	private static boolean tic;
	private static int ticTurn;
	private static Message ticMsg;
	
    public static void main( String[] args ) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException {
    	bot = new JDABuilder(AccountType.BOT).setToken("").buildBlocking();
    	bot.addEventListener(new App());
//    	begForDonations(1800000, bot); // 30 minutes
    	tic = false;
    	ticTurn = 1;
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
    	brianStuff(e);
    	stop = false;
    	checkCurses(e);
    	if(stop) return;
    	checkSlap(e);
    	checkHelp(e);
    	checkRoll(e);
    	checkHardstuck(e);
		checkGreets(e);
//		checkTicTacToe(e);
//		randomReaction(e);
    }
    
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
    	
    }

	private void randomReaction(MessageReceivedEvent e) {
		e.getMessage().addReaction(e.getGuild().getEmoteById(KAPPA)).queue();
	}

	private void brianStuff(MessageReceivedEvent e)
	{
		// TODO Auto-generated method stub
		if(e.getAuthor().isBot());
		if(e.getMessage().getContentDisplay().equals("test")) {
			e.getChannel().sendMessage("hello").queue();
    	}
    	
		if(e.getMessage().getContentDisplay().equals("test1")) {
    		e.getChannel().sendMessage("test2 " + e.getAuthor().getAsMention()).queue();
    	}
		if(e.getMessage().getContentDisplay().startsWith("!") && getMessage(e).indexOf("tic") == 1) {
		String SSS = "";
		ttt = new String[3][3];
			for(int row = 0; row < ttt.length; row ++) { // loop to create new board game
				for(int col = 0; col < ttt[0].length; col ++) {
					ttt[row][col] = ":white_medium_small_square:";
					sendMessage(e, SSS += ttt[row][col]);
				
    		}
    
		String Rollss = getMessage(e).substring(6);
		int i = Integer.parseInt(Rollss);
		if(getMessage(e).startsWith("!") && getMessage(e).indexOf("roll") == 1) {
			sendMessage(e, "You rolled a " + (int)(Math.random()*i+1));
		}
			}
			}
		}
		
	

	private void checkTicTacToe(MessageReceivedEvent e) {
		if(e.getMessage().getContentDisplay().startsWith(":")) ticMsg = e.getMessage(); // reference to previous message so we can delete it
		if(e.getAuthor().isBot()) return;
		if(!e.getMessage().getContentDisplay().startsWith("`") && !(e.getMessage().getContentDisplay().indexOf("tic") == 1)) return;
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
			tic = true; // tic is to make sure that there is only one tic-tac-toe running at once
		} else { // proceed if there is already a game
			ticMsg.delete().queue(); // delete the previous tic-tac-toe board message
			if(e.getMessage().getContentDisplay().equals("`tic end")) { // statement for if they want to end the games
				sendMessage(e, "This game of tic-tac-toe has ended.");
				tic = false; // turn to false to say that there is no tic-tac-tie running
				ticTurn = 1; // set turn back to starting with x
				return;
			}
			if(e.getMessage().getContentDisplay().length() < 6) return;
			int pick = Integer.parseInt(e.getMessage().getContentDisplay().substring(5, 6)) - 1;
			if(pick < 0 || pick > 8) return;
			int r = pick/3;
			int c = pick%3;
			switch(ticTurn) {
				case 1: // x's turn
					if(ttt[r][c] == ":white_medium_small_square:") {
						ttt[r][c] = ":heavy_multiplication_x:";
						ticTurn = 2;
					}
				case 2: // o's turn
					if(ttt[r][c] == ":white_medium_small_square:") {
						ttt[r][c] = ":white_circle:";
						ticTurn = 1;
					}
			}
			for(int row = 0; row < ttt.length; row ++) { // loop to add emotes to the string to be printed
				for(int col = 0; col < ttt[0].length; col ++) {
					s += ttt[row][col];
				}
				s += "\n";
			}
		}
		e.getMessage().delete().queue(); // delete the user's message
		sendMessage(e, s); // send out the new tic-tac-toe board 
		if(checkWin() != null) { // send another message and end the game if someone wins/tie
			sendMessage(e, checkWin());
			tic = false;
			ticTurn = 1;
		}
	}
	
	private String checkWin() {
		return null;
	}
	
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
