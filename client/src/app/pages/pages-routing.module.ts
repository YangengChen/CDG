import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import {LoginComponent} from './login/login.component';
import { CdgComponent } from '../cdg/cdg.component';
const pageRoutes: Routes = [
  {path: 'login', component: LoginComponent},
];

@NgModule({
  imports: [
    RouterModule.forChild(pageRoutes)
  ],
  exports:[RouterModule]
})
export class PagesRoutingModule { }
