package domain;

public class Player {
	private String name;
	private String password;
	private String country;

	public Player(String name, String password, String country) {
		super();
		this.name = name;
		this.password = password;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player p = (Player) obj;
			return this.getName().equals(p.getName());
		}
		return false;
	}
}
