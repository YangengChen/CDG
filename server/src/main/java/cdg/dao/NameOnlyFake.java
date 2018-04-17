package cdg.dao;

public class NameOnlyFake implements NameOnly {
	private int publicId;
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
	public int getPublicId() {
		return publicId;
	}
	public void setPublicId(int publicId) {
		this.publicId = publicId;
	}

}
