export interface NotificationMessage {
  type: 'success' | 'error' | 'warning';
  text: string;
}