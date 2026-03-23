import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getCars(ownerId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/cars?ownerId=${ownerId}`);
  }

  addCar(ownerId: number, car: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/cars?ownerId=${ownerId}`, car);
  }

  deleteCar(carId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/cars/${carId}`);
  }

  getServices(carId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/services?carId=${carId}`);
  }

  addService(carId: number, service: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/services?carId=${carId}`, service);
  }

  deleteService(serviceId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/services/${serviceId}`);
  }
}
