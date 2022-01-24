
var isReady=false;var onReadyCallbacks=[];
var isServiceReady=false;var onServiceReadyCallbacks=[];
var __uniConfig = {"pages":["pages/index/index","pages/shop/shop","pages/user/user","pages/login/login","pages/message/message","pages/user/settings","pages/user/user-info","pages/order/advance-payment","pages/order/pay","pages/address/address","pages/order/remark","pages/order/order"],"window":{"navigationBarTextStyle":"black","navigationBarBackgroundColor":"#FFD000","backgroundColor":"#FFFFF"},"tabBar":{"color":"#7A7E83","selectedColor":"#0c00c6","borderStyle":"black","backgroundColor":"#ffffff","list":[{"pagePath":"pages/index/index","iconPath":"static/tabbar/index.png","selectedIconPath":"static/tabbar/index-1.png","text":"主页"},{"pagePath":"pages/message/message","iconPath":"static/tabbar/service.png","selectedIconPath":"static/tabbar/service-1.png","text":"消息"},{"pagePath":"pages/user/user","iconPath":"static/tabbar/profile.png","selectedIconPath":"static/tabbar/profile-1.png","text":"个人中心"}]},"nvueCompiler":"uni-app","nvueStyleCompiler":"uni-app","renderer":"auto","splashscreen":{"alwaysShowBeforeRender":true,"autoclose":false},"appname":"Uni_SmartCity","compilerVersion":"3.3.5","entryPagePath":"pages/index/index","networkTimeout":{"request":60000,"connectSocket":60000,"uploadFile":60000,"downloadFile":60000}};
var __uniRoutes = [{"path":"/pages/index/index","meta":{"isQuit":true,"isTabBar":true},"window":{"navigationStyle":"custom"}},{"path":"/pages/shop/shop","meta":{},"window":{"navigationStyle":"custom","navigationBarTextStyle":"white","enablePullDownRefresh":false}},{"path":"/pages/user/user","meta":{"isQuit":true,"isTabBar":true},"window":{"navigationStyle":"custom","enablePullDownRefresh":false}},{"path":"/pages/login/login","meta":{},"window":{"navigationStyle":"custom","enablePullDownRefresh":false}},{"path":"/pages/message/message","meta":{"isQuit":true,"isTabBar":true},"window":{"navigationBarTitleText":"消息","enablePullDownRefresh":false}},{"path":"/pages/user/settings","meta":{},"window":{"navigationBarTitleText":"设置","enablePullDownRefresh":false}},{"path":"/pages/user/user-info","meta":{},"window":{"navigationBarTitleText":"个人信息","enablePullDownRefresh":false}},{"path":"/pages/order/advance-payment","meta":{},"window":{"navigationBarTitleText":"提交订单","enablePullDownRefresh":false}},{"path":"/pages/order/pay","meta":{},"window":{"navigationBarTitleText":"支付订单","enablePullDownRefresh":false}},{"path":"/pages/address/address","meta":{},"window":{"navigationBarTitleText":"我的收货地址","enablePullDownRefresh":false,"titleNView":{"buttons":[{"float":"right","fontSize":"30rpx","text":"删除"}]}}},{"path":"/pages/order/remark","meta":{},"window":{"navigationBarTitleText":"添加备注","enablePullDownRefresh":false,"navigationBarBackgroundColor":"#FFFFFF","titleNView":{"buttons":[{"float":"right","fontSize":"30rpx","text":"完成"}]}}},{"path":"/pages/order/order","meta":{},"window":{"navigationBarTitleText":"我的订单","enablePullDownRefresh":false}}];
__uniConfig.onReady=function(callback){if(__uniConfig.ready){callback()}else{onReadyCallbacks.push(callback)}};Object.defineProperty(__uniConfig,"ready",{get:function(){return isReady},set:function(val){isReady=val;if(!isReady){return}const callbacks=onReadyCallbacks.slice(0);onReadyCallbacks.length=0;callbacks.forEach(function(callback){callback()})}});
__uniConfig.onServiceReady=function(callback){if(__uniConfig.serviceReady){callback()}else{onServiceReadyCallbacks.push(callback)}};Object.defineProperty(__uniConfig,"serviceReady",{get:function(){return isServiceReady},set:function(val){isServiceReady=val;if(!isServiceReady){return}const callbacks=onServiceReadyCallbacks.slice(0);onServiceReadyCallbacks.length=0;callbacks.forEach(function(callback){callback()})}});
service.register("uni-app-config",{create(a,b,c){if(!__uniConfig.viewport){var d=b.weex.config.env.scale,e=b.weex.config.env.deviceWidth,f=Math.ceil(e/d);Object.assign(__uniConfig,{viewport:f,defaultFontSize:Math.round(f/20)})}return{instance:{__uniConfig:__uniConfig,__uniRoutes:__uniRoutes,global:void 0,window:void 0,document:void 0,frames:void 0,self:void 0,location:void 0,navigator:void 0,localStorage:void 0,history:void 0,Caches:void 0,screen:void 0,alert:void 0,confirm:void 0,prompt:void 0,fetch:void 0,XMLHttpRequest:void 0,WebSocket:void 0,webkit:void 0,print:void 0}}}});
