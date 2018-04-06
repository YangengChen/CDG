package cdg.domain.map;

import java.beans.PropertyEditorSupport;

public class MapTypeEnumConverter extends PropertyEditorSupport {	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {
        MapType mapType = MapType.valueOf(text.toUpperCase());
        setValue(mapType);
    }
}
