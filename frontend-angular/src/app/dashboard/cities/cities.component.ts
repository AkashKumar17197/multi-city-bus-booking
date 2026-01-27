import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule} from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-cities',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule
  ],
  templateUrl: './cities.component.html',
  styleUrl: './cities.component.scss'
})

export class CitiesComponent implements OnInit {
  showForm = false;
  editingCity: any = null;

  displayedColumns: string[] = ['id', 'name', 'code', 'actions'];
  cities = [
    { id: 1, name: 'Chennai', code: 'CHN'},
    { id: 2, name: 'Bangalore', code: 'BLR'}
  ];

  directions = ['North', 'South', 'East', 'West'];

  cityForm: FormGroup;

  constructor(private fb: FormBuilder){
    this.cityForm = this.fb.group({
      city_id: [''],
      city_name: ['', Validators.required],
      city_code: ['', Validators.required],
      parent_city_id: [''],
      direction_from_state: ['', Validators.required],
      direction_from_country: ['', Validators.required],
      nearby_city_id_1: [''],
      nearby_city_direction_1: [''],
      nearby_city_id_2: [''],
      nearby_city_direction_2: [''],
      nearby_city_id_3: [''],
      nearby_city_direction_3: [''],
      nearby_city_id_4: [''],
      nearby_city_direction_4: [''],
    });
  }

  ngOnInit(): void {
      this.loadCities();
  }

  loadCities() {
    const stored = localStorage.getItem('cities');
    this.cities = stored ? JSON.parse(stored) : [];
  }

  saveCitiesToStorage() {
    localStorage.setItem('cities', JSON.stringify(this.cities));
  }

  generateNextId(): number {
    const ids = this.cities.map(c => c.id);
    return ids.length ? Math.max(...ids) + 1 : 1;
  } 

  onAddCity() {
    this.showForm = true;
    this.editingCity = null;
    this.cityForm.reset();
    this.cityForm.patchValue({ city_id:  this.generateNextId() });
  }

  onEditCity(city: any){
    this.showForm = true;
    this.editingCity = city;
    this.cityForm.patchValue({
      ...city, 
      city_id: city.id
    });
  }

  onDeleteCity(city: any){
    const confirmDelete = confirm(`Are you sure you want to delete ${city.name}?`);
    if (confirmDelete) {
      this.cities = this.cities.filter(c => c.id !== city.id);
      this.saveCitiesToStorage();
    }
  }

  onCancel() {
    this.showForm = false;
    this.cityForm.reset();
  }

  onSaveCity() {
    if (this.cityForm.valid) {
      const formData = this.cityForm.getRawValue();
      
      if (this.editingCity) {
        const index = this.cities.findIndex(c => c.id === this.editingCity.id);
        this.cities[index] = { ...formData, id: this.editingCity.id}
      } else {
        this.cities.push({
          ...formData, id: formData.city_id
        });
      }

      this.saveCitiesToStorage();
      this.cityForm.reset();
      this.showForm = false;
      this.editingCity = null;
    }
  }
}