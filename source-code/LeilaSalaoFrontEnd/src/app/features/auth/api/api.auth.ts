import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/api/api.service';
import { LoginRequestDto } from '../dto/login-request-dto';
import { TokenResponse } from '../dto/token-response-dto';
import { Observable } from 'rxjs';
import { ApiConfig } from '../../../core/config/api/api.config';
import { HttpContext } from '@angular/common/http';
import { NO_AUTH } from '../../../core/config/no-auth/no-auth.token.config';
import { CreateClientDto } from '../dto/create-client-dto';

@Injectable({
    providedIn: 'root'
})
export class ApiAuth {
    constructor(
        private apiService: ApiService
    ) { }

    login(loginRequest: LoginRequestDto): Observable<TokenResponse> { 
        return this.apiService.post<TokenResponse>(
            ApiConfig.endpoints.auth.login, 
            loginRequest,
            {
                context: new HttpContext().set(NO_AUTH, true)
            }
        ) as unknown as Observable<TokenResponse>;
    }

    register(registerRequest: CreateClientDto): Observable<any> {
        return this.apiService.post(
            ApiConfig.endpoints.client.base,
            registerRequest,
            {
                context: new HttpContext().set(NO_AUTH, true)
            }
        );
    }
}
