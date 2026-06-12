import { ChangeDetectorRef, Component } from '@angular/core';
import { NotificationMessage } from '../../dto/notification.interface';
import { NotificationService } from '../../service/notification.service';

@Component({
  selector: 'app-toast',
  imports: [],
  templateUrl: './toast.html',
  styleUrl: './toast.sass',
})
export class Toast {
  toasts: NotificationMessage[] = [];

  constructor(
    private notification: NotificationService,
    private changeDetectorRef: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.notification.onNotify((msg) => {
      this.toasts.push(msg);

      this.changeDetectorRef.detectChanges();

      setTimeout(() => {
        this.toasts.shift();
        this.changeDetectorRef.detectChanges();
      }, 2500);
    });
  }
}
