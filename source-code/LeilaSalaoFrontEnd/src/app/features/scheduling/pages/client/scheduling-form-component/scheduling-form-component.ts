import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CreateSchedulingRequestDto } from '../../../dto/create.scheduling.request.dto';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiScheduling } from '../../../api/api.scheduling';
import { ApiServicedomain } from '../../../../service/api/api.servicedomain';
import { ServiceResponseDto } from '../../../../service/dto/service.response.dto';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-scheduling-form-component',
  imports: [
    ReactiveFormsModule,
    CurrencyPipe
  ],
  templateUrl: './scheduling-form-component.html',
  styleUrl: './scheduling-form-component.sass',
})
export class SchedulingFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder)
  private readonly router = inject(Router)
  private readonly apiScheduling = inject(ApiScheduling)
  private readonly apiService = inject(ApiServicedomain)
  private readonly changeDetector = inject(ChangeDetectorRef)

  services: ServiceResponseDto[] = [];

  selectedServices: string[] = [];

  readonly form = this.fb.group({
    dateHours: ['', Validators.required],
    observations: ['']
  });

  ngOnInit(): void {
    this.loadServices();
  }

  private loadServices(): void {
    this.apiService.findAll()
      .subscribe(res => {
        this.services = res;
        this.changeDetector.detectChanges();
      })
  }

  toggleService(id: string): void {
    if (this.selectedServices.includes(id)) {
      this.selectedServices =
        this.selectedServices.filter(
          x => x !== id
        );
      return;
    }

    this.selectedServices = [
      ...this.selectedServices,
      id
    ]
  }

  isSelected(id: string): boolean {
    return this.selectedServices.includes(id)
  };

  get totalPrice(): number {
    return this.services
      .filter(x => this.selectedServices.includes(x.id))
      .reduce((acc, curr) => acc + curr.price, 0)
  };

  get totalDuration(): number {
    return this.services
      .filter(x => this.selectedServices.includes(x.id))
      .reduce((acc, curr) => acc + curr.durationMinutes, 0);
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched()
      return
    }
    if (this.selectedServices.length === 0) {
      return
    }
    const payload: CreateSchedulingRequestDto = {
      dateHours: this.form.value.dateHours!,
      observations: this.form.value.observations,
      origin: 'APP',
      servicesIds: this.selectedServices
    }
    this.apiScheduling.create(payload)
      .subscribe({
        next: () => {
          this.router.navigate([
            '/app/my-schedulings'
          ])
        }
      })
  }
}
