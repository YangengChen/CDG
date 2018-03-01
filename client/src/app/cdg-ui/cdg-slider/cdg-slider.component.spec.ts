import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CdgSliderComponent } from './cdg-slider.component';

describe('CdgSliderComponent', () => {
  let component: CdgSliderComponent;
  let fixture: ComponentFixture<CdgSliderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CdgSliderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CdgSliderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
