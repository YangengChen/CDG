import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CdgComponent } from './cdg.component';

describe('CdgComponent', () => {
  let component: CdgComponent;
  let fixture: ComponentFixture<CdgComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CdgComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CdgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
