package tn.esprit.spring.entities;

public class SessionFake {
	
	private static Long id;

	public static Long getId() {
		return id;
	}

	public static void setId(Long id) {
		SessionFake.id = id;
	}
	
}
