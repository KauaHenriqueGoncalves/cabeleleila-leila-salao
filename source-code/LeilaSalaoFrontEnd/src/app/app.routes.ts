import { Routes } from '@angular/router';
import { HomeComponent } from './features/landing/pages/home-component/home.component';
import { RegisterComponent } from './features/auth/pages/register-component/register.component';
import { LoginComponent } from './features/auth/pages/login-component/login.component';
import { AuthLayout } from './layouts/auth-layout/auth.layout';
import { DashboardComponent } from './features/dashboard/pages/dashboard-component/dashboard.component';
import { authGuard } from './core/auth/guard/auth-guard';
import { ClientComponent } from './features/client/pages/client-component/client.component';
import { ServiceComponent } from './features/service/pages/service-component/service.component';
import { SchedulingClientComponent } from './features/scheduling/pages/client/scheduling-client-component/scheduling.client.component';
import { SchedulingFormComponent } from './features/scheduling/pages/client/scheduling-form-component/scheduling-form-component';
import { SchedulingAdminComponent } from './features/scheduling/pages/admin/scheduling-admin-component/scheduling-admin-component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
    },
    {
        path: 'register',
        component: RegisterComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'app',
        component: AuthLayout,
        canActivate: [authGuard],
        children: [
            {
                path: 'dashboard',
                component: DashboardComponent
            },
            {
                path: 'clients',
                component: ClientComponent
            },
            {
                path: 'services',
                component: ServiceComponent
            },
            {
                path: 'schedulings',
                component: SchedulingAdminComponent
            },
            {
                path: 'my-schedulings',
                component: SchedulingClientComponent
            },
            {
                path: 'create-scheduling-client',
                component: SchedulingFormComponent
            }
        ]
    },
    {
        path: '**',
        redirectTo: ''
    },
];
