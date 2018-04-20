import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class MapService {
  mapurl:string = "/api/map";
  usUrl:string = "http://localhost:8080/api/map/file/unitedstates";
  stateUrl:string = "http://localhost:8080/api/map/file/";
  constructor(private http: HttpClient) {}
  getMap(){
    return this.http.get(this.mapurl)
  }
  
  getUnitedStates() {
  	return this.http.get(this.usUrl);
  }

  getState(state:string){
    return this.http.get(this.stateUrl.concat(state).concat("/precinct"));
  }
}
