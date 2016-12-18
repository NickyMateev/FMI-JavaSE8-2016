package hackathon.testvoterapplication;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import hackathon.voterapplication.HackathonVoterApplication;
import hackathon.voterapplication.NonExistingTeamException;
import hackathon.voterapplication.Team;
import hackathon.voterapplication.TeamAlreadyExistsException;

public class TestHackathonVoterApplication {

	@Test
	public void testAddTeam() throws TeamAlreadyExistsException {

		Team team = new Team("Team1", "Project1");
		List<Team> expected = Arrays.asList(team);

		HackathonVoterApplication application = new HackathonVoterApplication();
		application.addTeam(team);

		List<Team> actual = application.getTeams();

		assertEquals(expected, actual);

	}

	@Test(expected=TeamAlreadyExistsException.class)
	public void testAddTeamException() throws TeamAlreadyExistsException {
		String name="Team1", projectName1 = "Project1", projectName2 = "Project2";
		HackathonVoterApplication application = new HackathonVoterApplication();
		application.addTeam(new Team(name, projectName1));
		application.addTeam(new Team(name, projectName2));
	}

	@Test
	public void testVote() throws TeamAlreadyExistsException, NonExistingTeamException {
		String name = "Team1", projectName = "Project1";
		HackathonVoterApplication application = new HackathonVoterApplication();
		application.addTeam(new Team(name, projectName));
		application.vote(name, 50);

		Team expected = new Team(name, projectName);
		expected.setPoints(50);

		Team actual = application.getTeam(name);
		assertEquals(expected, actual);
	}

	@Test(expected=NonExistingTeamException.class)
	public void testVoteException() throws NonExistingTeamException {
		HackathonVoterApplication application = new HackathonVoterApplication();
		application.vote("Team1", 50);
	}
	
	
}
