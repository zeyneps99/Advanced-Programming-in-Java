import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main  {
	 private static String actorName;
	 private static String actorSurname;
	 private static String movieName;
	 private static List<Actor> cast;
	 private static int releaseYear;
	 private static LinkedList<String> actors;
	 private static ArrayList<String> actorsList;
	 public static Map<String,ArrayList<String>> movieAndCast = new HashMap<String,ArrayList<String>>();
	 public static Map<String,Integer> movieAndYear = new HashMap<String,Integer>();
	 private static LinkedList<String> movies = new LinkedList<String>();
	 public static Map<String,Movie> movieMap = new HashMap<String,Movie>();
	 public static Map<String, String> nameFullname = new HashMap<String,String>();

	
	public static void main(String[]args) {
		try(Scanner fileInput = new Scanner(Paths.get("./src/movies.txt.txt"))){
			while(fileInput.hasNext()) {
				
				String movieEntry = fileInput.nextLine();
				//storing actor names as Strings in a linked list
				actors = new LinkedList<>( Arrays.asList(movieEntry.split("/")));
				//extracting release year for each movie
				LinkedList<String> movieNameYear = new LinkedList<>(Arrays.asList(actors.get(0).split("\\(")));
				Pattern yearFormat = Pattern.compile("\\d{4}");
				Matcher matcherYear = yearFormat.matcher(movieNameYear.get(1));
				if (matcherYear.find()) {
					String year = matcherYear.group();
					releaseYear = Integer.parseInt(year);
				}
				
				
				movieName = movieNameYear.get(0);
		
				//removing the first element, which is the movieNameYear from actors list so that we're left only with actor names
				actors.remove(0);

				//storing the cast for each movie in the form of List<Actor> and List<String>
				cast = new LinkedList<Actor>();
				actorsList = new ArrayList<String>();
				for (String actor:actors) {
					if (1<actor.split(",").length){
						//extracting actor names and surnames in separate strings, for each actor
						actorName = actor.split(",")[1].trim();
						actorSurname = actor.split(",")[0].trim();		
						//forming actor and movie objects for each movie, then forming a cast which is a linked list of actor
					}
					actorsList.add(actorName + " "+ actorSurname);
					
					//to extract full names from firstName in Task3
					if(!nameFullname.containsValue(actorName + " " + actorSurname)) {
						nameFullname.put(actorName, actorName + " " + actorSurname);
						}	
					
					//forming actor objects for each actor from their names as Strings
					Actor actorPerson = new Actor(actorName, actorSurname);
					cast.add(actorPerson);
					}

				//creating a map that stores movie names as String keys and movie objects as values
				movieMap.put(movieName, new Movie(movieName, releaseYear, cast));
				
				//stores movie and cast -> key: movies as Strings, value: cast as Strings (for Task 1)
				movieAndCast.put(movieName, actorsList);
				
				//stores movie names as Strings (for Task 2)
				movies.add(movieName);
			
				//stores movie names as String and year as Integer (for Task 4) 
				movieAndYear.put(movieName,releaseYear);
				
				
			}	
			

			}
		
		catch(IOException|NoSuchElementException|IllegalStateException e) {
			e.printStackTrace();
		}
		
		
		//Task1
		System.out.println("Task 1");
		System.out.println("***************************");
		System.out.println("Enter the name and surname of the actor separated by a comma (without a space character)");
		Scanner userInput1 = new Scanner(System.in);
		String actorNames = userInput1.nextLine();
		System.out.println(" ");
		Utils.findMovies(actorNames,movieAndCast);
		System.out.println(" ");
		
		//Task2
		System.out.println("Task 2");
		System.out.println("***************************");
		System.out.println("Enter the first character and ordering type: ");
		Scanner userInput2 = new Scanner(System.in);
		String charAndOrder = userInput2.nextLine();
		System.out.println(" ");
		Utils.sortAndDisplayMovies(charAndOrder, movies);
		System.out.println(" ");
		
		//Task3
		System.out.println("Task 3");
		System.out.println("***************************");
		System.out.println("Search movies by first name, please enter the actor's first name: ");
		Scanner userInput3 = new Scanner(System.in);
		String actorInput = userInput3.nextLine();
		System.out.println(" ");
		Utils.findMoviesByActor(actorInput); 
		System.out.println(" ");

		//Task4
		System.out.println("Task 4");
		System.out.println("***************************");
		System.out.println("Search movies by release date. Please enter the start year and end year of the period you want to search for seperated by a space: ");
		Scanner userInput4 = new Scanner(System.in);
		String years = userInput4.nextLine();
		System.out.println(" ");
		Utils.sortByYear(years, movieAndYear);
		System.out.println(" ");
		
		//Task5
		System.out.println("Task 5");
		System.out.println("***************************");
		System.out.println(" ");
		Utils.findActorWithMostMovies(movieAndCast); 
		
		
		
	
	}
	

}
