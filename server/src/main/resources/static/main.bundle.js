webpackJsonp(["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/app-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("./node_modules/@angular/common/esm5/common.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__cdg_cdg_component__ = __webpack_require__("./src/app/cdg/cdg.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__pages_home_home_component__ = __webpack_require__("./src/app/pages/home/home.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};





var appRoutes = [
    { path: "", component: __WEBPACK_IMPORTED_MODULE_4__pages_home_home_component__["a" /* HomeComponent */] },
    { path: 'cdg', component: __WEBPACK_IMPORTED_MODULE_3__cdg_cdg_component__["a" /* CdgComponent */] },
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_2__angular_router__["d" /* RouterModule */].forRoot(appRoutes),
                __WEBPACK_IMPORTED_MODULE_1__angular_common__["b" /* CommonModule */]
            ],
            exports: [__WEBPACK_IMPORTED_MODULE_2__angular_router__["d" /* RouterModule */]]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "./src/app/app.component.css":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<div style=\"width:100%;height:100%;\">\r\n    <router-outlet></router-outlet>\r\n</div>"

/***/ }),

/***/ "./src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = /** @class */ (function () {
    function AppComponent() {
        this.title = 'app';
    }
    AppComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-root',
            template: __webpack_require__("./src/app/app.component.html"),
            styles: [__webpack_require__("./src/app/app.component.css")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("./node_modules/@angular/platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_component__ = __webpack_require__("./src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__cdg_cdg_module__ = __webpack_require__("./src/app/cdg/cdg.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__pages_pages_module__ = __webpack_require__("./src/app/pages/pages.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__nebular_theme__ = __webpack_require__("./node_modules/@nebular/theme/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__ = __webpack_require__("./node_modules/@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__app_routing_module__ = __webpack_require__("./src/app/app-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__pages_home_home_component__ = __webpack_require__("./src/app/pages/home/home.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__angular_platform_browser_animations__ = __webpack_require__("./node_modules/@angular/platform-browser/esm5/animations.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__angular_material__ = __webpack_require__("./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_hammerjs__ = __webpack_require__("./node_modules/hammerjs/hammer.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_hammerjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_12_hammerjs__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};






// import { NbAuthModule,
//   NbAuthComponent,
//   NbLoginComponent,
//   NbRegisterComponent,
//   NbLogoutComponent,
//   NbRequestPasswordComponent,
//   NbResetPasswordComponent,
// } from '@nebular/auth';







var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["K" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_2__app_component__["a" /* AppComponent */],
                __WEBPACK_IMPORTED_MODULE_8__pages_home_home_component__["a" /* HomeComponent */],
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_5__nebular_theme__["g" /* NbThemeModule */].forRoot({ name: 'default' }),
                __WEBPACK_IMPORTED_MODULE_7__app_routing_module__["a" /* AppRoutingModule */],
                __WEBPACK_IMPORTED_MODULE_3__cdg_cdg_module__["a" /* CdgModule */],
                __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["a" /* NgbModule */].forRoot(),
                __WEBPACK_IMPORTED_MODULE_4__pages_pages_module__["a" /* PagesModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_common_http__["b" /* HttpClientModule */],
                __WEBPACK_IMPORTED_MODULE_9__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
                // NbAuthModule.forRoot(),
                // NbAuthComponent,
                // NbLoginComponent,
                // NbRegisterComponent,
                // NbLogoutComponent,
                // NbRequestPasswordComponent,
                // NbResetPasswordComponent,
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["a" /* MatAutocompleteModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["b" /* MatButtonModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["c" /* MatButtonToggleModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["d" /* MatCardModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["e" /* MatCheckboxModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["f" /* MatChipsModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["g" /* MatDatepickerModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["h" /* MatDialogModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["i" /* MatDividerModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["j" /* MatExpansionModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["k" /* MatGridListModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["l" /* MatIconModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["m" /* MatInputModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["n" /* MatListModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["o" /* MatMenuModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["p" /* MatNativeDateModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["q" /* MatPaginatorModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["r" /* MatProgressBarModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["s" /* MatProgressSpinnerModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["t" /* MatRadioModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["u" /* MatRippleModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["v" /* MatSelectModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["w" /* MatSidenavModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["y" /* MatSliderModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["x" /* MatSlideToggleModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["z" /* MatSnackBarModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["A" /* MatSortModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["B" /* MatStepperModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["C" /* MatTableModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["D" /* MatTabsModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["E" /* MatToolbarModule */],
                __WEBPACK_IMPORTED_MODULE_10__angular_material__["F" /* MatTooltipModule */],
            ],
            providers: [],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_2__app_component__["a" /* AppComponent */]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/cdg-ui/cdg-button/cdg-button.component.html":
/***/ (function(module, exports) {

module.exports = "  <div>\n    <button class=\"btn btn-primary\" type=\"button\">\n      {{name}}\n    </button>\n  </div>"

/***/ }),

/***/ "./src/app/cdg-ui/cdg-button/cdg-button.component.scss":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/cdg-ui/cdg-button/cdg-button.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CdgButtonComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var CdgButtonComponent = /** @class */ (function () {
    function CdgButtonComponent() {
    }
    CdgButtonComponent.prototype.ngOnInit = function () {
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* Input */])(),
        __metadata("design:type", String)
    ], CdgButtonComponent.prototype, "name", void 0);
    CdgButtonComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'cdg-button',
            template: __webpack_require__("./src/app/cdg-ui/cdg-button/cdg-button.component.html"),
            styles: [__webpack_require__("./src/app/cdg-ui/cdg-button/cdg-button.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], CdgButtonComponent);
    return CdgButtonComponent;
}());



/***/ }),

/***/ "./src/app/cdg-ui/cdg-dropdown/cdg-dropdown.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"dropdown\" ngbDropdown>\n  <button class=\"btn btn-primary\" type=\"button\" ngbDropdownToggle>\n  {{name}}\n  </button>\n  <ul id=\"test\" class=\"dropdown-menu\" ngbDropdownMenu>\n    <li class=\"dropdown-item\">New York</li>\n    <li class=\"dropdown-item\">California</li>\n    <li class=\"dropdown-item\">Texas</li>\n  </ul>\n</div>"

/***/ }),

/***/ "./src/app/cdg-ui/cdg-dropdown/cdg-dropdown.component.scss":
/***/ (function(module, exports) {

module.exports = "#test {\n  overflow: visible; }\n"

/***/ }),

/***/ "./src/app/cdg-ui/cdg-dropdown/cdg-dropdown.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CdgDropdownComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var CdgDropdownComponent = /** @class */ (function () {
    function CdgDropdownComponent() {
    }
    CdgDropdownComponent.prototype.ngOnInit = function () {
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* Input */])(),
        __metadata("design:type", String)
    ], CdgDropdownComponent.prototype, "name", void 0);
    CdgDropdownComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'cdg-dropdown',
            template: __webpack_require__("./src/app/cdg-ui/cdg-dropdown/cdg-dropdown.component.html"),
            styles: [__webpack_require__("./src/app/cdg-ui/cdg-dropdown/cdg-dropdown.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], CdgDropdownComponent);
    return CdgDropdownComponent;
}());



