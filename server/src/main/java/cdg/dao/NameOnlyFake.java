package cdg.dao;

public class NameOnlyFake implements NameOnly {
	private String publicId;
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
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

}
