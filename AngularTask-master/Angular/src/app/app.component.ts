import { Component, OnInit } from '@angular/core';
import { Employee } from './employee'
import { DataService } from './data.service';

@Component({
  selector: 'ang-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})



export class AppComponent implements OnInit{
  title = 'Angular Project';
  testEmployees : Employee;
localData : DataService

  constructor(data : DataService){
    this.localData = data;
   
  }

  ngOnInit(){

  }
  
}