/***/ }),

/***/ "./src/app/cdg-ui/cdg-slider/cdg-slider.component.html":
/***/ (function(module, exports) {

module.exports = "<div id=\"cdg-slider-container\">\n  <span style=\"display:inline-block;\">\n    <label style=\"display:block;\" for=\"slider\">{{name}}</label>\n    <mat-slider\n      id=\"slider\"\n      min=\"1\" \n      max=\"100\" \n      step=\"0.5\" \n      value=\"100\"\n      color=\"primary\"\n    ></mat-slider>\n  </span>\n</div>\n"

/***/ }),

/***/ "./src/app/cdg-ui/cdg-slider/cdg-slider.component.scss":
/***/ (function(module, exports) {

module.exports = "mat-slider {\n  width: 200px; }\n\n#cdg-slider-container {\n  width: 100%;\n  height: 100%; }\n"

/***/ }),

/***/ "./src/app/cdg-ui/cdg-slider/cdg-slider.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CdgSliderComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var CdgSliderComponent = /** @class */ (function () {
    function CdgSliderComponent() {
    }
    CdgSliderComponent.prototype.ngOnInit = function () {
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* Input */])(),
        __metadata("design:type", String)
    ], CdgSliderComponent.prototype, "name", void 0);
    CdgSliderComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'cdg-slider',
            template: __webpack_require__("./src/app/cdg-ui/cdg-slider/cdg-slider.component.html"),
            styles: [__webpack_require__("./src/app/cdg-ui/cdg-slider/cdg-slider.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], CdgSliderComponent);
    return CdgSliderComponent;
}());



/***/ }),

/***/ "./src/app/cdg-ui/cdg-ui.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CdgUiModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("./node_modules/@angular/common/esm5/common.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__cdg_dropdown_cdg_dropdown_component__ = __webpack_require__("./src/app/cdg-ui/cdg-dropdown/cdg-dropdown.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ng_bootstrap_ng_bootstrap__ = __webpack_require__("./node_modules/@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__cdg_button_cdg_button_component__ = __webpack_require__("./src/app/cdg-ui/cdg-button/cdg-button.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__cdg_slider_cdg_slider_component__ = __webpack_require__("./src/app/cdg-ui/cdg-slider/cdg-slider.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__angular_platform_browser_animations__ = __webpack_require__("./node_modules/@angular/platform-browser/esm5/animations.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_material__ = __webpack_require__("./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_hammerjs__ = __webpack_require__("./node_modules/hammerjs/hammer.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_hammerjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_8_hammerjs__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};









var CdgUiModule = /** @class */ (function () {
    function CdgUiModule() {
    }
    CdgUiModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_common__["b" /* CommonModule */],
                __WEBPACK_IMPORTED_MODULE_3__ng_bootstrap_ng_bootstrap__["a" /* NgbModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["y" /* MatSliderModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
            ],
            declarations: [
                __WEBPACK_IMPORTED_MODULE_2__cdg_dropdown_cdg_dropdown_component__["a" /* CdgDropdownComponent */],
                __WEBPACK_IMPORTED_MODULE_4__cdg_button_cdg_button_component__["a" /* CdgButtonComponent */],
                __WEBPACK_IMPORTED_MODULE_5__cdg_slider_cdg_slider_component__["a" /* CdgSliderComponent */],
            ],
            exports: [
                __WEBPACK_IMPORTED_MODULE_2__cdg_dropdown_cdg_dropdown_component__["a" /* CdgDropdownComponent */],
                __WEBPACK_IMPORTED_MODULE_4__cdg_button_cdg_button_component__["a" /* CdgButtonComponent */],
                __WEBPACK_IMPORTED_MODULE_5__cdg_slider_cdg_slider_component__["a" /* CdgSliderComponent */],
            ]
        })
    ], CdgUiModule);
    return CdgUiModule;
}());



