import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { ApiScheduling } from '../../../api/api.scheduling';
import { Router, RouterLink } from '@angular/router';
import { SchedulingResponseDto, SchedulingServiceDto } from '../../../dto/scheduling.response.dto';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { ApiServicedomain } from '../../../../service/api/api.servicedomain';
import { UpdateClientSchedulingRequestDto } from '../../../dto/update.client.scheduling.request.dto';
import { NotificationService } from '../../../../../core/notification/service/notification.service';

@Component({
  selector: 'app-scheduling.client.component',
  imports: [
    CurrencyPipe,
    DatePipe,
    RouterLink
  ],
  templateUrl: './scheduling.client.component.html',
  styleUrl: './scheduling.client.component.sass',
})
export class SchedulingClientComponent {
  private readonly api = inject(ApiScheduling);
  private readonly apiService = inject(ApiServicedomain);
  private readonly router = inject(Router);
  private readonly notificationService = inject(NotificationService);
  private readonly changeDetectorRef = inject(ChangeDetectorRef);

  loading = false;

  schedulings: SchedulingResponseDto[] = [];
  selectedScheduling: SchedulingResponseDto | null = null;
  drawerMode: 'details' | 'edit' | null = null;
  availableServices: SchedulingServiceDto[] = [];
  selectedServicesIds: string[] = [];

  ngOnInit(): void {
    this.loadSchedulings();
    this.loadServices();
  }

  loadSchedulings(): void {
    this.loading = true;

    this.api.findAllByMe()
      .subscribe({
        next: (res) => {
          this.schedulings = res;
          this.loading = false;
          this.changeDetectorRef.detectChanges();
        },
        error: () => {
          this.loading = false;
          this.changeDetectorRef.detectChanges();
        }
      })
  }

  loadServices(): void {
    this.apiService.findAll()
      .subscribe(res => {
        this.availableServices = res;
        this.changeDetectorRef.detectChanges();
      });
  }

  getStatusLabel(status: string): string {
    switch (status) {
      case 'DONE':
        return 'Concluído';
      case 'SCHEDULED':
        return 'Agendado';
      case 'CANCELLED':
        return 'Cancelado';
      default:
        return status;
    }
  }

  openDetails(item: SchedulingResponseDto): void {
    this.selectedScheduling = item
    this.drawerMode = 'details'
  }

  openEdit(item: SchedulingResponseDto): void {
    this.selectedScheduling = item
    this.selectedServicesIds =
      item.services.map(x => x.id)
    this.drawerMode = 'edit'
  }

  closeDrawer(): void {
    this.drawerMode = null
    this.selectedScheduling = null
    this.selectedServicesIds = []
  }

  saveUpdate(): void {
    if (!this.selectedScheduling ||this.selectedServicesIds.length === 0) {
      return
    }

    const payload: UpdateClientSchedulingRequestDto = {
        origin: 'APP',
        servicesIds: this.selectedServicesIds
      }

    this.api.updateClient(this.selectedScheduling.id, payload)
      .subscribe({
        next: () => {
          const scheduling =
            this.schedulings.find(
              x => x.id === this.selectedScheduling?.id
            )

          if (scheduling) {
            scheduling.services =
              this.availableServices.filter(
                x =>
                  this.selectedServicesIds.includes(
                    x.id
                  )
              )
          }

          this.closeDrawer()
          this.changeDetectorRef.detectChanges();
        },
        error: (err) => {
          this.notificationService.notify({
            type: 'error',
            text: err.error?.message || 'Erro inesperado, tente novamente mais tarde'
          });
        }
      })

  }

  toggleService(serviceId: string): void {
    if (this.selectedServicesIds.includes(serviceId)) {
      this.selectedServicesIds =
        this.selectedServicesIds.filter(
          x => x !== serviceId
        );
      return;
    }
    this.selectedServicesIds = [
      ...this.selectedServicesIds,
      serviceId
    ];
  }
}
