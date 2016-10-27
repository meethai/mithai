var express = require('express');
var cache = require('memory-cache');
var router = express.Router();
var x = "{ nodes:[ { id: 'node1', group: 0 },{ id: 'node2', group: 1 },{ id: 'node3', group: 2 },{ id: 'node5', group: 3 },{ id: 'sensor1', group: 0 },{ id: 'sensor2', group: 0 },{ id: 'sensor3', group: 0 } ],links:[ { source: 'node1', target: 'node2', value: 30 },{ source: 'node1', target: 'node3', value: 30 },{ source: 'node1', target: 'node5', value: 30 },{ source: 'node1', target: 'sensor1', value: 10 },{ source: 'sensor1', target: 'sensor2', value: 1 },{ source: 'sensor1', target: 'sensor3', value: 1 },{ source: 'node1', target: 'sensor2', value: 10 },{ source: 'sensor2', target: 'sensor1', value: 1 },{ source: 'sensor2', target: 'sensor3', value: 1 },{ source: 'node1', target: 'sensor3', value: 10 },{ source: 'sensor3', target: 'sensor1', value: 1 },{ source: 'sensor3', target: 'sensor2', value: 1 } ] }";
/* GET home page. */
router.get('/', function(req, res, next) {
    var data = cache.get('visualizationData');

    // if (data === "undefined") {
    //     // res.send("Visualization data is not present!");
    // } else {
    //     // res.send(data);
    // }
    console.log(data);
    res.render('home.ejs', {"vizData": JSON.stringify(data)});
});

router.post('/ingress', function(req, res, next) {
    cache.put('visualizationData', req.body);
    res.send("Hello world!");
});


module.exports = router;
