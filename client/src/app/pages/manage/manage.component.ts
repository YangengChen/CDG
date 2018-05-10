import { Component, OnInit } from '@angular/core';
import { User } from "../../cdg-objects/user"
import { StateStat } from "../../cdg-objects/statestat"
import { LoginService } from "../login/login.service"
import { Observable } from 'rxjs/Observable';
import { FlashMessagesService } from 'ngx-flash-messages';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';
import { Constants } from '../../constants';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.scss']
})
export class ManageComponent implements OnInit {
  
  Users : User[];
  StateStats : StateStat[];
  statusCode: number;
  modalUser : User;
  
  constructor(private router: Router, private loginService: LoginService, private flashMessagesService: FlashMessagesService) {
    this.loginService.isUserLoggedIn().subscribe(
      data => data ? this.getPageData() : this.router.navigate(["/"]),
      errorCode =>  this.statusCode = errorCode
    );
    this.modalUser = new User();
  }

  ngOnInit() : void{
  }
  logout(){
    this.loginService.logout().subscribe(
      (data) => {
        this.router.navigate(["/"]);
      },
      (err) => { }
    )
  }
  getPageData() {
    this.getUsers();
    this.getStateStats();
  }
  getUsers(): void {
    this.loginService.getAllUsers().subscribe(
      data => this.Users = data, 
      errorCode =>  this.statusCode = errorCode        
    );
  }
  getStateStats(): void {
    this.loginService.getStateStats().subscribe(
      data => this.StateStats = data, 
      errorCode =>  this.statusCode = errorCode        
    );
  }

  clickEdit(form){
    this.isFormValid(form) ? this.editUser() : this.flashError(environment.INVALID_CRED);
  }
  clickAdd(form){
    this.isFormValid(form) ? this.addUser() : this.flashError(environment.INVALID_CRED);
  }
  deleteUser(user){
    if(confirm(Constants.CONFIRM_DELETE_USER)){
    	this.loginService.deleteUser(user).subscribe(
          (data) => {this.getUsers()}, 
          (err) =>{}
      );  
    }
  }
  editUser(){
    if(confirm(Constants.CONFIRM_EDIT_USER)){
      this.loginService.editUser(this.modalUser).subscribe(
        (data) => {this.getUsers()}, 
        (err) =>{}
      );  
      this.closeModal()
    }
  }
  addUser(){
    if(confirm(Constants.CONFIRM_ADD_USER)){
      this.loginService.register(this.modalUser).subscribe(
        (data) => {this.getUsers()}, 
        (err) =>{}
      );  
      this.closeModal()
    } 
  }
  openModalEdit(user){
    this.modalUser.firstName = user.firstName;
    this.modalUser.lastName = user.lastName;
    this.modalUser.email = user.email;
    this.modalUser.password = ""
    var modal = document.getElementById('modalEdit');
    modal.style.display = "block";
  }
  openModalAdd(){
    this.modalUser.firstName = "";
    this.modalUser.lastName = "";
    this.modalUser.email = "";
    this.modalUser.password = "";
    var modal = document.getElementById('modalAdd');
    modal.style.display = "block";
  }
  closeModal(){
    var modalEdit = document.getElementById('modalEdit');
    var modalAdd = document.getElementById('modalAdd');
    modalEdit.style.display = "none";
    modalAdd.style.display = "none";
  }

  selectUsersTab(){
    document.getElementById("usersTab").className = "active";
    document.getElementById("propertiesTab").className = "";
    document.getElementById("mapsTab").className = "";
    document.getElementById("statsTab").className = "";
  }
  selectPropertiesTab(){
    document.getElementById("usersTab").className = "";
    document.getElementById("propertiesTab").className = "active";
    document.getElementById("mapsTab").className = "";
    document.getElementById("statsTab").className = "";
  }
  selectMapsTab(){
    document.getElementById("usersTab").className = "";
    document.getElementById("propertiesTab").className = "";
    document.getElementById("mapsTab").className = "active";
    document.getElementById("statsTab").className = "";
  }
  selectStatsTab(){
    document.getElementById("usersTab").className = "";
    document.getElementById("propertiesTab").className = "";
    document.getElementById("mapsTab").className = "";
    document.getElementById("statsTab").className = "active";
  }
  isFormValid(form){
    return form.valid && this.validEmail(this.modalUser.email)
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
