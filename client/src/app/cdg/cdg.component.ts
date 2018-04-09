import {Injectable, Component, OnInit, Input} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {LoginService } from "../pages/login/login.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-cdg',
  templateUrl: './cdg.component.html',
  styleUrls: ['./cdg.component.scss']
})
export class CdgComponent implements OnInit {
  @Input() items:[{title:"Testing"}, {title:"menu"}];
  url = "http://localhost:8080/api/map/states";
  dat = ""
  constructor(private router:Router, private loginService: LoginService, private http: HttpClient) { 
    
  };
  logout(){
    // TODO: NEEDS A LOGOUT
    this.router.navigateByUrl("/");
  }
  ngOnInit() {
  }

}
