import{q as V,i as v,s as T,o as P,a as A,c as B,b as a,e as I,w as J,u as c,n as w,v as y,x as b,y as O,t as $,F as E,r as M,p as q,h as z}from"./index-7421df47.js";/* empty css              */import{s as W,a as j,U as G,A as H}from"./index-d662d2a4.js";/* empty css              */import{_ as K}from"./Back-64c2790a.js";import{_ as Q}from"./footer.vue_vue_type_script_setup_true_lang-fbe79d79.js";import{A as X}from"./Citywalker-1838a2bc.js";import{u as Y}from"./userStore-b1331fcf.js";import{_ as Z}from"./_plugin-vue_export-helper-c27b6911.js";import{s as tt,a as _}from"./function-call-eaca0cc0.js";import{w as D}from"./index-dcd1bf9c.js";import"./use-scope-id-02176d93.js";import"./index-05100a24.js";import"./use-touch-fbe6bb65.js";import"./index-1a41a3f5.js";import"./index-03c1dd54.js";import"./use-route-2e53a763.js";/* empty css                                                                   */D(W);D(tt);const et=""+new URL("addblue-a5410c32.svg",import.meta.url).href,ot=""+new URL("savedraft-84a5d356.svg",import.meta.url).href,st=""+new URL("submitpost-64dfcf1a.svg",import.meta.url).href,at=V({id:"draft",state:()=>({title:"",content:"",images:[],fileList:[]}),actions:{clearDraft(){this.title="",this.content="",this.images=[],this.fileList=[],localStorage.removeItem("draftTitle"),localStorage.removeItem("draftContent"),localStorage.removeItem("draftImages"),localStorage.removeItem("draftFileList")},setTitle(o){this.title=o},setContent(o){this.content=o},setImages(o){this.images=o},setFileList(o){this.fileList=o},saveDraft(o,p,u,n){this.setTitle(o),this.setContent(p),this.setImages(u),this.setFileList(n),localStorage.setItem("draftTitle",o),localStorage.setItem("draftContent",p),localStorage.setItem("draftImages",JSON.stringify(u)),localStorage.setItem("draftFileList",JSON.stringify(n))},loadDraft(){this.title=localStorage.getItem("draftTitle")||"",this.content=localStorage.getItem("draftContent")||"",this.images=JSON.parse(localStorage.getItem("draftImages")||"[]"),this.fileList=JSON.parse(localStorage.getItem("draftFileList")||"[]")}}});const x=o=>(q("data-v-f580b9c0"),o=o(),z(),o),nt=x(()=>a("img",{src:K},null,-1)),lt=[nt],rt={class:"uploader-container"},it=x(()=>a("div",{class:"upload-container"},[a("img",{src:et,class:"upload-icon"})],-1)),ct={class:"post-container"},mt={__name:"post",setup(o){const p=Y(),u=new X,n=v("添加路线"),d=v([]),r=v([]),f=v(!1),h=v(0),L=[{name:"繁花路线",icon:"star-o",routeId:2},{name:"别样外滩",icon:"star-o",routeId:3},{name:"历史梧桐",icon:"star-o",routeId:4},{name:"不夜沪上",icon:"star-o",routeId:5},{name:"轮渡漫游",icon:"star-o",routeId:6},{name:"取消",icon:"star-o",routeId:0}],C=e=>{f.value=!1,e.routeId!==0?(n.value=e.name,h.value=e.routeId):(n.value="添加路线",h.value=0),console.log(n.value),console.log(h.value)},U=e=>{const t=e.index;return t!==void 0&&r.value.splice(t,1),console.log(r.value),!0},k=async e=>{console.log(e),console.log(e.file);try{const t=new FormData;t.append("file",e.file);const l={"content-type":"multipart/form-data",session:p.sessionId},g=await u.uploadImage.uploadImageUsingPost(t,l);r.value.push(g.data.result),console.log(r.value)}catch(t){console.error("上传图片时出错:",t),_({type:"danger",message:"上传图片时出错"})}},F=()=>{M.back()},s=T({title:"",content:""}),N=async()=>{if(!s.title||!s.content)try{await j({title:"警告",message:"标题和正文不能为空。仍要继续发布吗？"}),S()}catch{_({type:"warning",message:"发布已取消"})}else S()},S=async()=>{const e=new FormData;e.append("title",s.title),e.append("content",s.content),e.append("routeId",h.value),r.value.forEach((l,g)=>{e.append(`images[${g}]`,l)});const t={session:p.sessionId};try{const l=await u.post.createPostUsingPost(e,t);console.log(l),s.title="",s.content="",n.value="添加路线",r.value=[],d.value=[],m.clearDraft(),_({type:"success",message:"发布成功"})}catch(l){console.error(l),_({type:"danger",message:"发布失败"})}},m=at();P(()=>{m.loadDraft(),s.title=m.title,s.content=m.content,r.value=m.images,d.value=m.fileList});const R=()=>{m.saveDraft(s.title,s.content,r.value,d.value),_({type:"success",message:"草稿已保存"})};return(e,t)=>{const l=G,g=H;return A(),B(E,null,[a("header",null,[a("div",{class:"back-icon",onClick:F},lt)]),a("div",rt,[I(l,{modelValue:c(d),"onUpdate:modelValue":t[0]||(t[0]=i=>w(d)?d.value=i:null),multiple:"","max-count":9,"after-read":k,"before-delete":U},{default:J(()=>[it]),_:1},8,["modelValue"])]),a("main",null,[a("div",ct,[y(a("input",{type:"text","onUpdate:modelValue":t[1]||(t[1]=i=>c(s).title=i),placeholder:"输入标题",class:"post-title-input"},null,512),[[b,c(s).title]]),y(a("textarea",{"onUpdate:modelValue":t[2]||(t[2]=i=>c(s).content=i),placeholder:"输入正文",class:"post-content-textarea"},null,512),[[b,c(s).content]]),a("button",{class:O(["submit-button",{"selected-route":c(n)!=="添加路线"}]),onClick:t[3]||(t[3]=i=>f.value=!0)},$(c(n)),3),I(g,{show:c(f),"onUpdate:show":t[4]||(t[4]=i=>w(f)?f.value=i:null),actions:L,onSelect:C},null,8,["show"])]),a("div",{class:"buttons-container"},[a("img",{src:ot,class:"save-draft-icon",onClick:R}),a("img",{src:st,class:"submit-post-icon",onClick:N})])]),I(Q)],64)}}},kt=Z(mt,[["__scopeId","data-v-f580b9c0"]]);export{kt as default};