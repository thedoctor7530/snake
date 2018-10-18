const mysql = require("mysql");

/*
exports.initConn = function(username, password) {
    try {
        const db = mysql.createConnection({
            host: "localhost",
            user: username,
            password: password,
            database: "Company"
        });
    } catch (Exception) {
        res.send("Invalid login");
    }
}
*/

const db = mysql.createConnection({
    host: "localhost",
    user: "Conor",
    password: "password",
    database: "Company"
});

db.connect(function(err) {
    if(err) throw err;
    console.log("Connected to MySQL")
})

exports.getEmployee = function(callback) {
    db.query(
        "SELECT employeeID, employeeName, employeeAddress FROM EmployeeDetails",
        function(error, rows) {
            if(error) throw error;
            callback(rows);
        }
    )
}

exports.addEmployee = function(data, ready) {
    db.query("INSERT INTO EmployeeDetails SET ?",
    data,
    function(error, results) {
        if(error) throw error;
        ready(results.insertId);
    });
}
/*
exports.removeCity = function(id, callback) {
    db.query(
        "DELETE FROM city WHERE id = ?",
        [id],
        function(err, res) {
            if (err) throw err;
            callback(res);
        }
    );
};
*/