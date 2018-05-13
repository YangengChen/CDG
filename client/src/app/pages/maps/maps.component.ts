import { Component, OnInit } from '@angular/core';
import { SavedMap } from '../../cdg-objects/savedmap';
import { LoginService } from '../login/login.service';
import { Router } from '@angular/router';
import { GenerationService } from '../../cdg/generation.service';

@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.scss']
})
export class MapsComponent implements OnInit {

  SavedMaps : SavedMap[];

  getUserMaps(){
    this.loginService.getUserMaps().subscribe(
      data => this.SavedMaps = data
    );
  }

  constructor(private loginService: LoginService, private router: Router, private genService: GenerationService) { 
    this.getUserMaps();
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

  deleteMap(map: SavedMap){
    this.genService.deleteGeneration(map.id).subscribe(
      data => this.getUserMaps()
     );
  }

}
