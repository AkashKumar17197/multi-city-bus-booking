import { Component } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-routes',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
  ],
  templateUrl: './routes.component.html',
  styleUrl: './routes.component.scss',
})
export class RoutesComponent {
  showForm = false;
  editingRoute: any = null;

  routes: any[] = [];
  cities: string[] = [
    'Chennai',
    'Bengaluru',
    'Vellore',
    'Krishnagiri',
    'Madurai',
    'Tiruchy',
    'Villupuram',
  ];

  displayedColumns: string[] = [
    'id',
    'type',
    'origin',
    'destination',
    'via',
    'services',
    'actions',
  ];
  busTypes = ['Seater', 'Seater cum Sleeper', 'Sleeper', 'Double Decker'];

  routeForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.routeForm = this.fb.group({
      bus_id: [''],
      origination: ['', Validators.required],
      destination: ['', Validators.required],
      bus_type: ['', Validators.required],
      single: this.fb.array([]),
      return: this.fb.array([]),
    });

    this.loadRoutes();
  }

  get single() {
    return this.routeForm.get('single') as FormArray;
  }

  get return() {
    return this.routeForm.get('return') as FormArray;
  }

  loadRoutes() {
    const data = localStorage.getItem('routes');
    this.routes = data ? JSON.parse(data) : [];
  }

  saveRoutes() {
    localStorage.setItem('routes', JSON.stringify(this.routes));
  }

  onAddRoute() {
    this.showForm = true;
    this.editingRoute = null;
    this.routeForm.reset();
    this.single.clear();
    this.return.clear();

    // Add default origin and destination stations for forward route
    this.single.push(
      this.fb.group({
        sno: 1,
        city: '', // Can default to this.cities[0] if needed
        duration: '00:00',
        km: 0,
      })
    );

    this.single.push(
      this.fb.group({
        sno: 2,
        city: '',
        duration: '00:00',
        km: 0,
      })
    );
  }

  onEditRoute(route: any) {
    this.showForm = true;
    this.editingRoute = route;
    this.routeForm.patchValue(route);
    this.single.clear();
    this.return.clear();
    route.single.forEach((s: any) => this.single.push(this.fb.group(s)));
    route.return.forEach((r: any) => this.return.push(this.fb.group(r)));
  }

  onDeleteRoute(route: any) {
    if (confirm('Are you sure you want to delete this route?')) {
      this.routes = this.routes.filter((r) => r !== route);
      this.saveRoutes();
    }
  }

  onAddStation(type: 'single' | 'return') {
    const formArray = type === 'single' ? this.single : this.return;
    formArray.push(
      this.fb.group({
        sno: formArray.length + 1,
        city: ['', Validators.required],
        duration: ['00:00', Validators.required],
        km: [0, Validators.required],
      })
    );
  }

  onRemoveStation(type: 'single' | 'return', index: number) {
    const formArray = type === 'single' ? this.single : this.return;
    formArray.removeAt(index);
    // Recalculate S.No
    formArray.controls.forEach((ctrl, i) => ctrl.patchValue({ sno: i + 1 }));
  }

  onGenerateReturnStations() {
    const reverseList = [...this.single.value].reverse();
    this.return.clear();
    reverseList.forEach((s, i) => {
      this.return.push(
        this.fb.group({
          sno: i + 1,
          city: s.city,
          duration: s.duration,
          km: s.km,
        })
      );
    });
    console.log(this.single);
    console.log(reverseList);
    console.log(this.return);
  }

  onCancel() {
    this.showForm = false;
    this.routeForm.reset();
    this.single.clear();
    this.return.clear();
  }

  onSaveRoute() {
    if (this.routeForm.valid) {
      const data = this.routeForm.getRawValue();
      console.log(data);

      if (this.editingRoute) {
        const index = this.routes.findIndex(
          (r) => r.bus_id === this.editingRoute.bus_id
        );
        this.routes[index] = data;
      } else {
        data.bus_id = this.routes.length
          ? Math.max(...this.routes.map((r) => r.bus_id)) + 1
          : 1;
        this.routes.push(data);
      }

      this.saveRoutes();
      this.showForm = false;
      this.routeForm.reset();
      this.single.clear();
      this.return.clear();
    } else {
      alert('Error');
    }
  }

  getVia(route: any): string {
    if (!route?.single || !Array.isArray(route.single)) {
      return '-';
    }

    const viaStops = route.single.slice(1, -1);
    if (viaStops.length === 0) {
      return 'No Stops';
    }

    const cities = viaStops.map((stop: any) => stop.city);
    return cities.join(' - ');
  }
}
