package edu.upenn.cit594.util;
import java.util.Date;

public class Covid {
	
	private String zipCode;
	private long negTest;
	private long posTest;
	private long deathToll;
	private long hospitalization;
	private long partialVaccinated;
	private long fullyVaccinated;
	private long booster;
	private String date;
	
	
	public Covid (String zipCode, long negTest, long posTest, long deathToll, long hospitalization, long partialVaccinated,
			long fullyVaccinated, long booster,  String date) {
		
		this.zipCode = zipCode;
		this.negTest = negTest;
		this.posTest = posTest;
		this.deathToll = deathToll;
		this.hospitalization = hospitalization;
		this.partialVaccinated = partialVaccinated;
		this.fullyVaccinated = fullyVaccinated;
		this.booster = booster;
		this.date = date;
		
	}

	public String getZipCode() {
		return zipCode;
	}


	public long getNegTest() {
		return negTest;
	}


	public long getPosTest() {
		return posTest;
	}


	public long getDeathToll() {
		return deathToll;
	}


	public long getHospitalization() {
		return hospitalization;
	}


	public long getPartialVaccinated() {
		return partialVaccinated;
	}


	public long getFullyVaccinated() {
		return fullyVaccinated;
	}


	public long getBooster() {
		return booster;
	}


	public String getDate() {
		return date;
	}

	public String toString() {
		return "Covid [zipCode=" + zipCode + ", negTest=" + negTest + ", posTest=" + posTest + ", deathToll=" + deathToll
				+ ", hospitalization=" + hospitalization + ", partialVaccinated=" + partialVaccinated
				+ ", fullyVaccinated=" + fullyVaccinated + ", booster=" + booster + ", date=" + date + "]";
	}

	
	
}
