import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Constants } from "../../constants";
@Injectable()
export class MapService {
  constructor(private http: HttpClient) {}
  getMap(state:string, type:string){
    return this.http.get(Constants.GET_STATE_URL(state, type));
  }
  getData(state:string){
    return this.http.get(Constants.GET_MAP_DATA_URL(state));
  }
  getFullMap(){
    return this.http.get(Constants.UNITED_STATES_URL);
  }

  getUnitedStates() {
  	return this.http.get(Constants.UNITED_STATES_URL);
  }
  
  saveMap(){
    return this.http.get(Constants.SAVE_URL);
  }
  getStateList(){
    return this.http.get(Constants.STATELIST_URL);
  }
  getFinishedMap(){
    return this.http.get(Constants.FINISHED_URL);
  }
}
