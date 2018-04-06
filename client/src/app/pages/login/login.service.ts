import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class LoginService {
  constructor(private http: HttpClient) {}
  loginUrl:string  = "";
  authenticate(username: string, password: string){
    return this.http.post(this.loginUrl,{username:username, password:password});
  }
}
