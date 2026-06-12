import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/api/api.service';
import { Observable } from 'rxjs';
import { HistoryChangesResponseDto } from '../dto/history-changes.response.dto';
import { ApiConfig } from '../../../core/config/api/api.config';

@Injectable({
    providedIn: 'root'
})
export class ApiHistoryChanges {
    constructor(
        private apiService: ApiService
    ) { }

    findAllById(id: string): Observable<HistoryChangesResponseDto> {
        return this.apiService.get(
            `${ApiConfig.endpoints.historyChanges.base}/${id}`
        ) as unknown as Observable<HistoryChangesResponseDto>;
    }
}
