var koa = require('koa');
var app = module.exports = new koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({server});
const Router = require('koa-router');
const cors = require('@koa/cors');
const bodyParser = require('koa-bodyparser');

app.use(bodyParser());

app.use(cors());

app.use(middleware);

function middleware(ctx, next) {
  const start = new Date();
  return next().then(() => {
    const ms = new Date() - start;
    console.log(`${start.toLocaleTimeString()} ${ctx.request.method} ${ctx.request.url} ${ctx.response.status} - ${ms}ms`);
  });
}

const getRandomInt = (min, max) => {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min)) + min;
};

const tableNames = ['T1', 'T2', 'North', 'Big', 'Small', 'Round'];
const statuses = ['recorded', 'preparing', 'ready', 'canceled'];
const types = ['normal', 'delivery', 'urgent'];
const orders = [];

for (let i = 0; i < 100; i++) {
  orders.push({
    id: i + 1,
    table: tableNames[getRandomInt(0, tableNames.length)],
    details: "Something " + getRandomInt(0, 10),
    status: statuses[getRandomInt(0, statuses.length)],
    time: getRandomInt(10, 20),
    type: types[getRandomInt(0, types.length)]
  });
}

const router = new Router();
router.get('/orders', ctx => {
  ctx.response.body = orders.filter(order => order.status === 'ready');
  ctx.response.status = 200;
});

router.get('/order/:id', ctx => {
    // console.log("ctx: " + JSON.stringify(ctx));
    const headers = ctx.params;
    const id = headers.id;
    if (typeof id !== 'undefined') {
      const index = orders.findIndex(order => order.id == id);
      if (index === -1) {
        console.log("Order not available!");
        ctx.response.body = {text: 'Order not available!'};
        ctx.response.status = 404;
      } else {
        // console.log("type: " + JSON.stringify(type));
        ctx.response.body = orders[index];
        // console.log("id: " + id + "body: " + JSON.stringify(ctx.response.body));
        ctx.response.status = 200;
      }
    } else {
      console.log("Missing or invalid: id!");
      ctx.response.body = {text: 'Missing or invalid: id!'};
      ctx.response.status = 404;
    }
  }
);

router.get('/my/:table', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.params;
  const table = headers.table;
  // console.log("type: " + JSON.stringify(type));
  let tableOrders = [...orders.filter(order => order.table == table)];
  ctx.response.body = tableOrders.pop();
  // console.log("type: " + JSON.stringify(type) + "body: " + JSON.stringify(ctx.response.body));
  ctx.response.status = 200;
});

router.get('/recorded', ctx => {
  ctx.response.body = orders.filter(order => order.status === 'recorded');
  ctx.response.status = 200;
});

router.post('/status', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body;
  // console.log("body: " + JSON.stringify(headers));
  const id = headers.id;
  const status = headers.status;
  if (typeof id !== 'undefined' && typeof status !== 'undefined') {
    const index = orders.findIndex(order => order.id == id);
    if (index === -1) {
      console.log("Order not available!");
      ctx.response.body = {text: 'Order not available!'};
      ctx.response.status = 404;
    } else {
      let order = orders[index];
      order.status = status;
      // console.log("status changed: " + JSON.stringify(order));
      ctx.response.body = order;
      ctx.response.status = 200;
    }
  } else {
    console.log("Missing or invalid: id!");
    ctx.response.body = {text: 'Missing or invalid: id!'};
    ctx.response.status = 404;
  }
});

const broadcast = (data) =>
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(data));
    }
  });

router.post('/order', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body;
  // console.log("body: " + JSON.stringify(headers));
  const table = headers.table;
  const details = headers.details;
  const status = headers.status;
  const time = headers.time;
  const type = headers.type;
  if (typeof table !== 'undefined' && typeof details !== 'undefined' && typeof status !== 'undefined'
    && typeof time !== 'undefined' && type !== 'undefined') {
    const index = orders.findIndex(order => order.table == table && order.details == details);
    if (index !== -1) {
      console.log("Order already exists!");
      ctx.response.body = {text: 'Order already exists!'};
      ctx.response.status = 404;
    } else {
      let maxId = Math.max.apply(Math, orders.map(function (order) {
        return order.id;
      })) + 1;
      let order = {
        id: maxId,
        table,
        details,
        status,
        time,
        type
      };
      // console.log("created: " + JSON.stringify(order));
      orders.push(order);
      broadcast(order);
      ctx.response.body = order;
      ctx.response.status = 200;
    }
  } else {
    console.log("Missing or invalid fields!");
    ctx.response.body = {text: 'Missing or invalid fields!'};
    ctx.response.status = 404;
  }
});


app.use(router.routes());
app.use(router.allowedMethods());

server.listen(2301);