import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { OverviewComponent } from './dashboard/overview/overview.component';
import { CitiesComponent } from './dashboard/cities/cities.component';
import { RoutesComponent } from './dashboard/routes/routes.component';
import { UsersComponent } from './dashboard/users/users.component';

export const routes: Routes = [
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login', component: LoginComponent },
    {
        path: 'dashboard',
        component: DashboardComponent,
        children: [
            {path: '', redirectTo: 'overview', pathMatch: 'full'},
            {path: 'overview', component: OverviewComponent},
            {path: 'cities', component: CitiesComponent},
            {path: 'routes', component: RoutesComponent},
            {path: 'users', component: UsersComponent}
        ]
    }    
];
