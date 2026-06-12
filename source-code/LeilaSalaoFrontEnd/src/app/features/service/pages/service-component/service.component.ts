import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { ApiServicedomain } from '../../api/api.servicedomain';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ServiceResponseDto } from '../../dto/service.response.dto';
import { CreateServiceRequestDto } from '../../dto/create.service.request.dto';
import { CurrencyPipe } from '@angular/common';
import { NotificationService } from '../../../../core/notification/service/notification.service';

@Component({
  selector: 'app-service.component',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CurrencyPipe
  ],
  templateUrl: './service.component.html',
  styleUrl: './service.component.sass',
})
export class ServiceComponent implements OnInit {
  private readonly api = inject(ApiServicedomain);
  private readonly fb = inject(FormBuilder);
  private readonly notificationService = inject(NotificationService);
  private readonly changeDetectorRef = inject(ChangeDetectorRef);

  services: ServiceResponseDto[] = [];

  showModal = false;

  readonly form = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    durationMinutes: [30, Validators.required],
    price: [0, Validators.required]
  });

  ngOnInit(): void {
    this.loadServices();
  }

  loadServices(): void {
    this.api.findAll()
      .subscribe(res => {
        this.services = res;
        this.changeDetectorRef.detectChanges();
      });
  }

  openModal(): void {
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.form.reset({
      durationMinutes: 30,
      price: 0
    });
  }

  createService(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.api.create(
      this.form.getRawValue() as CreateServiceRequestDto
    )
      .subscribe({
        next: () => {
          this.closeModal();
          this.loadServices();
        },
        error: (err) => {
          this.notificationService.notify({
            type: 'error',
            text: err.error?.message || 'Erro inesperado, tente novamente mais tarde'
          });
        }
      });
    this.changeDetectorRef.detectChanges();
  }

  deleteService(service: ServiceResponseDto): void {
    const confirmed = confirm(
      `Deseja remover o serviço "${service.name}"?`
    );

    if (!confirmed) {
      return;
    }

    this.api.delete(service.id)
      .subscribe(() => {
        this.loadServices();
      });
  }
}
