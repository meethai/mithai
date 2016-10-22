var express = require('express');
var cache = require('memory-cache');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    var data = cache.get('visualizationData');

    if (data === "undefined") {
        res.send("Visualization data is not present!");
    } else {
        res.send(data);
    }
});

router.post('/ingress', function(req, res, next) {
    cache.put('visualizationData', req.body);
    res.send("Hello world!");
});

module.exports = router;
