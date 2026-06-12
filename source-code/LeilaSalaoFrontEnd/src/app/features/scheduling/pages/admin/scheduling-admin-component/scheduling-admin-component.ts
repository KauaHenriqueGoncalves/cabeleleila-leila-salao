import { CurrencyPipe, DatePipe } from '@angular/common';
import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ApiScheduling } from '../../../api/api.scheduling';
import { ApiServicedomain } from '../../../../service/api/api.servicedomain';
import { SchedulingResponseDto, SchedulingStatus } from '../../../dto/scheduling.response.dto';
import { ServiceResponseDto } from '../../../../service/dto/service.response.dto';
import { UpdateAdminSchedulingRequestDto } from '../../../dto/update.admin.scheduling.request.dto';
import { NotificationService } from '../../../../../core/notification/service/notification.service';
import { ApiHistoryChanges } from '../../../../historyChanges/api/api-history-changes';
import { HistoryChangesResponseDto } from '../../../../historyChanges/dto/history-changes.response.dto';

@Component({
  selector: 'app-scheduling-admin-component',
  imports: [
    ReactiveFormsModule,
    CurrencyPipe,
    DatePipe
  ],
  templateUrl: './scheduling-admin-component.html',
  styleUrl: './scheduling-admin-component.sass',
})
export class SchedulingAdminComponent implements OnInit {
  private readonly api = inject(ApiScheduling);
  private readonly apiService = inject(ApiServicedomain);
  private readonly fb = inject(FormBuilder);
  private readonly notificationService = inject(NotificationService);
  private readonly cdr = inject(ChangeDetectorRef);
  private readonly apiHistoryChanges = inject(ApiHistoryChanges);

  historyChanges: HistoryChangesResponseDto[] = []
  schedulings: SchedulingResponseDto[] = [];
  services: ServiceResponseDto[] = [];
  loading = false;
  loadingHistory = false;
  drawerMode: 'details' | 'edit' | null = null;
  selectedScheduling: SchedulingResponseDto | null = null;
  selectedServicesIds: string[] = [];

  readonly filterForm = this.fb.group({
    startDate: [''],
    endDate: ['']
  })

  readonly editForm = this.fb.group({
    status: ['SCHEDULED'],
    dateHours: [''],
    origin: ['APP']
  })

  readonly statuses = [
    'SCHEDULED',
    'DONE',
    'CANCELED'
  ];

  ngOnInit(): void {
    this.loadSchedulings();
    this.loadServices();
  }

  loadSchedulings(): void {
    this.loading = true;
    this.api.findAll()
      .subscribe({
        next: res => {
          this.schedulings = res;
          this.loading = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.loading = false;
        }
      })
  }

  loadServices(): void {
    this.apiService.findAll()
      .subscribe(res => {
        this.services = res;
      });
  }

  loadHistory(schedulingId: string): void {
    this.loadingHistory = true
    this.apiHistoryChanges
      .findAllById(schedulingId)
      .subscribe({
        next: (res: HistoryChangesResponseDto) => {
          this.historyChanges =
            Array.isArray(res)
              ? res
              : [res]
          this.loadingHistory = false
          this.cdr.detectChanges();
        },
        error: () => {
          this.loadingHistory = false
          this.historyChanges = []
          this.cdr.detectChanges();
        }
      })
  }

  search(): void {
    const start =
      this.filterForm.value.startDate;

    const end =
      this.filterForm.value.endDate;

    if (!start || !end) {
      this.loadSchedulings();
      return;
    }

    this.api.findAllBetweenDates(start, end)
      .subscribe(res => {
        this.schedulings = res;
        this.cdr.detectChanges();
      });
  }

  openDetails(scheduling: SchedulingResponseDto): void {
    this.selectedScheduling = scheduling;
    this.drawerMode = 'details';
    this.loadHistory(scheduling.id)
  }

  openEdit(scheduling: SchedulingResponseDto): void {
    this.selectedScheduling = scheduling;
    this.selectedServicesIds = scheduling.services.map(x => x.id);
    this.editForm.patchValue({
      status: scheduling.status,
      dateHours: scheduling.dateHours?.slice(0, 16),
      origin: scheduling.origin
    });
    this.drawerMode = 'edit';
  }

  closeDrawer(): void {
    this.drawerMode = null;
    this.selectedScheduling = null;
    this.selectedServicesIds = [];
    this.historyChanges = [];
  }

  toggleService(id: string): void {
    if (this.selectedServicesIds.includes(id)) {
      this.selectedServicesIds = this.selectedServicesIds.filter(x => x !== id);
      return;
    }
    this.selectedServicesIds = [
      ...this.selectedServicesIds,
      id
    ];
  }

  save(): void {
    if (!this.selectedScheduling) {
      return;
    }
    const payload: UpdateAdminSchedulingRequestDto = {
      status: this.editForm.value.status as SchedulingStatus,
      dateHours: this.editForm.value.dateHours!,
      origin: this.editForm.value.origin as any,
      servicesIds: this.selectedServicesIds
    }

    console.log(payload)

    this.api.updateAdmin(this.selectedScheduling.id, payload)
      .subscribe({
        next: () => {
          this.closeDrawer();
          this.loadSchedulings();
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.notificationService.notify({
            type: 'error',
            text: err.error?.message || 'Erro inesperado, tente novamente mais tarde'
          });
        }
      });
  }

  delete(scheduling: SchedulingResponseDto): void {
    const confirmed = confirm(
      `Deseja remover o agendamento de ${scheduling.nameClient}?`
    );
    if (!confirmed) {
      return;
    }
    this.api.deleteById(scheduling.id)
      .subscribe(() => {
        this.loadSchedulings();
        this.cdr.detectChanges();
      });
  }

  getStatusLabel(status: string): string {
    switch (status) {
      case 'DONE':
        return 'Concluído'
      case 'SCHEDULED':
        return 'Agendado'
      case 'CANCELED':
        return 'Cancelado'
      default:
        return status
    }
  }

  hasSchedulingConflict(scheduling: SchedulingResponseDto): boolean {
    const currentDate =
      new Date(scheduling.dateHours)
    const startOfWeek =
      new Date(currentDate)
    startOfWeek.setDate(
      currentDate.getDate() - currentDate.getDay()
    )
    startOfWeek.setHours(
      0, 0, 0, 0
    )
    const endOfWeek =
      new Date(startOfWeek)
    endOfWeek.setDate(
      startOfWeek.getDate() + 7
    )
    const sameClientSchedulings =
      this.schedulings.filter(item => {
        if (item.nameClient !== scheduling.nameClient) {
          return false
        }
        const date =
          new Date(item.dateHours)
        return (
          date >= startOfWeek &&
          date < endOfWeek
        )
      })
    return sameClientSchedulings.length > 1
  }

  getWeeklySchedulingCount(scheduling: SchedulingResponseDto): number {
    const currentDate =
      new Date(scheduling.dateHours)
    const startOfWeek =
      new Date(currentDate)
    startOfWeek.setDate(
      currentDate.getDate() - currentDate.getDay()
    )
    startOfWeek.setHours(
      0, 0, 0, 0
    )
    const endOfWeek =
      new Date(startOfWeek)
    endOfWeek.setDate(
      startOfWeek.getDate() + 7
    )
    return this.schedulings.filter(item => {
      if (item.nameClient !== scheduling.nameClient) {
        return false
      }
      const date =
        new Date(item.dateHours)
      return (
        date >= startOfWeek &&
        date < endOfWeek
      )
    }).length
  }
}
