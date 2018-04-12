package cdg.dao;

public class NameOnlyFake implements NameOnly {
	private String name;
	
	public void setName(String name)
	{
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}

}
