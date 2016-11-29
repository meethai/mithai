var express = require('express');
var router = express.Router();
var cache = require('memory-cache');

/* GET lots listing. */
router.get('/', function(req, res, next) {
  res.send('You hit lots!');
});

/*POST to accept parking lot state*/
router.post('/allavailable', function (req, res, next) {
  cache.put('spots', req.body);
  console.log(cache.get('spots'));
  res.send('updated!');
});

/*POST to accept closest parking lot*/
router.post('/theclosest', function (req, res, next) {
  cache.put('closest', req.body);
  console.log(cache.get('closest'));
  res.send('got the closest!');
});

/*GET to get closest parking lot*/
router.get('/closest', function (req, res, next) {
  res.send(JSON.stringify(cache.get('closest')));
});

/*GET to get all parking lots*/
router.get('/allspots', function (req, res, next) {
  res.send(JSON.stringify(cache.get('spots')));
});

module.exports = router;
