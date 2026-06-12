import { CurrencyPipe } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { DashboardResponseDto } from '../../dto/dashboard.response.dto';
import { ApiDashboard } from '../../api/api.dashboard';
import { NotificationService } from '../../../../core/notification/service/notification.service';

@Component({
  selector: 'app-dashboard.component',
  imports: [
    CurrencyPipe
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.sass',
})
export class DashboardComponent {
  private readonly apiDashboard = inject(ApiDashboard);
  private readonly notificationService = inject(NotificationService);
  private readonly changeDetectorRef = inject(ChangeDetectorRef);

  dashboard: DashboardResponseDto | null = null;

  loading = false;

  ngOnInit(): void {
    this.loadDashboard();
  }

  private loadDashboard(): void {
    this.loading = true;
    this.apiDashboard.getDashboardData()
      .subscribe({
        next: (response: DashboardResponseDto) => {
          console.log(response)
          this.dashboard = response;
          this.loading = false;
          this.changeDetectorRef.detectChanges();
        },
        error: (err) => {
          this.notificationService.notify({
            type: 'error',
            text: err.error?.message || 'Erro inesperado, tente novamente mais tarde'
          });
          this.loading = false;
          this.changeDetectorRef.detectChanges();
        }
      });
  }
}
