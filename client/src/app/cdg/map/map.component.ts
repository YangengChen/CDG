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
  mapObject:Object;
  stateName:string;
  ngOnInit() {
    this.mapService.getState("1")
    .subscribe(stateData =>{
        this.mapObject = stateData;
        this.stateName = stateData["description"];
        console.log(this.mapObject);
    });
  }

  showMap(){
    this.mapService.getMap()
    .subscribe(data => this.map = data.toString())
  }

  getState(chosenState: string){
    this.mapService.getState(chosenState)
    .subscribe(stateData =>{
        this.mapObject = stateData["features"]["geometry"]["coordinates"];
        this.stateName = stateData["description"];
    });
  }

  mapclick(e:Event){
    console.log(this.mapObject);
  }
}
