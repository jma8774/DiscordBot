package Botters.JavaDiscordBot;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;



public class App extends ListenerAdapter {
	
	private boolean stop;
	private static JDA bot;
	private final static String botID = "420395938272641034";
	
	// tic-tac-toe variables
	private String[][] ttt;
	private static boolean ticRunning = false;
	private static User whosTurn;
	private static int numTurn = 0;
	private static User player1;
	private static User player2;
	private static Message ticMsg;
	private static Message savedhand;
	
	//emote string and unicode
	private final String KAPPA = "420687983365193729";
	private final String BIBLETHUMP = "420687417272565770";
	private final String JEBAITED = "420687706616627200";
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
//    	begForDonations(1800000); // 30 minutes
//    	randomName(2000);
    }
    

	@Override
    public void onMessageReceived(MessageReceivedEvent e) {
    	stop = false;
    	checkCurses(e);
    	if(stop) return;
    	checkSlap(e);
    	checkHelp(e);
    	checkRoll(e);
    	checkHardstuck(e);
		checkGreets(e);
		brianroll(e); //command for roll #
		brianrps(e); //command for rps
		checkrps(e); //outcome for rps
		briannick(e);
		brianbj(e);
		briancheckbj(e);
		initializeTicTacToe(e); //starts the tic-tac-toe game by sending the board message
		addReactionsTic(e); //add emotes to the message once the event is recieved
    }
    
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
    	ticMove(e); //when people touch the emotes, it would trigger this function if there is a tic-tac-toe game going on
    }
    
    
    private void brianbj(MessageReceivedEvent e) {
    	String[] Cards = {"A" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" , "10" ,"J" , "Q", "K"};
    	String card1 = "";
    	String card2 = "";
    	String card3 = "";
    	String NPCcard1 = "";
    	String NPCcard2 = "";
    	String NPCcard3 = "";
    	for(int i = 0; i<1; i++)
    	card1 += Cards[(int)(Math.random()*Cards.length)];
    	card2 += Cards[(int)(Math.random()*Cards.length)];
    	card3 += Cards[(int)(Math.random()*Cards.length)];
    	NPCcard1 += Cards[(int)(Math.random()*Cards.length)];
    	NPCcard2 += Cards[(int)(Math.random()*Cards.length)];
    	NPCcard3 += Cards[(int)(Math.random()*Cards.length)];
    	if(getMessage(e).startsWith("!") && getMessage(e).indexOf("blackjack")==1) {
    	sendMessage(e, "Your Hand: \n" 
    					+ "`" + card1 + "`" 
    					+ " " 
    					+ "`" + card2 + "`"
    					+ "\nBots Hand: \n"
    					+ "`" + NPCcard1 + "`" 
    					+ " " 
    					+ "`" + NPCcard2 + "`");
    	}
    	if(e.getAuthor().isBot()) {
    		savedhand = e.getMessage();
    	}	
    }
    	
    
    private void briancheckbj (MessageReceivedEvent e) {
    	String[] Cards = {"A" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" , "10" ,"J" , "Q", "K"};
    	String card1 = "";
    	String card2 = "";
    	String card3 = "";
    	String NPCcard1 = "";
    	String NPCcard2 = "";
    	String NPCcard3 = "";
    	for(int i = 0; i<1; i++)
    	card1 += Cards[(int)(Math.random()*Cards.length)];
    	card2 += Cards[(int)(Math.random()*Cards.length)];
    	card3 += Cards[(int)(Math.random()*Cards.length)];
    	NPCcard1 += Cards[(int)(Math.random()*Cards.length)];
    	NPCcard2 += Cards[(int)(Math.random()*Cards.length)];
    	NPCcard3 += Cards[(int)(Math.random()*Cards.length)];
    	if(getMessage(e).startsWith("!") && getMessage(e).indexOf("hit")==1) {
			savedhand.editMessage("Your Hand: \n" 
					+ "`" + card1 + "`" 
					+ " " 
					+ "`" + card2 + "`"
					+ " " 
					+ "`" + card3 + "`"
					+ "\nBots Hand: \n"
					+ "`" + NPCcard1 + "`" 
					+ " " 
					+ "`" + NPCcard2 + "`"
					+ " " 
					+ "`" + NPCcard3 + "`").queue();
		}
    }
    private void briannick(MessageReceivedEvent e) {
    	
    	if(getMessage(e).startsWith("!") && getMessage(e).indexOf("nick") ==1) {
    		
    		String[] nick = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9","0","!","#","$","%","&","A","B","C","D","E","F","G","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    		String nickname = "";
    		String nicknames = "";
    		String nicknamess = "";
    		String nicknamesss = "";
    		final String Matt = "169259999300812800";
    		final String Mary = "197426338791948288";
    		final String Sam = "195347541452128257";
    		final String Steven = "152954300933472256";
    		for(int i = 0; i < 10; i++)
    		nickname += nick[(int)(Math.random()*nick.length)];
    		for(int i = 0; i <10; i++)
    		nicknames += nick[(int)(Math.random()*nick.length)];
    		for(int i = 0; i <10; i++)
    		nicknamess += nick[(int)(Math.random()*nick.length)];{
    		final Guild guild = bot.getGuildById("152954629993398272");
    		String past = guild.getMemberById(Matt).getNickname();
    		String pastmary = guild.getMemberById(Mary).getNickname();
    		String pastsam = guild.getMemberById(Sam).getNickname();
    		guild.getController().setNickname(guild.getMemberById(Mary), nickname).queue();
    		guild.getController().setNickname(guild.getMemberById(Matt), nicknames).queue();
    		guild.getController().setNickname(guild.getMemberById(Sam), nicknamess).queue();
    		guild.getController().setNickname(guild.getMemberById(Steven), "hardstuck").queue();
    		sendMessage(e, "**" + past + "**" + " changed to " + bot.getUserById(Matt).getAsMention());
    		sendMessage(e, "**" + pastmary + "**" + " changed to " + bot.getUserById(Mary).getAsMention());
    		sendMessage(e, "**" + pastsam + "**" + " changed to " + bot.getUserById(Sam).getAsMention());
    		sendMessage(e, "**Steven still **" + bot.getUserById(Steven).getAsMention() + bot.getEmoteById(KAPPA).getAsMention());
    				
    			}
    		}
    	}
    		
    private void brianroll(MessageReceivedEvent e) {
    	if(getMessage(e).startsWith("!") && getMessage(e).indexOf("roll") == 1) {
    		String Rollss = getMessage(e).substring(6);
    		int i = Integer.parseInt(Rollss);
    		sendMessage(e, "You rolled a " + (int)(Math.random()*i+1));
    	}
    }

    private void brianrps(MessageReceivedEvent e) {
    	if(e.getAuthor().isBot())return;
    	String[] hand = {"***rock***","***scissors***", "***paper***"};
    	String hands = "I chose ";
    	String selfhand = "You chose ";
    	hands +=  hand[(int)(Math.random()*hand.length)];
    	if(getMessage(e).startsWith("`") && getMessage(e).indexOf("rps") == 1){
    	if(getMessage(e).substring(5).equalsIgnoreCase("rock")) {
    		sendMessage(e, selfhand + "***" + getMessage(e).substring(5) + "***" + " and "+ hands);return;
    			}
    	if(getMessage(e).substring(5).equalsIgnoreCase("paper")) {
    		sendMessage(e, selfhand + "***" + getMessage(e).substring(5) + "***" + " and "+ hands);return;
    			}
    	if(getMessage(e).substring(5).equalsIgnoreCase("scissors")) {
    		sendMessage(e, selfhand + "***" + getMessage(e).substring(5) + "***" + " and "+ hands);return;
    			}
    	if(!getMessage(e).substring(5).equalsIgnoreCase("rock") && !getMessage(e).substring(5).equalsIgnoreCase("paper") && !getMessage(e).substring(5).equalsIgnoreCase("scissors")) {
    		sendMessage(e, "Please pick rock, paper, or scissors");
    			}
    		}	
    	}
    private void checkrps(MessageReceivedEvent e) {
    	if(e.getMessage().getContentStripped().equalsIgnoreCase("You chose rock and I chose scissors")) {
    		sendMessage(e, "***You Win***");
    	}
    	if(e.getMessage().getContentStripped().equalsIgnoreCase("You chose rock and I chose paper")) {
    		sendMessage(e, "***You Lose***");
    	}
    	if(e.getMessage().getContentStripped().equalsIgnoreCase("You chose rock and I chose rock")) {
    		sendMessage(e, "***Draw***");
    	}
    	if(e.getMessage().getContentStripped().equalsIgnoreCase("You chose paper and I chose scissors")) {
    		sendMessage(e, "***You Lose***");
    	}
    	if(e.getMessage().getContentStripped().equalsIgnoreCase("You chose paper and I chose paper")) {
    		sendMessage(e, "***Draw***");
    	}
    	if(e.getMessage().getContentStripped().equalsIgnoreCase("You chose paper and I chose rock")) {
    		sendMessage(e, "***You Win***");
    	}
    	if(e.getMessage().getContentStripped().equalsIgnoreCase("You chose scissors and I chose scissors")) {
    		sendMessage(e, "***Draw***");
    	}
    	if(e.getMessage().getContentStripped().equalsIgnoreCase("You chose scissors and I chose paper")) {
    		sendMessage(e, "***You Win***");
    	}
    	if(e.getMessage().getContentStripped().equalsIgnoreCase("You chose scissors and I chose rock")) {
    		sendMessage(e, "***You Lose***");
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
			ticRunning = true; // game official starts
			ticMsg = e.getMessage(); // saves this message in a variable to edit it later
		}
	}

	private void ticMove(MessageReactionAddEvent e) {
		if(!ticRunning) return; // stops running the function when the game is not running
		if(e.getUser().getId().equals(botID)) return; // makes sure to not log the events from the bot
		if(e.getUser() != whosTurn) return;
		int pick = getPick(e); // determine which emote they reacted to and return the integer value of it
		if(pick < 0 || pick > 8) return;
		String gameOverMsg = "";
		int r = pick/3; 
		int c = pick%3;
		// ill explain how r and c works, lets say you want the middle tile which is in array [1][1], the pick int that corresponds to that is 4
		// int(4/3) is rounded to 1 and 4mod3 is equal to 1. that's how we get array [1][1], which would give us the position to the middle tile of the board
		// this works for other cases too, try it urself
		if(whosTurn == player1) {
			if(ttt[r][c] == ":white_small_square:") {
				ttt[r][c] = ":heavy_multiplication_x:";
				gameOverMsg = ticGameOver(e);
				whosTurn = player2;
				numTurn ++;
			}
		} else {
			if(ttt[r][c] == ":white_small_square:") {
				ttt[r][c] = ":white_circle:";
				gameOverMsg = ticGameOver(e);
				whosTurn = player1;
				numTurn ++;
			}
		}
		String s = "";
		for(int row = 0; row < ttt.length; row ++) { // loop to add x/o emotes to the string to be printed
			for(int col = 0; col < ttt[0].length; col ++) {
				s += ttt[row][col];
			}
			s += "\n";
		}
		ticMsg.editMessage(s).queue();
		if(!gameOverMsg.isEmpty()) e.getChannel().sendMessage(gameOverMsg).queue();
	}

	private String ticGameOver(MessageReactionAddEvent e) { // this function determines if the game is over or not, if it is over it returns an empty string
		String s = "";
		if(whosTurn == player1 && checkWin(":heavy_multiplication_x:")) {
			s += e.getUser().getAsMention() + " wins!";
		}else if(whosTurn == player2 && checkWin(":white_circle:")) {
			s += e.getUser().getAsMention() + " wins!";
		}else if(numTurn == 9) s += "This game ended in a draw!";
		if(!s.isEmpty()) {
			ticRunning = false;
			numTurn = 0;
		}
		return s; 
	}
	
	private boolean checkWin(String emote) { // determine who wins based on the string passed in
		for(int r = 0; r < ttt.length; r++) {
			for(int c = 0; c < ttt[0].length; c++) {
				if(ttt[r][c].equals(emote)) {
					if(r - 1 > -1 && c - 1 > -1 && r + 1 < ttt.length && c + 1 < ttt.length) { // check to make sure not out of bounds
							if(ttt[r][c].equals(ttt[r-1][c-1]) && ttt[r][c].equals(ttt[r+1][c+1])) return true; // \ diagonal	[0][0] [1][0] [2][0]
							if(ttt[r][c].equals(ttt[r+1][c-1]) && ttt[r][c].equals(ttt[r-1][c+1])) return true; // / diagonal	[0][1] [1][1] [2][1]
					}																							//				[0][2] [1][2] [2][2]
					if(c - 1 > -1 && c + 1 < ttt.length) { // check to make sure not out of bounds
						if(ttt[r][c].equals(ttt[r][c-1]) && ttt[r][c].equals(ttt[r][c+1])) return true; // vertical	
					}
					if(r - 1 > -1 && r + 1 < ttt.length) {
						if(ttt[r][c].equals(ttt[r-1][c]) && ttt[r][c].equals(ttt[r+1][c])) return true; // horizontal
					}
				}
			}
		}
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

	private void initializeTicTacToe(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) return;
		String s = "";
		if(!ticRunning && e.getMessage().getContentDisplay().startsWith("`tic")) { // create a new game
			if(e.getMessage().getMentionedUsers().size() < 1) { // if they dont mention someone to play with, return
				sendMessage(e, "You need to mention someone to play with using @someone");
				return;
			}
			player1 = e.getAuthor();
			player2 = e.getMessage().getMentionedUsers().get(0);
			if(player1 == player2) { // if they want to play with themself, return
				sendMessage(e, "Why are you playing with yourself? " + e.getGuild().getEmoteById(BIBLETHUMP).getAsMention());
				return;
			}
			if(player2.isBot()) { // if they want to play against a bot, return
				sendMessage(e, "Why are you playing with a robot? You wanna get rekt? " + e.getGuild().getEmoteById(JEBAITED).getAsMention());
				return;
			}
			whosTurn = player1;
			sendMessage(e, "Player 1  (X): " + player1.getAsMention() + "\nPlayer 2 (O): " + player2.getAsMention());
			ttt = new String[3][3];
			for(int row = 0; row < ttt.length; row ++) { // loop to create new board game
				for(int col = 0; col < ttt[0].length; col ++) {
					ttt[row][col] = ":white_small_square:";
					s += ttt[row][col];
				}
				s += "\n";
			}
			sendMessage(e, s);
		} else if(ticRunning && e.getMessage().getContentDisplay().equals("`tic end")) { // statement for if they want to end the games
				sendMessage(e, "This game of tic-tac-toe has ended.");
				ticRunning = false; // turn to false to say that there is no tic-tac-tie running
				return;
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
					"`tic @someone - starts a tic-tac-toe game with that person\n" +
					"`tic end - to end the tic-tac-toe game \n" +
					"`rps - play rock, paper, scissors with bot" +
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
	
	private static void begForDonations(int ms) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {

		            @Override
		            public void run() {
		            	bot.getTextChannelById("420679175465336832").sendMessage("If you like this bot, please consider donating RP to the creators, " 
		            	+ bot.getUserById("152954180984635392").getAsMention() + " and " + bot.getUserById("152957206025863168").getAsMention() + ".").queue();
		            }
		        }, 100, ms); // ms = milleseconds until the next message, can alter this in the main function
	}

	private static void randomName(int ms) {
		Timer t = new Timer();
		final String MATT = "169259999300812800";
		final String MARY = "197426338791948288";
		final String STEVEN = "152954300933472256";
		final Guild guild = bot.getGuildById("152954629993398272");
		t.schedule(new TimerTask() {

		            @Override
		            public void run() {
		            	String oldName = guild.getMemberById(MARY).getNickname();
		            	guild.getController().setNickname(guild.getMemberById(MARY), randomName()).queue();
		            	guild.getTextChannelById("420679175465336832").sendMessage(guild.getMemberById(MARY).getAsMention() + "'s name has changed from " + oldName).queue();
		            }
		        }, 100, ms); // ms = milleseconds until the next message, can alter this in the main function
	}

    protected static String randomName() {
    	String[] words = {"Fat", "Butt", "Girl", "Guy", "ROFLMFAO", "STUCK", "Bronze", "Black", "Yellow", "River", "Hello", "Kitty", "Small", "5Feet", "Unique", "Funny", "Sad", "Bad", 
    			"FutureChallenger", "Argue", "Number", "Wary", "Meal", "Lumpy", "Look", "Swift", "unnatural", "Cumbersome", "Sore", "Finger", "mother", "Concentrate", "Saw", "Clover", 
    			"mess up", "Plant", "Shoes", "Fold", "Stimulating", "Goofy", "Puffy", "New", "van", "Suspect", "Turkey", "momentous", "Present", "mammoth", "manage", "Scissors", "Aftermath", 
    			"Guard", "Regret", "Drunk", "Show", "Happy", "Bang", "High-pitched", "Sheep", "Bear", "Statement", "muddled", "Quicksand", "Certain", "Plane", "Squeeze", "Knot", "medical", 
    			"Quickest", "Snake", "Trap", "Float", "Slippery", "Fade", "Force", "weary", "Tumble", "Pumped", "Paddle", "Reign", "Burst", "Library", "Coach", "Callous", "Price", "Best", 
    			"Regular", "Disgusting", "Idiotic", "Leg", "Breathe", "undress", "Classy", "Embarrassed", "wacky", "Amusement", "Excited", "Plastic", "Lace", "Fuzzy", "Match", "Calm", "yard", 
    			"Bolt", "Trite", "Girls", "Jolly", "Decisive", "Curtain", "Cycle", "Inexpensive", "Receptive", "Spotless", "Gainful", "Jagged", "Snore", "Tense", "Dysfunctional", "Heap", 
    			"Ajar", "Cultured", "waiting", "Quixotic", "Dress", "Rifle", "Cup", "Juicy", "Bell", "Clap", "Brake", "Statuesque"};
    	String s = "";
    	for(int i = 0; i < 4; i++) {
    		s += words[(int)(Math.random()*words.length)];
    	}
    	return s;
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
