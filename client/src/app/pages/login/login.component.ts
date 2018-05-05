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
  newUser : User;
  statusCode: number;
  
  constructor(private router: Router, private loginService: LoginService, private flashMessagesService: FlashMessagesService) {
    this.loginService.isUserLoggedIn().subscribe(
      data => data ? this.router.navigate(["/"]) : null,
      errorCode =>  this.statusCode = errorCode
    );
    this.newUser = new User();
  }

  clickLogin(form){
    console.log("loginForm: ", form.valid)
    this.isFormValid(form) ? this.login() : this.flashError(environment.INVALID_CRED);
  }

  clickRegister(form){
    console.log("registerForm: ", form.valid)
    this.isFormValid(form) ? this.register() : this.flashError(environment.INVALID_CRED);
  }

  login(){
    this.loginService.login(this.newUser).subscribe(
      (data) =>{
        this.router.navigate(["/cdg"]);
      }, (err) =>{
        this.flashError(environment.INVALID_CRED)
      }
    )  
  }

  register(){
    this.loginService.register(this.newUser).subscribe(
      (data) =>{
        this.flashSuccess(environment.SUCCESS_REGISTER)
        this.router.navigate(["/cdg"]);
      },
      (err) =>{
        this.flashError(environment.INVALID_CRED);
      }
    )
  }

  ngOnInit() {
  }

  selectLoginTab(){
    // Set tab toggle
    document.getElementById("loginTab").className = "active";
    document.getElementById("registerTab").className = "";
    document.forms["loginForm"].reset();
  }

  selectRegisterTab(){
    // Set tab toggle
    document.getElementById("loginTab").className = "";
    document.getElementById("registerTab").className = "active";
    document.forms["registerForm"].reset();
  }

  isFormValid(form){
    console.log("validEmal: ", this.validEmail(this.newUser.email))
    return form.valid && this.validEmail(this.newUser.email)
  }
  flashError(msg){
    this.flashMessagesService.show(msg, {
      classes: ['alert-danger'], 
      timeout: 1000
    });
  }
  flashSuccess(msg){
    this.flashMessagesService.show(msg, {
      classes: ['alert-success'], 
      timeout: 1000
    });
  }
  validEmail(email){
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }
}
