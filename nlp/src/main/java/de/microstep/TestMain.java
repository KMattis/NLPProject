package de.microstep;

public class TestMain {
	
	public static void main(String[] args) {
		System.out.println(Config.getConfig("system").getString("java.home"));
	}

}
