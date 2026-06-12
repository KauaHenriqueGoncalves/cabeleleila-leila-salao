import { TestBed } from '@angular/core/testing';

import { ApiScheduling } from './api.scheduling';

describe('ApiScheduling', () => {
  let service: ApiScheduling;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiScheduling);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
