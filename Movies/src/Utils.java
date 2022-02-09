
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.sound.midi.Soundbank;


public class Utils  {
	public static int count;
	public static Map<String,ArrayList<String>> movieAndCast;
	public static LinkedList<String> foundMovies = new LinkedList<String>(); 
	public static LinkedList<String> movies;
	public static Map<String, Integer> map = new HashMap<String, Integer>();
	

	//Task1
	public static void findMovies(String actorNames, Map<String,ArrayList<String>> movieAndCast){
		//separating the user entered String into two different actor names 
		String actor1 =	actorNames.split(",")[0];
		String actor2 = actorNames.split(",")[1];
		
		//counting the number of movies which actor1 and actor2 both take part in, and adding them to a list in type string
		count = 0;
		for(String movieNames: movieAndCast.keySet()) {
				if ((movieAndCast.get(movieNames)).contains(actor1) && (movieAndCast.get(movieNames)).contains(actor2)){	
					foundMovies.add(movieNames);
					count++;
		} 
		}
		//sorting found movies in ascending order
		Collections.sort(foundMovies);
		
		//converting the list of foundMovies to string before printing
		String result = foundMovies.stream()
								   .map(n->String.valueOf(n))
								   .collect(Collectors.joining(" "));
		
		System.out.println(actor1+ " and " + actor2 + " have co-starred in " + count + " movie(s): " + result);
	}
	
	
	//Task2
	public static void sortAndDisplayMovies(String userInput2, LinkedList<String> movies) {
		
		//extracting the character specified by the user from the input 
		String ch = (userInput2.split(" ")[0]);
		for(int i=0; i<ch.length();i++) {
		Character.toLowerCase(ch.charAt(i));
		}
		
		//creating a predicate which we will use to form a list of matching movies with the filter method. including both the uppercase and lowercase scenerios just in case a movie starts with a lower-case letter
		Predicate<String> includesCharacter = x->x.startsWith(ch) | x.startsWith(ch.toUpperCase());
		
		//extracting the specified order from the user input
		String userOrder = userInput2.split(" ")[1];
		
		//collecting matching movies as a list of type string
		List<String> matchingMovies = movies.stream()
											.filter(includesCharacter)
											.collect(Collectors.toList());
		
		
		//sorting the list of matching movies accordingly to the user request - either in ascending or descending order
		if(userOrder.equalsIgnoreCase("ascending")) {
			matchingMovies = matchingMovies.stream().sorted().collect(Collectors.toList());
		} else if(userOrder.equalsIgnoreCase("descending")) {
			matchingMovies = matchingMovies.stream()
						  .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		}
		
		//printing the list of matching movies in the requested order
		ListIterator<String> iterator = matchingMovies.listIterator();
		while(iterator.hasNext()) {
		System.out.println(iterator.next());
		}
	}
	
	
	//Task3
	public static void findMoviesByActor(String actorInput) {
			
			List<String> list4 = new LinkedList<String>();
			Map<String,String> actorMovie = new HashMap<>();
			List<String> list6 = new LinkedList<String>();
			
			
		// storing the full names of actors whose first name is the user entered String	in list4
			Main.nameFullname.keySet().stream()
										  .filter(x->x.equals(actorInput)|x.startsWith(actorInput))
										  .forEach(x-> list4.add(Main.nameFullname.get(x)));
		    System.out.println(list4);
		
		//storing the actors full name and the movie they starred in in a hashmap. key: actors full name, value: the name of the movie
		for(String a : Main.movieAndCast.keySet()) {	 
			for(String b : list4) {
			if( Main.movieAndCast.get(a).contains(b)) {
				actorMovie.put(a,b);
				}
				}
			}
		
		
		list6 = actorMovie.keySet().stream().sorted(Comparator.comparing(x->(Main.movieMap.get(x)).getReleaseYear())).collect(Collectors.toList());
		
		System.out.println("Movies played by the actors with first name: " + actorInput);
		System.out.println("Actor's Name/Surname" + "\t\t" + "  " + "Movies(s) Title(s)");
		System.out.println("------------------------------    ---------------------    ");
		for(String x : list6) {
		System.out.println(actorMovie.get(x) + "\t\t\t" + "  " + x + "(" + Main.movieAndYear.get(x) + ")");
		}
		}
	
	//Task4
	public static void sortByYear(String years, Map<String,Integer> movieAndYear) {
		//extracting year inputs as integers
		int year1 = Integer.parseInt((years.split(" ")[0]));
		int year2 = Integer.parseInt((years.split(" ")[1]));
		
		System.out.println("Movies released between " + year1 + "–" +year2);
		

		movieAndYear.entrySet()
					.stream()
					.filter(x->x.getValue()>=year1 &&x.getValue()<=year2) //filtering the movieAndYear map accordingly to the input years
					.sorted(Map.Entry.comparingByValue()) //sorting the map in ascending year order
					.forEach(e->System.out.println(e.getKey() + "\t" + e.getValue())); //printing the result
	}
	
	
	//Task5
	public static void findActorWithMostMovies(Map<String,ArrayList<String>> movieAndCast) {
	
		List<String> list = new LinkedList<String>();
		List<Integer> list2 = new LinkedList<Integer>();
		List<Movie> list3 = new LinkedList<Movie>();
		List<Integer> list5 = new LinkedList<Integer>();

		
		//storing all actors in a single list
		movieAndCast.values().stream().forEach(x->list.addAll(x));

		//keeping count of how many movies each actor has starred in and storing them in a map in which key:actor's name value: no. of movies they have starred in
		for(int i=0; i< list.size(); i++) {
			Integer frequency = map.get(list.get(i));
			if(frequency == null) {
				map.put(list.get(i), 1);
			} else {
				map.put(list.get(i), frequency+1);
			}
	}
		
		//	storing the no of frequencies in a list so that we can use the Collections.max() method to find the max frequency
		map.values().stream().forEach(x-> list2.add(x));
		Integer max = Collections.max(list2);
		
		
		//finding the name of the actor who has starred in the max number of movies
		String actor = map.keySet().stream().filter(entry -> map.get(entry)== max).collect(Collectors.joining(""));
	
		//filtering the movies in which the "actor" has starred in and adding said movie objects to a list
		movieAndCast.keySet().stream()
						  .filter(x->movieAndCast.get(x).contains(actor))
						  .forEach(x->list3.add(Main.movieMap.get(x)));
		
	
       //extracting the release year of the movie objects in which the actor has starred in
		List<Integer> yearsList=list3.stream()
						             .map(x->x.getReleaseYear())
						             .collect(Collectors.toList());

		//grouping the years by the number of movies the actor has starred in during a year
		Map<Integer,Long> yearNumber=yearsList.stream()
							                  .collect(Collectors.groupingBy(x->x,Collectors.counting()));
	
		//finding the max number of movies per year out of all the movies the actor has starred in
		int max2 = (yearNumber.values()
							 .stream()
							 .max(Comparator.naturalOrder()))
							 .get()
							 .intValue();
		
		//creating a list of the years which the actor has starred in 'max2' movies in
		list5 = yearNumber.keySet()
						  .stream()
						  .filter(entry -> yearNumber.get(entry)== max2).collect(Collectors.toList());
		
		System.out.println("The actor with the maximum number of movies played is " + actor + " who played in " + max.toString() + " movies.");
		System.out.println("");
		list5.stream().forEach(System.out::print);
		System.out.println(" were his/her most productive year(s) with " + max2 + " in each.");
	}
}
