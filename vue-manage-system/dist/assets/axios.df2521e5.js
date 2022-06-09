var e="undefined"!=typeof globalThis?globalThis:"undefined"!=typeof window?window:"undefined"!=typeof global?global:"undefined"!=typeof self?self:{};function t(e){if(e.__esModule)return e;var t=Object.defineProperty({},"__esModule",{value:!0});return Object.keys(e).forEach((function(r){var n=Object.getOwnPropertyDescriptor(e,r);Object.defineProperty(t,r,n.get?n:{enumerable:!0,get:function(){return e[r]}})})),t}var r={exports:{}},n=function(e,t){return function(){for(var r=new Array(arguments.length),n=0;n<r.length;n++)r[n]=arguments[n];return e.apply(t,r)}},o=n,i=Object.prototype.toString;function a(e){return"[object Array]"===i.call(e)}function s(e){return void 0===e}function u(e){return null!==e&&"object"==typeof e}function c(e){if("[object Object]"!==i.call(e))return!1;var t=Object.getPrototypeOf(e);return null===t||t===Object.prototype}function f(e){return"[object Function]"===i.call(e)}function l(e,t){if(null!=e)if("object"!=typeof e&&(e=[e]),a(e))for(var r=0,n=e.length;r<n;r++)t.call(null,e[r],r,e);else for(var o in e)Object.prototype.hasOwnProperty.call(e,o)&&t.call(null,e[o],o,e)}var d={isArray:a,isArrayBuffer:function(e){return"[object ArrayBuffer]"===i.call(e)},isBuffer:function(e){return null!==e&&!s(e)&&null!==e.constructor&&!s(e.constructor)&&"function"==typeof e.constructor.isBuffer&&e.constructor.isBuffer(e)},isFormData:function(e){return"undefined"!=typeof FormData&&e instanceof FormData},isArrayBufferView:function(e){return"undefined"!=typeof ArrayBuffer&&ArrayBuffer.isView?ArrayBuffer.isView(e):e&&e.buffer&&e.buffer instanceof ArrayBuffer},isString:function(e){return"string"==typeof e},isNumber:function(e){return"number"==typeof e},isObject:u,isPlainObject:c,isUndefined:s,isDate:function(e){return"[object Date]"===i.call(e)},isFile:function(e){return"[object File]"===i.call(e)},isBlob:function(e){return"[object Blob]"===i.call(e)},isFunction:f,isStream:function(e){return u(e)&&f(e.pipe)},isURLSearchParams:function(e){return"undefined"!=typeof URLSearchParams&&e instanceof URLSearchParams},isStandardBrowserEnv:function(){return("undefined"==typeof navigator||"ReactNative"!==navigator.product&&"NativeScript"!==navigator.product&&"NS"!==navigator.product)&&("undefined"!=typeof window&&"undefined"!=typeof document)},forEach:l,merge:function e(){var t={};function r(r,n){c(t[n])&&c(r)?t[n]=e(t[n],r):c(r)?t[n]=e({},r):a(r)?t[n]=r.slice():t[n]=r}for(var n=0,o=arguments.length;n<o;n++)l(arguments[n],r);return t},extend:function(e,t,r){return l(t,(function(t,n){e[n]=r&&"function"==typeof t?o(t,r):t})),e},trim:function(e){return e.trim?e.trim():e.replace(/^\s+|\s+$/g,"")},stripBOM:function(e){return 65279===e.charCodeAt(0)&&(e=e.slice(1)),e}},p=d;function h(e){return encodeURIComponent(e).replace(/%3A/gi,":").replace(/%24/g,"$").replace(/%2C/gi,",").replace(/%20/g,"+").replace(/%5B/gi,"[").replace(/%5D/gi,"]")}var m=function(e,t,r){if(!t)return e;var n;if(r)n=r(t);else if(p.isURLSearchParams(t))n=t.toString();else{var o=[];p.forEach(t,(function(e,t){null!=e&&(p.isArray(e)?t+="[]":e=[e],p.forEach(e,(function(e){p.isDate(e)?e=e.toISOString():p.isObject(e)&&(e=JSON.stringify(e)),o.push(h(t)+"="+h(e))})))})),n=o.join("&")}if(n){var i=e.indexOf("#");-1!==i&&(e=e.slice(0,i)),e+=(-1===e.indexOf("?")?"?":"&")+n}return e},g=d;function v(){this.handlers=[]}v.prototype.use=function(e,t,r){return this.handlers.push({fulfilled:e,rejected:t,synchronous:!!r&&r.synchronous,runWhen:r?r.runWhen:null}),this.handlers.length-1},v.prototype.eject=function(e){this.handlers[e]&&(this.handlers[e]=null)},v.prototype.forEach=function(e){g.forEach(this.handlers,(function(t){null!==t&&e(t)}))};var y=v,b=d,w=function(e,t,r,n,o){return e.config=t,r&&(e.code=r),e.request=n,e.response=o,e.isAxiosError=!0,e.toJSON=function(){return{message:this.message,name:this.name,description:this.description,number:this.number,fileName:this.fileName,lineNumber:this.lineNumber,columnNumber:this.columnNumber,stack:this.stack,config:this.config,code:this.code}},e},x=w,j=function(e,t,r,n,o){var i=new Error(e);return x(i,t,r,n,o)},E=j,O=d,S=O.isStandardBrowserEnv()?{write:function(e,t,r,n,o,i){var a=[];a.push(e+"="+encodeURIComponent(t)),O.isNumber(r)&&a.push("expires="+new Date(r).toGMTString()),O.isString(n)&&a.push("path="+n),O.isString(o)&&a.push("domain="+o),!0===i&&a.push("secure"),document.cookie=a.join("; ")},read:function(e){var t=document.cookie.match(new RegExp("(^|;\\s*)("+e+")=([^;]*)"));return t?decodeURIComponent(t[3]):null},remove:function(e){this.write(e,"",Date.now()-864e5)}}:{write:function(){},read:function(){return null},remove:function(){}},k=function(e){return/^([a-z][a-z\d\+\-\.]*:)?\/\//i.test(e)},N=function(e,t){return t?e.replace(/\/+$/,"")+"/"+t.replace(/^\/+/,""):e},C=d,A=["age","authorization","content-length","content-type","etag","expires","from","host","if-modified-since","if-unmodified-since","last-modified","location","max-forwards","proxy-authorization","referer","retry-after","user-agent"],T=d,R=T.isStandardBrowserEnv()?function(){var e,t=/(msie|trident)/i.test(navigator.userAgent),r=document.createElement("a");function n(e){var n=e;return t&&(r.setAttribute("href",n),n=r.href),r.setAttribute("href",n),{href:r.href,protocol:r.protocol?r.protocol.replace(/:$/,""):"",host:r.host,search:r.search?r.search.replace(/^\?/,""):"",hash:r.hash?r.hash.replace(/^#/,""):"",hostname:r.hostname,port:r.port,pathname:"/"===r.pathname.charAt(0)?r.pathname:"/"+r.pathname}}return e=n(window.location.href),function(t){var r=T.isString(t)?n(t):t;return r.protocol===e.protocol&&r.host===e.host}}():function(){return!0},P=d,U=function(e,t,r){var n=r.config.validateStatus;r.status&&n&&!n(r.status)?t(E("Request failed with status code "+r.status,r.config,null,r.request,r)):e(r)},B=S,L=m,q=function(e,t){return e&&!k(t)?N(e,t):t},D=function(e){var t,r,n,o={};return e?(C.forEach(e.split("\n"),(function(e){if(n=e.indexOf(":"),t=C.trim(e.substr(0,n)).toLowerCase(),r=C.trim(e.substr(n+1)),t){if(o[t]&&A.indexOf(t)>=0)return;o[t]="set-cookie"===t?(o[t]?o[t]:[]).concat([r]):o[t]?o[t]+", "+r:r}})),o):o},_=R,F=j,J=function(e){return new Promise((function(t,r){var n=e.data,o=e.headers,i=e.responseType;P.isFormData(n)&&delete o["Content-Type"];var a=new XMLHttpRequest;if(e.auth){var s=e.auth.username||"",u=e.auth.password?unescape(encodeURIComponent(e.auth.password)):"";o.Authorization="Basic "+btoa(s+":"+u)}var c=q(e.baseURL,e.url);function f(){if(a){var n="getAllResponseHeaders"in a?D(a.getAllResponseHeaders()):null,o={data:i&&"text"!==i&&"json"!==i?a.response:a.responseText,status:a.status,statusText:a.statusText,headers:n,config:e,request:a};U(t,r,o),a=null}}if(a.open(e.method.toUpperCase(),L(c,e.params,e.paramsSerializer),!0),a.timeout=e.timeout,"onloadend"in a?a.onloadend=f:a.onreadystatechange=function(){a&&4===a.readyState&&(0!==a.status||a.responseURL&&0===a.responseURL.indexOf("file:"))&&setTimeout(f)},a.onabort=function(){a&&(r(F("Request aborted",e,"ECONNABORTED",a)),a=null)},a.onerror=function(){r(F("Network Error",e,null,a)),a=null},a.ontimeout=function(){var t="timeout of "+e.timeout+"ms exceeded";e.timeoutErrorMessage&&(t=e.timeoutErrorMessage),r(F(t,e,e.transitional&&e.transitional.clarifyTimeoutError?"ETIMEDOUT":"ECONNABORTED",a)),a=null},P.isStandardBrowserEnv()){var l=(e.withCredentials||_(c))&&e.xsrfCookieName?B.read(e.xsrfCookieName):void 0;l&&(o[e.xsrfHeaderName]=l)}"setRequestHeader"in a&&P.forEach(o,(function(e,t){void 0===n&&"content-type"===t.toLowerCase()?delete o[t]:a.setRequestHeader(t,e)})),P.isUndefined(e.withCredentials)||(a.withCredentials=!!e.withCredentials),i&&"json"!==i&&(a.responseType=e.responseType),"function"==typeof e.onDownloadProgress&&a.addEventListener("progress",e.onDownloadProgress),"function"==typeof e.onUploadProgress&&a.upload&&a.upload.addEventListener("progress",e.onUploadProgress),e.cancelToken&&e.cancelToken.promise.then((function(e){a&&(a.abort(),r(e),a=null)})),n||(n=null),a.send(n)}))},M=d,H=function(e,t){b.forEach(e,(function(r,n){n!==t&&n.toUpperCase()===t.toUpperCase()&&(e[t]=r,delete e[n])}))},z=w,I={"Content-Type":"application/x-www-form-urlencoded"};function V(e,t){!M.isUndefined(e)&&M.isUndefined(e["Content-Type"])&&(e["Content-Type"]=t)}var X,W={transitional:{silentJSONParsing:!0,forcedJSONParsing:!0,clarifyTimeoutError:!1},adapter:(("undefined"!=typeof XMLHttpRequest||"undefined"!=typeof process&&"[object process]"===Object.prototype.toString.call(process))&&(X=J),X),transformRequest:[function(e,t){return H(t,"Accept"),H(t,"Content-Type"),M.isFormData(e)||M.isArrayBuffer(e)||M.isBuffer(e)||M.isStream(e)||M.isFile(e)||M.isBlob(e)?e:M.isArrayBufferView(e)?e.buffer:M.isURLSearchParams(e)?(V(t,"application/x-www-form-urlencoded;charset=utf-8"),e.toString()):M.isObject(e)||t&&"application/json"===t["Content-Type"]?(V(t,"application/json"),function(e,t,r){if(M.isString(e))try{return(t||JSON.parse)(e),M.trim(e)}catch(n){if("SyntaxError"!==n.name)throw n}return(r||JSON.stringify)(e)}(e)):e}],transformResponse:[function(e){var t=this.transitional,r=t&&t.silentJSONParsing,n=t&&t.forcedJSONParsing,o=!r&&"json"===this.responseType;if(o||n&&M.isString(e)&&e.length)try{return JSON.parse(e)}catch(i){if(o){if("SyntaxError"===i.name)throw z(i,this,"E_JSON_PARSE");throw i}}return e}],timeout:0,xsrfCookieName:"XSRF-TOKEN",xsrfHeaderName:"X-XSRF-TOKEN",maxContentLength:-1,maxBodyLength:-1,validateStatus:function(e){return e>=200&&e<300}};W.headers={common:{Accept:"application/json, text/plain, */*"}},M.forEach(["delete","get","head"],(function(e){W.headers[e]={}})),M.forEach(["post","put","patch"],(function(e){W.headers[e]=M.merge(I)}));var $=W,G=d,K=$,Z=function(e){return!(!e||!e.__CANCEL__)},Q=d,Y=function(e,t,r){var n=this||K;return G.forEach(r,(function(r){e=r.call(n,e,t)})),e},ee=Z,te=$;function re(e){e.cancelToken&&e.cancelToken.throwIfRequested()}var ne=d,oe=function(e,t){t=t||{};var r={},n=["url","method","data"],o=["headers","auth","proxy","params"],i=["baseURL","transformRequest","transformResponse","paramsSerializer","timeout","timeoutMessage","withCredentials","adapter","responseType","xsrfCookieName","xsrfHeaderName","onUploadProgress","onDownloadProgress","decompress","maxContentLength","maxBodyLength","maxRedirects","transport","httpAgent","httpsAgent","cancelToken","socketPath","responseEncoding"],a=["validateStatus"];function s(e,t){return ne.isPlainObject(e)&&ne.isPlainObject(t)?ne.merge(e,t):ne.isPlainObject(t)?ne.merge({},t):ne.isArray(t)?t.slice():t}function u(n){ne.isUndefined(t[n])?ne.isUndefined(e[n])||(r[n]=s(void 0,e[n])):r[n]=s(e[n],t[n])}ne.forEach(n,(function(e){ne.isUndefined(t[e])||(r[e]=s(void 0,t[e]))})),ne.forEach(o,u),ne.forEach(i,(function(n){ne.isUndefined(t[n])?ne.isUndefined(e[n])||(r[n]=s(void 0,e[n])):r[n]=s(void 0,t[n])})),ne.forEach(a,(function(n){n in t?r[n]=s(e[n],t[n]):n in e&&(r[n]=s(void 0,e[n]))}));var c=n.concat(o).concat(i).concat(a),f=Object.keys(e).concat(Object.keys(t)).filter((function(e){return-1===c.indexOf(e)}));return ne.forEach(f,u),r};var ie={name:"axios",version:"0.21.4",description:"Promise based HTTP client for the browser and node.js",main:"index.js",scripts:{test:"grunt test",start:"node ./sandbox/server.js",build:"NODE_ENV=production grunt build",preversion:"npm test",version:"npm run build && grunt version && git add -A dist && git add CHANGELOG.md bower.json package.json",postversion:"git push && git push --tags",examples:"node ./examples/server.js",coveralls:"cat coverage/lcov.info | ./node_modules/coveralls/bin/coveralls.js",fix:"eslint --fix lib/**/*.js"},repository:{type:"git",url:"https://github.com/axios/axios.git"},keywords:["xhr","http","ajax","promise","node"],author:"Matt Zabriskie",license:"MIT",bugs:{url:"https://github.com/axios/axios/issues"},homepage:"https://axios-http.com",devDependencies:{coveralls:"^3.0.0","es6-promise":"^4.2.4",grunt:"^1.3.0","grunt-banner":"^0.6.0","grunt-cli":"^1.2.0","grunt-contrib-clean":"^1.1.0","grunt-contrib-watch":"^1.0.0","grunt-eslint":"^23.0.0","grunt-karma":"^4.0.0","grunt-mocha-test":"^0.13.3","grunt-ts":"^6.0.0-beta.19","grunt-webpack":"^4.0.2","istanbul-instrumenter-loader":"^1.0.0","jasmine-core":"^2.4.1",karma:"^6.3.2","karma-chrome-launcher":"^3.1.0","karma-firefox-launcher":"^2.1.0","karma-jasmine":"^1.1.1","karma-jasmine-ajax":"^0.1.13","karma-safari-launcher":"^1.0.0","karma-sauce-launcher":"^4.3.6","karma-sinon":"^1.0.5","karma-sourcemap-loader":"^0.3.8","karma-webpack":"^4.0.2","load-grunt-tasks":"^3.5.2",minimist:"^1.2.0",mocha:"^8.2.1",sinon:"^4.5.0","terser-webpack-plugin":"^4.2.3",typescript:"^4.0.5","url-search-params":"^0.10.0",webpack:"^4.44.2","webpack-dev-server":"^3.11.0"},browser:{"./lib/adapters/http.js":"./lib/adapters/xhr.js"},jsdelivr:"dist/axios.min.js",unpkg:"dist/axios.min.js",typings:"./index.d.ts",dependencies:{"follow-redirects":"^1.14.0"},bundlesize:[{path:"./dist/axios.min.js",threshold:"5kB"}]},ae={};["object","boolean","number","function","string","symbol"].forEach((function(e,t){ae[e]=function(r){return typeof r===e||"a"+(t<1?"n ":" ")+e}}));var se={},ue=ie.version.split(".");function ce(e,t){for(var r=t?t.split("."):ue,n=e.split("."),o=0;o<3;o++){if(r[o]>n[o])return!0;if(r[o]<n[o])return!1}return!1}ae.transitional=function(e,t,r){var n=t&&ce(t);function o(e,t){return"[Axios v"+ie.version+"] Transitional option '"+e+"'"+t+(r?". "+r:"")}return function(r,i,a){if(!1===e)throw new Error(o(i," has been removed in "+t));return n&&!se[i]&&(se[i]=!0,console.warn(o(i," has been deprecated since v"+t+" and will be removed in the near future"))),!e||e(r,i,a)}};var fe=d,le=m,de=y,pe=function(e){return re(e),e.headers=e.headers||{},e.data=Y.call(e,e.data,e.headers,e.transformRequest),e.headers=Q.merge(e.headers.common||{},e.headers[e.method]||{},e.headers),Q.forEach(["delete","get","head","post","put","patch","common"],(function(t){delete e.headers[t]})),(e.adapter||te.adapter)(e).then((function(t){return re(e),t.data=Y.call(e,t.data,t.headers,e.transformResponse),t}),(function(t){return ee(t)||(re(e),t&&t.response&&(t.response.data=Y.call(e,t.response.data,t.response.headers,e.transformResponse))),Promise.reject(t)}))},he=oe,me={isOlderVersion:ce,assertOptions:function(e,t,r){if("object"!=typeof e)throw new TypeError("options must be an object");for(var n=Object.keys(e),o=n.length;o-- >0;){var i=n[o],a=t[i];if(a){var s=e[i],u=void 0===s||a(s,i,e);if(!0!==u)throw new TypeError("option "+i+" must be "+u)}else if(!0!==r)throw Error("Unknown option "+i)}},validators:ae},ge=me.validators;function ve(e){this.defaults=e,this.interceptors={request:new de,response:new de}}ve.prototype.request=function(e){"string"==typeof e?(e=arguments[1]||{}).url=arguments[0]:e=e||{},(e=he(this.defaults,e)).method?e.method=e.method.toLowerCase():this.defaults.method?e.method=this.defaults.method.toLowerCase():e.method="get";var t=e.transitional;void 0!==t&&me.assertOptions(t,{silentJSONParsing:ge.transitional(ge.boolean,"1.0.0"),forcedJSONParsing:ge.transitional(ge.boolean,"1.0.0"),clarifyTimeoutError:ge.transitional(ge.boolean,"1.0.0")},!1);var r=[],n=!0;this.interceptors.request.forEach((function(t){"function"==typeof t.runWhen&&!1===t.runWhen(e)||(n=n&&t.synchronous,r.unshift(t.fulfilled,t.rejected))}));var o,i=[];if(this.interceptors.response.forEach((function(e){i.push(e.fulfilled,e.rejected)})),!n){var a=[pe,void 0];for(Array.prototype.unshift.apply(a,r),a=a.concat(i),o=Promise.resolve(e);a.length;)o=o.then(a.shift(),a.shift());return o}for(var s=e;r.length;){var u=r.shift(),c=r.shift();try{s=u(s)}catch(f){c(f);break}}try{o=pe(s)}catch(f){return Promise.reject(f)}for(;i.length;)o=o.then(i.shift(),i.shift());return o},ve.prototype.getUri=function(e){return e=he(this.defaults,e),le(e.url,e.params,e.paramsSerializer).replace(/^\?/,"")},fe.forEach(["delete","get","head","options"],(function(e){ve.prototype[e]=function(t,r){return this.request(he(r||{},{method:e,url:t,data:(r||{}).data}))}})),fe.forEach(["post","put","patch"],(function(e){ve.prototype[e]=function(t,r,n){return this.request(he(n||{},{method:e,url:t,data:r}))}}));var ye=ve;function be(e){this.message=e}be.prototype.toString=function(){return"Cancel"+(this.message?": "+this.message:"")},be.prototype.__CANCEL__=!0;var we=be,xe=we;function je(e){if("function"!=typeof e)throw new TypeError("executor must be a function.");var t;this.promise=new Promise((function(e){t=e}));var r=this;e((function(e){r.reason||(r.reason=new xe(e),t(r.reason))}))}je.prototype.throwIfRequested=function(){if(this.reason)throw this.reason},je.source=function(){var e;return{token:new je((function(t){e=t})),cancel:e}};var Ee=je,Oe=d,Se=n,ke=ye,Ne=oe;function Ce(e){var t=new ke(e),r=Se(ke.prototype.request,t);return Oe.extend(r,ke.prototype,t),Oe.extend(r,t),r}var Ae=Ce($);Ae.Axios=ke,Ae.create=function(e){return Ce(Ne(Ae.defaults,e))},Ae.Cancel=we,Ae.CancelToken=Ee,Ae.isCancel=Z,Ae.all=function(e){return Promise.all(e)},Ae.spread=function(e){return function(t){return e.apply(null,t)}},Ae.isAxiosError=function(e){return"object"==typeof e&&!0===e.isAxiosError},r.exports=Ae,r.exports.default=Ae;var Te=r.exports;export{Te as E,e as c,t as g};