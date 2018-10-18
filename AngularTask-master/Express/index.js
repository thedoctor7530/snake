const express = require('express');
const app = express();
app.use(express.json());
const db = require("./db.js");

function updateEmployees(employeesReady) {
    db.getEmployee(function(rows) {
        employees = rows;
        employeesReady();
    });
};

employees = [];

app.get("/get-Employees", function(req, res) {
    updateEmployees(function() {
      res.send(employees);
    });  
  });

app.listen(8010, function() {
    console.log("Express started");
});