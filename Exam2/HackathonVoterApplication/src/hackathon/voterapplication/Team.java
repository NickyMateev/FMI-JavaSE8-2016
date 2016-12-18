package hackathon.voterapplication;

public class Team implements Comparable<Team> {
	
	private String name;
	private String projectName;
	private int points;

	public Team(String name, String projectName) {
		super();
		this.name = name;
		this.projectName = projectName;
		this.points = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addPoints(int points){
		this.points += points;
	}

	@Override
	public String toString() {
		return this.name + " - " + this.projectName + " - " + this.points;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + points;
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Team other = (Team) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;


		return true;
	}

	@Override
	public int compareTo(Team o) {
		if(this.points < o.points){
			return -1;
		} else if (this.points > o.points){
			return 1;
		} else {
			return this.name.compareTo(o.name);
		}
	}
	
}
