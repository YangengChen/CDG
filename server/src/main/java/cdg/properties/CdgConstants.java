package cdg.properties;

public class CdgConstants {
	//application constants
	public static final String PROPERTIES_FILE_PATH = "/cdg.properties";
	public static final String SESSION_USER = "sessionUser";
	public static final String NEIGHBOR_ANNOTATION_SCRIPT_PATH = "annotateNeighbors.js";
	public static final String NEIGHBOR_ANNOTATION_SCRIPT_ENGINE = "nashorn";
	public static final String NEIGHBOR_ANNOTATION_SCRIPT_JSON_BINDING = "geoString";
	public static final String NEIGHBOR_ANNOTATION_SCRIPT_CLIENT_BINDING = "topojsonClient";
	public static final String TOPOJSON_CLIENT_SCRIPT_PATH = "classpath:topojson.js";
	public static final String NEIGHBOR_ANNOTATION_SCRIPT_SERVER_BINDING = "topojsonServer";
	public static final String TOPOJSON_SERVER_SCRIPT_PATH = "classpath:topojson-server.js";
	public static final String NEIGHBOR_ANNOTATION_SCRIPT_NEIGHBOR_KEY_BINDING = "neighborField";
	public static final String NEIGHBOR_ANNOTATION_SCRIPT_PRECINCT_KEY_BINDING = "precinctIDField";
	
	//controller constants
	public static final String CROSS_ORIGIN_LOCATION = "http://localhost:4200";
	public static final String GENERATION_CONTROLLER_PATH_PREFIX = "/api/generation";
	public static final String GENERATION_START_PATH = "/start";
	public static final String GENERATION_CANCEL_PATH = "/cancel";
	public static final String GENERATION_PAUSE_PATH = "/pause";
	public static final String GENERATION_STATUS_PATH = "/status";
	public static final String GENERATION_MAP_PATH = "/file/{maptype}";
	public static final String GENERATION_SAVE_MAP_PATH = "/save/map";
	public static final String GENERATION_MAP_DATA_PATH = "/data/{maptype}";
	public static final String GENERATION_SAVE_MAP_DATA_PATH = "/save/data";
	public static final String MAP_CONTROLLER_PATH_PREFIX = "/api/map";
	public static final String MAP_STATEID_PATH_VARIABLE = "stateid";
	public static final String MAP_MAPTYPE_PATH_VARIABLE = "maptype";
	public static final String MAP_STATIC_MAP_PATH = "/geojson/{stateid}/{maptype}";
	public static final String MAP_STATIC_MAP_FILE_PATH = "/file/{stateid}/{maptype}";
	public static final String MAP_STATIC_MAP_DATA_PATH = "data/{stateid}/{maptype}";
	public static final String MAP_US_MAP_PATH = "/file/unitedstates";
	public static final String MAP_ALL_STATES_PATH = "/states";
	public static final String USER_CONTROLLER_PATH_PREFIX = "/api/user";
	public static final String USER_LOGIN_PATH = "/login";
	public static final String USER_REGISTER_PATH = "/register";
	public static final String USER_LOGOUT_PATH = "/logout";
	public static final String USER_ALL_USERS_PATH = "/all";
	
	//domain constants
	public static final String DISTRICT_NAME_PREFIX = "Congressional District ";
	public static final double MAX_GOODNESS = 100;
	public static final double SCHWARZBERG_WEIGHT = 0.34;
	public static final double HULL_RATIO_WEIGHT = 0.33;
	public static final double REOCK_WEIGHT = 0.33;
	
	//geojson output
	public static final String PRECINCT_NAME = "precinctName";
	public static final String DISTRICT_NAME = "districtName";
	public static final String STATE_NAME = "stateName";
	public static final String PRECINCT_ID = "precinctID";
	public static final String DISTRICT_ID = "districtID";
	public static final String STATE_ID = "stateID";
	public static final String POPULATION = "population";
	public static final String COUNTY= "county";
	public static final String VOTING_AGE_POPULATION = "votingAgePop";
	public static final String ELECTION_YEAR = "electionYear";
	public static final String TOTAL_VOTE = "prsTOTAL";
	public static final String DEM_VOTE = "prsDFL";
	public static final String REP_VOTE = "prsR";
	public static final String OTH_VOTE = "prsOTH";
	public static final String DISTRICT_REP = "districtRep";
	public static final String WASTED_VOTE_RATIO = "wastedVoteRatio";
	public static final String SCHWARTZBERG_COMPACTNESS = "schwarzbergCompactness";
	public static final String HULL_RATIO_COMPACTNESS = "hullRatioCompactness";
	public static final String REOCK_COMPACTNESS = "reockCompactness";
	public static final String GOODNESS = "goodness";
	

	//properties file keys
	public static final String STATE_NAME_FIELD = "geojson_stateName";
	public static final String STATE_IDENTIFIER_FIELD = "geojson_stateIdentifier";
	public static final String DISTRICT_IDENTIFIER_FIELD = "geojson_districtIdentifier";
	public static final String PRECINCT_NAME_FIELD = "geojson_precinctName";
	public static final String PRECINCT_IDENTIFIER_FIELD = "geojson_precinctIdentifier";
	public static final String PRECINCT_NEIGHBORS_FIELD = "geojson_precinctNeighbors";
	public static final String PRECINCT_POPULATION_FIELD = "geojson_precinctPopulation";
	public static final String PRECINCT_COUNTY_FIELD = "geojson_precinctCounty";
	public static final String PRECINCT_VOTING_AGE_POPULATION_FIELD = "geojson_precinctVotingAgePopulation";
	public static final String PRESIDENTIAL_ELECTION_YEAR_FIELD = "geojson_presidentialElectionYear";
	public static final String PRECINCT_PRESIDENTIAL_TOTAL_VOTE_FIELD = "geojson_precinctPresidentialTotalVote";
	public static final String PRECINCT_PRESIDENTIAL_DEM_VOTE_FIELD = "geojson_precinctPresidentialDemVote";
	public static final String PRECINCT_PRESIDENTIAL_REP_VOTE_FIELD = "geojson_precinctPresidentialRepVote";
	public static final String DISTRICT_REPRESENTATIVE_FIELD = "geojson_districtRepresentative";
	public static final String THRESHOLD_PERCENT = "generation_thresholdPercent";
	public static final String MAX_DISTRICT_ITERATIONS = "generation_maxDistrictIterations";
	public static final String MAX_GENERATION_ITERATIONS = "generation_maxGenIterations";
	public static final String DISTRICT_ITERATION_MAX_PERCENT = "generation_districtMaxPercent";
	public static final String END_THRESHOLD_PERCENT = "generation_endThresholdPercent";
	public static final String END_THRESHOLD_FORGIVENESS = "generation_endThresholdForgiveness";
	public static final String POLICY = "generation_policy";
}
