
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;        
import java.net.URLConnection;

public class androidserver {
	public static void main(String[] argv) {
		try {
			ServerSocket welcomeSocket = new ServerSocket(6789);
			HashMap<String, String> map = new HashMap<String, String>();
			HashMap<String, String> lexicon = new HashMap<String, String>();
			HashMap<String, HashMap<String, String>> notebook = new HashMap<String, HashMap<String, String>>();
			System.out.println("Server started.");
			while(true) {
				Socket connectionSocket = welcomeSocket.accept();
				Thread t = new Thread(new clientSocket(connectionSocket,map,lexicon,notebook));
				t.start();
			}
		}catch(Exception e) {
			System.out.println("Error: "+e);
		}
	}
}


class clientSocket implements Runnable{
	private Socket socket;
	private String clientSentence;
	private String RespondSentence;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private HashMap<String, String> map;
	private HashMap<String, String> lexicon;
	private HashMap<String, HashMap<String, String>> notebook;
	private String[]  ss ;
	private String username;
	private String password;
	
	public clientSocket(Socket socket,HashMap<String, String> map,HashMap<String, String> lexicon, HashMap<String, HashMap<String, String>> notebook) {
		this.socket = socket;
		this.map = map;
		this.lexicon = lexicon;
		this.notebook= notebook;
	}
	
	public String crawler(String urlString){
		  StringBuffer sBuffer=new StringBuffer();
		  try {
	  		java.net.URL url = new java.net.URL(urlString);
	  		int i=0;
	  		Pattern patternForHref = Pattern.compile("<span class=exp>.*?</span>");
	  		URLConnection context = url.openConnection();
	  		//Scanner input = new Scanner(url.openStream());
	  		//InputStream in = context.getInputStream();
	  		InputStream in = url.openStream();
	  		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
	  		//String line = input.readLine();
	  		String line = br.readLine();
	      	while (line!=null) {
	      		Matcher m = patternForHref.matcher(line);
	      		while(m.find()) {
	      			String textfinded = m.group();
	      			//System.out.println(textfinded);
	      			sBuffer.append(" " + textfinded.substring(16, textfinded.length()-7));
	      			System.out.println(sBuffer.toString());
	      			System.out.println();
	      			i+=1;
	      		}
	      		line = br.readLine();
	      	}
	  	}
	  	catch (java.net.MalformedURLException ex) {
	  	      System.out.println("Invalid URL");
	  	    }
	  	catch (java.io.IOException ex) {
	  	      System.out.println("IO Errors");
	  	    }
		  return sBuffer.toString();
	  }
	
	public void run() {
		try {
			System.out.println("System: thread starts.");
			inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			outToClient = new DataOutputStream(socket.getOutputStream());
			while(true) {
				clientSentence = inFromClient.readLine();
				if(clientSentence.startsWith("/register")) {
					ss = clientSentence.split(" ");
					if(ss.length==3) {
						System.out.println("Registration succeed, username: " + ss[1] + ", password: " + ss[2] );
						username = ss[1];
						password = ss[2];
						map.put(username,password);
						HashMap<String, String> book=new HashMap<String, String>();
						notebook.put(username,book);
						// System.out.println(map.containsKey(username));
						// System.out.println(map.get(username));
						RespondSentence="Registration succeed.";
					}
					else {
						System.out.println("Invalid command");
						RespondSentence="Invalid command.";
					}
				}
				else if(clientSentence.startsWith("/quit")) {
					System.out.println(username + " disconnected.");
				}
				else if(clientSentence.startsWith("/store")) {
					ss = clientSentence.split("#");
					if(ss.length==4) {
						if(notebook.containsKey(ss[1])) {
							if(notebook.get(ss[1]).containsKey(ss[2])) {
								System.out.println("Word repeated.");
								RespondSentence="Word repeated.";
							}
							else {
								RespondSentence="Stored successfully.";
								System.out.println(RespondSentence);
								notebook.get(ss[1]).put(ss[2], ss[3]);
								System.out.println("User " + ss[1] + " has stored: " + ss[2] + " | " + notebook.get(ss[1]).get(ss[2]));
							}
						}
						else {
							System.out.println("User not found.");
							RespondSentence="User not found.";
						}
					}
					else {
						System.out.println("Invalid command");
						RespondSentence="Invalid command.";
					}
				}
				else if(clientSentence.startsWith("/require")) {
					ss = clientSentence.split("#");
					if(ss.length==2) {
						if(notebook.containsKey(ss[1])) {
							int words_nums=notebook.get(ss[1]).size();
							System.out.println("Words number is " + Integer.toString(words_nums));
							if (words_nums == 0) {
								RespondSentence = "没有错词。";
							}
							else{
								Set set=notebook.get(ss[1]).keySet();
								String temp = new String();
								for(Iterator iter = set.iterator(); iter.hasNext();) {
									temp = (String)iter.next();
									RespondSentence = RespondSentence + "#" + temp;
									// response = (String)iter.next();
									RespondSentence = RespondSentence + ": " + notebook.get(ss[1]).get(temp);
									System.out.println(notebook.get(ss[1]).get(temp));
									// response += (String)iter.next() + "|" + (String)notebook.get(ss[1]).get(RespondSentence);
									// System.out.println(response + "\n");
									// outToClient.write((RespondSentence + "\n").getBytes("utf-8"));
								
								}
							}
							
							System.out.println(RespondSentence);
							// outToClient.write((response + "\n").getBytes("utf-8"));
							// RespondSentence="Send over.";
						}
						else {
							System.out.println("User not found.");
							RespondSentence="User not found..";
						}
					}
					else {
						System.out.println("Invalid command");
						RespondSentence="Invalid command.";
					}
				}
				else if(clientSentence.startsWith("/signin")) {
					ss = clientSentence.split(" ");
					if(ss.length==3) {
						if(map.containsKey(ss[1])) {
							if(map.get(ss[1]).equals(ss[2])) {
								System.out.println("Sign in succeed.");
								RespondSentence="Sign in succeed.";
							}
							else {
								System.out.println(map.get(ss[1]) + "  " + ss[2]);
								System.out.println("Wrong password.");
								RespondSentence="Wrong password.";
							}
						}
						else {
							System.out.println("User not found.");
							RespondSentence="User not found..";
						}
					}
					else {
						System.out.println("Invalid command");
						RespondSentence="Invalid command.";
					}
				}
				else if(clientSentence.startsWith("/demand")) {
					ss = clientSentence.split(" ");
					if(ss.length==2) {
						if(lexicon.containsKey(ss[1])) {
							System.out.println(ss[1] + ":" + lexicon.get(ss[1]));
							RespondSentence=ss[1] + ":" + lexicon.get(ss[1]);
						}
						else {
							String exp=new String();
						    String url=new String();
						    url = "http://www.frdic.com/dicts/fr/"+ss[1]; 
						    System.out.println(url);
						    exp=crawler(url); 
						    exp = exp.replaceAll("<.*?>" , "  ");
						    System.out.println(exp);
						    lexicon.put(ss[1], exp);
						    RespondSentence=ss[1] + ":" + lexicon.get(ss[1]);
						}
					}
					else {
						System.out.println("Invalid demand.");
						RespondSentence="Invalid command.";
					}
				}
				else {
					System.out.println("Invalid command");
					RespondSentence="Invalid command.";
				}
				System.out.println("Send to client: " + RespondSentence);
				outToClient.write((RespondSentence + "\n").getBytes("utf-8"));
				
				RespondSentence = new String();
			}
		}catch(Exception e) {
			System.out.println("Error: "+e);
		}
	}
}