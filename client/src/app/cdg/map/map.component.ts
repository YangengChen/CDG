import { Component, OnInit } from '@angular/core';
import { MapService } from './map.service';
@Component({
  selector: 'map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {


  constructor(private mapService: MapService) { }
  map: string;
  geoJson:string;
  stateName:string;
  ngOnInit() {
    this.map = "hi"
  }

  showMap(){
    this.mapService.getMap()
    .subscribe(data => this.map = data.toString())
  }

  getState(){
    this.mapService.getState()
    .subscribe(stateData =>{
        this.geoJson = stateData["geoJson"];
        this.stateName = stateData["state"];
    })
  }
}