/***/ }),

/***/ "./src/app/cdg/cdg-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CdgRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__cdg_component__ = __webpack_require__("./src/app/cdg/cdg.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var cdgRoutes = [
    { path: 'cdg', component: __WEBPACK_IMPORTED_MODULE_0__cdg_component__["a" /* CdgComponent */] },
];
var CdgRoutingModule = /** @class */ (function () {
    function CdgRoutingModule() {
    }
    CdgRoutingModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_2__angular_core__["K" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_router__["d" /* RouterModule */].forChild(cdgRoutes)
            ],
            exports: [__WEBPACK_IMPORTED_MODULE_1__angular_router__["d" /* RouterModule */]]
        })
    ], CdgRoutingModule);
    return CdgRoutingModule;
}());



/***/ }),

/***/ "./src/app/cdg/cdg.component.html":
/***/ (function(module, exports) {

module.exports = "    <nb-layout>\n      <nb-layout-header  fixed >\n        <div style=\"padding-top:1.5rem;padding-left:1rem;text-align:middle; width:100%;\">\n          <h1>CDG\n            <h6>Congressional District Generator</h6>\n          </h1>\n        </div>\n        <div id=\"user-container\" style=\"padding-right:5rem; margin-top:30px;\">\n              <nb-user title=\"Guest-User\" name=\"Bears\"></nb-user>\n        </div>\n      </nb-layout-header>\n      <nb-sidebar id=\"sidebar\">\n        <nb-card>\n          <nb-tabset class=\"full-width\">\n          <nb-tab tabTitle=\"Generate\">\n            <div id=\"generate-tool-container\">\n              <div id=\"pick-state-button\">\n                <cdg-dropdown name=\"States\"></cdg-dropdown>\n              </div>\n              <div id=\"slider-container\">\n                <cdg-slider name=\"Compactness\"></cdg-slider>\n                <cdg-slider name=\"Contiguity\"></cdg-slider>\n                <cdg-slider name=\"Equal population\"></cdg-slider>\n                <cdg-slider name=\"Partisan fairness\"></cdg-slider>\n                <cdg-slider name=\"Racial fairness\"></cdg-slider>\n              </div>\n              <div id=\"generate-button\">\n                <cdg-button (click)=\"clickOn()\" name=\"Generate Districts\"></cdg-button>\n              </div>\n              <div id=\"replay-generate-button\">\n                <cdg-button name=\"Replay Generation\"></cdg-button>\n              </div>\n            </div>\n          </nb-tab>\n          <nb-tab tabTitle=\"Information\">\n            <div id=\"information-container\" class=\"container\">\n              <div id=\"tab-data-block\">\n              <br>\n              <cdg-dropdown name=\"States\"></cdg-dropdown>\n              <br><br>\n              This is where the drop down info about the selected state would go\n              <br><p>Name: ______________</p>\n              <br><p>Socioeconomic Status: ______________</p>\n              <br><p>Population: ______________ </p>\n              <br><p>Partisan Fairness: ______________ </p>\n              <br><p>Racial Fairness: ______________ </p>\n              <br><p>Political Communities Preservation: ______________ </p>\n              </div>\n            </div>\n           </nb-tab>\n          <nb-tab tabTitle=\"File\">\n            <div id=\"information-container\" class=\"container\">\n            <div id=\"file-buttons\">\n              <cdg-button name=\"Load File\" class=\"file-button\"></cdg-button>\n              <cdg-button name=\"Save Map\" class=\"file-button\"></cdg-button>\n              <cdg-button name=\"Export Map\" class=\"file-button\"></cdg-button>\n            </div>\n            </div>\n          </nb-tab>\n          </nb-tabset>\n        </nb-card>\n      </nb-sidebar>\n\n      <nb-layout-column>\n        <map></map>\n      </nb-layout-column>\n    </nb-layout>"

/***/ }),

/***/ "./src/app/cdg/cdg.component.scss":
/***/ (function(module, exports) {

module.exports = "/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * This is a starting point where we declare the maps of themes and globally available functions/mixins\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * This mixin generates keyfames.\n * Because of all keyframes can't be scoped,\n * we need to puts unique name in each btn-pulse call.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * This mixin generates keyfames.\n * Because of all keyframes can't be scoped,\n * we need to puts unique name in each btn-pulse call.\n */\n#state-button {\n  width: 200px; }\n#dropperino {\n  width: 100%;\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-pack: justify;\n      -ms-flex-pack: justify;\n          justify-content: space-between;\n  text-align: center;\n  vertical-align: middle;\n  line-height: 50px; }\nnb-layout-colum {\n  padding: 0px, 0px ,0px , 0px; }\nnb-card {\n  height: 98%; }\nnb-tab {\n  width: 98%;\n  height: 98%; }\nnb-tabset {\n  width: 90%;\n  height: 90%; }\n#pick-state-button {\n  width: 300px;\n  height: 50px;\n  margin: 7%;\n  overflow: visible; }\n#generate-tool-container {\n  width: 98%;\n  height: 90%;\n  padding: 10%; }\n#slider-container {\n  margin: 7%; }\n#generate-button {\n  margin: 7%; }\n#replay-generate-button {\n  margin: 7%; }\n#user-container {\n  height: 75px;\n  width: 200px; }\n#sidebar {\n  padding-top: 16px; }\n.file-button {\n  padding: 20px; }\n#file-buttons {\n  margin-left: 7%; }\n"

/***/ }),

