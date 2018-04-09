import { Injectable, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import {LoginService } from "./login.service";
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  response: string = "";
  model: any = {};
  constructor(private router: Router, private loginService: LoginService) { }

  login(){
    if(this.model.password == "password" && this.model.username == "username")
      this.router.navigateByUrl("/cdg")
  }

  logout(){
      this.router.navigateByUrl("/");
  }
  ngOnInit() {

  }

}
