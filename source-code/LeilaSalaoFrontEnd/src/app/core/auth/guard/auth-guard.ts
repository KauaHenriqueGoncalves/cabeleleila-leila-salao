import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthStoreService } from '../store/auth.store.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authStore = inject(AuthStoreService);
  const router = inject(Router);
  const token = authStore.getToken();
  if (!token) {
    router.navigate(['/login']);
    return false;
  };
  return true;
};
