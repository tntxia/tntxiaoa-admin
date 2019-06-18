(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('user_edit',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let id = router.getParams().id;
	
	new Vue({
		el: '#form',
		data: {
			form: {
				name: null,
				name_en: null,
				sex: null,
				workj: null,
				department_id: null,
				worktel: null,
				ipbind: null,
				ip: null
			},
			restrainList: [],
			positionList: [],
			departmentList: []
			
		},
		mounted() {
			let me = this;
			http.post({
				url:'user!detail.do',
				data:{
					id
				}
			}).then(res=>{
				this.form = res;
				me.loadRestrainList();
				me.loadPositionList();
				me.loadDepartmentList();
			});
		},
		methods: {
			loadDepartmentList() {
				let me = this;
				http.post({
					url: 'dept!listAll.do'
				}).then(res=> {
					me.departmentList = res;
				})
			},
			loadPositionList() {
				let me = this;
				http.post({
					url: 'position!listAll.do'
				}).then(res=> {
					me.positionList = res;
				})
			},
			loadRestrainList() {
				let me = this;
				http.post({
					url: 'restrain!listAll.do'
				}).then(res=> {
					me.restrainList = res;
				})
			},
			sub() {
				let params = this.form;
				debugger
				http.post({
					url:'user!update.do',
					data:params
				}).then(res=>{
					debugger
					
					if(res.success){
						// router.goRoute("user");
					}
				})
			}
		}
	})
	
};
return module.exports;});