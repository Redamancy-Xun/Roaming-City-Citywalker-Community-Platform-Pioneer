import{d as y,C,i as S,a as E,c as V,b as s,D as f,E as h,l,e as r,w as p,f as u,x as I,F as B,g as D,r as m,p as N,h as O}from"./index-c336339f.js";import{C as U}from"./index-30cdb3de.js";import"./index-832b6491.js";import{E as R}from"./el-button-8f1fae4c.js";import{_ as z}from"./Back-31df5cd7.js";import{A}from"./Citywalker-5c40621a.js";import{u as F}from"./userStore-afec8895.js";import{a as n}from"./function-call-d16eb335.js";import{_ as T}from"./_plugin-vue_export-helper-c27b6911.js";import"./use-touch-78a9110c.js";import"./index-965c857b.js";const H=""+new URL("iconthree-c9747d4d.png",import.meta.url).href,_=i=>(N("data-v-795a7ab4"),i=i(),O(),i),L=D('<div class="clouds" data-v-795a7ab4><div class="cloud" id="cloud1" data-v-795a7ab4></div><div class="cloud" id="cloud2" data-v-795a7ab4></div><div class="cloud" id="cloud3" data-v-795a7ab4></div><div class="cloud" id="cloud4" data-v-795a7ab4></div></div>',1),M=_(()=>s("img",{src:z},null,-1)),P=[M],W=_(()=>s("header",{class:"app-header"},[s("h1",{class:"text"},"CityWalker"),s("div",{class:"sub-header"},"漫游城")],-1)),j={class:"post-container"},q=_(()=>s("div",{class:"welcome-section"},[s("div",{class:"welcome-text"},[s("div",{class:"welcome-text-hello"},"Hello!"),s("div",null,"欢迎来到漫游城")]),s("img",{src:H,class:"welcome-icon"})],-1)),G={class:"button-container"},J={class:"route-button"},K={class:"route-button"},Q={class:"checkbox-container"},X=y({__name:"log_in",setup(i){const c=F(),g=new A,b=async()=>{if(!e.usernameOrEmail||!e.password){n({type:"danger",message:"昵称/邮箱和密码不能为空"});return}try{const o=await g.user.loginUsingPost({information:e.usernameOrEmail,password:e.password});o.data.code===0?(n({type:"success",message:"登录成功"}),console.log(o.data),c.setRole(o.data.result.role),c.setSessionId(o.data.result.sessionId),c.setSessionData(o.data.result.sessionData),m.push("/index")):(n({type:"danger",message:"登录失败，请检查您的昵称/邮箱和密码"}),console.log(o.data))}catch{n({type:"danger",message:"请求出错，请稍后再试"})}},x=()=>{console.log("寻找搭子"),m.push("/register")},w=()=>{m.back()},e=C({usernameOrEmail:"",password:""}),d=S(!0);return(o,t)=>{const v=R,k=U;return E(),V(B,null,[L,s("div",{class:"goback"},[s("div",{class:"back-icon",onClick:w},P)]),W,s("main",null,[s("div",j,[q,f(s("input",{type:"text","onUpdate:modelValue":t[0]||(t[0]=a=>l(e).usernameOrEmail=a),placeholder:"输入昵称/邮箱",class:"post-title-input"},null,512),[[h,l(e).usernameOrEmail]]),f(s("input",{type:"password","onUpdate:modelValue":t[1]||(t[1]=a=>l(e).password=a),placeholder:"输入密码",class:"post-title-input"},null,512),[[h,l(e).password]]),s("div",G,[s("div",J,[r(v,{class:"butt blue-button",onClick:b,style:{"font-size":"24px",padding:"25px 50px"}},{default:p(()=>[u("登录")]),_:1})]),s("div",K,[r(v,{class:"butt yellow-button",onClick:x,style:{"font-size":"24px",padding:"25px 30px"}},{default:p(()=>[u("没有账号？去注册")]),_:1})])])])]),s("footer",null,[s("div",Q,[r(k,{modelValue:l(d),"onUpdate:modelValue":t[2]||(t[2]=a=>I(d)?d.value=a:null),"checked-color":"#D68829",class:"checkbox_text"},{default:p(()=>[u("我已阅读并同意《漫游城社区用户协议》")]),_:1},8,["modelValue"])])])],64)}}});const cs=T(X,[["__scopeId","data-v-795a7ab4"]]);export{cs as default};
