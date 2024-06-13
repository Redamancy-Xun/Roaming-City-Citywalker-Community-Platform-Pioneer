import{i as I,o as G,n as z,s as j,e as a,d as S,q as H,C as J,G as V,M as W}from"./index-c336339f.js";import{I as Y,p as Q,a as X,P as Z}from"./index-965c857b.js";import{r as L,u as p}from"./use-route-86a7542e.js";import{L as ee}from"./index-d6d9cd4a.js";import{C as ne,D as te,E as oe,F as ae,c as P,r as le,t as D,w as A,e as O,a as y,n as q,G as ie,z as ce,s as se,u as re,q as de,p as ue,v as fe,I as F,d as ge,J as me,K as Be,j as he}from"./use-touch-78a9110c.js";const be=(e,s)=>{const t=I(),l=()=>{t.value=ae(e).height};return G(()=>{if(z(l),s)for(let i=1;i<=3;i++)setTimeout(l,100*i)}),ne(()=>z(l)),j([te,oe],l),t};function ve(e,s){const t=be(e,!0);return l=>a("div",{class:s("placeholder"),style:{height:t.value?`${t.value}px`:void 0}},[l()])}const[M,K]=P("action-bar"),U=Symbol(M),we={placeholder:Boolean,safeAreaInsetBottom:D};var ye=S({name:M,props:we,setup(e,{slots:s}){const t=I(),l=ve(t,K),{linkChildren:i}=le(U);i();const f=()=>{var B;return a("div",{ref:t,class:[K(),{"van-safe-area-bottom":e.safeAreaInsetBottom}]},[(B=s.default)==null?void 0:B.call(s)])};return()=>e.placeholder?l(f):f()}});const Ce=A(ye),[xe,w]=P("button"),Se=O({},L,{tag:y("button"),text:String,icon:String,type:y("default"),size:y("normal"),color:String,block:Boolean,plain:Boolean,round:Boolean,square:Boolean,loading:Boolean,hairline:Boolean,disabled:Boolean,iconPrefix:String,nativeType:y("button"),loadingSize:q,loadingText:String,loadingType:String,iconPosition:y("left")});var Pe=S({name:xe,props:Se,emits:["click"],setup(e,{emit:s,slots:t}){const l=p(),i=()=>t.loading?t.loading():a(ee,{size:e.loadingSize,type:e.loadingType,class:w("loading")},null),f=()=>{if(e.loading)return i();if(t.icon)return a("div",{class:w("icon")},[t.icon()]);if(e.icon)return a(Y,{name:e.icon,class:w("icon"),classPrefix:e.iconPrefix},null)},B=()=>{let o;if(e.loading?o=e.loadingText:o=t.default?t.default():e.text,o)return a("span",{class:w("text")},[o])},r=()=>{const{color:o,plain:h}=e;if(o){const g={color:h?o:"white"};return h||(g.background=o),o.includes("gradient")?g.border=0:g.borderColor=o,g}},b=o=>{e.loading?ce(o):e.disabled||(s("click",o),l())};return()=>{const{tag:o,type:h,size:g,block:C,round:k,plain:T,square:R,loading:_,disabled:n,hairline:c,nativeType:m,iconPosition:d}=e,v=[w([h,g,{plain:T,block:C,round:k,square:R,loading:_,disabled:n,hairline:c}]),{[ie]:c}];return a(o,{type:m,class:v,style:r(),disabled:n,onClick:b},{default:()=>[a("div",{class:w("content")},[d==="left"&&f(),B(),d==="right"&&f()])]})}}});const E=A(Pe),[ke,Te]=P("action-bar-button"),Re=O({},L,{type:String,text:String,icon:String,color:String,loading:Boolean,disabled:Boolean});var _e=S({name:ke,props:Re,setup(e,{slots:s}){const t=p(),{parent:l,index:i}=se(U),f=H(()=>{if(l){const r=l.children[i.value-1];return!(r&&"isButton"in r)}}),B=H(()=>{if(l){const r=l.children[i.value+1];return!(r&&"isButton"in r)}});return re({isButton:!0}),()=>{const{type:r,icon:b,text:o,color:h,loading:g,disabled:C}=e;return a(E,{class:Te([r,{last:B.value,first:f.value}]),size:"large",type:r,icon:b,color:h,loading:g,disabled:C,onClick:t},{default:()=>[s.default?s.default():o]})}}});const N=A(_e),[$e,u,x]=P("dialog"),De=O({},Q,{title:String,theme:String,width:q,message:[String,Function],callback:Function,allowHtml:Boolean,className:de,transition:y("van-dialog-bounce"),messageAlign:String,closeOnPopstate:D,showCancelButton:Boolean,cancelButtonText:String,cancelButtonColor:String,cancelButtonDisabled:Boolean,confirmButtonText:String,confirmButtonColor:String,confirmButtonDisabled:Boolean,showConfirmButton:D,closeOnClickOverlay:Boolean}),Ee=[...X,"transition","closeOnPopstate"];var Fe=S({name:$e,props:De,emits:["confirm","cancel","keydown","update:show"],setup(e,{emit:s,slots:t}){const l=I(),i=J({confirm:!1,cancel:!1}),f=n=>s("update:show",n),B=n=>{var c;f(!1),(c=e.callback)==null||c.call(e,n)},r=n=>()=>{e.show&&(s(n),e.beforeClose?(i[n]=!0,he(e.beforeClose,{args:[n],done(){B(n),i[n]=!1},canceled(){i[n]=!1}})):B(n))},b=r("cancel"),o=r("confirm"),h=W(n=>{var c,m;if(n.target!==((m=(c=l.value)==null?void 0:c.popupRef)==null?void 0:m.value))return;({Enter:e.showConfirmButton?o:F,Escape:e.showCancelButton?b:F})[n.key](),s("keydown",n)},["enter","esc"]),g=()=>{const n=t.title?t.title():e.title;if(n)return a("div",{class:u("header",{isolated:!e.message&&!t.default})},[n])},C=n=>{const{message:c,allowHtml:m,messageAlign:d}=e,v=u("message",{"has-title":n,[d]:d}),$=ge(c)?c():c;return m&&typeof $=="string"?a("div",{class:v,innerHTML:$},null):a("div",{class:v},[$])},k=()=>{if(t.default)return a("div",{class:u("content")},[t.default()]);const{title:n,message:c,allowHtml:m}=e;if(c){const d=!!(n||t.title);return a("div",{key:m?1:0,class:u("content",{isolated:!d})},[C(d)])}},T=()=>a("div",{class:[Be,u("footer")]},[e.showCancelButton&&a(E,{size:"large",text:e.cancelButtonText||x("cancel"),class:u("cancel"),style:{color:e.cancelButtonColor},loading:i.cancel,disabled:e.cancelButtonDisabled,onClick:b},null),e.showConfirmButton&&a(E,{size:"large",text:e.confirmButtonText||x("confirm"),class:[u("confirm"),{[me]:e.showCancelButton}],style:{color:e.confirmButtonColor},loading:i.confirm,disabled:e.confirmButtonDisabled,onClick:o},null)]),R=()=>a(Ce,{class:u("footer")},{default:()=>[e.showCancelButton&&a(N,{type:"warning",text:e.cancelButtonText||x("cancel"),class:u("cancel"),color:e.cancelButtonColor,loading:i.cancel,disabled:e.cancelButtonDisabled,onClick:b},null),e.showConfirmButton&&a(N,{type:"danger",text:e.confirmButtonText||x("confirm"),class:u("confirm"),color:e.confirmButtonColor,loading:i.confirm,disabled:e.confirmButtonDisabled,onClick:o},null)]}),_=()=>t.footer?t.footer():e.theme==="round-button"?R():T();return()=>{const{width:n,title:c,theme:m,message:d,className:v}=e;return a(Z,V({ref:l,role:"dialog",class:[u([m]),v],style:{width:fe(n)},tabindex:0,"aria-labelledby":c||d,onKeydown:h,"onUpdate:show":f},ue(e,Ee)),{default:()=>[g(),k(),_()]})}}});export{Fe as s};
