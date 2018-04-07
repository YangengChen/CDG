import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class MapService {
  mapurl:string = "/api/map";
  stateUrl:string = "/api/map/geoJson/minnesota/state";
  constructor(private http: HttpClient) {}
  getMap(){
    return this.http.get(this.mapurl)
  }

  getState(state:string){
    return this.http.get(this.stateUrl.concat(state));
  }
}
