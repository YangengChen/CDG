import {Injectable, Component, OnInit, Input} from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-cdg',
  templateUrl: './cdg.component.html',
  styleUrls: ['./cdg.component.scss']
})
export class CdgComponent implements OnInit {
  @Input() items:[{title:"Testing"}, {title:"menu"}];
  url = "http://localhost:8080/api/map/states";
  dat = ""
  constructor(private http: HttpClient) { 
    
  };
  clickOn(){
    this.http.get(this.url).subscribe(data => this.dat=data[0]);
  }
  ngOnInit() {
  }

}
