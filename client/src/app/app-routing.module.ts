import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './pages/home/home.component';
const appRoutes: Routes = [
  {path: "", component:HomeComponent},
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
