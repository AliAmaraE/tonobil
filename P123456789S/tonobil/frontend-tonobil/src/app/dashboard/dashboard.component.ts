import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  currentUser: any;
  cars: any[] = [];
  
  newCar = { brand: '', model: '', year: 2024, licensePlate: '' };
  showAddCarForm = false;

  constructor(private apiService: ApiService, private router: Router) {}

  ngOnInit(): void {
    const userStr = localStorage.getItem('currentUser');
    if (!userStr) {
      this.router.navigate(['/login']);
      return;
    }
    this.currentUser = JSON.parse(userStr);
    this.loadCars();
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.router.navigate(['/login']);
  }

  loadCars() {
    this.apiService.getCars(this.currentUser.id).subscribe(res => {
      this.cars = res;
    });
  }

  addCar() {
    this.apiService.addCar(this.currentUser.id, this.newCar).subscribe(res => {
      this.loadCars();
      this.newCar = { brand: '', model: '', year: 2024, licensePlate: '' };
      this.showAddCarForm = false;
    });
  }

  toggleAddCarForm() {
    this.showAddCarForm = !this.showAddCarForm;
  }

  deleteCar(id: number) {
    this.apiService.deleteCar(id).subscribe(res => {
      this.loadCars();
    });
  }

  viewServices(carId: number) {
    this.router.navigate(['/services', carId]);
  }
}
