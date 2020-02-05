require('source-map-support/register');
module.exports =
/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 0);
/******/ })
/************************************************************************/
/******/ ({

/***/ "./src/index.js":
/*!**********************!*\
  !*** ./src/index.js ***!
  \**********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var koa = __webpack_require__(/*! koa */ "koa");

var app = module.exports = new koa();

const server = __webpack_require__(/*! http */ "http").createServer(app.callback());

const WebSocket = __webpack_require__(/*! ws */ "ws");

const wss = new WebSocket.Server({
  server
});

const Router = __webpack_require__(/*! koa-router */ "koa-router");

const cors = __webpack_require__(/*! @koa/cors */ "@koa/cors");

const bodyParser = __webpack_require__(/*! koa-bodyparser */ "koa-bodyparser");

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
const specDetails = ['Known as Russian salad around the world', 'Probably the most famous traditional Russian/Ukrainian dish internationally', 'Another Russian dish to receive global recognition', 'Another soup on the list to warm you during the six to eight months of cold'];
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
  ctx.response.body = robots.filter(robot => robot.type == type); // console.log("body: " + JSON.stringify(ctx.response.body));

  ctx.response.status = 200;
});
router.get('/old', ctx => {
  ctx.response.body = robots;
  ctx.response.status = 200;
});
router.post('/age', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body; // console.log("body: " + JSON.stringify(headers));

  const id = headers.id;
  const age = headers.age;

  if (typeof id !== 'undefined' && typeof age !== 'undefined') {
    const index = robots.findIndex(robot => robot.id == id);

    if (index === -1) {
      console.log("Robot not available!");
      ctx.response.body = {
        text: 'Robot not available!'
      };
      ctx.response.status = 404;
    } else {
      let robot = robots[index];
      robot.age = age;
      ctx.response.body = robot;
      ctx.response.status = 200;
    }
  } else {
    console.log("Missing or invalid: id!");
    ctx.response.body = {
      text: 'Missing or invalid: id!'
    };
    ctx.response.status = 404;
  }
});

const broadcast = data => wss.clients.forEach(client => {
  if (client.readyState === WebSocket.OPEN) {
    client.send(JSON.stringify(data));
  }
});

router.post('/robot', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body; // console.log("body: " + JSON.stringify(headers));

  const name = headers.name;
  const specs = headers.specs;
  const height = headers.height;
  const type = headers.type;
  const age = headers.age;

  if (typeof name !== 'undefined' && typeof specs !== 'undefined' && typeof height !== 'undefined' && typeof type !== 'undefined' && age !== 'undefined') {
    const index = robots.findIndex(recipe => recipe.name == name);

    if (index !== -1) {
      console.log("Recipe already exists!");
      ctx.response.body = {
        text: 'Recipe already exists!'
      };
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
    ctx.response.body = {
      text: 'Missing or invalid fields!'
    };
    ctx.response.status = 404;
  }
});
router.post('/height', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body; // console.log("body: " + JSON.stringify(headers));

  const id = headers.id;
  const height = headers.height;

  if (typeof id !== 'undefined' && typeof height !== 'undefined') {
    const index = robots.findIndex(robot => robot.id == id);

    if (index === -1) {
      console.log("Robot not available!");
      ctx.response.body = {
        text: 'Robot not available!'
      };
      ctx.response.status = 404;
    } else {
      let robot = robots[index];
      robot.height = height;
      ctx.response.body = robot;
      ctx.response.status = 200;
    }
  } else {
    console.log("Missing or invalid: id!");
    ctx.response.body = {
      text: 'Missing or invalid: id!'
    };
    ctx.response.status = 404;
  }
});
app.use(router.routes());
app.use(router.allowedMethods());
server.listen(2202);

/***/ }),

/***/ 0:
/*!****************************!*\
  !*** multi ./src/index.js ***!
  \****************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! E:\UBB\Semester 5\Mobile\Projects\exam-2020-22b-master\server\src/index.js */"./src/index.js");


/***/ }),

/***/ "@koa/cors":
/*!****************************!*\
  !*** external "@koa/cors" ***!
  \****************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("@koa/cors");

/***/ }),

/***/ "http":
/*!***********************!*\
  !*** external "http" ***!
  \***********************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("http");

/***/ }),

/***/ "koa":
/*!**********************!*\
  !*** external "koa" ***!
  \**********************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("koa");

/***/ }),

/***/ "koa-bodyparser":
/*!*********************************!*\
  !*** external "koa-bodyparser" ***!
  \*********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("koa-bodyparser");

/***/ }),

/***/ "koa-router":
/*!*****************************!*\
  !*** external "koa-router" ***!
  \*****************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("koa-router");

/***/ }),

/***/ "ws":
/*!*********************!*\
  !*** external "ws" ***!
  \*********************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("ws");

/***/ })

/******/ });
//# sourceMappingURL=main.map