import java.util.List;

public class Movie {

private String name;
private int releaseYear;
private List<Actor> cast;


public Movie(String name, int releaseYear, List<Actor> cast) {
	setName(name);
	setReleaseYear(releaseYear);
	setCast(cast);
}



public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getReleaseYear() {
	return releaseYear;
}
public void setReleaseYear(int releaseYear) {
	this.releaseYear = releaseYear;
}
public List<Actor> getCast() {
	return cast;
}
public void setCast(List<Actor> cast) {
	this.cast = cast;
}


}
