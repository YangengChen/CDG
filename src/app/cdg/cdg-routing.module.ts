import { CdgComponent } from './cdg.component';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

const cdgRoutes: Routes = [
  {path: 'cdg', component: CdgComponent},
];

@NgModule({
  imports: [
    RouterModule.forChild(cdgRoutes)
  ],
  exports:[RouterModule]
})
export class CdgRoutingModule { }
