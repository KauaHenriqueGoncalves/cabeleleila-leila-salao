import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/api/api.service';
import { ApiConfig } from '../../../core/config/api/api.config';
import { SchedulingResponseDto } from '../dto/scheduling.response.dto';
import { Observable } from 'rxjs';
import { CreateSchedulingRequestDto } from '../dto/create.scheduling.request.dto';
import { UpdateClientSchedulingRequestDto } from '../dto/update.client.scheduling.request.dto';
import { UpdateAdminSchedulingRequestDto } from '../dto/update.admin.scheduling.request.dto';

@Injectable({
    providedIn: 'root'
})
export class ApiScheduling {
    constructor(
        private apiService: ApiService
    ) { }

    findAll(): Observable<SchedulingResponseDto[]> {
        return this.apiService.get(
            ApiConfig.endpoints.scheduling.base
        ) as unknown as Observable<SchedulingResponseDto[]>;
    }

    findAllByMe(): Observable<SchedulingResponseDto[]> {
        return this.apiService.get(
            ApiConfig.endpoints.scheduling.me
        ) as unknown as Observable<SchedulingResponseDto[]>;
    }

    findAllBetweenDates(startDate: string, endDate: string): Observable<SchedulingResponseDto[]> {
        const params = {
            start: startDate,
            end: endDate
        };

        return this.apiService.get(
            `${ApiConfig.endpoints.scheduling.base}/dates`,
            { params }
        ) as unknown as Observable<SchedulingResponseDto[]>;
    }

    create(data: CreateSchedulingRequestDto): Observable<void> {
        return this.apiService.post(
            ApiConfig.endpoints.scheduling.base,
            data
        ) as unknown as Observable<void>;
    }

    updateClient(id: string, data: UpdateClientSchedulingRequestDto): Observable<void> {
        return this.apiService.put(
            `${ApiConfig.endpoints.scheduling.base}/${id}`,
            data
        ) as unknown as Observable<void>;
    }

    updateAdmin(id: string, data: UpdateAdminSchedulingRequestDto): Observable<void> {
        return this.apiService.put(
            `${ApiConfig.endpoints.scheduling.base}/admin/${id}`,
            data
        ) as unknown as Observable<void>;
    }

    deleteById(id: string): Observable<void> {
        return this.apiService.delete(
            `${ApiConfig.endpoints.scheduling.base}/${id}`
        ) as unknown as Observable<void>;
    }
}
