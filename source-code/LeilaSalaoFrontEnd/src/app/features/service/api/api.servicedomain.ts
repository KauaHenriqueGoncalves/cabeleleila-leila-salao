import { Injectable } from "@angular/core";
import { ApiService } from "../../../core/api/api.service";
import { Observable } from "rxjs";
import { CreateServiceRequestDto } from "../dto/create.service.request.dto";
import { ServiceResponseDto } from "../dto/service.response.dto";
import { ApiConfig } from "../../../core/config/api/api.config";

@Injectable({
    providedIn: 'root'
})
export class ApiServicedomain {
    constructor(
        private apiService: ApiService
    ) { }

    findAll(): Observable<ServiceResponseDto[]> {
        return this.apiService.get(
            ApiConfig.endpoints.service.base
        ) as unknown as Observable<ServiceResponseDto[]>
    }

    create(request: CreateServiceRequestDto): Observable<ServiceResponseDto> {
        return this.apiService.post(
            ApiConfig.endpoints.service.base,
            request
        ) as unknown as Observable<ServiceResponseDto>
    }

    delete(id: string): Observable<void> {
        return this.apiService.delete(
            `${ApiConfig.endpoints.service.base}/${id}`
        ) as unknown as Observable<void>
    }
}
