package cdg.dao;

import java.util.Map;

import cdg.domain.region.Party;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name = "ElectionResults")
public class ElectionResult {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name="id", updatable = false, nullable = false)
	@JsonIgnore
	private long id;
	private int year;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "partyTotals")
	@MapKeyColumn(name = "party", length = 40, nullable = false)
	@MapKeyClass(Party.class)
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "voteTotal", nullable = false)
	private Map<Party,Long> partyTotals;
	private long totalVotes;
	private long votingAgePopulation;
	
	public ElectionResult() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Map<Party, Long> getPartyTotals() {
		return partyTotals;
	}

	public void setPartyTotals(Map<Party, Long> voteTotals) {
		this.partyTotals = voteTotals;
	}

	public long getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(long totalVotes) {
		this.totalVotes = totalVotes;
	}
	
	public void setTotal(Party party, long voteTotal) {
		partyTotals.put(party, voteTotal);
	}
	
	public long getTotal(Party party) {
		return partyTotals.get(party);
	}

	public long getVotingAgePopulation() {
		return votingAgePopulation;
	}

	public void setVotingAgePopulation(long votingAgePopulation) {
		this.votingAgePopulation = votingAgePopulation;
	}
	
	public void addElectionResult(ElectionResult election) {
		if (election == null) {
			throw new IllegalArgumentException();
		}
		if (this.getPartyTotals() == null) {
			throw new IllegalStateException();
		}
		setVotingAgePopulation(this.getVotingAgePopulation() + election.getVotingAgePopulation());
		setTotalVotes(this.getTotalVotes() + election.getTotalVotes());
		setTotal(Party.DEMOCRATIC, this.getTotal(Party.DEMOCRATIC) + election.getTotal(Party.DEMOCRATIC));
		setTotal(Party.REPUBLICAN, this.getTotal(Party.REPUBLICAN) + election.getTotal(Party.REPUBLICAN));
		setTotal(Party.OTHER, this.getTotal(Party.OTHER) + election.getTotal(Party.OTHER));
	}
	
	public void subtractElectionResult(ElectionResult election) {
		if (election == null) {
			throw new IllegalArgumentException();
		}
		if (this.getPartyTotals() == null) {
			throw new IllegalStateException();
		}
		setVotingAgePopulation(this.getVotingAgePopulation() - election.getVotingAgePopulation());
		setTotalVotes(this.getTotalVotes() - election.getTotalVotes());
		setTotal(Party.DEMOCRATIC, this.getTotal(Party.DEMOCRATIC) - election.getTotal(Party.DEMOCRATIC));
		setTotal(Party.REPUBLICAN, this.getTotal(Party.REPUBLICAN) - election.getTotal(Party.REPUBLICAN));
		setTotal(Party.OTHER, this.getTotal(Party.OTHER) - election.getTotal(Party.OTHER));
	}
}
