import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class LoginService {
  constructor(private http: HttpClient) {}
  loginUrl:string  = "http://localhost:8080/api/user/login";
  logoutUrl:string  = "http://localhost:8080/api/user/logout";
  authenticate(username: string, password: string){
    return this.http.post(this.loginUrl,{email:username, password:password});
  }

  logout(){
    this.http.get(this.logoutUrl);
  }

}
