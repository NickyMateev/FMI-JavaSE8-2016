package hackathon.voterapplication;

import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HackathonVoterApplication {
	
	private static final String BACKUP_FILE_NAME = "backups";
	
	private List<Team> teams;

	public HackathonVoterApplication(){
		this.teams = new ArrayList<>();
	}
	
	public void addTeam(Team team) throws TeamAlreadyExistsException{
		if(!teams.contains(team)){
			this.teams.add(team);
		} else {
			throw new TeamAlreadyExistsException("ERROR: Team is already registered!");
		}
	}
	
	public void vote(String teamName, int points) throws NonExistingTeamException {
		for (Team team : this.teams) {
			if(team.getName().equals(teamName)){
				team.addPoints(points);
				return;
			}
		}
		
		throw new NonExistingTeamException("ERROR: Team is not registered!");
	}
	
	public List<Team> getRanking(){
		List<Team> rankedTeams = new ArrayList<>(teams);
		Collections.sort(rankedTeams);
		
		return rankedTeams;
	}

	public void announceWinners(String path) throws IOException{
		
		Path filePath = Paths.get(path);
		if(!Files.exists(filePath)){
			throw new FileNotFoundException();
		}

		List<Team> teams = this.getRanking();
		try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), TRUNCATE_EXISTING)){
			for (Team team : teams) {
				writer.write(team.toString());
				writer.write(System.lineSeparator());
			}
		}

		backup(filePath);
	}
	
	private void backup(Path filePath) throws IOException{
		Path backupFolder = Paths.get(filePath.getParent() + "/" + BACKUP_FILE_NAME);
	    Files.createDirectories(backupFolder);
	    Files.copy(filePath, backupFolder);
	}
	
	private String generateBackupName(){
		return "standing_" + System.currentTimeMillis() + ".txt";
	}
	
	public List<Team> getTeams(){
		return new ArrayList<>(this.teams);
	}

	public Team getTeam(String name) throws NonExistingTeamException{
		for (Team team : this.teams) {
			if(team.getName().equals(name)){
				return team;
			}
		}
		
		throw new NonExistingTeamException("ERROR: Team is not registered!");
	}
}
