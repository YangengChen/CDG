import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { CdgModule } from './cdg/cdg.module';
import { CdgUiModule } from './cdg-ui/cdg-ui.module';
import { PagesModule } from './pages/pages.module';
import { NbThemeModule } from '@nebular/theme';
import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
import { APP_INITIALIZER } from '@angular/core';
import { AppProperties }       from './app.properties'
import { HttpModule }      from '@angular/http';
// import { NbAuthModule,
//   NbAuthComponent,
//   NbLoginComponent,
//   NbRegisterComponent,
//   NbLogoutComponent,
//   NbRequestPasswordComponent,
//   NbResetPasswordComponent,
// } from '@nebular/auth';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './pages/home/home.component';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { CdgMapService } from "./cdg/map/map.service";

import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatStepperModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
} from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import 'hammerjs';
//import { CdgStepComponent } from './cdg-step/cdg-step.component'
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
  //  CdgStepComponent,
  ],
  imports: [
        NgxMapboxGLModule.forRoot({
      accessToken:"pk.eyJ1IjoiYnJvYmljaGVhdSIsImEiOiJjamZ2cGlsZXczaHA5MzNtZG52MWoxMjJtIn0.jnvZ-TdxfZ5Qm8zoWzO65g",
      geocoderAccessToken: "pk.eyJ1IjoiYnJvYmljaGVhdSIsImEiOiJjamZ2cGlsZXczaHA5MzNtZG52MWoxMjJtIn0.jnvZ-TdxfZ5Qm8zoWzO65g"
    }), 
    HttpModule,
    BrowserModule,
    NbThemeModule.forRoot({ name: 'default' }),
    AppRoutingModule,
    CdgModule,
    NgbModule.forRoot(),
    PagesModule,
    HttpClientModule,
    BrowserAnimationsModule,
    LeafletModule.forRoot(),
    // NbAuthModule.forRoot(),
    // NbAuthComponent,
    // NbLoginComponent,
    // NbRegisterComponent,
    // NbLogoutComponent,
    // NbRequestPasswordComponent,
    // NbResetPasswordComponent,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatStepperModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
  ],
  providers: [
    AppProperties,
    { provide: APP_INITIALIZER, useFactory: (config: AppProperties) => () => config.load(), deps: [AppProperties], multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
