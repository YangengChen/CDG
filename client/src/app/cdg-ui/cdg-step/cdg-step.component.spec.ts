import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CdgStepComponent } from './cdg-step.component';

describe('CdgStepComponent', () => {
  let component: CdgStepComponent;
  let fixture: ComponentFixture<CdgStepComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CdgStepComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CdgStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
