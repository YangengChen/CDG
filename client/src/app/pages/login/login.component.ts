import { Injectable, Component, OnInit } from '@angular/core';
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
  constructor(private loginService: LoginService) { }

  login(){

  }

  ngOnInit() {

  }

}
