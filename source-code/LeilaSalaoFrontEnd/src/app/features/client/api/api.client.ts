import { Injectable } from "@angular/core";
import { ApiService } from "../../../core/api/api.service";
import { ClientResponseDto } from "../dto/client.response.dto";
import { Observable } from "rxjs";
import { ApiConfig } from "../../../core/config/api/api.config";


@Injectable({
    providedIn: 'root'
})
export class ApiClient {
    constructor(
        private apiService: ApiService
    ) { }

    getAll(): Observable<ClientResponseDto[]> {
        return this.apiService.get(
            ApiConfig.endpoints.client.base
        ) as unknown as Observable<ClientResponseDto[]>
    }

    deleteById(id: string): Observable<void> {
        return this.apiService.delete(
            `${ApiConfig.endpoints.client.base}/${id}`
        ) as unknown as Observable<void>
    }
}
