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
    console.log(`${start.toLocaleTimeString()} ${ctx.request.method} ${ctx.request.url}  ${ctx.response.status} - ${ms}ms`);
  });
}

const getRandomInt = (min, max) => {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min)) + min;
};

const robotNames = ['Data', 'Cole', 'Ojal', 'Motorized Excevation Android', 'Preliminary Space Navigation Emulator', 'Extraterrestrial Supervision Device'];
const specDetails = ['Known as Russian salad around the world',
  'Probably the most famous traditional Russian/Ukrainian dish internationally',
  'Another Russian dish to receive global recognition',
  'Another soup on the list to warm you during the six to eight months of cold'
];
const types = ['lego', 'industrial', 'custom', 'new', 'trial'];
const robots = [];

for (let i = 0; i < 10; i++) {
  robots.push({
    id: i + 1,
    name: robotNames[getRandomInt(0, robotNames.length)],
    specs: specDetails[getRandomInt(0, specDetails.length)],
    height: getRandomInt(50, 100),
    type: types[getRandomInt(0, types.length)],
    age: getRandomInt(10, 100)
  });
}

const router = new Router();
router.get('/types', ctx => {
  ctx.response.body = types;
  ctx.response.status = 200;
});

router.get('/robots/:type', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.params;
  const type = headers.type;
  console.log("type: " + JSON.stringify(type));
  ctx.response.body = robots.filter(robot => robot.type == type);
  // console.log("body: " + JSON.stringify(ctx.response.body));
  ctx.response.status = 200;
});

router.get('/old', ctx => {
  ctx.response.body = robots;
  ctx.response.status = 200;
});

router.post('/age', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body;
  // console.log("body: " + JSON.stringify(headers));
  const id = headers.id;
  const age = headers.age;
  if (typeof id !== 'undefined' && typeof age !== 'undefined') {
    const index = robots.findIndex(robot => robot.id == id);
    if (index === -1) {
      console.log("Robot not available!");
      ctx.response.body = {text: 'Robot not available!'};
      ctx.response.status = 404;
    } else {
      let robot = robots[index];
      robot.age = age;
      ctx.response.body = robot;
      ctx.response.status = 200;
    }
  }
  else {
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

router.post('/robot', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body;
  // console.log("body: " + JSON.stringify(headers));
  const name = headers.name;
  const specs = headers.specs;
  const height = headers.height;
  const type = headers.type;
  const age = headers.age;
  if (typeof name !== 'undefined' && typeof specs !== 'undefined' && typeof height !== 'undefined'
    && typeof type !== 'undefined' && age !== 'undefined') {
    const index = robots.findIndex(recipe => recipe.name == name);
    if (index !== -1) {
      console.log("Recipe already exists!");
      ctx.response.body = {text: 'Recipe already exists!'};
      ctx.response.status = 404;
    } else {
      let maxId = Math.max.apply(Math, robots.map(function (recipe) {
        return recipe.id;
      })) + 1;
      let robot = {
        id: maxId,
        name,
        specs,
        height,
        type,
        age
      };
      robots.push(robot);
      broadcast(robot);
      ctx.response.body = robot;
      ctx.response.status = 200;
    }
  } else {
    console.log("Missing or invalid fields!");
    ctx.response.body = {text: 'Missing or invalid fields!'};
    ctx.response.status = 404;
  }
});

router.post('/height', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body;
  // console.log("body: " + JSON.stringify(headers));
  const id = headers.id;
  const height = headers.height;
  if (typeof id !== 'undefined' && typeof height !== 'undefined') {
    const index = robots.findIndex(robot => robot.id == id);
    if (index === -1) {
      console.log("Robot not available!");
      ctx.response.body = {text: 'Robot not available!'};
      ctx.response.status = 404;
    } else {
      let robot = robots[index];
      robot.height = height;
      ctx.response.body = robot;
      ctx.response.status = 200;
    }
  }
  else {
    console.log("Missing or invalid: id!");
    ctx.response.body = {text: 'Missing or invalid: id!'};
    ctx.response.status = 404;
  }
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(2202);