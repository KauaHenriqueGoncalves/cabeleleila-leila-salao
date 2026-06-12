import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/api/api.service';
import { ApiConfig } from '../../../core/config/api/api.config';
import { DashboardResponseDto } from '../dto/dashboard.response.dto';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ApiDashboard {
    constructor(
        private apiService: ApiService
    ) { }

    getDashboardData(): Observable<DashboardResponseDto> {
        return this.apiService.get(
            ApiConfig.endpoints.dashboard.base
        ) as unknown as Observable<DashboardResponseDto>;
    }
}
