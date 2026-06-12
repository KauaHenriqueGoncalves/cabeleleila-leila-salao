import { environment } from "../../../../environments/environment.develop";

export class ApiConfig {
    static get baseUrl(): string {
        return environment.api.baseUrl;
    }

    static get version(): string {
        return environment.api.version;
    }

    static get apiUrl(): string {
        return `${this.baseUrl}/${this.version}`;
    }

    static endpoints = {
        dashboard: {
            base: '/dashboard',
        },
        client: {
            base: '/clients',
            delete: '/clients/{id}',
        },
        historyChanges: {
            base: '/history-changes',
        },
        auth: {
            login: '/auth',
        },
        scheduling: {
            base: '/schedulings',
            me: '/schedulings/me',
            betweenDates: '/schedulings/dates',
            delete: '/schedulings/{id}',
            update: '/schedulings/{id}',
            updateAdmin: '/schedulings/admin/{id}',
        },
        service: {
            base: '/services',
            delete: '/services/{id}',
        }
    };
}