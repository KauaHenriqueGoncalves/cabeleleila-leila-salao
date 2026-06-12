import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ApiAuth } from '../../api/api.auth';
import { CreateClientDto } from '../../dto/create-client-dto';
import { NotificationService } from '../../../../core/notification/service/notification.service';

@Component({
  selector: 'app-register.component',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.sass',
})
export class RegisterComponent {
  private readonly fb = inject(FormBuilder);
  private readonly apiAuth = inject(ApiAuth);
  private readonly notificationService = inject(NotificationService);
  private readonly router = inject(Router);

  protected readonly form = this.fb.group({
    name: ['', Validators.required],
    phoneNumber: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    confirmPassword: ['', Validators.required]
  })

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched()
      return
    }

    const {
      name,
      phoneNumber,
      email,
      password
    } = this.form.getRawValue()

    const payload: CreateClientDto = {
      user: {
        email,
        password
      },
      name,
      phoneNumber
    }

    this.apiAuth.register(payload)
      .subscribe({
        next: () => {
          this.notificationService.notify({
            type: 'success',
            text: 'Usuário registrado com sucesso!'
          });
          this.router.navigate(['/'])
        },
        error: (err) => {
          this.notificationService.notify({
            type: 'error',
            text: err.error?.message || 'Erro inesperado, tente novamente mais tarde'
          });
        }
      })
  }
}
