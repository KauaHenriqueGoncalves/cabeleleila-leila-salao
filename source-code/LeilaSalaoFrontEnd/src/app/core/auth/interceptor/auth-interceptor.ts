import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthStoreService } from '../store/auth.store.service';
import { Router } from '@angular/router';
import { NO_AUTH } from '../../config/no-auth/no-auth.token.config';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authStore = inject(AuthStoreService);
  const router = inject(Router);

  if (req.context.get(NO_AUTH)) {
    return next(req);
  }

  const token: string | null = authStore.getToken();

  let authReq = req;

  if (token) {
    authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      },
      withCredentials: true
    });
  }

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      console.log('HTTP Error:', error);
      if (error.status === 401) {
        console.log('Unauthorized access - clearing token and redirecting to login.');
        authStore.clear();
        router.navigate(['/']);
      }
      return throwError(() => error);
    })
  );
};
