import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginRequestDto } from '../../dto/login-request-dto';
import { finalize } from 'rxjs';
import { TokenResponse } from '../../dto/token-response-dto';
import { ApiAuth } from '../../api/api.auth';
import { AuthStoreService } from '../../../../core/auth/store/auth.store.service';
import { NotificationService } from '../../../../core/notification/service/notification.service';
import { AuthService } from '../../../../core/auth/service/auth.service';

@Component({
  selector: 'app-login.component',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.sass',
})
export class LoginComponent {
  private readonly fb = inject(FormBuilder);
  private readonly apiAuth = inject(ApiAuth);
  private readonly authStore = inject(AuthStoreService);
  private readonly router = inject(Router);
  private readonly notificationService = inject(NotificationService);
  private readonly authService = inject(AuthService);

  loading = false

  readonly form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  })

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched()
      return
    }

    this.loading = true

    const payload: LoginRequestDto = {
      email: this.form.value.email,
      password: this.form.value.password
    }

    this.apiAuth.login(payload)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe({
        next: (res: TokenResponse) => {
          this.authStore.setToken(res.accessToken)
          const role = this.authService.getPayload()?.role;
          if (role == 'admin') {
            this.router.navigate(['/app/dashboard'])
          }
          else if (role == 'client') {
            this.router.navigate(['/app/my-schedulings'])
          }
        },
        error: (err: any) => {
          this.notificationService.notify({
            type: 'error',
            text: err.error?.message || 'Erro inesperado, tente novamente mais tarde'
          });
        }
      })
  }
}
