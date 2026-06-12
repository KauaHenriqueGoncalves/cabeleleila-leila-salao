import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { ClientResponseDto } from '../../dto/client.response.dto';
import { ApiClient } from '../../api/api.client';

@Component({
  selector: 'app-client.component',
  imports: [],
  templateUrl: './client.component.html',
  styleUrl: './client.component.sass',
})
export class ClientComponent {
  private readonly apiClient = inject(ApiClient);
  private readonly changeDetectorRef = inject(ChangeDetectorRef);

  clients: ClientResponseDto[] = [];

  loading = false;

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.loading = true;

    this.apiClient.getAll()
      .subscribe({
        next: res => {
          this.clients = res;
          this.loading = false;
          this.changeDetectorRef.detectChanges();
        },
        error: () => {
          this.loading = false;
          this.changeDetectorRef.detectChanges();
        }
      })
  }

  deleteClient(client: ClientResponseDto): void {
    const confirmed = confirm(
      `Deseja remover ${client.name}?`
    );

    if (!confirmed) {
      return;
    }

    this.apiClient.deleteById(client.id)
      .subscribe({
        next: () => {
          this.clients = this.clients.filter(
            x => x.id !== client.id
          );
          this.changeDetectorRef.detectChanges();
        }
      });
  }
}
