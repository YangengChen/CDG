import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable()
export class MapService {
  mapurl:string = "/api/map";
  mapType:string = "state"
  stateUrl:string = "http://localhost:8080/api/map/file/";
  constructor(private http: HttpClient) {}
  getMap(){
    return this.http.get(this.mapurl)
  }
  getState(state:string){
    return this.http.get(this.stateUrl.concat(state).concat("/".concat(this.mapType)));
  }
  setType(type:string){
    this.mapType = type;
  }
}
