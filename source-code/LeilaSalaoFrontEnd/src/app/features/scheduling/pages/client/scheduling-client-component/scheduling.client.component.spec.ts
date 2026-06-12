import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedulingClientComponent } from './scheduling.client.component';

describe('SchedulingClientComponent', () => {
  let component: SchedulingClientComponent;
  let fixture: ComponentFixture<SchedulingClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SchedulingClientComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SchedulingClientComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
