const express = require('express');
const app = express();
const deliverable = require('./routes/delivery');
const users = require('./routes/users')

app.use('/api/get', deliverable);
app.use('/api/get/users', users);

app.get('/api', (req, res) => {
    res.send('ACCESS ONE OF THE ENDPOINTS.');
})

app.listen(4000, function() {
    console.log('Server is running!');
});