import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from "../../cdg-objects/user";
import { endpoints } from "../../../environments/endpoints";
import { Observable } from 'rxjs';

@Injectable()
export class LoginService {
  constructor(private http: HttpClient) {}

  login(user){
    return this.http.post(endpoints.LOGIN_ENDPOINT,JSON.stringify(user), {headers:{'Content-Type': 'application/json'}, withCredentials:true})
  }
  register(user){
    return this.http.post(endpoints.REGISTER_ENDPOINT,JSON.stringify(user), {headers:{'Content-Type': 'application/json'}, withCredentials:true});
  }
  logout(){
    return this.http.post(endpoints.LOGOUT_ENDPOINT, null, {withCredentials:true});
  }
  deleteUser(user){
    return this.http.post(endpoints.DELETE_USER_ENDPOINT, user, {withCredentials: true});
  }
  editUser(user){
    return this.http.post(endpoints.EDIT_USER_ENDPOINT, user, {withCredentials: true});
  }
  getAllUsers(): Observable<User[]> {    
    return this.http.get(endpoints.GET_ALL_USERS_ENDPOINT,{withCredentials: true})
      .catch(this.handleError);
  }
  isUserLoggedIn(): Observable<boolean>{    
    return this.http.get(endpoints.GET_LOGGED_IN_USER, {withCredentials: true})
    .catch(this.handleError);
  }
  private handleError (error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.status);
  }
}
