import { Injectable } from '@angular/core';
import { Employee } from "./employee";
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }

  employees = this.http.get<Employee[]>('/api/get-Employees');
}
