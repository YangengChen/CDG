import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { CdgModule} from './cdg/cdg.module';
const appRoutes: Routes = [
  {path: "", component:HomeComponent},
  {path: 'login', component: LoginComponent},
];

@NgModule({
  imports: [
    RouterModule.forRoot(
    appRoutes
    ),
    CommonModule
  ],
  exports:[RouterModule]
})
export class AppRoutingModule { }
