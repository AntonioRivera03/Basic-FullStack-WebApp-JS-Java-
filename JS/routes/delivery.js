const express = require('express');
const router = express.Router();

router.get('/', (req, res) => {
    const mysql = require('mysql');
    const connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: 'password',
        database: 'database',
        insecureAuth: true
    })

    connection.connect();

    connection.query('SELECT * FROM message', (err, data)=>{
        if (err) throw err;

        console.log(data)
        res.json(data);
    })
    
});

module.exports = router;