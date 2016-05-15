package DOMparser;

public class Users {

	private String name;
	private int level;
	
	public Users(String name , int level) {
		this.name=name;
		this.level=level;
	}

	public String getName() {
		return name;
	}

	

	public int getLevel() {
		return level;
	}

	@Override
	public String toString() {
		return name;
	}

	
	
	
	
}
