package cdg.dao;

import java.sql.Blob;

public interface GeoFiles {
	Blob getStateMapFile();
	Blob getConDistrictMapFile();
	Blob getPrecinctMapFile();
}
