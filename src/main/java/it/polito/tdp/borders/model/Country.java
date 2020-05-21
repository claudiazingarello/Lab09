package it.polito.tdp.borders.model;

public class Country implements Comparable<Country> {
	private String StateAbb;
	private int cCode;
	private String StateName;
	
	public Country(String stateAbb, int cCode, String stateName) {
		StateAbb = stateAbb;
		this.cCode = cCode;
		StateName = stateName;
	}

	public String getStateAbb() {
		return StateAbb;
	}

	public void setStateAbb(String stateAbb) {
		StateAbb = stateAbb;
	}

	public int getcCode() {
		return cCode;
	}

	public void setcCode(int cCode) {
		this.cCode = cCode;
	}

	public String getStateName() {
		return StateName;
	}

	public void setStateName(String stateName) {
		StateName = stateName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cCode;
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
		Country other = (Country) obj;
		if (cCode != other.cCode)
			return false;
		return true;
	}

	@Override
	public int compareTo(Country o) {
		//Ordino gli stati per nome
		return this.getStateName().compareTo(o.StateName);
	}

	@Override
	public String toString() {
		return "Country [StateAbb=" + StateAbb + ", cCode=" + cCode + ", StateName=" + StateName + "]";
	}
	
	
	
}
