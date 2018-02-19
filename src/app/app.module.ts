import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { CdgModule } from './cdg/cdg.module';
import { PagesModule } from './pages/pages.module';
import { NbThemeModule } from '@nebular/theme';
import {AppRoutingModule} from './app-routing.module';
import { HomeComponent } from './pages/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    NbThemeModule.forRoot({ name: 'default' }),
    AppRoutingModule,
    CdgModule,
    PagesModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
