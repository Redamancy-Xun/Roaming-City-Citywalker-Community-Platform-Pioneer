import{d as C,e as l,A as F,B as M,F as ce,i as z,m as N,v as q,E as U,z as re,a as de,c as me,b as K,u as J,n as Q,w,p as ve,h as fe}from"./index-7421df47.js";import{c as ge,s as be}from"./index-3fb4e60e.js";import{_ as he}from"./_plugin-vue_export-helper-c27b6911.js";import{c as I,e as ae,m as _e,n as b,t as h,i as A,z as x,w as y,d as Se,C as le,f as Ve,h as Pe,E as we,A as k,j as X,y as ke,H as Z,F as ee,G as xe,p as H,r as Ce,I as Ie,L as ye}from"./index-dcd1bf9c.js";import{r as Te,u as $e}from"./use-route-2e53a763.js";import{I as te,u as Be}from"./use-scope-id-02176d93.js";const[Le,S]=I("cell"),Ee={tag:_e("div"),icon:String,size:String,title:b,value:b,label:b,center:Boolean,isLink:Boolean,border:h,iconPrefix:String,valueClass:A,labelClass:A,titleClass:A,titleStyle:null,arrowDirection:String,required:{type:[Boolean,String],default:null},clickable:{type:Boolean,default:null}},Ne=ae({},Ee,Te);var Re=C({name:Le,props:Ne,setup(e,{slots:n}){const o=$e(),r=()=>{if(n.label||x(e.label))return l("div",{class:[S("label"),e.labelClass]},[n.label?n.label():e.label])},a=()=>{var u;if(n.title||x(e.title)){const g=(u=n.title)==null?void 0:u.call(n);return Array.isArray(g)&&g.length===0?void 0:l("div",{class:[S("title"),e.titleClass],style:e.titleStyle},[g||l("span",null,[e.title]),r()])}},s=()=>{const u=n.value||n.default;if(u||x(e.value))return l("div",{class:[S("value"),e.valueClass]},[u?u():l("span",null,[e.value])])},i=()=>{if(n.icon)return n.icon();if(e.icon)return l(te,{name:e.icon,class:S("left-icon"),classPrefix:e.iconPrefix},null)},f=()=>{if(n["right-icon"])return n["right-icon"]();if(e.isLink){const u=e.arrowDirection&&e.arrowDirection!=="right"?`arrow-${e.arrowDirection}`:"arrow";return l(te,{name:u,class:S("right-icon")},null)}};return()=>{var u;const{tag:g,size:d,center:G,border:T,isLink:V,required:O}=e,P=(u=e.clickable)!=null?u:V,$={center:G,required:!!O,clickable:P,borderless:!T};return d&&($[d]=!!d),l(g,{class:S($),role:P?"button":void 0,tabindex:P?0:void 0,onClick:o},{default:()=>{var _;return[i(),a(),s(),f(),(_=n.extra)==null?void 0:_.call(n)]}})}}});const De=y(Re),[ie,Fe]=I("radio-group"),Ae={shape:String,disabled:Boolean,iconSize:b,direction:String,modelValue:A,checkedColor:String},oe=Symbol(ie);var Me=C({name:ie,props:Ae,emits:["change","update:modelValue"],setup(e,{emit:n,slots:o}){const{linkChildren:r}=Se(oe),a=s=>n("update:modelValue",s);return F(()=>e.modelValue,s=>n("change",s)),r({props:e,updateValue:a}),le(()=>e.modelValue),()=>{var s;return l("div",{class:Fe([e.direction]),role:"radiogroup"},[(s=o.default)==null?void 0:s.call(o)])}}});const ze=y(Me),Ge=ae({},ge,{shape:String}),[Oe,qe]=I("radio");var Ue=C({name:Oe,props:Ge,emits:["update:modelValue"],setup(e,{emit:n,slots:o}){const{parent:r}=Ve(oe),a=()=>(r?r.props.modelValue:e.modelValue)===e.name,s=()=>{r?r.updateValue(e.name):n("update:modelValue",e.name)};return()=>l(be,M({bem:qe,role:"radio",parent:r,checked:a(),onToggle:s},e),Pe(o,["default","icon"]))}});const He=y(Ue),[Ke,ne]=I("cell-group"),We={title:String,inset:Boolean,border:h};var je=C({name:Ke,inheritAttrs:!1,props:We,setup(e,{slots:n,attrs:o}){const r=()=>{var s;return l("div",M({class:[ne({inset:e.inset}),{[we]:e.border&&!e.inset}]},o,Be()),[(s=n.default)==null?void 0:s.call(n)])},a=()=>l("div",{class:ne("title",{inset:e.inset})},[n.title?n.title():e.title]);return()=>e.title||n.title?l(ce,null,[a(),r()]):r()}});const Ye=y(je),[pe,R]=I("stepper"),Je=200,D=(e,n)=>String(e)===String(n),Qe={min:k(1),max:k(1/0),name:k(""),step:k(1),theme:String,integer:Boolean,disabled:Boolean,showPlus:h,showMinus:h,showInput:h,longPress:h,autoFixed:h,allowEmpty:Boolean,modelValue:b,inputWidth:b,buttonSize:b,placeholder:String,disablePlus:Boolean,disableMinus:Boolean,disableInput:Boolean,beforeChange:Function,defaultValue:k(1),decimalLength:b};var Xe=C({name:pe,props:Qe,emits:["plus","blur","minus","focus","change","overlimit","update:modelValue"],setup(e,{emit:n}){const o=(t,c=!0)=>{const{min:m,max:L,allowEmpty:v,decimalLength:E}=e;return v&&t===""||(t=ee(String(t),!e.integer),t=t===""?0:+t,t=Number.isNaN(t)?+m:t,t=c?Math.max(Math.min(+L,t),+m):t,x(E)&&(t=t.toFixed(+E))),t},r=()=>{var t;const c=(t=e.modelValue)!=null?t:e.defaultValue,m=o(c);return D(m,e.modelValue)||n("update:modelValue",m),m};let a;const s=z(),i=z(r()),f=N(()=>e.disabled||e.disableMinus||+i.value<=+e.min),u=N(()=>e.disabled||e.disablePlus||+i.value>=+e.max),g=N(()=>({width:X(e.inputWidth),height:X(e.buttonSize)})),d=N(()=>ke(e.buttonSize)),G=()=>{const t=o(i.value);D(t,i.value)||(i.value=t)},T=t=>{e.beforeChange?Ce(e.beforeChange,{args:[t],done(){i.value=t}}):i.value=t},V=()=>{if(a==="plus"&&u.value||a==="minus"&&f.value){n("overlimit",a);return}const t=a==="minus"?-e.step:+e.step,c=o(Ie(+i.value,t));T(c),n(a)},O=t=>{const c=t.target,{value:m}=c,{decimalLength:L}=e;let v=ee(String(m),!e.integer);if(x(L)&&v.includes(".")){const p=v.split(".");v=`${p[0]}.${p[1].slice(0,+L)}`}e.beforeChange?c.value=String(i.value):D(m,v)||(c.value=v);const E=v===String(+v);T(E?+v:v)},P=t=>{var c;e.disableInput?(c=s.value)==null||c.blur():n("focus",t)},$=t=>{const c=t.target,m=o(c.value,e.autoFixed);c.value=String(m),i.value=m,re(()=>{n("blur",t),xe()})};let _,B;const W=()=>{B=setTimeout(()=>{V(),W()},Je)},ue=()=>{e.longPress&&(_=!1,clearTimeout(B),B=setTimeout(()=>{_=!0,V(),W()},ye))},j=t=>{e.longPress&&(clearTimeout(B),_&&H(t))},se=t=>{e.disableInput&&H(t)},Y=t=>({onClick:c=>{H(c),a=t,V()},onTouchstartPassive:()=>{a=t,ue()},onTouchend:j,onTouchcancel:j});return F(()=>[e.max,e.min,e.integer,e.decimalLength],G),F(()=>e.modelValue,t=>{D(t,i.value)||(i.value=o(t))}),F(i,t=>{n("update:modelValue",t),n("change",t,{name:e.name})}),le(()=>e.modelValue),()=>l("div",{role:"group",class:R([e.theme])},[q(l("button",M({type:"button",style:d.value,class:[R("minus",{disabled:f.value}),{[Z]:!f.value}],"aria-disabled":f.value||void 0},Y("minus")),null),[[U,e.showMinus]]),q(l("input",{ref:s,type:e.integer?"tel":"text",role:"spinbutton",class:R("input"),value:i.value,style:g.value,disabled:e.disabled,readonly:e.disableInput,inputmode:e.integer?"numeric":"decimal",placeholder:e.placeholder,"aria-valuemax":e.max,"aria-valuemin":e.min,"aria-valuenow":i.value,onBlur:$,onInput:O,onFocus:P,onMousedown:se},null),[[U,e.showInput]]),q(l("button",M({type:"button",style:d.value,class:[R("plus",{disabled:u.value}),{[Z]:!u.value}],"aria-disabled":u.value||void 0},Y("plus")),null),[[U,e.showPlus]])])}});const Ze=y(Xe);const et=e=>(ve("data-v-089b8acc"),e=e(),fe(),e),tt=et(()=>K("div",null,"繁花路线",-1)),nt={class:"custom-stepper"},at={__name:"purchase",setup(e){const n=z(1),o=z("1");return(r,a)=>{const s=Ze,i=He,f=De,u=Ye,g=ze;return de(),me("main",null,[tt,K("div",nt,[l(s,{modelValue:J(n),"onUpdate:modelValue":a[0]||(a[0]=d=>Q(n)?n.value=d:null),integer:"",theme:"round"},null,8,["modelValue"])]),K("div",null,[l(g,{modelValue:J(o),"onUpdate:modelValue":a[4]||(a[4]=d=>Q(o)?o.value=d:null)},{default:w(()=>[l(u,{inset:""},{default:w(()=>[l(f,{title:"单选框 1",clickable:"",onClick:a[1]||(a[1]=d=>o.value="1")},{"right-icon":w(()=>[l(i,{name:"1"})]),_:1}),l(f,{title:"单选框 2",clickable:"",onClick:a[2]||(a[2]=d=>o.value="2")},{"right-icon":w(()=>[l(i,{name:"2"})]),_:1}),l(f,{title:"单选框 3",clickable:"",onClick:a[3]||(a[3]=d=>o.value="3")},{"right-icon":w(()=>[l(i,{name:"3"})]),_:1})]),_:1})]),_:1},8,["modelValue"])])])}}},rt=he(at,[["__scopeId","data-v-089b8acc"]]);export{rt as default};