/***/ "./src/app/cdg/cdg.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CdgComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var CdgComponent = /** @class */ (function () {
    function CdgComponent(http) {
        this.http = http;
        this.url = "localhost/8080/api/map";
        this.dat = "";
    }
    ;
    CdgComponent.prototype.clickOn = function () {
        var _this = this;
        this.http.get(this.url).subscribe(function (data) { return _this.dat = data[0]; });
    };
    CdgComponent.prototype.ngOnInit = function () {
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* Input */])(),
        __metadata("design:type", Array)
    ], CdgComponent.prototype, "items", void 0);
    CdgComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-cdg',
            template: __webpack_require__("./src/app/cdg/cdg.component.html"),
            styles: [__webpack_require__("./src/app/cdg/cdg.component.scss")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], CdgComponent);
    return CdgComponent;
}());



/***/ }),

/***/ "./src/app/cdg/cdg.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CdgModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("./node_modules/@angular/common/esm5/common.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__cdg_component__ = __webpack_require__("./src/app/cdg/cdg.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__cdg_ui_cdg_ui_module__ = __webpack_require__("./src/app/cdg-ui/cdg-ui.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__map_map_service__ = __webpack_require__("./src/app/cdg/map/map.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__nebular_theme__ = __webpack_require__("./node_modules/@nebular/theme/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__cdg_routing_module__ = __webpack_require__("./src/app/cdg/cdg-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__map_map_component__ = __webpack_require__("./src/app/cdg/map/map.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__agm_core__ = __webpack_require__("./node_modules/@agm/core/index.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};










var CdgModule = /** @class */ (function () {
    function CdgModule() {
    }
    CdgModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_5__angular_common_http__["b" /* HttpClientModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_common__["b" /* CommonModule */],
                __WEBPACK_IMPORTED_MODULE_6__nebular_theme__["d" /* NbSidebarModule */],
                __WEBPACK_IMPORTED_MODULE_6__nebular_theme__["b" /* NbLayoutModule */],
                __WEBPACK_IMPORTED_MODULE_7__cdg_routing_module__["a" /* CdgRoutingModule */],
                __WEBPACK_IMPORTED_MODULE_6__nebular_theme__["a" /* NbCardModule */],
                __WEBPACK_IMPORTED_MODULE_6__nebular_theme__["f" /* NbTabsetModule */],
                __WEBPACK_IMPORTED_MODULE_3__cdg_ui_cdg_ui_module__["a" /* CdgUiModule */],
                __WEBPACK_IMPORTED_MODULE_6__nebular_theme__["h" /* NbUserModule */],
                __WEBPACK_IMPORTED_MODULE_6__nebular_theme__["c" /* NbMenuModule */],
                __WEBPACK_IMPORTED_MODULE_9__agm_core__["a" /* AgmCoreModule */].forRoot({
                    apiKey: 'AIzaSyDbo0ubVsgdziReD82-FBC9KCbuiZeFnuU'
                })
            ],
            declarations: [
                __WEBPACK_IMPORTED_MODULE_2__cdg_component__["a" /* CdgComponent */],
                __WEBPACK_IMPORTED_MODULE_8__map_map_component__["a" /* MapComponent */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_6__nebular_theme__["e" /* NbSidebarService */], __WEBPACK_IMPORTED_MODULE_4__map_map_service__["a" /* MapService */]]
        })
    ], CdgModule);
    return CdgModule;
}());



/***/ }),

/***/ "./src/app/cdg/map/map.component.html":
/***/ (function(module, exports) {

module.exports = "<nb-card>\n  <nb-card-header>\n    <div style=\"position:relative\" id=\"header-container\">\n      <div style=\"width:200px; display:inline-block;\">\n        <cdg-dropdown name=\"MapType\"></cdg-dropdown>\n      </div>\n      <div style=\"width:200px; display:inline-block;\">\n        <cdg-dropdown name=\"Saved Maps\"></cdg-dropdown>\n      </div>\n      <div style=\"padding-top:0.7rem;position:absolute;right:10px;display:inline-block;\">\n        Districts Map\n      </div>\n    </div>\n  </nb-card-header>\n  <nb-card-body>\n    <agm-map>\n      <agm-marker></agm-marker>\n    </agm-map>\n  </nb-card-body>\n</nb-card>"

/***/ }),

/***/ "./src/app/cdg/map/map.component.scss":
/***/ (function(module, exports) {

module.exports = "/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * This is a starting point where we declare the maps of themes and globally available functions/mixins\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * This mixin generates keyfames.\n * Because of all keyframes can't be scoped,\n * we need to puts unique name in each btn-pulse call.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * This mixin generates keyfames.\n * Because of all keyframes can't be scoped,\n * we need to puts unique name in each btn-pulse call.\n */\n:host-context(.nb-theme-default) nb-card {\n  padding: 0;\n  height: 98%; }\n:host-context(.nb-theme-default) nb-card-body {\n  padding: 0;\n  height: 100%; }\n:host-context(.nb-theme-default) /deep/ agm-map {\n  width: 100%;\n  height: 98%; }\n"

/***/ }),

/***/ "./src/app/cdg/map/map.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MapComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__map_service__ = __webpack_require__("./src/app/cdg/map/map.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var MapComponent = /** @class */ (function () {
    function MapComponent(mapService) {
        this.mapService = mapService;
    }
    MapComponent.prototype.ngOnInit = function () {
        this.map = "hi";
    };
    MapComponent.prototype.showMap = function () {
        var _this = this;
        this.mapService.getMap()
            .subscribe(function (data) { return _this.map = data.toString(); });
    };
    MapComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'map',
            template: __webpack_require__("./src/app/cdg/map/map.component.html"),
            styles: [__webpack_require__("./src/app/cdg/map/map.component.scss")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__map_service__["a" /* MapService */]])
    ], MapComponent);
    return MapComponent;
}());



