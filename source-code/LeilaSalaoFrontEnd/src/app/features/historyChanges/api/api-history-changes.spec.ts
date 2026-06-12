import { TestBed } from '@angular/core/testing';

import { ApiHistoryChanges } from './api-history-changes';

describe('ApiHistoryChanges', () => {
  let service: ApiHistoryChanges;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiHistoryChanges);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
