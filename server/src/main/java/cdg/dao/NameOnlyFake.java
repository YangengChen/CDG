package cdg.dao;

public class NameOnlyFake implements NameOnly {
	private String publicID;
	private String name;
	
	public void setName(String name)
	{
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String getPublicID() {
		return publicID;
	}
	public void setPublicID(String publicID) {
		this.publicID = publicID;
	}

}
