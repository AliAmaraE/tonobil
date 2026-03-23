import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-car-service-page',
  templateUrl: './car-service-page.component.html',
  styleUrls: ['./car-service-page.component.css']
})
export class CarServicePageComponent implements OnInit {
  carId!: number;
  services: any[] = [];
  
  newService = { 
    serviceType: '', 
    description: '', 
    cost: 0, 
    kilometerAtService: 0, 
    nextServiceKilometer: 0, 
    serviceDate: new Date().toISOString().split('T')[0],
    oilFilterChanged: false,
    fuelFilterChanged: false,
    airFilterChanged: false,
    acAirFilterChanged: false
  };
  showAddServiceForm = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('carId');
    if (id) {
      this.carId = +id;
      this.loadServices();
    } else {
      this.goBack();
    }
  }

  loadServices() {
    this.apiService.getServices(this.carId).subscribe(res => {
      this.services = res;
    });
  }

  addService() {
    this.apiService.addService(this.carId, this.newService).subscribe(res => {
      this.loadServices();
      this.newService = { 
        serviceType: '', 
        description: '', 
        cost: 0, 
        kilometerAtService: 0, 
        nextServiceKilometer: 0, 
        serviceDate: new Date().toISOString().split('T')[0],
        oilFilterChanged: false,
        fuelFilterChanged: false,
        airFilterChanged: false,
        acAirFilterChanged: false
      };
      this.showAddServiceForm = false;
    });
  }

  toggleAddServiceForm() {
    this.showAddServiceForm = !this.showAddServiceForm;
  }

  deleteService(serviceId: number) {
    this.apiService.deleteService(serviceId).subscribe(res => {
      this.loadServices();
    });
  }

  goBack() {
    this.router.navigate(['/dashboard']);
  }
}
