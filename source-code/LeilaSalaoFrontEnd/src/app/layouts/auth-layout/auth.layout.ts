import { Component, computed, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../core/auth/service/auth.service';
import { AuthStoreService } from '../../core/auth/store/auth.store.service';

@Component({
  selector: 'app-auth.layout',
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './auth.layout.html',
  styleUrl: './auth.layout.sass',
})
export class AuthLayout {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly authStore = inject(AuthStoreService);

  readonly menu = computed(() => {
    const role = this.authService.getPayload()?.role

    if (role === 'admin') {
      return [
        {
          label: 'Dashboard',
          route: '/app/dashboard'
        },
        {
          label: 'Clientes',
          route: '/app/clients'
        },
        {
          label: 'Agendamentos',
          route: '/app/schedulings'
        },
        {
          label: 'Serviços',
          route: '/app/services'
        }
      ]
    }

    return [
      {
        label: 'Meus Agendamentos',
        route: '/my-schedulings'
      }
    ]
  });

  logout(): void {
    this.authStore.clear();
    this.router.navigate(['/login']);
  }
}
