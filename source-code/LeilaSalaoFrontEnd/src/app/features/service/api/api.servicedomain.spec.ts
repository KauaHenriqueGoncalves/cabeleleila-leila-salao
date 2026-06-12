import { TestBed } from '@angular/core/testing';

import { ApiServicedomain } from './api.servicedomain';

describe('ApiServicedomain', () => {
  let service: ApiServicedomain;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiServicedomain);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