/***/ }),

/***/ "./src/app/cdg/map/map.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MapService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var MapService = /** @class */ (function () {
    function MapService(http) {
        this.http = http;
        this.mapurl = "/api/map";
    }
    MapService.prototype.getMap = function () {
        return this.http.get(this.mapurl);
    };
    MapService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], MapService);
    return MapService;
}());



/***/ }),

/***/ "./src/app/pages/home/home.component.css":
/***/ (function(module, exports) {

module.exports = "\r\n@media (min-width: 768px) {\r\n  p {\r\n    font-size: 18px;\r\n    line-height: 1.6;\r\n    margin: 0 0 35px;\r\n  }\r\n}\r\n\r\na {\r\n  -webkit-transition: all 0.2s ease-in-out;\r\n  transition: all 0.2s ease-in-out;\r\n  color: #42DCA3;\r\n}\r\n\r\na:focus, a:hover {\r\n  text-decoration: none;\r\n  color: #1d9b6c;\r\n}\r\n\r\n#mainNav {\r\n  font-family: 'Cabin', 'Helvetica Neue', Helvetica, Arial, sans-serif;\r\n  margin-bottom: 0;\r\n  text-transform: uppercase;\r\n  border-bottom: 1px solid rgba(255, 255, 255, 0.3);\r\n  background-color: #050649;\r\n}\r\n\r\n#mainNav .navbar-toggler {\r\n  font-size: 14px;\r\n  padding: 11px;\r\n  color: white;\r\n  border: 1px solid white;\r\n}\r\n\r\n#mainNav .navbar-brand {\r\n  font-weight: 700;\r\n}\r\n\r\n#mainNav a {\r\n  color: white;\r\n}\r\n\r\n#mainNav .navbar-nav .nav-item {\r\n  -webkit-transition: background 0.3s ease-in-out;\r\n  transition: background 0.3s ease-in-out;\r\n}\r\n\r\n#mainNav .navbar-nav .nav-item:hover {\r\n  color: fade(white, 80%);\r\n  outline: none;\r\n  background-color: transparent;\r\n}\r\n\r\n#mainNav .navbar-nav .nav-item:active, #mainNav .navbar-nav .nav-item:focus {\r\n  outline: none;\r\n  background-color: transparent;\r\n}\r\n\r\n@media (min-width: 992px) {\r\n  #mainNav {\r\n    padding-top: 20px;\r\n    padding-bottom: 20px;\r\n    -webkit-transition: background 0.3s ease-in-out, padding-top 0.3s ease-in-out, padding-bottom 0.3s;\r\n    transition: background 0.3s ease-in-out, padding-top 0.3s ease-in-out, padding-bottom 0.3s;\r\n    letter-spacing: 1px;\r\n    border-bottom: none;\r\n    background: transparent;\r\n  }\r\n  #mainNav.navbar-shrink {\r\n    padding-top: 10px;\r\n    padding-bottom: 10px;\r\n    border-bottom: 1px solid rgba(255, 255, 255, 0.3);\r\n    background: #050649;\r\n  }\r\n  #mainNav .nav-link.active {\r\n    outline: none;\r\n    background-color: rgba(255, 255, 255, 0.3);\r\n  }\r\n  #mainNav .nav-link.active:hover {\r\n    color: white;\r\n  }\r\n}\r\n\r\n.masthead {\r\n  display: table;\r\n  width: 100%;\r\n  height: 100vh;\r\n  padding: 200px 0;\r\n  text-align: center;\r\n  color: white;\r\n  background: url('intro-bg.9703cf625a06208c4c63.jpg') no-repeat bottom center scroll;\r\n  background-color: black;\r\n  background-size: cover;\r\n}\r\n\r\n.masthead .intro-body {\r\n  display: table-cell;\r\n  vertical-align: middle;\r\n}\r\n\r\n.masthead .intro-body .brand-heading {\r\n  font-size: 50px;\r\n  color:white;\r\n  stroke-width: 2px;\r\n  stroke:black;\r\n  background-color: rgba(255, 0, 0, 0.4);\r\n}\r\n\r\n.masthead .intro-body .intro-text {\r\n  font-size: 18px;\r\n  color:white;\r\n  font-weight: bold;\r\n  stroke:black;\r\n  background-color: rgba(0, 0, 255, 0.5);\r\n  padding: 10px;\r\n}\r\n\r\n@media (min-width: 768px) {\r\n  .masthead {\r\n    height: 100vh;\r\n    padding: 0;\r\n  }\r\n  .masthead .intro-body .brand-heading {\r\n    font-size: 100px;\r\n  }\r\n  .masthead .intro-body .intro-text {\r\n    font-size: 22px;\r\n  }\r\n}\r\n\r\nsection{\r\n    height:100vh;\r\n}\r\n\r\n.btn-circle {\r\n  font-size: 26px;\r\n  width: 55px;\r\n  height: 55px;\r\n  margin-top: 15px;\r\n  line-height: 45px;\r\n  -webkit-transition: background 0.3s ease-in-out;\r\n  transition: background 0.3s ease-in-out;\r\n  color: white;\r\n  border: 2px solid white;\r\n  border-radius: 100% !important;\r\n  background: transparent;\r\n}\r\n\r\n.btn-circle:focus, .btn-circle:hover {\r\n  color: white;\r\n  outline: none;\r\n  background: rgba(255, 255, 255, 0.1);\r\n}\r\n\r\n.content-section {\r\n  padding-top: 150px;\r\n  padding-bottom: 150px;\r\n}\r\n\r\n.download-section {\r\n  color: white;\r\n  background: url('downloads-bg.cc21d65a246542d885a6.jpg') no-repeat center center scroll;\r\n  background-color: black;\r\n  background-size: cover;\r\n}\r\n\r\n#map {\r\n  width: 100%;\r\n  height: 300px;\r\n}\r\n\r\n@media (min-width: 992px) {\r\n  .content-section {\r\n    padding-top: 200px;\r\n    padding-bottom: 200px;\r\n  }\r\n  #map {\r\n    height: 350px;\r\n  }\r\n}\r\n\r\n.btn {\r\n  font-family: 'Cabin', 'Helvetica Neue', Helvetica, Arial, sans-serif;\r\n  font-weight: 400;\r\n  -webkit-transition: all 0.3s ease-in-out;\r\n  transition: all 0.3s ease-in-out;\r\n  text-transform: uppercase;\r\n  border-radius: 0;\r\n}\r\n\r\n.btn-default {\r\n  color: #42DCA3;\r\n  border: 1px solid #42DCA3;\r\n  background-color: transparent;\r\n}\r\n\r\n.btn-default:focus, .btn-default:hover {\r\n  color: black;\r\n  border: 1px solid #42DCA3;\r\n  outline: none;\r\n  background-color: #42DCA3;\r\n}\r\n\r\nul.banner-social-buttons {\r\n  margin-top: 0;\r\n}\r\n\r\n@media (max-width: 1199px) {\r\n  ul.banner-social-buttons {\r\n    margin-top: 15px;\r\n  }\r\n}\r\n\r\n@media (max-width: 767px) {\r\n  ul.banner-social-buttons li {\r\n    display: block;\r\n    margin-bottom: 20px;\r\n    padding: 0;\r\n  }\r\n  ul.banner-social-buttons li:last-child {\r\n    margin-bottom: 0;\r\n  }\r\n}\r\n\r\nfooter {\r\n  padding: 50px 0;\r\n}\r\n\r\nfooter p {\r\n  font-size: 14px;\r\n  margin: 0;\r\n}\r\n\r\n::-moz-selection {\r\n  background: #fcfcfc;\r\n  background: rgba(255, 255, 255, 0.2);\r\n  text-shadow: none;\r\n}\r\n\r\n::selection {\r\n  background: #fcfcfc;\r\n  background: rgba(255, 255, 255, 0.2);\r\n  text-shadow: none;\r\n}\r\n\r\nimg::-moz-selection {\r\n  background: transparent;\r\n}\r\n\r\nimg::selection {\r\n  background: transparent;\r\n}\r\n\r\nimg::-moz-selection {\r\n  background: transparent;\r\n}\r\n\r\n.info-block {\r\n  margin: auto;\r\n  width: 60%;\r\n  padding: 100px 25px 0px 25px;\r\n}\r\n\r\n.member-box {\r\n  color:black;\r\n}\r\n\r\n.title-text {\r\n  color:white;\r\n  background-color: rgba(0, 0, 255, 0.3);\r\n}"

/***/ }),

