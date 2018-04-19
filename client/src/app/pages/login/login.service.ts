import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from "../../cdg-objects/user"
import { endpoints } from "../../../environments/endpoints"

@Injectable()
export class LoginService {
  constructor(private http: HttpClient) {}

  login(user){
    return this.http.post(endpoints.LOGIN_ENDPOINT,user);
  }
  register(user){
    return this.http.post(endpoints.REGISTER_ENDPOINT,user);
  }
  logout(){
    return this.http.get(endpoints.LOGOUT_ENDPOINT);
  }

}
