import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedulingAdminComponent } from './scheduling-admin-component';

describe('SchedulingAdminComponent', () => {
  let component: SchedulingAdminComponent;
  let fixture: ComponentFixture<SchedulingAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SchedulingAdminComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SchedulingAdminComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