/***/ "./src/app/pages/home/home.component.html":
/***/ (function(module, exports) {

module.exports = " <!-- Navigation -->\n    <nav class=\"navbar navbar-expand-lg navbar-light fixed-top\" \n    [ngClass]=\"{'navbar-shrink':isScrolled}\" \n    id=\"mainNav\">\n      <div class=\"container\">\n        <a class=\"navbar-brand js-scroll-trigger\" href=\"#page-top\">Congressional District Generator</a>\n        <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarResponsive\" aria-controls=\"navbarResponsive\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n          Menu\n          <i class=\"fa fa-bars\"></i>\n        </button>\n        <div class=\"collapse navbar-collapse\" id=\"navbarResponsive\">\n          <ul class=\"navbar-nav ml-auto\">\n            <li class=\"nav-item\">\n              <a class=\"nav-link js-scroll-trigger\" href=\"/login\">Sign-in</a>\n            </li>\n            <li class=\"nav-item\">\n              <a class=\"nav-link js-scroll-trigger\" href=\"#about\">About</a>\n            </li>\n            <li class=\"nav-item\">\n              <a class=\"nav-link js-scroll-trigger\" href=\"#contact\">Contact</a>\n            </li>\n          </ul>\n        </div>\n      </div>\n    </nav>\n\n    <header class=\"masthead\">\n       <div class=\"intro-body\">\n        <div class=\"container\">\n          <div class=\"row\">\n            <div class=\"col-lg-8 mx-auto\">\n              <h1 id=\"headind\"class=\"brand-heading\">CDG</h1>\n              <p id=\"heading-intro\" class=\"intro-text\">A Unbiased Congressional District Generating Application</p>\n            </div>\n          </div>\n        </div>\n      </div>\n    </header>\n\n    <section style=\"background:#050649;\" id=\"about\">\n      <div class=\"info-block\">\n        <h1>What is CDG</h1>\n        <p><b>A Gerrymander is a voting district that is designed to serve some political purpose. Within the past 10 years, databases for voter characterization as well as tools for precise map drawing have made it possible to create congressional districts that favor the party responsible for the creation of the districts. Redistricting is done in states where census data requires a change in the number of delegates in the state, and the 2010 census triggered redistricting in a number of states. Many of these redistricting efforts resulted in a shift in the political representation in the states. \n        <br><br>\n        Our program will take in user input and will perform the 'redistricting formula' to measure the goodness of districts and re-format them.\n        </b></p>\n      </div>\n    </section>\n\n    <section style=\"background:white;\" id=\"contact\">  \n      <div class=\"container\" class=\"info-block\">\n            <div class=\"row\">\n                <div class=\"col-lg-3 text-center\">\n                    <div class=\"member-box\">\n                        <i class=\"fa fa-4x fa-code\"></i>\n                        <h3>Lead Programmer</h3>\n                        <p>Kimberly Allan</p>\n                    </div>\n                </div>\n                <div class=\"col-lg-3 text-center\">\n                    <div class=\"member-box\">\n                        <i class=\"fa fa-4x fa-users\"></i>\n                        <h3>Project Manager</h3>\n                        <p>Micah Baja</p>\n                    </div>\n                </div>\n                <div class=\"col-lg-3 text-center\">\n                    <div class=\"member-box\">\n                        <i class=\"fa fa-4x fa-database\"></i>\n                        <h3>Data Designer</h3>\n                        <p>Yangeng Chen</p>\n                    </div>\n                </div>\n                <div class=\"col-lg-3 text-center\">\n                    <div class=\"member-box\">\n                        <i class=\"fa fa-4x fa-pencil-square-o\"></i>\n                        <h3>Lead Designer</h3>\n                        <p>Bryan Robicheau</p>\n                    </div>\n                </div>\n            </div>\n            <div class=\"member-box\"> \n                <div class=\"col-lg-12 text-center\">\n                    <i class=\"fa fa-4x fa-envelope-open\"></i>\n                    <h2>Contact Us</h2>\n                    <p>CDG_bears@gmail.com</p>\n                </div>\n            </div>\n        </div>\n    </section>"

/***/ }),

