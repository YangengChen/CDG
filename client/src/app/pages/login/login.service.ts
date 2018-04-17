import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from "../../cdg-objects/user"
import { endpoints } from "../../../environments/endpoints"

@Injectable()
export class LoginService {
  constructor(private http: HttpClient) {}
  loginUrl:string  = "http://localhost:8080/api/user/login";
  logoutUrl:string  = "http://localhost:8080/api/user/logout";
  registerUrl:string  = "http://localhost:8080/api/user/register";

  authenticate(username: string, password: string){
    return this.http.post(this.loginUrl,{email:username, password:password});
  }
  register(user){
    return this.http.post(endpoints.REGISTER_ENDPOINT,user);
  }
  logout(){
    this.http.get(this.logoutUrl);
  }

}
