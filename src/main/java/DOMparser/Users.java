package DOMparser;

public class Users {

	private String name;
	private int level;
	private int hard;
	public Users(String name , int level , int hard) {
		this.name=name;
		this.level=level;
		this.hard = hard;
	}

	public String getName() {
		return name;
	}
	
	

	public int getLevel() {
		return level;
	}

	public int getHard(){
		return hard;
	}
	@Override
	public String toString() {
		return name+"  "+hard;
	}

	
	
	
	
}
