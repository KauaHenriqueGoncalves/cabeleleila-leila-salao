import { TestBed } from '@angular/core/testing';

import { ApiDashboard } from './api.dashboard';

describe('ApiDashboard', () => {
  let service: ApiDashboard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiDashboard);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
