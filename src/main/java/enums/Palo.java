package enums;

public enum Palo {
	
	C("Treboles"),
	D("Diamantes"),
	H("Corazones"),
	S("Espadas");
	
	private final String name;
	
	private Palo(String name) {
		this.name = name;
	}
		
	public String getNombre() {
		return name;
	}

}
