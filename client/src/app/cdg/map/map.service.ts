import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Constants } from "../../constants";
import { Observable} from 'rxjs/Rx';
@Injectable()
export class CdgMapService {
  constructor(private http: HttpClient) {}
  getMap(state:string, type:string){
    return this.http.get(Constants.GET_STATE_URL(state, type), {withCredentials:true});
  }
  getData(state:string, type:string){
    return this.http.get(Constants.GET_MAP_DATA_URL(state, type), {withCredentials:true});
  }
  getFullMap(){
    return this.http.get(Constants.UNITED_STATES_URL, {withCredentials:true});
  }

  getUnitedStates() {
  	return this.http.get(Constants.UNITED_STATES_URL, {withCredentials:true});
  }
  
  saveMap(){
    return this.http.get(Constants.SAVE_URL, {withCredentials:true});
  }
  getStateList(){
    console.log("STATELIST: " + Constants.STATELIST_URL);
    return this.http.get(Constants.STATELIST_URL, {withCredentials:true});
  }
  getFinishedMap(type:string){
    return this.http.get(Constants.GET_FINISHED_URL(type), {withCredentials:true});
  }  
  getFinishedData(type:string){
    return this.http.get(Constants.GET_FINISHED_DATA_URL(type), {withCredentials:true});
  }
  changeMap(map:any): Observable<Object>{
    return new Observable<Object>(map);
  }
}
