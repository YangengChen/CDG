import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CdgButtonComponent } from './cdg-button.component';

describe('CdgButtonComponent', () => {
  let component: CdgButtonComponent;
  let fixture: ComponentFixture<CdgButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CdgButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CdgButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
