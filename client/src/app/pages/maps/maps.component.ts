import { Component, OnInit } from '@angular/core';
import { SavedMap } from '../../cdg-objects/savedmap';
import { LoginService } from '../login/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.scss']
})
export class MapsComponent implements OnInit {

  SavedMaps : SavedMap[];

  constructor(private loginService: LoginService, private router: Router) { 
    this.loginService.getUserMaps().subscribe(
      data => this.SavedMaps = data
    );
  }

  ngOnInit() {
  }

  logout(){
    this.loginService.logout().subscribe(
      (data) => {
        this.router.navigate(["/"]);
      },
      (err) => { }
    )
  }

}
