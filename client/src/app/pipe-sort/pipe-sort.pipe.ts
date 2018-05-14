import {Component, Inject, OnInit, Pipe, PipeTransform, NgModule} from '@angular/core';


@Pipe({
  name: "sort",
  pure: false
})
export class PipeSort implements PipeTransform {
  transform(array: Array<string>, args: string): Array<string> {
    array.sort((a: any, b: any) => {
      if (a < b) {
        return -1;
      } else if (a > b) {
        return 1;
      } else {
        return 0;
      }
    });
    return array;
  }
}

 @NgModule({
     imports:        [],
     declarations:   [PipeSort],
     exports:        [PipeSort],
 })

 export class PipeSortModule {

   static forRoot() {
      return {
          ngModule: PipeSortModule,
          providers: [],
      };
   }
 } 