/***/ "./src/app/pages/home/home.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HomeComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var HomeComponent = /** @class */ (function () {
    function HomeComponent() {
        this.isScrolled = false;
        this.currPos = 0;
        this.startPos = 0;
        this.changePos = 100;
    }
    HomeComponent.prototype.onScroll = function (evt) {
        this.currPos = (window.pageYOffset || evt.target.scrollTop) - (evt.target.clientTop || 0);
        if (this.currPos >= this.changePos) {
            this.isScrolled = true;
        }
        else {
            this.isScrolled = false;
        }
    };
    HomeComponent.prototype.ngOnInit = function () {
    };
    HomeComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-home',
            template: __webpack_require__("./src/app/pages/home/home.component.html"),
            styles: [__webpack_require__("./src/app/pages/home/home.component.css")],
            host: {
                '(window:scroll)': 'onScroll($event)'
            }
        }),
        __metadata("design:paramtypes", [])
    ], HomeComponent);
    return HomeComponent;
}());



/***/ }),

/***/ "./src/app/pages/login/login.component.html":
/***/ (function(module, exports) {

module.exports = "<div id=\"login\">\n  <div class=\"row\">\n    <div id=\"login-container\" class=\"col-md-4\">\n        <nb-card>\n          <nb-card-header>Login</nb-card-header>\n          <nb-card-body>\n            <form>\n              <div class=\"form-group\">\n                <label for=\"exampleInputEmail1\">Email address</label>\n                <input type=\"email\" class=\"form-control\" id=\"exampleInputEmail1\" placeholder=\"Email\">\n              </div>\n              <div class=\"form-group\">\n                <label for=\"exampleInputPassword1\">Password</label>\n                <input type=\"password\" class=\"form-control\" id=\"exampleInputPassword1\" placeholder=\"Password\">\n              </div>\n              <div class=\"form-group\">\n              </div>\n              <button type=\"submit\" class=\"btn btn-danger\"><a href=\"/cdg\">Submit</a></button>\n            </form>\n          </nb-card-body>\n        </nb-card>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/pages/login/login.component.scss":
/***/ (function(module, exports) {

module.exports = "/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * This is a starting point where we declare the maps of themes and globally available functions/mixins\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * This mixin generates keyfames.\n * Because of all keyframes can't be scoped,\n * we need to puts unique name in each btn-pulse call.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * @license\n * Copyright Akveo. All Rights Reserved.\n * Licensed under the MIT License. See License.txt in the project root for license information.\n */\n/**\n * This mixin generates keyfames.\n * Because of all keyframes can't be scoped,\n * we need to puts unique name in each btn-pulse call.\n */\n#login {\n  width: 100%;\n  height: 100%;\n  background-color: navy; }\n#login-container {\n  margin: 15rem 30%; }\n"

/***/ }),

/***/ "./src/app/pages/login/login.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoginComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var LoginComponent = /** @class */ (function () {
    function LoginComponent() {
    }
    LoginComponent.prototype.ngOnInit = function () {
    };
    LoginComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-login',
            template: __webpack_require__("./src/app/pages/login/login.component.html"),
            styles: [__webpack_require__("./src/app/pages/login/login.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], LoginComponent);
    return LoginComponent;
}());



/***/ }),

/***/ "./src/app/pages/pages-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PagesRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__login_login_component__ = __webpack_require__("./src/app/pages/login/login.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var pageRoutes = [
    { path: 'login', component: __WEBPACK_IMPORTED_MODULE_2__login_login_component__["a" /* LoginComponent */] },
];
var PagesRoutingModule = /** @class */ (function () {
    function PagesRoutingModule() {
    }
    PagesRoutingModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["K" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_0__angular_router__["d" /* RouterModule */].forChild(pageRoutes)
            ],
            exports: [__WEBPACK_IMPORTED_MODULE_0__angular_router__["d" /* RouterModule */]]
        })
    ], PagesRoutingModule);
    return PagesRoutingModule;
}());



