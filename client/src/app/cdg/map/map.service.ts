import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class MapService {
  mapurl = "/api/map"
  constructor(private http: HttpClient) {}
  getMap(){
    return this.http.get(this.mapurl)
  }
}
