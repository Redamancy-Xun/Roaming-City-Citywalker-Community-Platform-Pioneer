import{_ as F}from"./Back-31df5cd7.js";/* empty css                                                                   */import{i as d,z as M,q as T,a as y,c as x,b as t,t as a,e as f,w as v,F as S,k as j,m as D,f as k,l as i,x as C,A as G,r as z,p as B,h as $,y as L,o as O}from"./index-c336339f.js";import{_ as q}from"./_plugin-vue_export-helper-c27b6911.js";/* empty css              *//* empty css              *//* empty css              */import{_ as H}from"./arrow_right-40db7ce5.js";import{A as N}from"./Citywalker-5c40621a.js";import{u as P}from"./userStore-afec8895.js";import{S as J,a as K}from"./index-0421c015.js";import{I as Q}from"./index-fcfdd17c.js";import"./use-touch-78a9110c.js";import"./index-965c857b.js";import"./index-d6d9cd4a.js";const o=e=>(B("data-v-287efcd1"),e=e(),$(),e),W={class:"container"},X={class:"title"},Y={class:"content"},Z=["src","onClick"],tt={class:"route"},st=o(()=>t("img",{src:H},null,-1)),at=o(()=>t("span",{class:"span_title"},"路线概括",-1)),ot={class:"content"},et=o(()=>t("span",{class:"span_title"},"行程亮点",-1)),nt={class:"content"},it={class:"price-container"},ct={class:"price-button"},dt={style:{"font-size":"24px"}},lt=o(()=>t("span",{class:"span_titles"},"起点:",-1)),_t={class:"content"},rt={style:{"font-size":"24px"}},pt=o(()=>t("span",{class:"span_titles"},"人数:",-1)),ut={class:"content"},ht={style:{"font-size":"24px"}},mt=o(()=>t("span",{class:"span_titles"},"时间安排:",-1)),wt={class:"content"},vt=o(()=>t("button",{class:"invite-button"},"购买",-1)),yt=[vt],ft=o(()=>t("button",{class:"invite-button"},"加入购物车",-1)),gt=[ft],It=o(()=>t("span",{class:"span_title"},"费用包括",-1)),xt={class:"content"},bt=o(()=>t("span",{class:"span_title"},"费用不包括",-1)),kt={class:"content"},Ct=o(()=>t("span",{class:"span_title"},"退订退款",-1)),St={class:"content"},zt=o(()=>t("span",{class:"span_title"},"地图",-1)),Bt={class:"map-container"},$t=["src"],qt=o(()=>t("span",{class:"span_title"},"关于citywalk",-1)),Nt={class:"content"},Pt=o(()=>t("span",{class:"span_title"},"行前提示",-1)),At={class:"content"},Et={__name:"findway_detail_card",props:{waydata:{type:Object,required:!0}},setup(e){const s=e;new N,P();const h=(r,n)=>{z.push({path:"/purchase",query:{action:r,routeId:s.waydata.routeId}})};d("");const m={1:"繁花路线",2:"历史梧桐",3:"小资愚园",4:"甜爱多伦",5:"不夜沪上",6:"购物天堂",7:"轮渡漫游",8:"都市风光",9:"别样外滩",10:"疗愈滨江",11:"巨富长线",12:"淮海中路"},l=d("normal-font");M(()=>{s.waydata&&s.waydata.price&&s.waydata.price.length>12?l.value="small-font":l.value="normal-font"});const g=T(()=>m[s.waydata.routeId]||"未知路线"),I=()=>{_.value=0,w.value=!0};d([{id:1,author:"张三",content:"很棒的分享！",avatar:"path_to_avatar1.png"},{id:2,author:"李四",content:"我也去过那里，景色很美。",avatar:"path_to_avatar1.png"}]);const c=r=>{_.value=r,u.value=!0},u=d(!1),w=d(!1),_=d(0);console.log(s.waydata);const E=r=>{_.value=r};return(r,n)=>{const R=J,U=K,b=Q;return y(),x("div",W,[t("header",null,[t("span",X,a(s.waydata.theme),1)]),t("main",null,[t("div",Y,a(s.waydata.subtitle),1),f(U,{"lazy-render":""},{default:v(()=>[(y(!0),x(S,null,j(s.waydata.photo,(p,V)=>(y(),D(R,{key:p,class:"zoom"},{default:v(()=>[t("img",{src:p,class:"img",onClick:()=>c(V)},null,8,Z)]),_:2},1024))),128))]),_:1}),f(b,{show:i(u),"onUpdate:show":n[0]||(n[0]=p=>C(u)?u.value=p:null),images:s.waydata.photo,"start-position":i(_),onChange:E},{index:v(()=>[k("第"+a(i(_)+1)+"页",1)]),_:1},8,["show","images","start-position"]),t("div",tt,[t("span",null,a(i(g)),1),st]),t("div",null,[at,t("div",ot,a(s.waydata.summary),1)]),t("div",null,[et,t("div",nt,a(s.waydata.highlight),1)]),t("div",it,[t("button",{class:G(["price-button",i(l)])},a(s.waydata.price),3),t("button",ct,a(s.waydata.time),1)]),t("div",dt,[lt,t("span",_t,a(s.waydata.start),1)]),t("div",rt,[pt,t("span",ut,a(s.waydata.people),1)]),t("div",ht,[mt,t("span",wt,a(s.waydata.schedule),1)]),t("div",{class:"button-container",onClick:n[1]||(n[1]=()=>h(0))},yt),t("div",{class:"button-container",onClick:n[2]||(n[2]=()=>h(1))},gt),t("div",null,[It,t("div",xt,a(s.waydata.costInclude),1)]),t("div",null,[bt,t("div",kt,a(s.waydata.costExclude),1)]),t("div",null,[Ct,t("div",St,a(s.waydata.refund),1)]),t("div",null,[zt,t("div",Bt,[t("img",{src:s.waydata.map,alt:"路线地图",class:"map-image",onClick:I},null,8,$t)])]),f(b,{show:i(w),"onUpdate:show":n[3]||(n[3]=p=>C(w)?w.value=p:null),images:[s.waydata.map]},{index:v(()=>[k("第"+a(i(_)+1)+"页",1)]),_:1},8,["show","images"]),t("div",null,[qt,t("div",Nt,a(s.waydata.other),1)]),t("div",null,[Pt,t("div",At,a(s.waydata.remind),1)])])])}}},Rt=q(Et,[["__scopeId","data-v-287efcd1"]]);const A=e=>(B("data-v-203fc9a5"),e=e(),$(),e),Ut=A(()=>t("img",{src:F},null,-1)),Vt=[Ut],Ft=A(()=>t("span",null,"找路线",-1)),Mt={class:"image-container"},Tt={__name:"findway_detail",setup(e){const s=new N,h=P(),m=L(),l=d([]),g=()=>{z.back()},I=async()=>{try{const c=await s.route.getRouteByIdUsingGet({routeId:m.query.id},{session:h.sessionId});console.log(c.data),l.value=c.data.result}catch(c){console.error("获取帖子失败",c)}};return O(()=>{I(),console.log(m.query.id)}),(c,u)=>(y(),x(S,null,[t("header",null,[t("div",{class:"back-icon",onClick:g},Vt),Ft]),t("main",null,[t("div",Mt,[f(Rt,{waydata:i(l)},null,8,["waydata"])])])],64))}},as=q(Tt,[["__scopeId","data-v-203fc9a5"]]);export{as as default};