/***/ }),

/***/ "./src/app/pages/pages.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PagesModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("./node_modules/@angular/common/esm5/common.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__pages_routing_module__ = __webpack_require__("./src/app/pages/pages-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__login_login_component__ = __webpack_require__("./src/app/pages/login/login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__nebular_theme__ = __webpack_require__("./node_modules/@nebular/theme/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__agm_core__ = __webpack_require__("./node_modules/@agm/core/index.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};






//  import { NbAuthModule,
//   NbAuthComponent,
//   NbLoginComponent,
//   NbRegisterComponent,
//   NbLogoutComponent,
//   NbRequestPasswordComponent,
//   NbResetPasswordComponent,
// } from '@nebular/auth';

var PagesModule = /** @class */ (function () {
    function PagesModule() {
    }
    PagesModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_common__["b" /* CommonModule */],
                // NbAuthModule,
                // NbAuthComponent,
                // NbLoginComponent,
                // NbRegisterComponent,
                // NbLogoutComponent,
                // NbRequestPasswordComponent,
                // NbResetPasswordComponent,
                __WEBPACK_IMPORTED_MODULE_5__nebular_theme__["d" /* NbSidebarModule */],
                __WEBPACK_IMPORTED_MODULE_5__nebular_theme__["b" /* NbLayoutModule */],
                __WEBPACK_IMPORTED_MODULE_3__pages_routing_module__["a" /* PagesRoutingModule */],
                __WEBPACK_IMPORTED_MODULE_5__nebular_theme__["a" /* NbCardModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["c" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_6__agm_core__["a" /* AgmCoreModule */].forRoot({
                    apiKey: 'AIzaSyDbo0ubVsgdziReD82-FBC9KCbuiZeFnuU'
                })
            ],
            declarations: [
                __WEBPACK_IMPORTED_MODULE_4__login_login_component__["a" /* LoginComponent */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_5__nebular_theme__["e" /* NbSidebarService */]]
        })
    ], PagesModule);
    return PagesModule;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
var environment = {
    production: false
};


/***/ }),

/***/ "./src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__polyfills__ = __webpack_require__("./src/polyfills.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_platform_browser_dynamic__ = __webpack_require__("./node_modules/@angular/platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__app_app_module__ = __webpack_require__("./src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__environments_environment__ = __webpack_require__("./src/environments/environment.ts");





if (__WEBPACK_IMPORTED_MODULE_4__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["_18" /* enableProdMode */])();
}
Object(__WEBPACK_IMPORTED_MODULE_2__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_3__app_app_module__["a" /* AppModule */])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ "./src/polyfills.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_core_js_es7_reflect__ = __webpack_require__("./node_modules/core-js/es7/reflect.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_core_js_es7_reflect___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_core_js_es7_reflect__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_zone_js_dist_zone__ = __webpack_require__("./node_modules/zone.js/dist/zone.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_zone_js_dist_zone___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_zone_js_dist_zone__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_hammerjs__ = __webpack_require__("./node_modules/hammerjs/hammer.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_hammerjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_hammerjs__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_web_animations_js__ = __webpack_require__("./node_modules/web-animations-js/web-animations.min.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_web_animations_js___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_web_animations_js__);
/**
 * This file includes polyfills needed by Angular and is loaded before the app.
 * You can add your own extra polyfills to this file.
 *
 * This file is divided into 2 sections:
 *   1. Browser polyfills. These are applied before loading ZoneJS and are sorted by browsers.
 *   2. Application imports. Files imported after ZoneJS that should be loaded before your main
 *      file.
 *
 * The current setup is for so-called "evergreen" browsers; the last versions of browsers that
 * automatically update themselves. This includes Safari >= 10, Chrome >= 55 (including Opera),
 * Edge >= 13 on the desktop, and iOS 10 and Chrome on mobile.
 *
 * Learn more in https://angular.io/docs/ts/latest/guide/browser-support.html
 */
/***************************************************************************************************
 * BROWSER POLYFILLS
 */
/** IE9, IE10 and IE11 requires all of the following polyfills. **/
// import 'core-js/es6/symbol';
// import 'core-js/es6/object';
// import 'core-js/es6/function';
// import 'core-js/es6/parse-int';
// import 'core-js/es6/parse-float';
// import 'core-js/es6/number';
// import 'core-js/es6/math';
// import 'core-js/es6/string';
// import 'core-js/es6/date';
// import 'core-js/es6/array';
// import 'core-js/es6/regexp';
// import 'core-js/es6/map';
// import 'core-js/es6/weak-map';
// import 'core-js/es6/set';
/** IE10 and IE11 requires the following for NgClass support on SVG elements */
// import 'classlist.js';  // Run `npm install --save classlist.js`.
/** IE10 and IE11 requires the following for the Reflect API. */
// import 'core-js/es6/reflect';
/** Evergreen browsers require these. **/
// Used for reflect-metadata in JIT. If you use AOT (and only Angular decorators), you can remove.

/**
 * Required to support Web Animations `@angular/platform-browser/animations`.
 * Needed for: All but Chrome, Firefox and Opera. http://caniuse.com/#feat=web-animation
 **/
// import 'web-animations-js';  // Run `npm install --save web-animations-js`.
/***************************************************************************************************
 * Zone JS is required by default for Angular itself.
 */
 // Included with Angular CLI.
/***************************************************************************************************
 * APPLICATION IMPORTS
 */




/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("./src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map