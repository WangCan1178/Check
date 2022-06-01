import{e,z as r,p as o,a,r as l,o as t,c as n,f as d,h as i,t as s,I as c,w as u}from"./vendor.9c82358b.js";const p={name:"Register",data:()=>({clickVerCode:!1,verSecond:60,timer:null}),setup(){e();const o=c(),a=r({phone:"",verification:""});return{userInfo:a,rules:{phone:[{required:!0,validator:(e,r,o)=>""===r?o(new Error("请输入手机密码")):11!==r.toString().length?o(new Error("手机号码必须为11位")):/^1[3-9]\d{9}$/.test(r)?void o():o(new Error("请输入有效的手机号码")),trigger:"blur"}],verification:[{required:!0,validator:(e,r,o)=>""==r?o(new Error("请输入验证码")):4!=r.length?o(new Error("请输入正确4位验证码")):void o(),trigger:"blur"}]},verCodeClick:()=>{o.data.clickVerCode||(o.data.clickVerCode=!0,o.data.timer=setInterval((()=>{1==o.data.verSecond&&(o.data.clickVerCode=!1,o.data.verSecond=60,o.data.timer&&(clearInterval(o.data.timer),o.data.timer=null)),o.data.verSecond-=1}),1e3))},next:()=>{alert(a.phone),localStorage.setItem("phone",a.phone)}}}},m=u();o("data-v-5c7ee61d");const v={class:"login-wrap"},f={class:"ms-login"},h=d("div",{class:"ms-title"},"新用户注册",-1),g=d("i",{class:"el-icon-mobile-phone",style:{"font-size":"20px"}},null,-1),k=d("i",{class:"el-icon-message",style:{"font-size":"20px"}},null,-1),C={class:"login-btn"},I=i("下一步");a();const V=m(((e,r,o,a,c,u)=>{const p=l("el-input"),V=l("el-form-item"),_=l("el-button"),w=l("router-link"),b=l("el-form");return t(),n("div",v,[d("div",f,[h,d(b,{model:a.userInfo,rules:a.rules,"label-width":"0px",class:"ms-content"},{default:m((()=>[d(V,{prop:"phone"},{default:m((()=>[d(p,{modelValue:a.userInfo.phone,"onUpdate:modelValue":r[1]||(r[1]=e=>a.userInfo.phone=e),placeholder:"请输入电话"},{prepend:m((()=>[g])),_:1},8,["modelValue"])])),_:1}),d(V,{prop:"verification"},{default:m((()=>[d(p,{modelValue:a.userInfo.verification,"onUpdate:modelValue":r[2]||(r[2]=e=>a.userInfo.verification=e),placeholder:"请输入验证码"},{prepend:m((()=>[k])),append:m((()=>[d(_,{onClick:a.verCodeClick,style:{"background-color":"#409EFF",color:"white"}},{default:m((()=>[i(s(c.clickVerCode?c.verSecond+"s":"获取验证码"),1)])),_:1},8,["onClick"])])),_:1},8,["modelValue"])])),_:1}),d("div",C,[d(w,{to:"/next"},{default:m((()=>[d(_,{type:"primary",onClick:r[3]||(r[3]=e=>a.next())},{default:m((()=>[I])),_:1})])),_:1})])])),_:1},8,["model","rules"])])])}));p.render=V,p.__scopeId="data-v-5c7ee61d";export default p;