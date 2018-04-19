import { Injectable, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { LoginService } from "./login.service";
import { User } from "../../cdg-objects/user"
import { FlashMessagesService } from 'ngx-flash-messages';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  response: string = "";
  model: any = {};
  newUser : User;

  constructor(private router: Router, private loginService: LoginService, private flashMessagesService: FlashMessagesService) {
    this.newUser = new User()
  }

  login(){
    this.loginService.login(this.newUser).subscribe(
      (data) =>{
        console.log(data);
        this.router.navigate(["/cdg"]);
      },
      (err) =>{
        console.error(err);
        this.flashMessagesService.show(environment.INVALID_CRED, {
          classes: ['alert-danger'], 
          timeout: 1000
        });
      }
    )      
  }

  register(){
    console.log("New User: ", this.newUser)
    this.loginService.register(this.newUser).subscribe(
      (data) =>{
        console.log(data);
      },
      (err) =>{
        console.error(err);
        this.flashMessagesService.show(environment.INVALID_CRED, {
          classes: ['alert-danger'], 
          timeout: 1000
        });
      }
    )
  }

  ngOnInit() {
  }

  selectLoginTab(e){
    console.log("Login");
    // Set tab toggle
    document.getElementById("loginTab").className = "active";
    document.getElementById("registerTab").className = "";
  }

  selectRegisterTab(e){
    console.log("Register");
    // Set tab toggle
    document.getElementById("loginTab").className = "";
    document.getElementById("registerTab").className = "active";
  }

}
