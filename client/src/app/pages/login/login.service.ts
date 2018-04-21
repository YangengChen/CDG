import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from "../../cdg-objects/user"
import { endpoints } from "../../../environments/endpoints"

@Injectable()
export class LoginService {
  constructor(private http: HttpClient) {}

  login(user){
    return this.http.post(endpoints.LOGIN_ENDPOINT,user,{withCredentials: true})
  }
  register(user){
    return this.http.post(endpoints.REGISTER_ENDPOINT,user,{withCredentials: true});
  }
  logout(){
    console.log("logout")
    return this.http.post(endpoints.LOGOUT_ENDPOINT, null,{withCredentials: true});
  }
}
