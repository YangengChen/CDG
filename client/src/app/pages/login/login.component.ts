import { Injectable, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { LoginService } from "./login.service";
import { User } from "../../cdg-objects/user"

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  response: string = "";
  model: any = {};
  newUser : User;

  constructor(private router: Router, private loginService: LoginService) {
    this.newUser = new User()
  }

  login(){
    if(this.model.password == "password" && this.model.username == "username")
      this.router.navigate(["/cdg"])
  }

  logout(){
      this.router.navigateByUrl("/");
  }

  register(){
    console.log("New User: ", this.newUser)
    this.loginService.register(this.newUser).subscribe(
      (data) =>{
        console.log(data);
      },
      (err) =>{
        console.error(err);
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
